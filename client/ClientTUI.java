package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.InetAddress;
import java.util.Scanner;

import exceptions.ExitProgram;
import exceptions.ProtocolException;
import exceptions.ServerUnavailableException;
import protocol.ProtocolMessages;

public class ClientTUI implements ClientView {

	private Client client;
	Scanner read;

	public ClientTUI() {
		client = new Client();
		read = new Scanner(System.in);
	}

	@Override
	public void start() throws ServerUnavailableException, ProtocolException {
		try {
			InetAddress ip = getIp();
			int port = getInt("Port: ");
			showMessage("Attempting to connect to " + ip + ":" + port + "...");
			client.createConnection(ip, port);
		} catch (ExitProgram e1) {
			e1.printStackTrace();
		}
		String name = "";
		while(name.equals("")||name == null) {
			name = getString("E;NotConnected"+"Please enter C;<name> to connect to the server:");
		}
		client.start(name);

		//printHelpMenu();

		while (true) {
			try {
				handleUserInput(getString("> Enter command here: "));
			} catch (ExitProgram e) {
				// TODO Auto-generated catch block
				break;
			}
		}

		client.sendDisconnect();
		client.closeConnection();

		boolean a = getBoolean("Do you want to reestablish the connection? 'Y' or 'N");
		if (a) {
			start();
		}
	}

	@Override
	public void handleUserInput(String input) throws ExitProgram, ServerUnavailableException, ProtocolException {
		String[] msgs = input.split(ProtocolMessages.DELIMITER);
		String s1, s2, s3;

		if (msgs.length == 1) {
			s1 = msgs[0];
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
				//break;
			default:
				System.out.println("Unknown command: " + s1);
				printHelpMenu();
				break;
			}
		} else if (msgs.length == 2) {
			s1 = msgs[0];
			s2 = msgs[1];
			switch (s1) {
			case "J":
				client.joinRoom(s2);;
				break;
			case "A":
				client.leaderTeammate(s2);;
				break;
			default:
				System.out.println("Unknown command: " + s1);
				printHelpMenu();
				break;
			}
		} else if (msgs.length == 3) {
			s1 = msgs[0];
			switch (s1) {

			case "x":
				throw new ExitProgram("Program Exited");
			default:
				showMessage("Unknown command: " + s1 + "\n");
				printHelpMenu();
				break;
			}
		}

	}

	@Override
	public void showMessage(String message) {
		System.out.print(message);
	}

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

	@Override
	public String getString(String question) {
		showMessage(question);
		return read.nextLine();
	}

	@Override
	public int getInt(String question) {
		showMessage(question);
		return read.nextInt();
	}

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

	@Override
	public void printHelpMenu() {
		System.out.println("Commands:");
		System.out.println("R; ................................ Displays all of the rooms");
		System.out.println("J;<room> .......................... Joins you to a room from numbers 1-10");
		System.out.println("L; ................................ Leaves a room");
		System.out.println("A;<nameofteam> .................... Party leader chooses a teammate");
		System.out.println("S; ................................ Input from party leader to start a game");
		System.out.println("M;<position>;<direction> .......... Move");
		System.out.println("D; .................................Disconnecting from a server");

	}

	public static void main(String[] args) throws ProtocolException {
		try {
			(new ClientTUI()).start();
		} catch (ServerUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
