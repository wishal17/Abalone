package features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Move {

	public boolean MovePossible() {
		return true;// false;
	}

	public void MoveNW(Marble m, String... coords) {
		for (String c : coords) {
			char originalYAXIS = c.charAt(1);
			char originalXAXIS = c.charAt(0);
			originalYAXIS++;
			char newYAXIS = originalYAXIS;
			String MarbleAtNW = (originalXAXIS + "" + newYAXIS);
			if (Board.hasMarble(MarbleAtNW) == true) {
				Board.map.replace(MarbleAtNW, Board.getMarble(MarbleAtNW), m);
				Board.map.replace(c, m, Marble.EE);
			} else {
				Board.map.replace(MarbleAtNW, Marble.EE, m);
				Board.map.replace(c, m, Marble.EE);
			}

		}
	}

	public void MoveNE(Marble m, String... coords) {
	}

	public void MoveW(Marble m, String... coords) {
		List<String> sortedlist = new ArrayList<String>();
		for (String s : coords) {
			sortedlist.add(s);
		}
		Collections.sort(sortedlist);
		if (coords.length == 1) {// If only one coordinate is selected to move
			String headcoord = sortedlist.get(sortedlist.size() - 1);
			// Getting the next coordinate's value.
			char headcoordXAXIS = headcoord.charAt(0);
			char headcoordYAXIS = headcoord.charAt(1);
			char nextcoordXAXIS = --headcoordXAXIS;
			String nextcoord = nextcoordXAXIS + "" + headcoordYAXIS;
			Marble HeadMarbleVal = Board.getMarble(headcoord);
			Board.map.replace(headcoord, m, Marble.EE);
			while (Board.map.get(nextcoord) != null && Board.map.get(nextcoord) != Marble.EE) {
				Marble NextMarbleVal = Board.getMarble(nextcoord);
				Marble temp = Board.getMarble(nextcoord);
				Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
				HeadMarbleVal = temp;
				headcoord = nextcoord;
				headcoordXAXIS = headcoord.charAt(0);
				nextcoordXAXIS = --headcoordXAXIS;
				nextcoord = nextcoordXAXIS + "" + headcoordYAXIS;
			}
			if (Board.map.get(nextcoord) == Marble.EE) {
				Marble NextMarbleVal = Board.getMarble(nextcoord);
				Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
			} else if (Board.map.get(nextcoord) == null) {
				System.out.println("Invalid move");
			}

		} else if (sortedlist.size() > 1) { // If multiple coordinates are selected to move
			for (int i = 0; i < sortedlist.size(); i++) {
				String headcoord = sortedlist.get(i);
				char headcoordXAXIS = headcoord.charAt(0);
				char headcoordYAXIS = headcoord.charAt(1);
				char nextcoordXAXIS = --headcoordXAXIS;
				String nextcoord = nextcoordXAXIS + "" + headcoordYAXIS;
				Marble HeadMarbleVal = Board.getMarble(headcoord);
				Board.map.replace(headcoord, m, Marble.EE);
				while (Board.map.get(nextcoord) != null && Board.map.get(nextcoord) != Marble.EE) {
					Marble NextMarbleVal = Board.getMarble(nextcoord);
					Marble temp = Board.getMarble(nextcoord);
					Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
					HeadMarbleVal = temp;
					headcoord = nextcoord;
					headcoordXAXIS = headcoord.charAt(0);
					nextcoordXAXIS = --headcoordXAXIS;
					nextcoord = nextcoordXAXIS + "" + headcoordYAXIS;

				}
				if (Board.map.get(nextcoord) == Marble.EE) {
					Marble NextMarbleVal = Board.getMarble(nextcoord);
					Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
				} else if (Board.map.get(nextcoord) == null) {
					System.out.println("Invalid move");
				}
			}
		}
	}

	public void MoveE(Marble m, String... coords) {
		List<String> sortedlist = new ArrayList<String>();
		for (String s : coords) {
			sortedlist.add(s);
		}
		Collections.sort(sortedlist);
		if (coords.length == 1) {// If only one coordinate is selected to move
			String headcoord = sortedlist.get(sortedlist.size() - 1);
			// Getting the next coordinate's value.
			char headcoordXAXIS = headcoord.charAt(0);
			char headcoordYAXIS = headcoord.charAt(1);
			char nextcoordXAXIS = ++headcoordXAXIS;
			String nextcoord = nextcoordXAXIS + "" + headcoordYAXIS;
			Marble HeadMarbleVal = Board.getMarble(headcoord);
			Board.map.replace(headcoord, m, Marble.EE);
			while (Board.map.get(nextcoord) != null && Board.map.get(nextcoord) != Marble.EE) {
				Marble NextMarbleVal = Board.getMarble(nextcoord);
				Marble temp = Board.getMarble(nextcoord);
				Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
				HeadMarbleVal = temp;
				headcoord = nextcoord;
				headcoordXAXIS = headcoord.charAt(0);
				nextcoordXAXIS = ++headcoordXAXIS;
				nextcoord = nextcoordXAXIS + "" + headcoordYAXIS;
			}
			if (Board.map.get(nextcoord) == Marble.EE) {
				Marble NextMarbleVal = Board.getMarble(nextcoord);
				Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
			} else if (Board.map.get(nextcoord) == null) {
				System.out.println("Invalid move");
			}

		} else if (sortedlist.size() > 1) { // If multiple coordinates are selected to move
			Collections.reverse(sortedlist);
			for (int i = 0; i < sortedlist.size(); i++) {
				String headcoord = sortedlist.get(i);
				char headcoordXAXIS = headcoord.charAt(0);
				char headcoordYAXIS = headcoord.charAt(1);
				char nextcoordXAXIS = ++headcoordXAXIS;
				String nextcoord = nextcoordXAXIS + "" + headcoordYAXIS;
				Marble HeadMarbleVal = Board.getMarble(headcoord);
				Board.map.replace(headcoord, m, Marble.EE);
				while (Board.map.get(nextcoord) != null && Board.map.get(nextcoord) != Marble.EE) {
					Marble NextMarbleVal = Board.getMarble(nextcoord);
					Marble temp = Board.getMarble(nextcoord);
					Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
					HeadMarbleVal = temp;
					headcoord = nextcoord;
					headcoordXAXIS = headcoord.charAt(0);
					nextcoordXAXIS = ++headcoordXAXIS;
					nextcoord = nextcoordXAXIS + "" + headcoordYAXIS;

				}
				if (Board.map.get(nextcoord) == Marble.EE) {
					Marble NextMarbleVal = Board.getMarble(nextcoord);
					Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
				} else if (Board.map.get(nextcoord) == null) {
					System.out.println("Invalid move");
				}
			}
		}
	}

	public void MoveSW(Marble m, String... coords) {
	}

	public void MoveSE(Marble... m) {
	}

	public void main(String args[]) {

	}
}
