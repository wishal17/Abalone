package game;

import java.util.Scanner;

public class Game {
	private Board board;
	private Player[] players;
	private int layout;

	/*
	 * Constructor for Game. Creates a Board with the number of players as
	 * parameters.
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
			layout = 4;
		} else if (p.length == 3) {
			board = new Board(new Layout(3));
			players[0] = p[0];
			players[1] = p[1];
			players[2] = p[2];
			layout = 3;
		} else if (p.length == 2) {
			board = new Board(new Layout(2));
			players[0] = p[0];
			players[1] = p[1];
			layout = 2;
		}
	}

	/**
	 * Method used to start the game.
	 */
	public void start() {
		boolean continueGame = true;
		while (continueGame) {
			reset();
			play();
			System.out.println("\n> Play another time? (true/false)?");
			Scanner sc = new Scanner(System.in);
			continueGame = sc.nextBoolean();
			sc.close();
		}
	}

	/**
	 * Used to reset the board
	 */
	private void reset() {
		board.reset();
	}

	/**
	 * Plays the game. First the board is shown. Then the game is played until it is
	 * over. Players can make a move one after the other. After each move, the
	 * changed game situation is printed.
	 */
	private void play() {
		update();
		if (board.gamemode(board.getLayout()) == 2) {
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
			}
		} else if (board.gamemode(board.getLayout()) == 3) {
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
			}
		} else if (board.gamemode(board.getLayout()) == 4) {
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
				if (board.gameOver() == true) {
					printResult();
					return;
				}
			}
		}

	}

	/**
	 * Prints out the result of the game after the game is over. If there is a
	 * winner, the winner(s) will be printed (depending on the number of players).
	 */
	public void printResult() {
		if (layout == 4) {
			if (board.hasWinner()) {
				Player winner1 = null;
				Player winner2 = null;
				if (board.isWinner(players[0].getMarble())) {
					winner1 = players[0];
					winner2 = players[2];
				} else if (board.isWinner(players[1].getMarble())) {
					winner1 = players[1];
					winner2 = players[3];
				} else if (board.isWinner(players[2].getMarble())) {
					winner1 = players[2];
					winner2 = players[0];
				} else if (board.isWinner(players[3].getMarble())) {
					winner1 = players[3];
					winner2 = players[1];
				}
				System.out.println("Player " + winner1.getName() + " (" + winner1.getMarble().toString() + " and "
						+ winner2.getName() + " (" + winner2.getMarble().toString() + ") has won!");
			} else {
				System.out.println("Draw. There is no winner!");
			}
		} else if (layout == 3 || layout == 2) {
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
				System.out.println("Player " + winner.getName() + " (" + winner.getMarble().toString() + ") has won!");
			} else {
				System.out.println("Draw. There is no winner!");
			}
		}

	}

	/**
	 * Returns the current board
	 * @return Board board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Used to print out the updated board after every move.
	 */
	public void update() {
		System.out.println(
				"\ncurrent game situation: \n\n" + board.printBoardCoords() + "\n" + "\n" + board.printBoardValues()+ "\n"+board.geteliminated());
	}
}
