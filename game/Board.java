package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
	private Layout layout;
	public Map<String, Marble> map = new LinkedHashMap<String, Marble>();
	public List<Marble> eliminated = new LinkedList<Marble>();
	public int turns = 0;

	/*
	 * Creates a board with the specified layout
	 */
	public Board(Layout l) {
		this.layout = l;
		map = l.returnLayoutMap();
	}

	/**
	 * Creates a deep copy of this field.
	 * 
	 * @ensures the result is a new object, so not this object
	 * @ensures the values of all fields of the copy match the ones of this Board
	 */
	public Board deepCopy() {
		Board copy = new Board(layout);
		copy.map = this.map;
		return copy;
	}

	/**
	 * @return layout of the current board
	 */
	public Layout getLayout() {
		return layout;
	}

	/**
	 * Returns the number of players in an active match
	 * @param l
	 */
	public int gamemode(Layout l) {
		if (l.returnPlayers().equals("two")) {
			return 2;
		} else if (l.returnPlayers().equals("three")) {
			return 3;
		} else if (l.returnPlayers().equals("four")) {
			return 4;
		}
		return -1;
	}

	/**
	 * Returns the Linked hash map of the board
	 * 
	 * @return map
	 */
	public Map<String, Marble> returnBoardMap() {
		return map;
	}

	/**
	 * Returns a marble found at a specific coordinate
	 * 
	 * @param s (coordinate)
	 * @return marble
	 */
	public Marble getMarble(String s) {
		return map.get(s);
	}

	/**
	 * Used to determine if there is a marble at a specific coordinate.
	 * 
	 * @param s (coordinate)
	 * @return true if marble is found else false.
	 */
	public boolean hasMarble(String s) {
		if (map.get(s) != Marble.EE) {
			return true;
		}
		return false;
	}

	/**
	 * Used to determine whether a coordinate exists in the board.
	 * 
	 * @param s (coordinate)
	 * @return true if the coordinate exists else returns false.
	 */
	public boolean isValidCoordinate(String s) {
		if (map.containsKey(s)) {
			return true;
		}
		return false;
	}

	/**
	 * Used to check if a game has a winner
	 * 
	 * @return true if the game has a winner
	 */
	boolean hasWinner() {
		int team1 = 0;
		int team2 = 0;
		int black = 0;
		int white = 0;
		int yellow = 0;
		if (gamemode(layout) == 4) {
			for (Marble elims : eliminated) {
				if (elims == Marble.BB || elims == Marble.WW) {
					team1++;
				} else if (elims == Marble.RR || elims == Marble.YY) {
					team2++;
				}
			}
			if (team1 == 6 || team2 == 6) {
				return true;
			}

		} else if (gamemode(layout) == 3) {
			for (Marble elims : eliminated) {
				if (elims == Marble.BB) {
					black++;
				} else if (elims == Marble.WW) {
					white++;
				} else if (elims == Marble.YY) {
					yellow++;
				}
			}
			if (black + white == 6 || black + yellow == 6 || yellow + white == 6) {
				return true;
			}

		} else if (gamemode(layout) == 2) {
			for (Marble elims : eliminated) {
				if (elims == Marble.BB) {
					black++;
				} else if (elims == Marble.WW) {
					white++;
				}
			}
			if (black == 6 || white == 6) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Used to determine if the current player is the winner.
	 * 
	 * @param m
	 * @return
	 */
	public boolean isWinner(Marble m) {
		int team1 = 0;
		int team2 = 0;
		int opponent = 0;
		int player = 0;
		if (gamemode(layout) == 4) {
			for (Marble elims : eliminated) {
				if (Marble.returnTeammate(m) == elims || m == elims) {
					team1++;
				} else {
					team2++;
				}
			}
			if (team1 == 6) {
				return false;
			} else if (team2 == 6) {
				return true;
			}

		} else if (gamemode(layout) == 3) {
			for (Marble elims : eliminated) {
				if (elims != m) {
					opponent++;
				} else if (elims == m) {
					player++;
				}
			}
			if (opponent == 6) {
				return true;
			} else if (player == 6) {
				return false;
			}

		} else if (gamemode(layout) == 2) {
			for (Marble elims : eliminated) {
				if (elims != m) {
					opponent++;
				} else if (elims == m) {
					player++;
				}
			}
			if (opponent == 6) {
				return true;
			} else if (player == 6) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Used to validate a one marble move
	 * 
	 * @param pos1
	 * @param direction
	 * @param m
	 * @return true if valid move else false
	 */
	public boolean isValidMove(String pos1, int direction, Marble m) {
		return makeMove(pos1, direction, m);
	}

	/**
	 * Used to validate a two marble move
	 * 
	 * @param pos1
	 * @param pos2
	 * @param direction
	 * @param m
	 * @return true if valid move else false
	 */
	public boolean isValidMove(String pos1, String pos2, int direction, Marble m) {
		return makeMove(pos1, pos2, direction, m);
	}

	/**
	 * Used to validate a three marble move
	 * 
	 * @param pos1
	 * @param pos2
	 * @param pos3
	 * @param direction
	 * @param m
	 * @return true if valid move else false
	 */
	public boolean isValidMove(String pos1, String pos2, String pos3, int direction, Marble m) {
		return makeMove(pos1, pos2, pos3, direction, m);
	}

	/**
	 * Used to make a one marble move
	 * 
	 * @param pos1
	 * @param direction
	 * @param m
	 * @return true if move is made else false
	 */
	public boolean makeMove(String pos1, int direction, Marble m) {
		Move move = new Move(this);
		return move.moveWithoutPushing(pos1, direction, m);

	}

	/**
	 * Used to make a two marble move
	 * 
	 * @param pos1,     pos2
	 * @param direction
	 * @param m
	 * @return true if move is made else false
	 */
	public boolean makeMove(String pos1, String pos2, int direction, Marble m) {
		Move move = new Move(this);
		String headcoord;
		if (move.nextCoordinates(pos1, direction).equals(pos2)) {
			headcoord = pos2;
		} else {
			headcoord = pos1;
		}
		if (getMarble(move.nextCoordinates(headcoord, direction)) == Marble.EE) {
			return move.moveWithoutPushing(pos1, pos2, direction, m);
		} else {
			return move.moveWithPushing(pos1, pos2, direction, m);
		}
	}

	/**
	 * Used to make a three marble move
	 * 
	 * @param pos1,     pos2, pos3
	 * @param direction
	 * @param m
	 * @return true if move is made else false
	 */
	public boolean makeMove(String pos1, String pos2, String pos3, int direction, Marble m) {
		Move move = new Move(this);
		List<String> list = new ArrayList<String>();
		String headcoord;
		list.add(pos1);
		list.add(pos2);
		list.add(pos3);
		Collections.sort(list);
		if (move.nextCoordinates(list.get(1), direction).equals(list.get(0))) {
			headcoord = list.get(0);
		} else {
			headcoord = list.get(2);
		}
		if (getMarble(move.nextCoordinates(headcoord, direction)) == Marble.EE) {
			return move.moveWithoutPushing(pos1, pos2, pos3, direction, m);
		} else {
			return move.moveWithPushing(pos1, pos2, pos3, direction, m);
		}
	}

	/**
	 * Returns number of turns performed
	 * 
	 * @return an integer turns
	 */
	public int getTurns() {
		return turns;
	}

	/*
	 * Used to reset a board to its original state.
	 */
	public void reset() {
		map = layout.returnLayoutMap();
		eliminated.removeAll(eliminated);
		turns = 0;
	}

	public void removePlayersMarbles(Marble m) {
		for (String k : map.keySet()) {
			if (map.get(k) == m) {
				map.replace(k, Marble.EE);
			}
		}
	}
	
	public List<Marble> geteliminated(){
		return eliminated;
	}

	public List<Marble> orderofMarbles(int i) {
		List<Marble> l = new ArrayList<Marble>();
		switch (i) {
		case 2:
			l.add(Marble.WW);
			l.add(Marble.BB);
			break;
		case 3:
			l.add(Marble.WW);
			l.add(Marble.BB);
			l.add(Marble.YY);
			break;
		case 4:
			l.add(Marble.WW);
			l.add(Marble.RR);
			l.add(Marble.BB);
			l.add(Marble.YY);
			break;
		}
		return l;
	}

	/**
	 * Used to print out the coordinates of the board in an arranged format.
	 * 
	 * @return the board in coordinates form
	 */
	public String printBoardCoords() {
		String board = "";
		String ds = "  ";
		for (String k : map.keySet()) {
			switch (k) {
			case "1A":
				board = board + ds + ds + ds + ds;
				break;
			case "1B":
				board = board + ds + ds + ds;
				break;
			case "1C":
				board = board + ds + ds;
				break;
			case "1D":
				board = board + ds;
				break;
			case "2F":
				board = board + ds;
				break;
			case "3G":
				board = board + ds + ds;
				break;
			case "4H":
				board = board + ds + ds + ds;
				break;
			case "5I":
				board = board + ds + ds + ds + ds;
				break;
			}
			board = board + k + ds;
			if (k.equals("9I") || k.equals("9H") || k.equals("9G") || k.equals("9F") || k.equals("9E") || k.equals("8D")
					|| k.equals("7C") || k.equals("6B") || k.equals("5A")) {
				board = board + "\n";
			}
		}
		return board;
	}

	/**
	 * Used to print out the marbles at each position in the board
	 * 
	 * @return the board in a marble form.
	 */
	public String printBoardValues() {
		String board = "";
		String ds = "  ";
		for (String k : map.keySet()) {
			switch (k) {
			case "1A":
				board = board + ds + ds + ds + ds;
				break;
			case "1B":
				board = board + ds + ds + ds;
				break;
			case "1C":
				board = board + ds + ds;
				break;
			case "1D":
				board = board + ds;
				break;
			case "2F":
				board = board + ds;
				break;
			case "3G":
				board = board + ds + ds;
				break;
			case "4H":
				board = board + ds + ds + ds;
				break;
			case "5I":
				board = board + ds + ds + ds + ds;
				break;
			}
			board = board + map.get(k) + ds;
			if (k.equals("9I") || k.equals("9H") || k.equals("9G") || k.equals("9F") || k.equals("9E") || k.equals("8D")
					|| k.equals("7C") || k.equals("6B") || k.equals("5A")) {
				board = board + "\n";
			}
		}
		return board;
	}

	/**
	 * Determines if a game has ended or not
	 * 
	 * @return true if it has ended else false
	 */
	public boolean gameOver() {
		if (hasWinner() == true || turns == 96) {
			return true;
		}
		return false;
	}
}
