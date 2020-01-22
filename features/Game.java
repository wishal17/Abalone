package features;

public class Game {
	private Board board;
	private Player[] players;
	private int current;
	
	/*
	 * Constructor for Game.
	 * Creates a Board with the number of players as parameters (MIGHT ADD game sizee too but thats for later)
	 */
	public Game(Layout l, Player ...players) {
		board = new Board(l);
		int NUMBER_PLAYERS = players.length;
		players = new Player[NUMBER_PLAYERS];
		while(players != null) {
			for(Player p: players) {
			}
		}
	}
}
