package client;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.ExitProgram;
import exceptions.NoServerOnPortException;
import exceptions.ProtocolException;
import exceptions.ServerUnavailableException;
import game.Board;
import game.Layout;
import protocol.ProtocolMessages;

public class ClientTUI implements ClientView, Runnable {

	private Client client;
	private boolean connected = false;
	private Scanner read;
	private Board localboard;
	private List<String> names;
	private boolean started = false;
	private InetAddress ip;
	private int port;
	private boolean reconnecting = false;
	private String name = "";
	private String response = "";

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
			while (client.serverSock == null) {
				ip = getIp();
				port = getInt("Port: ");
				showMessage("Attempting to connect to " + ip + ":" + port + "...");
				try {
					client.createConnection(ip, port);
					break;
				} catch (NoServerOnPortException e1) {
					System.out.println("\nPort number invalid. Please enter a valid port number");
				}
			}
			connected = true;
			while (response.contains(String.valueOf(ProtocolMessages.ERROR) + String.valueOf(ProtocolMessages.DELIMITER))||response.equals("")) {
				response = "";
				name = getString("\nNot Connected. \nPlease enter a name to connect to the server:");
				client.start(name);
				response = client.readLineFromServer();
				handleIncoming(response);
			}

			if (reconnecting) {
				try {
					client.createConnection(ip, port);
				} catch (NoServerOnPortException e1) {
					System.out.println("\nPort number invalid. Please enter a valid port number");
				}
				client.start(name);
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
			System.out.println("Would you like to reconnect('y'):");
			String answer = read.nextLine();
			if (answer.equals("y")) {
				reconnecting = true;
				start();
			}
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
			if (started) {
				localboard
						.removePlayersMarbles(localboard.orderofMarbles(names.size()).get(names.indexOf(messages[1])));
				System.out.println(localboard.printBoardCoords());
				System.out.println(localboard.printBoardValues());
			}
			started = false;
			System.out.println(messages[1] + " has left a room");
			break;

		case "S":
			names = new ArrayList<>();
			System.out.println("A new game with " + messages[1] + " players has started in this room.\n");
			localboard = new Board(new Layout((Integer.parseInt(messages[1]))));
			for (int i = 2; i < messages.length; i++) {
				names.add(messages[i]);
			}
			started = true;
			System.out.println();
			System.out.println(localboard.printBoardCoords());
			System.out.println(localboard.printBoardValues());
			break;
		case "T":
			System.out.println("It is " + messages[1] + "'s turn!");
			break;
		case "F":
			started = false;
			if (messages.length == 3) {
				System.out.println(messages[2] + " and " + messages[1] + " are the winners!");
			} else if (messages.length == 2) {
				System.out.println(messages[1] + " is the winner!");
			} else {
				System.out.println("Draw there is no winner!");
			}
			break;
		case "D":
			if (started) {
				localboard
						.removePlayersMarbles(localboard.orderofMarbles(names.size()).get(names.indexOf(messages[1])));
				System.out.println();
				System.out.println(localboard.printBoardCoords());
				System.out.println(localboard.printBoardValues());
			}
			System.out.println(messages[1] + " has left the server");
			break;
		case "A":
			System.out.println("The party leader chose " + messages[1] + " as their teammate");
			break;
		case "M":
			String pos1, pos2, pos3;
			String move = messages[1];
			if (move.length() == 6) {
				pos1 = move.charAt(0) + "" + move.charAt(1);
				pos2 = move.charAt(2) + "" + move.charAt(3);
				pos3 = move.charAt(4) + "" + move.charAt(5);
				localboard.isValidMove(pos1, pos2, pos3, Integer.parseInt(messages[2]),
						localboard.orderofMarbles(names.size()).get(names.indexOf(messages[3])));
			} else if (move.length() == 4) {
				pos1 = move.charAt(0) + "" + move.charAt(1);
				pos2 = move.charAt(2) + "" + move.charAt(3);
				localboard.isValidMove(pos1, pos2, Integer.parseInt(messages[2]),
						localboard.orderofMarbles(names.size()).get(names.indexOf(messages[3])));
			} else if (move.length() == 2) {
				pos1 = move.charAt(0) + "" + move.charAt(1);
				localboard.isValidMove(pos1, Integer.parseInt(messages[2]),
						localboard.orderofMarbles(names.size()).get(names.indexOf(messages[3])));
			}
			System.out.println();
			System.out.println(localboard.printBoardCoords());
			System.out.println(localboard.printBoardValues());
			System.out.println("eliminated marbles: " + localboard.eliminated);
			System.out.println(messages[3] + " has moved " + messages[1] + " in the direction " + messages[2]);
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
				client.makeMove(s2, s3);
				break;
			default:
				System.out.println("ERROR: CommandNotRecognized: " + s1);
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
		while (!numerical(result)) {
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

	/**
	 * Used to check if an expected number is indeed a number
	 * 
	 * @param s
	 * @return true if is a number else returns false
	 */
	public static boolean numerical(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
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
