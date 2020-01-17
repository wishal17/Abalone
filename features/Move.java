package features;

import java.util.Map;

public class Move {
	Board board = new Board(2);
	Map<String, Marble> map = board.getMap();
	public boolean MovePossible() {
		return true;//false;
	}
	public void MoveNW(Marble m, String ...s) {
		for(String string : s) {
			char originalYAXIS = string.charAt(1);
			char originalXAXIS = string.charAt(0);
			char newYAXIS = originalYAXIS++;
			if(Board.hasMarble(originalXAXIS+""+newYAXIS)==true) {
				map.replace(string, Marble.EE,m);
				map.replace(string, m, Marble.EE);
			} else {
				map.replace(string, Marble.EE,m);
				map.replace(string, m, Marble.EE);
			}
			
		}

	}
	public void MoveNE(Marble ...m) {
		
		
	}
	public void MoveW(Marble ...m){
		
	}
	public void MoveE(Marble ...m) {
		
	}
	public void MoveSW(Marble ...m) {
		
	}
	public void MoveSE(Marble ...m) {
		
	}
	public void main(String args[]) {
		Board b = new Board(2);
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
