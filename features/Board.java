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
		if(map.get(s)!=Marble.EE) {
			return true;
		}
		return false;
	}
	

	public static void main(String[] args) {
		Board board = new Board(new Layout(2));
		System.out.print("    ");
		for (String k : Board.map.keySet()) {
			if (k.equals("9I")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'I') {
				System.out.print(k + " ");
			}
		}
		System.out.print("   ");
		for (String k : Board.map.keySet()) {
			if (k.equals("9H")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'H') {
				System.out.print(k + " ");
			}
		}
		System.out.print("  ");
		for (String k : Board.map.keySet()) {
			if (k.equals("9G")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'G') {
				System.out.print(k + " ");
			}
		}
		System.out.print(" ");
		for (String k : Board.map.keySet()) {
			if (k.equals("9F")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'F') {
				System.out.print(k + " ");
			}
		}
		System.out.print("");
		for (String k : Board.map.keySet()) {
			if (k.equals("9E")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'E') {
				System.out.print(k + " ");
			}
		}
		System.out.print(" ");
		for (String k : Board.map.keySet()) {
			if (k.equals("8D")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'D') {
				System.out.print(k + " ");
			}
		}
		System.out.print("  ");
		for (String k : Board.map.keySet()) {
			if (k.equals("7C")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'C') {
				System.out.print(k + " ");
			}
		}
		System.out.print("   ");
		for (String k : Board.map.keySet()) {
			if (k.equals("6B")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'B') {
				System.out.print(k + " ");
			}
		}
		System.out.print("    ");
		for (String k : Board.map.keySet()) {
			if (k.equals("5A")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'A') {
				System.out.print(k + " ");
			}
		}
		System.out.println("\n");
		System.out.println();

		System.out.print("    ");
		for (String k : Board.map.keySet()) {
			if (k.equals("9I")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'I') {
				System.out.print(Board.map.get(k) + " ");
			}
		}
		System.out.print("   ");
		for (String k : Board.map.keySet()) {
			if (k.equals("9H")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'H') {
				System.out.print(Board.map.get(k) + " ");
			}
		}
		System.out.print("  ");
		for (String k : Board.map.keySet()) {
			if (k.equals("9G")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'G') {
				System.out.print(Board.map.get(k) + " ");
			}
		}
		System.out.print(" ");
		for (String k : Board.map.keySet()) {
			if (k.equals("9F")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'F') {
				System.out.print(Board.map.get(k) + " ");
			}
		}
		System.out.print("");
		for (String k : Board.map.keySet()) {
			if (k.equals("9E")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'E') {
				System.out.print(Board.map.get(k) + " ");
			}
		}
		System.out.print(" ");
		for (String k : Board.map.keySet()) {
			if (k.equals("8D")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'D') {
				System.out.print(Board.map.get(k) + " ");
			}
		}
		System.out.print("  ");
		for (String k : Board.map.keySet()) {
			if (k.equals("7C")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'C') {
				System.out.print(Board.map.get(k) + " ");
			}
		}
		System.out.print("   ");
		for (String k : Board.map.keySet()) {
			if (k.equals("6B")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'B') {
				System.out.print(Board.map.get(k) + " ");
			}
		}
		System.out.print("    ");
		for (String k : Board.map.keySet()) {
			if (k.equals("5A")) {
				System.out.print(Board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'A') {
				System.out.print(Board.map.get(k) + " ");
			}
		}

	}

}
