package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Move {
	/**
	 * Increments the YAXIS of the coordinate
	 * 
	 * @param a
	 * @return gives the next YAXIS value
	 */
	public static char nextYAXIS(char a) {
		return ++a;
	}

	/**
	 * Decrements the YAXIS of the coordinate
	 * 
	 * @param a
	 * @return gives the previous YAXIS value
	 */
	public static char previousYAXIS(char a) {
		return --a;
	}

	/**
	 * 
	 * @param pos
	 * @param direction
	 * @return the next coordinate in the given direction
	 */
	public static String nextCoordinates(String pos, int direction) {
		String newposition = "";
		if (Board.isValidCoordinate(pos)) {
			int num = Character.getNumericValue(pos.charAt(0));
			char YAXIS = pos.charAt(1);

			switch (direction) {
			case 1:
				newposition = "" + num + nextYAXIS(YAXIS);
				break;
			case 2:
				newposition = "" + (num + 1) + nextYAXIS(YAXIS);
				break;
			case 3:
				newposition = "" + (num + 1) + YAXIS;
				break;
			case 4:
				newposition = "" + num + previousYAXIS(YAXIS);
				break;
			case 5:
				newposition = "" + (num - 1) + previousYAXIS(YAXIS);
				break;
			case 6:
				newposition = "" + (num - 1) + YAXIS;
				break;
			default:
				newposition = null;
			}
		}
		if (Board.isValidCoordinate(newposition)) {
			return newposition;
		}
		return null;
	}

	/**
	 * Checks if there are more team/player marbles than opponent marbles for
	 * pushing
	 * 
	 * @param pos
	 * @param direction
	 * @param m
	 * @return true if the move is successful else returns false.
	 */
	public boolean pushtwovsEnemyMarbles(String pos, int direction, Marble m) {
		String onenexttohead = nextCoordinates(pos, direction);
		String twonexttohead = nextCoordinates(onenexttohead, direction);
		if (Board.getLayout().returnPlayers().equals("four")) {
			if (!m.isTeammate(Board.getMarble(onenexttohead))
					&& (Board.getMarble(twonexttohead) == null || Board.getMarble(twonexttohead) == Marble.EE)) {
				return true;
			} else {
				return false;
			}
		} else if (Board.getLayout().returnPlayers().equals("three")
				|| Board.getLayout().returnPlayers().equals("two")) {
			if (Board.getMarble(onenexttohead) != m
					&& (Board.getMarble(twonexttohead) == null || Board.getMarble(twonexttohead) == Marble.EE)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Checks if there are more team/player marbles than opponent marbles for
	 * pushing
	 * 
	 * @param pos
	 * @param direction
	 * @param m
	 * @return true if the move is possibles else returns false.
	 */
	public boolean pushthreevsEnemyMarbles(String pos, int direction, Marble m) {
		String onenexttohead = nextCoordinates(pos, direction);
		String twonexttohead = nextCoordinates(onenexttohead, direction);
		String threenexttohead = nextCoordinates(twonexttohead, direction);
		if (Board.getLayout().returnPlayers().equals("four")) {
			if (!m.isTeammate(Board.getMarble(onenexttohead)) && !m.isTeammate(Board.getMarble(twonexttohead))
					&& (Board.getMarble(threenexttohead) == null || Board.getMarble(threenexttohead) == Marble.EE)) {
				return true;
			} else {
				return false;
			}
		} else if (Board.getLayout().returnPlayers().equals("three")
				|| Board.getLayout().returnPlayers().equals("two")) {
			if (Board.getMarble(onenexttohead) != m && Board.getMarble(twonexttohead) != m
					&& (Board.getMarble(threenexttohead) == null || Board.getMarble(threenexttohead) == Marble.EE)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Moves a single marble in a given direction without pushing any opponent
	 * marbles
	 * 
	 * @param pos
	 * @param direction
	 * @param m
	 * @return true if the move is successful else returns false.
	 */
	public boolean moveWithoutPushing(String pos, int direction, Marble m) {
		String nextcoordspos = nextCoordinates(pos, direction);
		if (Board.map.get(pos) == m && !Board.hasMarble(nextcoordspos)) {
			Board.map.put(nextcoordspos, m);
			Board.map.put(pos, Marble.EE);
			return true;
		}
		return false;
	}

	/**
	 * Moves a pair of marbles in a given direction without pushing any opponent
	 * marbles
	 * 
	 * @param pos1
	 * @param pos2
	 * @param direction
	 * @param m
	 * @return true if the move is successful else returns false.
	 */
	public boolean moveWithoutPushing(String pos1, String pos2, int direction, Marble m) {
		String headcoord;
		String endcoord;
		if (nextCoordinates(pos1, direction).equals(pos2)) {
			headcoord = pos2;
			endcoord = pos1;
		} else {
			headcoord = pos1;
			endcoord = pos2;
		}
		String nextheadcoord = nextCoordinates(headcoord, direction);
		String nextendcoord = nextCoordinates(endcoord, direction);
		if (nextCoordinates(endcoord, direction).equals(headcoord)) {
			if (Board.map.get(pos1) == m && Board.map.get(pos2) == m && (!Board.hasMarble(nextheadcoord))) {
				Board.map.put(nextheadcoord, m);
				Board.map.put(nextendcoord, m);
				Board.map.put(endcoord, Marble.EE);
				return true;
			}
			return false;
		} else {
			if (Board.map.get(pos1) == m && Board.map.get(pos2) == m && (!Board.hasMarble(nextheadcoord))) {
				Board.map.put(nextCoordinates(pos1, direction), m);
				Board.map.put(nextCoordinates(pos2, direction), m);
				Board.map.put(pos1, Marble.EE);
				Board.map.put(pos2, Marble.EE);
				return true;
			}
			return false;
		}

	}

	/**
	 * Moves three marbles in a given direction without pushing any opponent marbles
	 * 
	 * @param pos1
	 * @param pos2
	 * @param pos3
	 * @param direction
	 * @param m
	 * @return true if the move is successful else returns false.
	 */
	public boolean moveWithoutPushing(String pos1, String pos2, String pos3, int direction, Marble m) {
		List<String> list = new ArrayList<String>();
		String headcoord;
		String endcoord;
		list.add(pos1);
		list.add(pos2);
		list.add(pos3);
		Collections.sort(list);
		if (nextCoordinates(list.get(1), direction).equals(list.get(0))) {
			headcoord = list.get(0);
			endcoord = list.get(2);
		} else {
			headcoord = list.get(2);
			endcoord = list.get(0);
		}
		String nextheadcoord = nextCoordinates(headcoord, direction);
		String nextendcoord = nextCoordinates(endcoord, direction);
		if ((nextCoordinates(list.get(1), direction).equals(list.get(0)))
				|| (nextCoordinates(list.get(0), direction).equals(list.get(1)))) {
			if (Board.map.get(pos1) == m && Board.map.get(pos2) == m && Board.map.get(pos3) == m
					&& (!Board.hasMarble(nextheadcoord))) {
				Board.map.put(nextheadcoord, m);
				Board.map.put(nextendcoord, m);
				Board.map.put(endcoord, Marble.EE);
				return true;
			}
			return false;
		} else {
			if (Board.map.get(pos1) == m && Board.map.get(pos2) == m && Board.map.get(pos3) == m
					&& (!Board.hasMarble(nextCoordinates(pos1, direction)))
					&& (!Board.hasMarble(nextCoordinates(pos1, direction)))
					&& (!Board.hasMarble(nextCoordinates(pos1, direction)))) {
				Board.map.put(nextCoordinates(pos1, direction), m);
				Board.map.put(nextCoordinates(pos2, direction), m);
				Board.map.put(nextCoordinates(pos3, direction), m);
				Board.map.put(pos1, Marble.EE);
				Board.map.put(pos2, Marble.EE);
				Board.map.put(pos3, Marble.EE);
				return true;
			}
			return false;
		}

	}

	/**
	 * Moves two marbles in a given direction by pushing opponent marbles
	 * 
	 * @param pos1
	 * @param pos2
	 * @param direction
	 * @param m
	 * @return true if the move is successful else returns false.
	 */
	public boolean moveWithPushing(String pos1, String pos2, int direction, Marble m) {
		String headcoord;
		String endcoord;
		if (nextCoordinates(pos1, direction) != null && nextCoordinates(pos1, direction).equals(pos2)) {
			headcoord = pos2;
			endcoord = pos1;
		} else {
			headcoord = pos1;
			endcoord = pos2;
		}
		String onenextheadcoord = nextCoordinates(headcoord, direction);
		if (Board.map.get(pos1) == m && Board.map.get(pos2) == m && Board.hasMarble(onenextheadcoord)) {
			if (pushtwovsEnemyMarbles(headcoord, direction, m) == false) {
				return false;
			} else if (pushtwovsEnemyMarbles(headcoord, direction, m) == true) {
				Marble onenexttoheadMarble = Board.getMarble(onenextheadcoord);
				String twonextheadcoord = nextCoordinates(onenextheadcoord, direction);
				if (Board.getMarble(twonextheadcoord) == null) {
					Board.map.put(onenextheadcoord, m);
					Board.map.put(endcoord, Marble.EE);
					Board.eliminated.add(onenexttoheadMarble);
					return true;
				} else if (Board.getMarble(twonextheadcoord) == Marble.EE) {
					Board.map.put(twonextheadcoord, onenexttoheadMarble);
					Board.map.put(onenextheadcoord, m);
					Board.map.put(endcoord, Marble.EE);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Moves three marbles in a given direction by pushing opponent marbles
	 * 
	 * @param pos1
	 * @param pos2
	 * @param pos3
	 * @param direction
	 * @param m
	 * @return
	 */
	public boolean moveWithPushing(String pos1, String pos2, String pos3, int direction, Marble m) {
		List<String> list = new ArrayList<String>();
		String headcoord;
		String endcoord;
		String midcoord;
		list.add(pos1);
		list.add(pos2);
		list.add(pos3);
		Collections.sort(list);
		if (nextCoordinates(list.get(1), direction).equals(list.get(0))) {
			headcoord = list.get(0);
			midcoord = list.get(1);
			endcoord = list.get(2);
		} else {
			headcoord = list.get(2);
			midcoord = list.get(1);
			endcoord = list.get(0);
		}
		String onenextheadcoord = nextCoordinates(headcoord, direction);
		String twonextheadcoord = nextCoordinates(onenextheadcoord, direction);
		String threenextheadcoord = nextCoordinates(twonextheadcoord, direction);

		if (Board.map.get(pos1) == m && Board.map.get(pos2) == m && Board.map.get(pos3) == m
				&& (Board.hasMarble(onenextheadcoord) || Board.hasMarble(twonextheadcoord))) {
			if (pushthreevsEnemyMarbles(headcoord, direction, m) == false) {
				return false;
			} else if (pushthreevsEnemyMarbles(headcoord, direction, m) == true) {
				Marble onenexttoheadMarble = Board.getMarble(onenextheadcoord);
				Marble twonexttoheadMarble = Board.getMarble(twonextheadcoord);
				if (twonexttoheadMarble == null) {
					Board.map.put(endcoord, Marble.EE);
					moveWithPushing(midcoord, headcoord, direction, m);
					Board.map.put(midcoord, m);
					return true;
				} else {
					if (Board.getMarble(threenextheadcoord) == null) {
						Board.map.put(onenextheadcoord, m);
						Board.map.put(twonextheadcoord, onenexttoheadMarble);
						Board.map.put(endcoord, Marble.EE);
						Board.eliminated.add(twonexttoheadMarble);
						return true;
					} else if (Board.getMarble(threenextheadcoord) == Marble.EE) {
						Board.map.put(threenextheadcoord, twonexttoheadMarble);
						Board.map.put(twonextheadcoord, onenexttoheadMarble);
						Board.map.put(onenextheadcoord, m);
						Board.map.put(endcoord, Marble.EE);
						return true;
					}
				}

			}
		}
		return false;
	}
}
