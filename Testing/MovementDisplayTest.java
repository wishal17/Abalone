package Testing;

import features.Board;
import features.Layout;
import features.Move;
import features.Marble;

public class MovementDisplayTest {
	public static void main(String[] args) {
		Board board = new Board(new Layout(2));
		Move m = new Move();
		m.moveWithoutPushing("7I", "7H", "7G", 4, Marble.WW);
		m.moveWithoutPushing("7F", "7H", "7G", 4, Marble.WW);
		m.moveWithoutPushing("7F", "7E", "7G", 4, Marble.WW);
		m.moveWithoutPushing("7F", "7E", "7D", 4, Marble.WW);
		m.moveWithoutPushing("7C", "7E", "7D", 4, Marble.WW);
		m.moveWithoutPushing("4C","5C", 3, Marble.BB);
		m.moveWithoutPushing("5C","6C", 3, Marble.BB);
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
