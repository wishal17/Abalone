package features;

public class Game {
	private Board board;
	private Player[] players;
	private int current;
	
	/*
	 * Constructor for Game.
	 * Creates a Board with the number of players as parameters (MIGHT ADD game sizee too but thats for later)
	 */
	public Game(Player ...players) {
		board = new Board(2);
		int NUMBER_PLAYERS = players.length;
		int playerpos=0;
		players = new Player[NUMBER_PLAYERS];
		for(Player p: players) {
			while(playerpos<=NUMBER_PLAYERS) {
				players[playerpos]=p;
			}
		}
		current = 0;
	}
}
