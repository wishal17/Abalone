package features;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Board {
	private static final char[] YAXIS = "123456789".toCharArray();
	private static final char[] XAXIS = "ABCDEFGHI".toCharArray();
	private Marble[] values;
	private int layout;
	private Map<String, Marble> map = new LinkedHashMap<String, Marble>();

	public Board(int layout) {
		this.layout = layout;
		
		/**
		 * Coordinate scheme for the board
		 * To create a blank board, the YAXIS has been split into two parts A-E and F-I.
		 */
		//For X AXIS coordinates A-E
		for (char a : XAXIS) {
			if (a == 'F') {
				break;
			}
			for (int i = 1; i <= 9; i++) {
				if (a == 'A' && i == 6) {
					break;
				}
				if (a == 'B' && i == 7) {
					break;
				}
				if (a == 'C' && i == 8) {
					break;
				}
				if (a == 'D' && i == 9) {
					break;
				}
				if (a == 'E' && i == 10) {
					break;
				}
				map.put(i + "" + a, Marble.EMPTY);
			}
		}
		//For Y AXIS coordinates F-I
		int incrementer = 2;
		int starting = 2;
		for (char a = 'F'; a <= 'I'; a++) {
			while (incrementer <= 9) {
				map.put(incrementer + "" + a, Marble.EMPTY);
				incrementer++;
			}
			starting++;
			incrementer = starting;
		}

		switch (layout) {

		case 2:	// 2-Player Game Formation.
			values = new Marble[61];
			for (int i = 0; i < 61; i++) {
				if (i < 16 && i != 11 && i != 12) {
					values[i] = Marble.BLACK;
				} else if (i > 44 && i != 48 && i != 49) {
					values[i] = Marble.WHITE;
				} else {
					values[i] = Marble.EMPTY;
				}
			}
			int twoplayerlayout = 0;
			for(String k: map.keySet()) {
				map.replace(k, Marble.EMPTY, values[twoplayerlayout]);
				twoplayerlayout++;
			}
			
		case 3:// 3-Player Game Formation (UNDER CONSTRUCTION)
			values = new Marble[61];
			int currentline = 0;
			for (int i = 0; i < 61; i++) {
				if (i <= 10) {
					values[i] = Marble.WHITE;
				} else if (i == 18) {
					values[i] = Marble.BLACK;
				} else if (i==0){
					
				}
				else {
					values[i] = Marble.EMPTY;
				}
			}
			
			int threeplayerlayout = 0;
			for(String k: map.keySet()) {
				map.replace(k, Marble.EMPTY, values[threeplayerlayout]);
				threeplayerlayout++;
			
			
			
			
			
			
			
		}
		}
	}
	
	public void printBoardCoords() {
		System.out.print("    ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9I")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'I') {
				System.out.print(k + " ");
			}
		}
		System.out.print("   ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9H")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'H') {
				System.out.print(k + " ");
			}
		}
		System.out.print("  ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9G")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'G') {
				System.out.print(k + " ");
			}
		}
		System.out.print(" ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9F")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'F') {
				System.out.print(k + " ");
			}
		}
		System.out.print("");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9E")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'E') {
				System.out.print(k + " ");
			}
		}
		System.out.print(" ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("8D")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'D') {
				System.out.print(k + " ");
			}
		}
		System.out.print("  ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("7C")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'C') {
				System.out.print(k + " ");
			}
		}
		System.out.print("   ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("6B")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'B') {
				System.out.print(k + " ");
			}
		}
		System.out.print("    ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("5A")) {
				System.out.print(k + " ");
				System.out.println();
			} else if (k.charAt(1) == 'A') {
				System.out.print(k + " ");
			}
		}
		System.out.println("\n");
		System.out.println();
	}

	public void printBoardValues() {
		System.out.print("    ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9I")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'I') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}
		System.out.print("   ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9H")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'H') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}
		System.out.print("  ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9G")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'G') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}
		System.out.print(" ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9F")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'F') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}
		System.out.print("");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("9E")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'E') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}
		System.out.print(" ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("8D")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'D') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}
		System.out.print("  ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("7C")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'C') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}
		System.out.print("   ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("6B")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'B') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}
		System.out.print("    ");
		for (String k : getBoard().map.keySet()) {
			if (k.equals("5A")) {
				System.out.print(getBoard().map.get(k) + " ");
				System.out.println();
			} else if (k.charAt(1) == 'A') {
				System.out.print(getBoard().map.get(k) + " ");
			}
		}

	}
	public Board createBoard(int number) {
		Board board = new Board(number);
		return board;
	}
	
	public Board getBoard() {
		Board b = createBoard(2);
		return b;
	}
	


	public static void main(String[] args) {
		
	}
}