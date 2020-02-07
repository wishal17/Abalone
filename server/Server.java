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

	/** The ServerSocket of this Server */
	private ServerSocket ssock;

	/** List of ClientHandlers, one for each connected client */
	private List<ClientHandler> clients;

	/** List of rooms, one for each connected client */
	private List<Room> rooms = new ArrayList<Room>(10);

	/** The view of this Server */
	private ServerTUI view;

	/**
	 * Constructs a new server. Initializes the clients list, the view and the
	 * next_client_no.
	 */
	public Server() {
		clients = new ArrayList<>();
		view = new ServerTUI();
	}

	/**
	 * Opens a new socket by calling {@link #setup()} and starts a new ClientHandler
	 * for every connecting client. If {@link #setup()} throws a ExitProgram
	 * exception, stop the program. In case of any other errors, ask the user
	 * whether the setup should be ran again to open a new socket.
	 */
	public void run() {
		boolean openNewSocket = true;
		while (openNewSocket) {
			try {
				// Sets up the Abalone application
				setup();

				while (true) {
					Socket sock = ssock.accept();
					String name = sock.getInetAddress().getHostName();
					view.showMessage("New client connected!" + "\n");
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
	 * Sets up 10 rooms using {@link #setupGame()} and opens a new ServerSocket at
	 * localhost on a user-defined port.
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
			int port = view.getInt("Enter the server port: ");
			try {
				// try to open a new ServerSocket
				view.showMessage("Attempting to open a socket at localhost " + "on port number: " + port + "...\n");
				ssock = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
				view.showMessage("Server started at port " + port);
			} catch (IOException e) {
				view.showMessage("\nERROR: Invalid port number entered. Please enter a valid port number");

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
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void sendMessagetoAll(String msg) throws IOException {
		for (ClientHandler ch : clients) {
			ch.getOut().write(msg);
			ch.getOut().newLine();
			ch.getOut().flush();
		}
	}

	/**
	 * Used to send a message to all the clients in a room about the start of a game
	 * or the party leader's teammate
	 * 
	 * @param msg
	 * @param cl
	 * @throws IOException
	 */
	public void sendMessagetoRoom(String msg, ClientHandler cl) throws IOException {
		if (msg.charAt(0) != 'E') {
			for (Room r : rooms) {
				if (r.getPlayerList().contains(cl)) {
					for (ClientHandler ch : r.getPlayerList()) {
						ch.sendMessage(msg);
					}
					return;
				}
			}
		} else {
			cl.sendMessage(msg);
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
		for (ClientHandler c : clients) {
			if (c.getClientHandlerName().equals(name)) {
				return String.valueOf(ProtocolMessages.ERROR) + ProtocolMessages.DELIMITER + "NameTaken\n";
			}
		}
		return ProtocolMessages.CONNECT + ProtocolMessages.DELIMITER + name + "\n";
	}

	/**
	 * Removes a clientHandler from the client list if the client chooses to
	 * disconnect.
	 * 
	 * @requires client != null
	 */
	public String removeClient(ClientHandler client) {
		if (client.isinRoom() == true) {
			removePlayerfromRoom(client);
		}
		client.assignRoom(null);
		this.clients.remove(client);
		return String.valueOf(ProtocolMessages.DISCONNECT) + ProtocolMessages.DELIMITER + client.getClientHandlerName()
				+ "\n";
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
		int roomno;
		try {
			roomno = Integer.parseInt(roomnum);
			if (rooms.get(roomno - 1).getStatus().equals("Started")) {
				return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "RoomHasStarted\n";
			}
			if (roomno < 1 || roomno > 10) {
				return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "NotValidNumber\n";
			}
			if (rooms.get(roomno - 1).getPlayerList().size() == 4) {
				return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "Full\n";
			}
			if (rooms.get(roomno - 1).getPlayerList().contains(client)) {
				rooms.get(roomno - 1).removePlayer(client);
			}
			rooms.get(roomno - 1).addPlayer(client);
			client.assignRoom(rooms.get(roomno - 1));
			client.addedtoRoom();
			return (String.valueOf(ProtocolMessages.JOIN) + ProtocolMessages.DELIMITER + roomnum
					+ ProtocolMessages.DELIMITER + client.getClientHandlerName() + "\n");
		} catch (NumberFormatException e) {
			return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "NotValidNumber\n";
		}
	}

	/**
	 * Given the name of a player, the removePlayertoRoom command of the application
	 * is called. The result is returned as String and can be: - Parameter is wrong
	 * (name is null) - Removing successful.
	 * 
	 * @requires guestName != null
	 * @param client ClientHandler object
	 * @return textual result, to be shown to the user
	 */
	public synchronized String removePlayerfromRoom(ClientHandler client) {
		for (Room r : rooms) {
			if (r.getPlayerList().contains(client)) {
				r.removePlayer(client);
				if (r.getStatus().equals("Started") && r.getPlayerList().size() == 1) {
					try {
						r.getPlayerList().get(0).sendMessage(r.printResult(client));
					} catch (IOException e) {
						e.printStackTrace();
					}
					r.setStatus("NotStarted");
				}
				client.assignRoom(null);
				client.removedfromRoom();
				return ProtocolMessages.LEAVE + ProtocolMessages.DELIMITER + client.toString() + "\n";
			}
		}
		return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "Player is not in a room\n";

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
			display = display + String.valueOf(ProtocolMessages.DELIMITER);
		}
		display = display.substring(0, display.length() - 2);
		return display + "\n";
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
					return ProtocolMessages.ALLY + ProtocolMessages.DELIMITER + name + "\n";
				}
			}
			return ProtocolMessages.ALLY + ProtocolMessages.DELIMITER + "PlayerNotFound\n";
		}
		return ProtocolMessages.ALLY + ProtocolMessages.DELIMITER + "DidntWorkLol\n";

	}

	/**
	 * Starts the game when the party leader of a room decides to.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Doestnt work yet
	 * 
	 * @return textual result of the number of players, and each players name
	 */
	public String startGame(ClientHandler cl) {
		String display = "";
		if (!cl.isinRoom()) {
			return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "NotInRoom\n";
		}
		for (Room r : rooms) {
			if (r.isLeader(cl)) {
				if (r.getNumPlayers() > 1) {
					display = display + ProtocolMessages.START + ProtocolMessages.DELIMITER + r.getNumPlayers()
							+ ProtocolMessages.DELIMITER;
					for (ClientHandler ch : r.getPlayerList()) {
						display = display + ch.getClientHandlerName() + ProtocolMessages.DELIMITER;
					}
					r.startGame(r.getNumPlayers());
					return display + "\n";
				}
				return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "Empty\n";
			}
		}
		return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "InvalidPermission\n";

	}


	// ------------------ Main --------------------------

	/** Start a new Server */
	public static void main(String[] args) {
		Server server = new Server();
		System.out.println("Welcome to the Abalone Server! Starting...");
		new Thread(server).start();
	}
}
