package exceptions;

public class NoServerOnPortException extends Exception {
	public NoServerOnPortException(int port) {
		super("No server was found on the specified port "+port);
	}
}
