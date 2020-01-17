package features;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Board {
	private static final char[] XAXIS = "123456789".toCharArray();
	private static final char[] YAXIS = "ABCDEFGHI".toCharArray();
	private Marble[] values;
	private Layout layout;
	private Map<String, Marble> map = new LinkedHashMap<String, Marble>();

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

	public static void main(String[] args) {
		Board board = new Board(new Layout(3));
		System.out.print("    ");
		for (String k : board.map.keySet()) {
			if (k.equals("9I")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'I') {
				System.out.print(k + " ");
			}
		}
		System.out.print("   ");
		for (String k : board.map.keySet()) {
			if (k.equals("9H")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'H') {
				System.out.print(k + " ");
			}
		}
		System.out.print("  ");
		for (String k : board.map.keySet()) {
			if (k.equals("9G")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'G') {
				System.out.print(k + " ");
			}
		}
		System.out.print(" ");
		for (String k : board.map.keySet()) {
			if (k.equals("9F")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'F') {
				System.out.print(k + " ");
			}
		}
		System.out.print("");
		for (String k : board.map.keySet()) {
			if (k.equals("9E")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'E') {
				System.out.print(k + " ");
			}
		}
		System.out.print(" ");
		for (String k : board.map.keySet()) {
			if (k.equals("8D")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'D') {
				System.out.print(k + " ");
			}
		}
		System.out.print("  ");
		for (String k : board.map.keySet()) {
			if (k.equals("7C")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'C') {
				System.out.print(k + " ");
			}
		}
		System.out.print("   ");
		for (String k : board.map.keySet()) {
			if (k.equals("6B")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'B') {
				System.out.print(k + " ");
			}
		}
		System.out.print("    ");
		for (String k : board.map.keySet()) {
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
		for (String k : board.map.keySet()) {
			if (k.equals("9I")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'I') {
				System.out.print(board.map.get(k) + " ");
			}
		}
		System.out.print("   ");
		for (String k : board.map.keySet()) {
			if (k.equals("9H")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'H') {
				System.out.print(board.map.get(k) + " ");
			}
		}
		System.out.print("  ");
		for (String k : board.map.keySet()) {
			if (k.equals("9G")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'G') {
				System.out.print(board.map.get(k) + " ");
			}
		}
		System.out.print(" ");
		for (String k : board.map.keySet()) {
			if (k.equals("9F")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'F') {
				System.out.print(board.map.get(k) + " ");
			}
		}
		System.out.print("");
		for (String k : board.map.keySet()) {
			if (k.equals("9E")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'E') {
				System.out.print(board.map.get(k) + " ");
			}
		}
		System.out.print(" ");
		for (String k : board.map.keySet()) {
			if (k.equals("8D")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'D') {
				System.out.print(board.map.get(k) + " ");
			}
		}
		System.out.print("  ");
		for (String k : board.map.keySet()) {
			if (k.equals("7C")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'C') {
				System.out.print(board.map.get(k) + " ");
			}
		}
		System.out.print("   ");
		for (String k : board.map.keySet()) {
			if (k.equals("6B")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'B') {
				System.out.print(board.map.get(k) + " ");
			}
		}
		System.out.print("    ");
		for (String k : board.map.keySet()) {
			if (k.equals("5A")) {
				System.out.print(board.map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'A') {
				System.out.print(board.map.get(k) + " ");
			}
		}

	}

}
