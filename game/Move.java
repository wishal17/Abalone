package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Move {
	private Board b;

	public Move(Board board) {
		this.b = board;
		// TODO Auto-generated constructor stub
	}

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
	 * Gets the next coordinate of a marble in the given direction
	 * @param pos
	 * @param direction
	 * @return the next coordinate in the given direction
	 */
	public String nextCoordinates(String pos, int direction) {
		String newposition = "";
		if (b.isValidCoordinate(pos)) {
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
		if (b.isValidCoordinate(newposition)) {
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
		if (b.getLayout().returnPlayers().equals("four")) {
			if (!m.isTeammate(b.getMarble(onenexttohead))
					&& (b.getMarble(twonexttohead) == null || b.getMarble(twonexttohead) == Marble.EE)) {
				return true;
			} else {
				return false;
			}
		} else if (b.getLayout().returnPlayers().equals("three") || b.getLayout().returnPlayers().equals("two")) {
			if (b.getMarble(onenexttohead) != m
					&& (b.getMarble(twonexttohead) == null || b.getMarble(twonexttohead) == Marble.EE)) {
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
		if (b.getLayout().returnPlayers().equals("four")) {
			if (!m.isTeammate(b.getMarble(onenexttohead))
					&& (b.getMarble(twonexttohead) == null || b.getMarble(twonexttohead) == Marble.EE)) {
				return true;
			}
			if (!m.isTeammate(b.getMarble(onenexttohead)) && !m.isTeammate(b.getMarble(twonexttohead))
					&& (b.getMarble(threenexttohead) == null || b.getMarble(threenexttohead) == Marble.EE)) {
				return true;
			} else {
				return false;
			}
		} else if (b.getLayout().returnPlayers().equals("three") || b.getLayout().returnPlayers().equals("two")) {
			if (b.getMarble(onenexttohead) != m && b.getMarble(twonexttohead) != m
					&& (b.getMarble(threenexttohead) == null || b.getMarble(threenexttohead) == Marble.EE)) {
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
		if (b.map.get(pos) == m && !b.hasMarble(nextcoordspos)) {
			b.map.put(nextcoordspos, m);
			b.map.put(pos, Marble.EE);
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
			if (b.map.get(pos1) == m && b.map.get(pos2) == m && (!b.hasMarble(nextheadcoord))) {
				b.map.put(nextheadcoord, m);
				b.map.put(nextendcoord, m);
				b.map.put(endcoord, Marble.EE);
				return true;
			}
			return false;
		} else {
			if (b.map.get(pos1) == m && b.map.get(pos2) == m && (!b.hasMarble(nextheadcoord))) {
				b.map.put(nextCoordinates(pos1, direction), m);
				b.map.put(nextCoordinates(pos2, direction), m);
				b.map.put(pos1, Marble.EE);
				b.map.put(pos2, Marble.EE);
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
			if (b.map.get(pos1) == m && b.map.get(pos2) == m && b.map.get(pos3) == m && (!b.hasMarble(nextheadcoord))) {
				b.map.put(nextheadcoord, m);
				b.map.put(nextendcoord, m);
				b.map.put(endcoord, Marble.EE);
				return true;
			}
			return false;
		} else {
			if (b.map.get(pos1) == m && b.map.get(pos2) == m && b.map.get(pos3) == m
					&& (!b.hasMarble(nextCoordinates(pos1, direction)))
					&& (!b.hasMarble(nextCoordinates(pos1, direction)))
					&& (!b.hasMarble(nextCoordinates(pos1, direction)))) {
				b.map.put(nextCoordinates(pos1, direction), m);
				b.map.put(nextCoordinates(pos2, direction), m);
				b.map.put(nextCoordinates(pos3, direction), m);
				b.map.put(pos1, Marble.EE);
				b.map.put(pos2, Marble.EE);
				b.map.put(pos3, Marble.EE);
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
		if (b.map.get(pos1) == m && b.map.get(pos2) == m && b.hasMarble(onenextheadcoord)) {
			if (pushtwovsEnemyMarbles(headcoord, direction, m) == false) {
				return false;
			} else if (pushtwovsEnemyMarbles(headcoord, direction, m) == true) {
				Marble onenexttoheadMarble = b.getMarble(onenextheadcoord);
				String twonextheadcoord = nextCoordinates(onenextheadcoord, direction);
				if (b.getMarble(twonextheadcoord) == null) {
					b.map.put(onenextheadcoord, m);
					b.map.put(endcoord, Marble.EE);
					b.eliminated.add(onenexttoheadMarble);
					return true;
				} else if (b.getMarble(twonextheadcoord) == Marble.EE) {
					b.map.put(twonextheadcoord, onenexttoheadMarble);
					b.map.put(onenextheadcoord, m);
					b.map.put(endcoord, Marble.EE);
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

		if (b.map.get(pos1) == m && b.map.get(pos2) == m && b.map.get(pos3) == m
				&& (b.hasMarble(onenextheadcoord) || b.hasMarble(twonextheadcoord))) {
			if (pushthreevsEnemyMarbles(headcoord, direction, m) == false) {
				return false;
			} else if (pushthreevsEnemyMarbles(headcoord, direction, m) == true) {
				Marble onenexttoheadMarble = b.getMarble(onenextheadcoord);
				Marble twonexttoheadMarble = b.getMarble(twonextheadcoord);
				if (twonexttoheadMarble == null) {
					b.map.put(endcoord, Marble.EE);
					moveWithPushing(midcoord, headcoord, direction, m);
					b.map.put(midcoord, m);
					return true;
				} else {
					if (b.getMarble(threenextheadcoord) == null) {
						if (b.getMarble(twonextheadcoord) != Marble.EE) {
							b.map.put(onenextheadcoord, m);
							b.map.put(twonextheadcoord, onenexttoheadMarble);
							b.map.put(endcoord, Marble.EE);
							b.eliminated.add(twonexttoheadMarble);
						} else if (b.getMarble(twonextheadcoord) == Marble.EE) {
							b.map.put(onenextheadcoord, m);
							b.map.put(twonextheadcoord, onenexttoheadMarble);
							b.map.put(endcoord, Marble.EE);
						}
						return true;
					} else if (b.getMarble(threenextheadcoord) == Marble.EE) {
						b.map.put(threenextheadcoord, twonexttoheadMarble);
						b.map.put(twonextheadcoord, onenexttoheadMarble);
						b.map.put(onenextheadcoord, m);
						b.map.put(endcoord, Marble.EE);
						return true;
					}
				}

			}
		}
		return false;
	}
}
