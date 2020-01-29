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
import game.Room;
import protocol.*;
import server.ClientHandler;

/**
 * Client for Abalone.
 * 
 * @author Wim Kamerman
 */
public class Client{
	
	private Socket serverSock;
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
	 * Starts a new Client by creating a connection, followed by the 
	 * CONNECT handshake as defined in the protocol. After a successful 
	 * connection and handshake, the view is started. The view asks for 
	 * user input and handles all further calls to methods of this class. 
	 * 
	 * When errors occur, or when the user terminates a server connection, the
	 * user is asked whether a new connection should be made.
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
	 * Creates a connection to the server. Requests the IP and port to 
	 * connect to at the view (TUI).
	 * 
	 * The method continues to ask for an IP and port and attempts to connect 
	 * until a connection is established or until the user indicates to exit 
	 * the program.
	 * 
	 * @throws ExitProgram if a connection is not established and the user 
	 * 				       indicates to want to exit the program.
	 * @ensures serverSock contains a valid socket connection to a server
	 */
	public void createConnection(InetAddress addr, int port) throws ExitProgram {
		clearConnection();
		while (serverSock == null) {
			// try to open a Socket to the server
			try {
				serverSock = new Socket(addr, port);
				in = new BufferedReader(new InputStreamReader(
						serverSock.getInputStream()));
				out = new BufferedWriter(new OutputStreamWriter(
						serverSock.getOutputStream()));
			} catch (IOException e) {
				showMessage("ERROR: could not create a socket on " 
					+ addr.toString() + " and port " + port + ".");
			}
		}
		System.out.println("Connected.");
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
	 * Sends a message to the connected server, followed by a new line. 
	 * The stream is then flushed.
	 * 
	 * @param msg the message to write to the OutputStream.
	 * @throws ServerUnavailableException if IO errors occur.
	 */
	public void sendMessage(String msg) 
			throws ServerUnavailableException {
		if (out != null) {
			try {
				out.write(msg);
				out.newLine();
				out.flush();
			} catch (IOException e) {
				showMessage(e.getMessage());
				throw new ServerUnavailableException("Could not write "
						+ "to server.");
			}
		} else {
			throw new ServerUnavailableException("Could not write "
					+ "to server.");
		}
	}

	/**
	 * Reads and returns one line from the server.
	 * 
	 * @return the line sent by the server.
	 * @throws ServerUnavailableException if IO errors occur.
	 */
	public String readLineFromServer() 
			throws ServerUnavailableException {
		if (in != null) {
			try {
				// Read and return answer from Server
				String answer = in.readLine();
				if (answer == null) {
					throw new ServerUnavailableException("Could not read "
							+ "from server.");
				}
				return answer;
			} catch (IOException e) {
				throw new ServerUnavailableException("Could not read "
						+ "from server.");
			}
		} else {
			throw new ServerUnavailableException("Could not read "
					+ "from server.");
		}
	}

	/**
	 * Reads and returns multiple lines from the server until the end of 
	 * the text is indicated using a line containing ProtocolMessages.EOT.
	 * 
	 * @return the concatenated lines sent by the server.
	 * @throws ServerUnavailableException if IO errors occur.
	 */
	public String readMultipleLinesFromServer() 
			throws ServerUnavailableException {
		if (in != null) {
			try {
				// Read and return answer from Server
				StringBuilder sb = new StringBuilder();
				for (String line = in.readLine(); line != null
						&& !line.equals(ProtocolMessages.EOT); 
						line = in.readLine()) {
					sb.append(line + System.lineSeparator());
				}
				return sb.toString();
			} catch (IOException e) {
				throw new ServerUnavailableException("Could not read "
						+ "from server.");
			}
		} else {
			throw new ServerUnavailableException("Could not read "
					+ "from server.");
		}
	}

	/**
	 * Closes the connection by closing the In- and OutputStreams, as 
	 * well as the serverSocket.
	 * @throws ServerUnavailableException 
	 */
	public void closeConnection() throws ServerUnavailableException {
		showMessage("Closing the connection...");
		try {
			in.close();
			out.close();
			serverSock.close();
		} catch (IOException e) {
			throw new ServerUnavailableException("Could not read "
					+ "from server.");
		}
	}
	
	public void getConnection(String name)
			throws ServerUnavailableException, ProtocolException {
		sendMessage(name);
		showMessage(">"+readLineFromServer());
	}
	
	public void sendDisconnect() throws ServerUnavailableException {
		
		sendMessage(String.valueOf(ProtocolMessages.DISCONNECT));
		showMessage(">"+readLineFromServer());
		notExited = false;
		closeConnection();
	}
	
	
	public void joinRoom(String roomnum) throws ServerUnavailableException {
		if (!roomnum.equals(null)) {
			sendMessage(String.valueOf(ProtocolMessages.JOIN)+ String.valueOf(ProtocolMessages.DELIMITER) + roomnum);
			//System.out.println("join bruda");
			showMessage(">"+readLineFromServer());
		} else {
			showMessage("The room does not exist");
		}
	}
	
	public void leaveRoom() throws ServerUnavailableException {
		sendMessage(String.valueOf(ProtocolMessages.LEAVE)+ String.valueOf(ProtocolMessages.DELIMITER));
		showMessage(">"+readLineFromServer());
	}
	
	public void displayRooms() throws ServerUnavailableException {
		sendMessage(String.valueOf(ProtocolMessages.ROOM)+ String.valueOf(ProtocolMessages.DELIMITER));
		System.out.println("display bruda");
		showMessage(">"+readMultipleLinesFromServer());
	}
	
	public void leaderTeammate(String name) throws ServerUnavailableException {
		if(!name.equals(null)) {
			sendMessage(String.valueOf(ProtocolMessages.ROOM)+ String.valueOf(ProtocolMessages.DELIMITER)+name);
			showMessage(">"+readLineFromServer());
		} else {
			showMessage("Please enter a valid player name");
		}
	}
	public void startGame() throws ServerUnavailableException {
		sendMessage((String.valueOf(ProtocolMessages.START)+ String.valueOf(ProtocolMessages.DELIMITER)));
		showMessage(">"+readLineFromServer());
	}

	public void showMessage(String message) {
		System.out.println(message);
	}

}
