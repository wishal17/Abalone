package features;

public class Game {
	private Board board;
	private Player[] players;
	private int current;

	/*
	 * Constructor for Game. Creates a Board with the number of players as
	 * parameters (MIGHT ADD game sizee too but thats for later)
	 */
	public Game(Player... p) {
		int NUMBER_PLAYERS = p.length;
		players = new Player[NUMBER_PLAYERS];
			if (p.length == 4) {
				board = new Board(new Layout(4));
				players[0] = p[0];
				players[1] = p[1];
				players[2] = p[2];
				players[3] = p[3];
			} else if (p.length == 3) {
				board = new Board(new Layout(3));
				players[0] = p[0];
				players[1] = p[1];
				players[2] = p[2];
			} else if (p.length == 2) {
				board = new Board(new Layout(2));
				players[0] = p[0];
				players[1] = p[1];
			}
	}

	public void start() {
		boolean continueGame = true;
		while (continueGame) {
			reset();
			play();
		}
	}

	private void reset() {
		current = 0;
		board.reset();
	}

	/**
	 * Plays the game. <br>
	 * First the board is shown. Then the game is played until it is over. Players
	 * can make a move one after the other. After each move, the changed game
	 * situation is printed.
	 */
	private void play() {
		update();
		if (Board.gamemode(Board.getLayout()) == 2) {
			while (board.gameOver() != true) {
				players[0].makeMove(board);
				update();
				if (board.gameOver() == true) {
					printResult();
					return;
				}
				players[1].makeMove(board);
				update();
			}
		} else if (Board.gamemode(Board.getLayout()) == 3) {
			while (board.gameOver() != true) {
				players[0].makeMove(board);
				update();
				if (board.gameOver() == true) {
					printResult();
					return;
				}
				players[1].makeMove(board);
				update();
				if (board.gameOver() == true) {
					printResult();
					return;
				}
				players[2].makeMove(board);
				update();
			}
		} else if (Board.gamemode(Board.getLayout()) == 4) {
			while (board.gameOver() != true) {
				players[0].makeMove(board);
				update();
				if (board.gameOver() == true) {
					printResult();
					return;
				}
				players[1].makeMove(board);
				update();
				if (board.gameOver() == true) {
					printResult();
					return;
				}
				players[2].makeMove(board);
				update();
				if (board.gameOver() == true) {
					printResult();
					return;
				}
				players[3].makeMove(board);
				update();
			}
		}

	}

	private void printResult() {
		if (board.hasWinner()) {

			Player winner = null;
			if (board.isWinner(players[0].getMarble())) {
				winner = players[0];
			} else if (board.isWinner(players[1].getMarble())) {
				winner = players[1];
			} else if (board.isWinner(players[2].getMarble())) {
				winner = players[2];
			} else if (board.isWinner(players[3].getMarble())) {
				winner = players[3];
			}
			System.out.println("Speler " + winner.getName() + " (" + winner.getMarble().toString() + ") has won!");
		} else {
			System.out.println("Draw. There is no winner!");
		}
	}
	//needs to be changed
	private void update() {
		System.out.println("\ncurrent game situation: \n\n" + board.printBoardCoords() + "\n"+"\n"+board.printBoardValues());
	}
}
