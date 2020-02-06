package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Move extends Board {
	public Move(Layout l) {
		super(l);
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
	 * 
	 * @param pos
	 * @param direction
	 * @return the next coordinate in the given direction
	 */
	public String nextCoordinates(String pos, int direction) {
		String newposition = "";
		if (isValidCoordinate(pos)) {
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
		if (isValidCoordinate(newposition)) {
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
		if (getLayout().returnPlayers().equals("four")) {
			if (!m.isTeammate(getMarble(onenexttohead))
					&& (getMarble(twonexttohead) == null || getMarble(twonexttohead) == Marble.EE)) {
				return true;
			} else {
				return false;
			}
		} else if (getLayout().returnPlayers().equals("three")
				|| getLayout().returnPlayers().equals("two")) {
			if (getMarble(onenexttohead) != m
					&& (getMarble(twonexttohead) == null || getMarble(twonexttohead) == Marble.EE)) {
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
		if (getLayout().returnPlayers().equals("four")) {
			if (!m.isTeammate(getMarble(onenexttohead)) && !m.isTeammate(getMarble(twonexttohead))
					&& (getMarble(threenexttohead) == null || getMarble(threenexttohead) == Marble.EE)) {
				return true;
			} else {
				return false;
			}
		} else if (getLayout().returnPlayers().equals("three")
				|| getLayout().returnPlayers().equals("two")) {
			if (getMarble(onenexttohead) != m && getMarble(twonexttohead) != m
					&& (getMarble(threenexttohead) == null || getMarble(threenexttohead) == Marble.EE)) {
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
		if (map.get(pos) == m && !hasMarble(nextcoordspos)) {
			map.put(nextcoordspos, m);
			map.put(pos, Marble.EE);
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
			if (map.get(pos1) == m && map.get(pos2) == m && (!hasMarble(nextheadcoord))) {
				map.put(nextheadcoord, m);
				map.put(nextendcoord, m);
				map.put(endcoord, Marble.EE);
				return true;
			}
			return false;
		} else {
			if (map.get(pos1) == m && map.get(pos2) == m && (!hasMarble(nextheadcoord))) {
				map.put(nextCoordinates(pos1, direction), m);
				map.put(nextCoordinates(pos2, direction), m);
				map.put(pos1, Marble.EE);
				map.put(pos2, Marble.EE);
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
			if (map.get(pos1) == m && map.get(pos2) == m && map.get(pos3) == m
					&& (!hasMarble(nextheadcoord))) {
				map.put(nextheadcoord, m);
				map.put(nextendcoord, m);
				map.put(endcoord, Marble.EE);
				return true;
			}
			return false;
		} else {
			if (map.get(pos1) == m && map.get(pos2) == m && map.get(pos3) == m
					&& (!hasMarble(nextCoordinates(pos1, direction)))
					&& (!hasMarble(nextCoordinates(pos1, direction)))
					&& (!hasMarble(nextCoordinates(pos1, direction)))) {
				map.put(nextCoordinates(pos1, direction), m);
				map.put(nextCoordinates(pos2, direction), m);
				map.put(nextCoordinates(pos3, direction), m);
				map.put(pos1, Marble.EE);
				map.put(pos2, Marble.EE);
				map.put(pos3, Marble.EE);
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
		if (map.get(pos1) == m && map.get(pos2) == m && hasMarble(onenextheadcoord)) {
			if (pushtwovsEnemyMarbles(headcoord, direction, m) == false) {
				return false;
			} else if (pushtwovsEnemyMarbles(headcoord, direction, m) == true) {
				Marble onenexttoheadMarble = getMarble(onenextheadcoord);
				String twonextheadcoord = nextCoordinates(onenextheadcoord, direction);
				if (getMarble(twonextheadcoord) == null) {
					map.put(onenextheadcoord, m);
					map.put(endcoord, Marble.EE);
					eliminated.add(onenexttoheadMarble);
					return true;
				} else if (getMarble(twonextheadcoord) == Marble.EE) {
					map.put(twonextheadcoord, onenexttoheadMarble);
					map.put(onenextheadcoord, m);
					map.put(endcoord, Marble.EE);
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

		if (map.get(pos1) == m && map.get(pos2) == m && map.get(pos3) == m
				&& (hasMarble(onenextheadcoord) || hasMarble(twonextheadcoord))) {
			if (pushthreevsEnemyMarbles(headcoord, direction, m) == false) {
				return false;
			} else if (pushthreevsEnemyMarbles(headcoord, direction, m) == true) {
				Marble onenexttoheadMarble = getMarble(onenextheadcoord);
				Marble twonexttoheadMarble = getMarble(twonextheadcoord);
				if (twonexttoheadMarble == null) {
					map.put(endcoord, Marble.EE);
					moveWithPushing(midcoord, headcoord, direction, m);
					map.put(midcoord, m);
					return true;
				} else {
					if (getMarble(threenextheadcoord) == null) {
						map.put(onenextheadcoord, m);
						map.put(twonextheadcoord, onenexttoheadMarble);
						map.put(endcoord, Marble.EE);
						eliminated.add(twonexttoheadMarble);
						return true;
					} else if (getMarble(threenextheadcoord) == Marble.EE) {
						map.put(threenextheadcoord, twonexttoheadMarble);
						map.put(twonextheadcoord, onenexttoheadMarble);
						map.put(onenextheadcoord, m);
						map.put(endcoord, Marble.EE);
						return true;
					}
				}

			}
		}
		return false;
	}
}
