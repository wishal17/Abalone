package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
	private static Layout layout;
	public static Map<String, Marble> map = new LinkedHashMap<String, Marble>();
	public static List<Marble> eliminated = new LinkedList<Marble>();

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
		Board copy = new Board(Board.layout);
		return copy;
	}

	public static Layout getLayout() {
		return layout;
	}

	/**
	 * Returns the number of players in an active match
	 * 
	 * @param l
	 */
	public static int gamemode(Layout l) {
		if (l.returnPlayers().equals("two")) {
			return 2;
		} else if (l.returnPlayers().equals("three")) {
			return 3;
		} else if (l.returnPlayers().equals("four")) {
			return 4;
		}
		return -1;
	}

	public Map<String, Marble> returnBoardMap() {
		return map;
	}

	public static Marble getMarble(String s) {
		return map.get(s);
	}

	public static boolean hasMarble(String s) {
		if (map.get(s) != Marble.EE) {
			return true;
		}
		return false;
	}

	public static boolean isValidCoordinate(String s) {
		if (map.containsKey(s)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return if the game has a winner
	 */
	boolean hasWinner() {
		int team1 = 0;
		int team2 = 0;
		int black = 0;
		int white = 0;
		int yellow = 0;
		if (gamemode(Board.layout) == 4) {
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

		} else if (gamemode(Board.layout) == 3) {
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

		} else if (gamemode(Board.layout) == 2) {
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
	
	public boolean isWinner(Marble m) {
		int team1 = 0;
		int team2 = 0;
		int opponent = 0;
		int player = 0;
		if (gamemode(Board.layout) == 4) {
			for (Marble elims : eliminated) {
				if (Marble.returnTeammate(m) == elims || m == elims) {
					team1++;
				} else{
					team2++;
				}
			}
			if (team1 == 6) {
				return false;
			} else if(team2 == 6) {
				return true;
			}

		} else if (gamemode(Board.layout) == 3) {
			for (Marble elims : eliminated) {
				if (elims != m) {
					opponent++;
				} else if (elims == m) {
					player++;
				}
			}
			if (opponent == 6) {
				return true;
			} else if(player ==6) {
				return false;
			}

		} else if (gamemode(Board.layout) == 2) {
			for (Marble elims : eliminated) {
				if (elims != m) {
					opponent++;
				} else if (elims == m) {
					player++;
				}
			}
			if (opponent == 6) {
				return true;
			} else if(player == 6) {
				return false;
			}
		}
		return false;
	}

	public boolean isValidMove(String pos1, int direction, Marble m) {
		return makeMove(pos1,direction, m);
	}
	
	public boolean isValidMove(String pos1, String pos2, int direction, Marble m) {
		return makeMove(pos1, pos2, direction, m);
	}
	
	public boolean isValidMove(String pos1, String pos2, String pos3, int direction, Marble m) {
		return makeMove(pos1, pos2, pos3, direction, m);
	}
	
	public boolean makeMove(String pos1, int direction, Marble m) {
		Move move = new Move();
		return move.moveWithoutPushing(pos1, direction, m);

	}

	public boolean makeMove(String pos1, String pos2, int direction, Marble m) {
		Move move = new Move();
		String headcoord;
		if (Move.nextCoordinates(pos1, direction).equals(pos2)) {
			headcoord = pos2;
		} else {
			headcoord = pos1;
		}
		if (Board.getMarble(Move.nextCoordinates(headcoord, direction)) == Marble.EE) {
			return move.moveWithoutPushing(pos1, pos2, direction, m);
		} else {
			return move.moveWithPushing(pos1, pos2, direction, m);
		}
	}

	public boolean makeMove(String pos1, String pos2, String pos3, int direction, Marble m) {
		Move move = new Move();
		List<String> list = new ArrayList<String>();
		String headcoord;
		list.add(pos1);
		list.add(pos2);
		list.add(pos3);
		Collections.sort(list);
		if (Move.nextCoordinates(list.get(1), direction).equals(list.get(0))) {
			headcoord = list.get(0);
		} else {
			headcoord = list.get(2);
		}
		if (Board.getMarble(Move.nextCoordinates(headcoord, direction)) == Marble.EE) {
			return move.moveWithoutPushing(pos1, pos2, pos3, direction, m);
		} else {
			return move.moveWithPushing(pos1, pos2, pos3, direction, m);
		}
	}

	public void reset() {
		map = Board.layout.returnLayoutMap();
		eliminated.removeAll(eliminated);
	}

	 public String printBoardCoords() {
	        String board = "";
	        String ds = " ";
	        for (String k : Board.map.keySet()) {
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
	        if (k.equals("9I") || k.equals("9H") || k.equals("9G") || k.equals("9F") || k.equals("9E") || 
	                k.equals("8D") || k.equals("7C") || k.equals("6B") || k.equals("5A")) {
	                board = board + "\n";
	            }
	        }
	        return board;
	    }
	 
	 public String printBoardValues() {
	        String board = "";
	        String ds = " ";
	        for (String k : Board.map.keySet()) {
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
	        if (k.equals("9I") || k.equals("9H") || k.equals("9G") || k.equals("9F") || k.equals("9E") || 
	                k.equals("8D") || k.equals("7C") || k.equals("6B") || k.equals("5A")) {
	                board = board + "\n";
	            }
	        }
	        return board;
	    }

	public boolean gameOver() {
		if (hasWinner()==true) {
			return true;
		}
		return false;
	}
}
