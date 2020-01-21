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

	public static void main(String[] args) {

	}
}
