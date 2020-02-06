package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.InetAddress;
import java.util.Scanner;

import exceptions.ExitProgram;
import exceptions.ProtocolException;
import exceptions.ServerUnavailableException;
import game.Board;
import game.Layout;
import game.Marble;
import game.Player;
import protocol.ProtocolMessages;

public class ClientTUI implements ClientView, Runnable {

	private Client client;
	private boolean connected = false;
	Scanner read;
	Board localboard;
	Player p;

	public ClientTUI() {
		client = new Client();
		read = new Scanner(System.in);
	}

	/**
	 * The start method is responsible for creating a connection between the client
	 * and the server. The user is asked to input the IP and port of the server. If
	 * valid, the user will have to input a valid name to enter and a connection
	 * will be made. As long as the user is connected, user input will be read and
	 * interpreted by the TUI to send to the server.
	 */
	@Override
	public void start() throws ServerUnavailableException, ProtocolException {
		try {
			InetAddress ip = getIp();
			int port = getInt("Port: ");
			showMessage("Attempting to connect to " + ip + ":" + port + "...");
			client.createConnection(ip, port);
			connected = true;

			String response = "";
			boolean valid = false;
			String name = "";
			while (!valid) {
				while (name.equals("") || name == null) {
					name = getString("Not Connected. " + "Please enter a name to connect to the server:");
					valid = true;
				}
				client.start(name);
				response = client.readLineFromServer();
				if (response.contains(ProtocolMessages.ERROR + ProtocolMessages.DELIMITER)) {
					valid = false;
				}
				name = "";
			}
			System.out.println("Welcome to Abalone");
			printHelpMenu();
			(new Thread(this)).start();
			while (true) {
				try {
					handleUserInput(read.nextLine());
				} catch (ExitProgram e) {
					break;
				}
			}

			connected = false;
			client.sendDisconnect();
			client.closeConnection();
		} catch (ServerUnavailableException | ExitProgram e) {
			System.out.println("You have disconnected");
		}

	}

	/**
	 * The run method is responsible for handling incoming messages from the server.
	 * This is required as it notifies a client about updates in the server.
	 */
	@Override
	public void run() {
		while (connected) {
			String incoming;
			try {
				incoming = client.readLineFromServer();
				if (incoming != null && !incoming.equals("")) {
					handleIncoming(incoming);
					System.out.println(incoming);
				}
			} catch (ServerUnavailableException e) {
				System.out.println("No connection to the server");
				break;
			}
		}

	}

	/**
	 * This method is responsible for interpreting the messages coming from the
	 * server and printing it out to the client in the console.
	 * 
	 * @param input
	 */
	public void handleIncoming(String input) {
		String messages[] = input.split(ProtocolMessages.DELIMITER);
		switch (messages[0]) {
		case "R":
			System.out.println(input.replace(";;", ";\n;"));
			break;
		case "C":
			System.out.println(messages[1] + " is connected");
			break;
		case "J":
			System.out.println(messages[2] + " has joined room " + messages[1]);
			break;
		case "L":
			System.out.println(messages[1] + " has left a room");
			break;
		case "S":
			System.out.println("A new game with " + messages[1] + " players has started in this room.");
			localboard = new Board(new Layout((Integer.parseInt(messages[1]))));
			
			break;

		case "E":
			System.out.println(messages[1]);
		}
		System.out.print("\n> ");

	}

	/**
	 * This method is responsible for checking if the user input satisfies one of
	 * the several commands of the Abalone application. It then sends over the input
	 * to the Client class where it will be sent to the server.
	 */
	@Override
	public void handleUserInput(String input) throws ExitProgram, ServerUnavailableException, ProtocolException {
		String[] msgs = input.split(ProtocolMessages.DELIMITER);
		String s1, s2, s3;

		if (msgs.length == 1) {
			s1 = msgs[0].toUpperCase();
			switch (s1) {
			case "R":
				client.displayRooms();
				break;
			case "L":
				client.leaveRoom();
				break;
			case "S":
				client.startGame();
				break;
			case "D":
				client.sendDisconnect();
				throw new ExitProgram("Program Exited");
			// break;
			default:
				System.out.println("Unknown command: " + s1);
				printHelpMenu();
				System.out.print("\nEnter command here: ");
			}
		} else if (msgs.length == 2) {
			s1 = msgs[0].toUpperCase();
			s2 = msgs[1];
			switch (s1) {
			case "J":
				client.joinRoom(s2);
				break;
			case "A":
				client.leaderTeammate(s2);
				break;
			default:
				System.out.println("Unknown command: " + s1);
				printHelpMenu();
				System.out.print("\n>: ");
			}
		} else if (msgs.length == 3) {
			s1 = msgs[0].toUpperCase();
			s2 = msgs[1].toUpperCase();
			s3 = msgs[2];
			switch (s1) {
			case "M":
				String pos1, pos2, pos3;
				String move = s2;
				int direction = Integer.parseInt(s3);
				Marble marble = p.getMarble();
				boolean valid = true;
				if (move.length() == 6) {
					pos1 = move.charAt(0) + "" + move.charAt(1);
					pos2 = move.charAt(2) + "" + move.charAt(3);
					pos3 = move.charAt(4) + "" + move.charAt(5);
					valid = localboard.isValidMove(pos1, pos2, pos3, direction, marble);
				} else if (move.length() == 4) {
					pos1 = move.charAt(0) + "" + move.charAt(1);
					pos2 = move.charAt(2) + "" + move.charAt(3);
					valid = localboard.isValidMove(pos1, pos2, direction, marble);
				} else if (move.length() == 2) {
					pos1 = move.charAt(0) + "" + move.charAt(1);
					valid = localboard.isValidMove(pos1, direction, marble);
				}
				while (!valid) {
					System.out
							.println("ERROR: This move " + move + " is not a valid choice. Please enter another move");
					handleUserInput(input);
				}
				client.makeMove(s2, Integer.parseInt(s3));
				break;
			default:
				System.out.println("Unknown command: " + s1);
				printHelpMenu();
				System.out.print("\n> ");
			}
		}

	}

	@Override
	public void showMessage(String message) {
		System.out.print(message);
	}

	/**
	 * Gets the server's IP. If not a valid IP, the exception is caught by a
	 * try-catch block.
	 */
	@Override
	public InetAddress getIp() {
		String ips = getString("Please enter the server IP address here: ");
		InetAddress ip;
		try {
			ip = InetAddress.getByName(ips);
		} catch (Exception e) {
			System.out.println("Invalid IP. Please try again.");
			return getIp();
		}
		return ip;
	}

	/**
	 * Returns user input in a String format
	 */
	@Override
	public String getString(String question) {
		showMessage(question);
		return read.nextLine();
	}

	/**
	 * Returns user input in an Integer format
	 */
	@Override
	public int getInt(String question) {
		String result = getString(question);
		while(!numerical(result)) {
			result = getString(question);
		}
		return Integer.parseInt(result);
	}

	/**
	 * Returns user input in a Boolean format
	 */
	@Override
	public boolean getBoolean(String question) {
		String b = getString(question);
		if (b.toUpperCase().equals("YES") || b.toUpperCase().equals("Y")) {
			return true;
		} else if (b.toUpperCase().equals("NO") || b.toUpperCase().equals("N")) {
			return false;
		} else {
			return getBoolean(question);
		}
	}
	
	public boolean numerical(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException e){
			System.out.println("Error enter a valid port number");
			return false;
		}
	}

	/**
	 * Prints out a list of all the commands that can be used.
	 */
	@Override
	public void printHelpMenu() {
		System.out.println("Commands:");
		System.out.println("R; ................................ Displays all of the rooms");
		System.out.println("J;<room> .......................... Joins you to a room from numbers 1-10");
		System.out.println("L; ................................ Leaves a room");
		System.out.println("A;<nameofteammate> .................... Party leader chooses a teammate");
		System.out.println("S; ................................ Input from party leader to start a game");
		System.out.println("M;<position>;<direction> .......... Move");
		System.out.println("D; .................................Disconnecting from a server");

	}

	public static void main(String[] args) throws ProtocolException {
		try {
			(new ClientTUI()).start();
		} catch (ServerUnavailableException e) {
			e.printStackTrace();
		}
	}

}
