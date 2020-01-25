package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import protocol.ProtocolMessages;

/**
 * ClientHandler for the Server application. This class can handle
 * the communication with one client.
 * 
 * @author Aim Kamerman
 */
public class ClientHandler implements Runnable {

	/** The socket and In- and OutputStreams */
	private BufferedReader in;
	private BufferedWriter out;
	private Socket sock;

	/** The connected HotelServer */
	private Server srv;

	/** Name of this ClientHandler */
	private String name;

	boolean hasNotShutDown = true;

	/**
	 * Constructs a new HotelClientHandler. Opens the In- and OutputStreams.
	 * 
	 * @param sock The client socket
	 * @param srv  The connected server
	 * @param name The name of this ClientHandler
	 */
	public ClientHandler(Socket sock, Server srv, String name) {
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			this.sock = sock;
			this.srv = srv;
			this.name = name;
		} catch (IOException e) {
			shutdown();
		}
	}

	/**
	 * Continuously listens to client input and forwards the input to the
	 * {@link #handleCommand(String)} method.
	 */
	public void run() {
		String msg;
		try {
			while (hasNotShutDown) {
				msg = in.readLine();
				if (msg != null) {
					System.out.println("> [" + name + "] Incoming: " + msg);
					handleCommand(msg);
					out.newLine();
					out.flush();
				}
			}
			shutdown();
		} catch (IOException e) {
			shutdown();
		}
	}

	/**
	 * Handles commands received from the client by calling the according methods at
	 * the HotelServer. For example, when the message "i Name" is received, the
	 * method doIn() of HotelServer should be called and the output must be sent to
	 * the client.
	 * 
	 * If the received input is not valid, send an "Unknown Command" message to the
	 * server.
	 * 
	 * @param msg command from client
	 * @throws IOException if an IO errors occur.
	 */
	private void handleCommand(String msg) throws IOException {
		String[] msgs = msg.split(ProtocolMessages.DELIMITER);
		String s1, s2, s3;
		if (msgs.length == 3) {
			s1 = msgs[0];
			s2 = msgs[1];
			s3 = msgs[2];
			switch (s1) {
			case "b":
				out.write(srv.doBill(s2, s3) + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			case "a":
				out.write(srv.doAct(s2, s3) + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			default:
				out.write("Unknown command: " + s1 + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			}
		} else if (msgs.length == 2) {
			s1 = msgs[0];
			s2 = msgs[1];
			switch (s1) {
			case "i":
				out.write(srv.doIn(s2) + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			case "r":
				out.write(srv.doRoom(s2) + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			case "o":
				out.write(srv.doOut(s2) + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			case "a":
				out.write(srv.doAct(s2, "") + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			default:
				out.write("Unknown command: " + s1 + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			}
		} else if (msgs.length == 1) {
			s1 = msgs[0];
			switch (s1) {
			case "h":
				System.out.println(srv.getHello() + System.lineSeparator() + ProtocolMessages.EOT);
				out.write(srv.getHello() + System.lineSeparator());
				out.newLine();
				out.flush();
				break;
			case "p":
				out.write(srv.doPrint() + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			case "x":
				hasNotShutDown = false;
				break;
			default:
				out.write("Unknown command: " + s1 + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			}
		}
	}

	private void printHelpMenu() {
		System.out.println("Commands:");
		System.out.println("C;name ........... Connects to the server (Note: no spaces or semicolons allowed in name");
		System.out.println("R;     ........... Displays all of the rooms and the people inside");
		System.out.println("L;     ........... Leave current room");
		System.out.println("A;     ........... Only for party leader to select a teammate (Only for 4-player mode");
		System.out.println("S;     ........... Used to start a game by a party leader");
		System.out.println(
				"M;7E8E9E;5........ Used to move your marble. The coordinates and direction is seperated by a semicolon");
		System.out.println("D; ................Disconnects from the server");
		System.out.println("All possible Direction movements :  🡤 = 1,🡥  = 2,🡢  = 3,🡦  = 4,🡧 = 5,🡠  = 6");

	}

	/**
	 * Shut down the connection to this client by closing the socket and the In- and
	 * OutputStreams.
	 */
	private void shutdown() {
		System.out.println("> [" + name + "] Shutting down.");
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		srv.removeClient(this);
	}
}
