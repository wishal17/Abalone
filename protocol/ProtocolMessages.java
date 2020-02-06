package protocol;

/**
 * Protocol for Networked Application.
 * 
 */
public class ProtocolMessages {

	/**
	 * Delimiter used to separate arguments sent over the network.
	 */
	public static final String DELIMITER = ";";

	/**
	 * The following chars are both used by the TUI to receive user input, and the
	 * server and client to distinguish messages.
	 */
	/** Used for the server-client handshake */
	public static final String CONNECT = "C";
	//Used to display all rooms and the people inside
	public static final String ROOM = "R";
	//Used to leave a room
	public static final String LEAVE = "L";
	//Used to join a room
	public static final String JOIN = "J";
	//Used to start a game in a room
	public static final String START = "S";
	//Used to make a move
	public static final String MOVE = "M";
	//Used to send errors
	public static final String ERROR = "E";
	public static final String TURN = "T";
	public static final String DISCONNECT = "D";
	public static final String FINISH = "F";
	public static final String ALL = "A";

}
