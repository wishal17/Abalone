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
	 * Sent as last line in a multi-line response to indicate the end of the text.
	 */
	public static final String EOT = "--EOT--";

	/**
	 * The following chars are both used by the TUI to receive user input, and the
	 * server and client to distinguish messages.
	 */
	/** Used for the server-client handshake */
	public static final char CONNECT = 'C';
	//Used to display all rooms and the people inside
	public static final char ROOM = 'R';
	//Used to leave a room
	public static final char LEAVE = 'L';
	//Used to join a room
	public static final char JOIN = 'J';
	//Used to start a game in a room
	public static final char START = 'S';
	//Used to make a move
	public static final char MOVE = 'M';
	public static final char TURN = 'T';
	public static final char DISCONNECT = 'D';
	public static final char FINISH = 'F';
	public static final char ALL = 'A';
	public static final char HELP = 'H';

}
