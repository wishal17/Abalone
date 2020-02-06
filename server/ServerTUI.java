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

	@Override
	public void showMessage(String message) {
		System.out.print(message);
	}

	@Override
	public String getString(String question) {
		showMessage(question);
		return read.nextLine();
	}

	@Override
	public int getInt(String question) {
		String result = getString(question);
		while(!numerical(result)) {
			result = getString(question);
		}
		return Integer.parseInt(result);
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
