package game;

/**
 * Class to store information on the colors of marbles.
 *
 */
public enum Marble {
	EE, BB, WW, YY, RR;

	/**
	 * Used to return a marble's teammate in a 4-player game. For example a white marble's
	 * teammate is always black and vice versa.
	 * @param m
	 * @return
	 */
	public static Marble returnTeammate(Marble m) {
		switch (m) {
		case WW:
			return BB;
		case BB:
			return WW;
		case YY:
			return RR;
		case RR:
			return YY;
		default:
			return null;
		}
	}

	/**
	 * Used to determine if a given marble is a teammate of another marble.
	 * @param m
	 * @return true if it is a teammate else false
	 */
	public boolean isTeammate(Marble m) {
		return this == m || this == returnTeammate(m);
	}
}
