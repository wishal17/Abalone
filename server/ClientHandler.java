package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import exceptions.ServerUnavailableException;
import game.Marble;
import game.Room;
import protocol.ProtocolMessages;
import exceptions.ServerUnavailableException;

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

	// Ensures that when a client joins a server he is not in a room. This can be
	// changed later.
	public boolean inroom = false;

	// Player's marble
	public Marble marble = null;

	// Player's room
	public Room room = null;

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
	 * server.getConnection(String name) of server should be called and the output
	 * must be sent to the client.
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
				if (server.startGame(this).contains(ProtocolMessages.ERROR)) {
					sendMessage(server.startGame(this));
				} else {
					server.sendMessagetoRoom(server.startGame(this), this);
					server.sendMessagetoRoom(turn(), this);
				}
				break;
			case "D":
				if (getRoom() != null) {
					if (getRoom().getStatus().equals("Started")) {
						server.sendMessagetoRoom(turn(), this);
					}
				}
				server.sendMessagetoAll(server.removeClient(this));
				
				hasNotShutDown = false;
				break;
			default:
				out.write("Unknown command: " + s1);
				out.newLine();
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
				if (this.inroom) {
					sendMessage("You are already in a room!\n");
				} else {
					server.sendMessagetoAll(server.addPlayertoRoom(s2, this));
				}
				break;
			case "A":
				server.sendMessagetoRoom(server.leaderTeammate(s2), this);
				break;
			default:
				out.write("Unknown command: " + s1);
				out.newLine();
				out.flush();
				break;
			}
		} else if (msgs.length == 3) {
			s1 = msgs[0];
			s2 = msgs[1];
			s3 = msgs[2];
			switch (s1) {
			case "M":
				server.sendMessagetoRoom(this.getRoom().makeMove(this, s2, s3), this);
				server.sendMessagetoRoom(turn(), this);
				break;
			default:
				out.write("Unknown command: " + s1);
				out.newLine();
				out.flush();
				break;
			}
		}
	}

	/**
	 * Shuts down the connection to this client by closing the socket and the In-
	 * and OutputStreams.
	 */
	private void shutdown() {
		System.out.println("> [" + name + "] has disconnected from the server.");
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.removeClient(this);
	}

	public String toString() {
		return name;
	}

	public boolean equals(ClientHandler ch) {
		if (ch instanceof ClientHandler) {
			return this.toString().equals(ch.toString());
		}
		return false;
	}

	public void addedtoRoom() {
		inroom = true;
	}

	public void removedfromRoom() {
		inroom = false;
	}

	public boolean isinRoom() {
		return inroom;
	}

	public void assignRoom(Room r) {
		this.room = r;
	}

	public Room getRoom() {
		return room;
	}

	public String turn() {
		return ProtocolMessages.TURN + ProtocolMessages.DELIMITER + room.playerturn() + "\n";
	}

	public String getClientHandlerName() {
		return name;
	}

	public void assignMarble(Marble m) {
		this.marble = m;
	}

	public Marble getMarble() {
		return marble;
	}

	public BufferedWriter getOut() {
		return out;
	}
}
