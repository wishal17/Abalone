package features;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Board {
	private static final char[] XAXIS = "123456789".toCharArray();
	private static final char[] YAXIS = "ABCDEFGHI".toCharArray();
	private Marble[] values;
	private Layout layout;
	public static Map<String, Marble> map = new LinkedHashMap<String, Marble>();

	/*
	 * Creates a board with the specified layout
	 */
	public Board(Layout l) {
		this.layout = layout;
		map = l.returnLayoutMap();
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

	public boolean isPlayersMarble(Marble m, String s) {
		if (map.get(s) != m) {
			return false;
		}
		return true;
	}

	public static boolean isValidCoordinate(String s) {
		if (map.containsKey(s)) {
			return true;
		}
		return false;
	}

	public boolean validMove(Marble m, String s) {
		return isPlayersMarble(m, s) && isValidCoordinate(s);
	}

	boolean hasLoser(Marble m) {
		int count = 0;
		for (String key : map.keySet()) {
			if (map.get(key) == m) {
				count++;
			}
		}
		if(count <= 8) {
			return true;
		}
		return false;
	}


	public void makeMove(Move m) {

	}

	public static void main(String[] args) {

	}
}
