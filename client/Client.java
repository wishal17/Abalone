package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import exceptions.*;
import protocol.*;

/**
 * Client for Abalone.
 */
public class Client {

	public Socket serverSock;
	private BufferedReader in;
	private BufferedWriter out;
	private Scanner user;

	boolean notExited = true;

	/**
	 * Constructs a new client. Initialises the view.
	 */
	public Client() {
		user = new Scanner(System.in);
	}

	/**
	 * Starts a new Client by creating a connection, followed by the CONNECT
	 * handshake as defined in the protocol. After a successful connection and
	 * handshake, the view is started. The view asks for user input and handles all
	 * further calls to methods of this class.
	 * 
	 * When errors occur, or when the user terminates a server connection, the user
	 * is asked whether a new connection should be made.
	 */
	public void start(String input) {
		try {
			getConnection(input);
		} catch (ServerUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

	}

	/**
	 * Creates a connection to the server. Requests the IP and port to connect to at
	 * the view (TUI).
	 * 
	 * The method continues to ask for an IP and port and attempts to connect until
	 * a connection is established or until the user indicates to exit the program.
	 * 
	 * @throws ExitProgram             if a connection is not established and the
	 *                                 user indicates to want to exit the program.
	 * @throws NoServerOnPortException
	 * @ensures serverSock contains a valid socket connection to a server
	 */
	public void createConnection(InetAddress addr, int port) throws ExitProgram, NoServerOnPortException {
		clearConnection();
		// try to open a Socket to the server
		try {
			serverSock = new Socket(addr, port);
			in = new BufferedReader(new InputStreamReader(serverSock.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(serverSock.getOutputStream()));
		} catch (IOException e) {
			throw new NoServerOnPortException(port);
		}
	}

	/**
	 * Resets the serverSocket and In- and OutputStreams to null.
	 */
	public void clearConnection() {
		serverSock = null;
		in = null;
		out = null;
	}

	/**
	 * Sends a message to the connected server, followed by a new line. The stream
	 * is then flushed.
	 * 
	 * @param msg the message to write to the OutputStream.
	 * @throws ServerUnavailableException if IO errors occur.
	 */
	public void sendMessage(String msg) throws ServerUnavailableException {
		if (out != null) {
			try {
				out.write(msg);
				out.newLine();
				out.flush();
			} catch (IOException e) {
				showMessage(e.getMessage());
				throw new ServerUnavailableException("Could not write " + "to server.");
			}
		} else {
			throw new ServerUnavailableException("Could not write " + "to server.");
		}
	}

	/**
	 * Reads and returns one line from the server.
	 * 
	 * @return the line sent by the server.
	 * @throws ServerUnavailableException if IO errors occur.
	 */
	public String readLineFromServer() throws ServerUnavailableException {
		if (in != null) {
			try {
				// Read and return answer from Server
				String answer = in.readLine();
				if (answer == null) {
					throw new ServerUnavailableException("Could not read " + "from server.");
				}
				return answer;
			} catch (IOException e) {
				throw new ServerUnavailableException("Could not read " + "from server.");
			}
		} else {
			throw new ServerUnavailableException("Could not read " + "from server.");
		}
	}

	/**
	 * Closes the connection by closing the In- and OutputStreams, as well as the
	 * serverSocket.
	 * 
	 * @throws ServerUnavailableException
	 */
	public void closeConnection() throws ServerUnavailableException {
		showMessage("Closing the connection...");
		try {
			in.close();
			out.close();
			serverSock.close();
		} catch (IOException e) {
			throw new ServerUnavailableException("Could not read " + "from server.");
		}
	}

	/**
	 * Sends a message to the server to connect a client to the server
	 * @param name
	 * @throws ServerUnavailableException
	 * @throws ProtocolException
	 */
	public void getConnection(String name) throws ServerUnavailableException, ProtocolException {
		sendMessage(ProtocolMessages.CONNECT + ProtocolMessages.DELIMITER + name);
	}

	/**
	 * Send a message to the server to allow a client to disconnect.
	 * @throws ServerUnavailableException
	 */
	public void sendDisconnect() throws ServerUnavailableException {
		sendMessage(String.valueOf(ProtocolMessages.DISCONNECT));
		notExited = false;
		closeConnection();
	}

	/**
	 * Sends a message to the server to allow the client to join a room
	 * @param roomnum
	 * @throws ServerUnavailableException
	 */
	public void joinRoom(String roomnum) throws ServerUnavailableException {
		if (!roomnum.equals(null)) {
			sendMessage(String.valueOf(ProtocolMessages.JOIN) + String.valueOf(ProtocolMessages.DELIMITER) + roomnum);
		} else {
			showMessage("The room does not exist");
		}
	}

	/**
	 * Sends a message to the server to allow the client to leave a room
	 * @throws ServerUnavailableException
	 */
	public void leaveRoom() throws ServerUnavailableException {
		sendMessage(String.valueOf(ProtocolMessages.LEAVE) + String.valueOf(ProtocolMessages.DELIMITER));
	}

	/**
	 * Sends a message to the server to display all rooms to the client
	 * @throws ServerUnavailableException
	 */
	public void displayRooms() throws ServerUnavailableException {
		sendMessage(String.valueOf(ProtocolMessages.ROOM) + String.valueOf(ProtocolMessages.DELIMITER));
	}

	/**
	 * Sends a message to the server to assign a player to the the party leader's team
	 * @param name
	 * @throws ServerUnavailableException
	 */
	public void leaderTeammate(String name) throws ServerUnavailableException {
		if (!name.equals(null)) {
			sendMessage(String.valueOf(ProtocolMessages.ALLY) + String.valueOf(ProtocolMessages.DELIMITER) + name);
		}
	}

	/**
	 * Sends a message to the server to make a move
	 * @param move
	 * @param direction
	 * @throws ServerUnavailableException
	 */
	public void makeMove(String move, String direction) throws ServerUnavailableException {
		sendMessage(ProtocolMessages.MOVE + ProtocolMessages.DELIMITER + move + ProtocolMessages.DELIMITER + direction);
	}

	/**
	 * Sends a message to the server to start a game.
	 * @throws ServerUnavailableException
	 */
	public void startGame() throws ServerUnavailableException {
		sendMessage((String.valueOf(ProtocolMessages.START) + String.valueOf(ProtocolMessages.DELIMITER)));
		//showMessage(">" + readLineFromServer());
	}

	/**
	 * Prints out a message
	 * @param message
	 */
	public void showMessage(String message) {
		System.out.println(message);
	}

}
