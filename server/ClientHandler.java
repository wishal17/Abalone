package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import protocol.ProtocolMessages;

/**
 * ClientHandler for the server application. This class can handle the
 * communication with one client.
 */
public class ClientHandler implements Runnable {
	
	/** The socket and In- and OutputStreams */
	private BufferedReader in;
	private BufferedWriter out;
	private Socket sock;

	/** The connected server */
	private Server server;

	/** Name of this ClientHandler */
	private String name;
	boolean hasNotShutDown = true;

	// Used to determine whether the handshake has been performed
	private boolean getConnection = false;

	/**
	 * Constructs a new ClientHandler. Opens the In- and OutputStreams.
	 * 
	 * @param sock   The client socket
	 * @param server The connected server
	 * @param name   The name of this ClientHandler
	 */
	public ClientHandler(Socket sock, Server server, String name) {
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			this.sock = sock;
			this.server = server;
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
					if (getConnection == true) {
						System.out.println("> [" + name + "] sends... " + msg);
					}
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

	public void sendMessage(String msg) throws IOException {
		out.write(msg);
		out.newLine();
		out.flush();
	}

	/**
	 * Handles commands received from the client by calling the according methods at
	 * the server. For example, when the message "C;Name" is received, the method
	 * getConnection(String name) of server should be called and the output must be
	 * sent to the client.
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
		if (msgs.length == 1) {
			s1 = msgs[0];
			switch (s1) {
			case "R":
				sendMessage(server.displayRooms());
				break;
			case "L":
				server.sendMessagetoAll(server.removePlayerfromRoom(this));
				break;
			case "S":
				server.sendMessagetoRoom(server.startGame(this), this);
				break;
			case "D":
				server.sendMessagetoAll(server.removeClient(this));
				hasNotShutDown = false;
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
			case "C":
				server.sendMessagetoAll(server.getConnection(s2));
				getConnection = true;
				this.name = s2;
				break;
			case "J":
				server.sendMessagetoAll(server.addPlayertoRoom(s2, this));
				break;
			case "A":
				server.sendMessagetoRoom(server.leaderTeammate(s2), this);
				break;
			default:
				out.write("Unknown command: " + s1 + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			}
		} else if (msgs.length == 3) {
			s1 = msgs[0];
			s2 = msgs[1];
			s3 = msgs[2];
			switch (s1) {
			case "M":

				// server.sendMessagetoRoom(server.makeMove(this, s2, Integer.parseInt(s3)),
				// this);
				break;
			default:
				out.write("Unknown command: " + s1 + System.lineSeparator() + ProtocolMessages.EOT);
				out.flush();
				break;
			}
		}
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
		server.removeClient(this);
	}

	public String getClientHandlerName() {
		return name;
	}
}
