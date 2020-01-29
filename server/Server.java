package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import exceptions.*;
import game.*;
import protocol.ProtocolMessages;

/**
 * Server TUI for Abalone
 * 
 * Intended Functionality: interactively set up & monitor a new server
 */
public class Server implements Runnable {
	private Game g;

	/** The ServerSocket of this Server */
	private ServerSocket ssock;

	/** List of ClientHandlers, one for each connected client */
	private List<ClientHandler> clients;

	/** List of ClientHandlers, one for each connected client */
	private List<Room> rooms = new ArrayList<Room>(10);

	/** Next client number, increasing for every new connection */
	private int next_client_no;

	/** Incrementer for turns **/
	public static int turns = 0;

	/** The view of this Server */
	private ServerTUI view;

	/**
	 * Constructs a new server. Initializes the clients list, the view and the
	 * next_client_no.
	 */
	public Server() {
		clients = new ArrayList<>();
		view = new ServerTUI();
		next_client_no = 1;
	}

	/**
	 * Opens a new socket by calling {@link #setup()} and starts a new ClientHandler
	 * for every connecting client.
	 * 
	 * If {@link #setup()} throws a ExitProgram exception, stop the program. In case
	 * of any other errors, ask the user whether the setup should be ran again to
	 * open a new socket.
	 */
	public void run() {
		boolean openNewSocket = true;
		while (openNewSocket) {
			try {
				// Sets up the Abalone application
				setup();

				while (true) {
					Socket sock = ssock.accept();
					String name = "Client " + String.format("%02d", next_client_no++);
					view.showMessage("New client [" + name + "] connected!");
					ClientHandler handler = new ClientHandler(sock, this, name);
					new Thread(handler).start();
					clients.add(handler);
				}

			} catch (ExitProgram e) {
				// If setup() throws an ExitProgram exception,
				// stop the program.
				openNewSocket = false;
			} catch (IOException e) {
				System.out.println("A server IO error occurred: " + e.getMessage());

				if (!view.getBoolean("Do you want to open a new socket?")) {
					openNewSocket = false;
				}
			}
		}
		view.showMessage("See you later!");
	}

	/**
	 * Sets up 10 rooms using {@link #setupGame()} and opens a new ServerSocket
	 * at localhost on a user-defined port.
	 * 
	 * The user is asked to input a port, after which a socket is attempted to be
	 * opened. If the attempt succeeds, the method ends, If the attempt fails, the
	 * user decides to try again, after which an ExitProgram exception is thrown or
	 * a new port is entered.
	 * 
	 * @throws ExitProgram if a connection can not be created on the given port and
	 *                     the user decides to exit the program.
	 * @ensures a serverSocket is opened.
	 */
	public void setup() throws ExitProgram {
		// Initializes the 10 rooms.
		setupGame();
		ssock = null;
		while (ssock == null) {
			int port = view.getInt("Enter the server port.");

			// try to open a new ServerSocket
			try {
				view.showMessage("Attempting to open a socket at localhost " + "on port number: " + port + "...");
				ssock = new ServerSocket(port, 0, InetAddress.getByName("localhost"));

				view.showMessage("Server started at port " + port);
			} catch (IOException e) {
				view.showMessage(
						"ERROR: could not create a socket on " + "130.89.173.62" + " and port number: " + port + ".");

				if (!view.getBoolean("Do you want to enter a different port number??(y/n)")) {
					throw new ExitProgram("User indicated to exit program.");
				}
			}
		}
	}

	/**
	 * Creates 10 rooms on the server.
	 */
	public void setupGame() {
		for (int i = 1; i <= 10; i++) {
			rooms.add(new Room(i + ""));
		}
	}

	/**
	 * Used to send a message to all the clients connected to a server
	 * @param msg
	 * @throws IOException
	 */
	public void sendMessagetoAll(String msg) throws IOException {
		for (ClientHandler ch : clients) {
			ch.sendMessage(msg);
		}
	}
	/**
	 * Used to send a message to all the clients in a room about the start of a game or the party leader's teammate
	 * @param msg
	 * @param cl
	 * @throws IOException
	 */
	public void sendMessagetoRoom(String msg, ClientHandler cl) throws IOException {
		for (Room r : rooms) {
			if (r.getPlayerList().contains(cl)) {
				for (ClientHandler ch : r.getPlayerList()) {
					ch.sendMessage(msg);
				}
				return;
			}
		}
	}

	/**
	 * Returns a String to be sent as a response to a Client CONNECT request,
	 * including the name of the hotel: ProtocolMessages.CONNECT +
	 * ProtocolMessages.DELIMITER + player name);
	 * 
	 * @return String to be sent to client as a handshake response.
	 */
	public String getConnection(String name) {
		return (String.valueOf(ProtocolMessages.CONNECT) + ProtocolMessages.DELIMITER + name);
	}
	
	/**
	 * Removes a clientHandler from the client list if the client chooses to
	 * disconnect.
	 * 
	 * @requires client != null
	 */
	public String removeClient(ClientHandler client) {
		this.clients.remove(client);
		return ProtocolMessages.DISCONNECT + ProtocolMessages.DELIMITER + client.getClientHandlerName();
	}

	/**
	 * Given the name of a room, the addPlayertoRoom command of the application is
	 * called. The result is returned as String and can be: - Parameter is wrong
	 * (name is null) - Add Player to the room failed (no room assigned) - Adding
	 * successful The first player to be added will be the party leader.
	 * 
	 * @requires guestName != null
	 * @param guestName Name of the guest
	 * @return textual result, to be shown to the user
	 */
	public synchronized String addPlayertoRoom(String roomnum, ClientHandler client) {
		if (roomnum.equals(null)) {
			return "The room does not exist";
		}
		for (Room r : rooms) {
			if (r.getRoomNum().equals(roomnum)) {
				r.addtoRoom(client);
				return (String.valueOf(ProtocolMessages.JOIN) + ProtocolMessages.DELIMITER + roomnum
						+ ProtocolMessages.DELIMITER + client.getClientHandlerName());
			}
		}
		return "The room does not exist";
	}

	/**
	 * Given the name of a player, the removePlayertoRoom command of the application
	 * is called. The result is returned as String and can be: - Parameter is wrong
	 * (name is null) - Removing successful.
	 * 
	 * @requires guestName != null
	 * @param guestName Name of the guest
	 * @return textual result, to be shown to the user
	 */
	public synchronized String removePlayerfromRoom(ClientHandler client) {
		for (Room r : rooms) {
			for (ClientHandler cl : r.getPlayerList()) {
				if (cl.equals(client)) {
					r.getPlayerList().remove(client);
					this.clients.remove(client);
					return (String.valueOf(ProtocolMessages.LEAVE) + ProtocolMessages.DELIMITER
							+ client.getClientHandlerName());
				}
			}
		}
		return "The player was not found";

	}

	/**
	 * All rooms are returned. The result is returned as String. The result shows
	 * the room number, status of the game, each players name
	 * 
	 * @return all rooms
	 */
	public String displayRooms() {
		String display = "";
		for (Room r : rooms) {
			display = display + String.valueOf(ProtocolMessages.ROOM) + ProtocolMessages.DELIMITER + r.getStatus()
					+ ProtocolMessages.DELIMITER + r.getRoomNum() + ProtocolMessages.DELIMITER + r.getNumPlayers()
					+ ProtocolMessages.DELIMITER;
			for (ClientHandler cl : r.getPlayerList()) {
				display = display + cl.getClientHandlerName() + ProtocolMessages.DELIMITER;
			}
			display = display + "\n";
		}
		return display;
	}

	/**
	 * Given the name of a player, the party leader will be assigned in a team with
	 * that player. The result is returned as String and can be: - Parameters are
	 * wrong (name is null) - Player has not been added to party leader's team.
	 * 
	 * @requires name != null
	 * @param name Name of the player
	 * @return textual result, to be shown to all players in the room
	 */
	public String leaderTeammate(String name) {
		for (Room r : rooms) {
			for (ClientHandler cl : r.getPlayerList()) {
				if (cl.getClientHandlerName().equals(name)) {
					r.leaderTeammate(cl);
					return String.valueOf(ProtocolMessages.ALL) + ProtocolMessages.DELIMITER + name;
				}
			}
			return "Player not found";
		}
		return null;

	}

	/**
	 * Starts the game when the party leader of a room decides to.
	 * 
	 * @return textual result of the number of players, and each players name
	 */
	public String startGame(ClientHandler cl) {
		for (Room r : rooms) {
			if (r.isLeader(cl) && r.getNumPlayers() > 1) {
				String display = "";
				r.startGame(r.getNumPlayers());
				display = display + String.valueOf(ProtocolMessages.START) + ProtocolMessages.DELIMITER
						+ r.getNumPlayers() + ProtocolMessages.DELIMITER;
				for (ClientHandler ch : r.getPlayerList()) {
					display = display + ch.getClientHandlerName() + ProtocolMessages.DELIMITER;
				}
				return display;
			}
		}
		return null;
	}

	/**
	 * Makes move for the given coordinate
	 * 
	 * @return the string representation of the Hotel
	 */
	/*
	 * public String makeMove(ClientHandler cl, String coords, int direction) {
	 * for(Room r: rooms) { if(r.getPlayerList().contains(cl)) { Marble m =
	 * r.getPlayer(cl).getMarble();
	 * r.getPlayer(cl).determineMove(r.getGame().getBoard()); } }
	 * //cl.getClientHandlerName()
	 * 
	 * }
	 */

	/**
	 * Increments the number of turns every time a move is made.s
	 */
	public void incturn() {
		++turns;
	}

	// ------------------ Main --------------------------

	/** Start a new Server */
	public static void main(String[] args) {
		Server server = new Server();
		System.out.println("Welcome to the Abalone Server! Starting...");
		new Thread(server).start();
	}
}