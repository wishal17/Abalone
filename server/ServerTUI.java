package server;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Scanner;


/**
 * Server TUI for user input and user messages
 */
public class ServerTUI implements ServerView {
	
	/** The PrintWriter to write messages to */
	private PrintWriter console;
	
	private Scanner read;

	/**
	 * Constructs a new ServerTUI. Initializes the console.
	 */
	public ServerTUI() {
		console = new PrintWriter(System.out, true);
		read = new Scanner(System.in);
	}

	/**
	 * Displays the message on the server's console
	 */
	@Override
	public void showMessage(String message) {
		System.out.print(message);
	}

	/**
	 * Gets a string input from the client
	 */
	@Override
	public String getString(String question) {
		showMessage(question);
		return read.nextLine();
	}

	/**
	 * Gets an int input from the client
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
	 * Gets a booleaninput from the client
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
	 * Checks if an expected number is actually a number
	 * @param s
	 * @return true if a number else return false
	 */
	public boolean numerical(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException e){
			System.out.println("Error enter a valid port number");
			return false;
		}
	}

}
