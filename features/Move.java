package features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Move {

	public boolean MovePossible() {
		return true;// false;
	}

	// being done - Done testing required
	public void MoveNW(Marble m, String... coords) {
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
			char nextcoordYAXIS = ++headcoordYAXIS;
			String nextcoord = headcoordXAXIS + "" + nextcoordYAXIS;
			Marble HeadMarbleVal = Board.getMarble(headcoord);
			Board.map.replace(headcoord, m, Marble.EE);
			while (Board.map.get(nextcoord) != null && Board.map.get(nextcoord) != Marble.EE) {
				Marble NextMarbleVal = Board.getMarble(nextcoord);
				Marble temp = Board.getMarble(nextcoord);
				Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
				HeadMarbleVal = temp;
				headcoord = nextcoord;
				headcoordXAXIS = headcoord.charAt(0);
				nextcoordYAXIS = ++headcoordYAXIS;
				nextcoord = headcoordXAXIS + "" + nextcoordYAXIS;
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
				char nextcoordYAXIS = ++headcoordYAXIS;
				String nextcoord = headcoordXAXIS + "" + nextcoordYAXIS;
				Marble HeadMarbleVal = Board.getMarble(headcoord);
				Board.map.replace(headcoord, m, Marble.EE);
				while (Board.map.get(nextcoord) != null && Board.map.get(nextcoord) != Marble.EE) {
					Marble NextMarbleVal = Board.getMarble(nextcoord);
					Marble temp = Board.getMarble(nextcoord);
					Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
					HeadMarbleVal = temp;
					headcoord = nextcoord;
					headcoordXAXIS = headcoord.charAt(0);
					nextcoordYAXIS = ++headcoordYAXIS;
					nextcoord = headcoordXAXIS + "" + nextcoordYAXIS;

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

	// being done - Done testing required
	public void MoveNE(Marble m, String... coords) {
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
			char nextcoordYAXIS = ++headcoordYAXIS;
			String nextcoord = nextcoordXAXIS + "" + nextcoordYAXIS;
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
				nextcoordYAXIS = ++headcoordYAXIS;
				nextcoord = nextcoordXAXIS + "" + nextcoordYAXIS;
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
				char nextcoordYAXIS = ++headcoordYAXIS;
				String nextcoord = nextcoordXAXIS + "" + nextcoordYAXIS;
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
					nextcoordYAXIS = ++headcoordYAXIS;
					nextcoord = nextcoordXAXIS + "" + nextcoordYAXIS;

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

	// done testing required
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

	// done testing required
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

	// done testing required
	public void MoveSW(Marble m, String... coords) {
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
			char nextcoordYAXIS = --headcoordYAXIS;
			String nextcoord = nextcoordXAXIS + "" + nextcoordYAXIS;
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
				nextcoordYAXIS = --headcoordYAXIS;
				nextcoord = nextcoordXAXIS + "" + nextcoordYAXIS;
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
				char nextcoordYAXIS = --headcoordYAXIS;
				String nextcoord = nextcoordXAXIS + "" + nextcoordYAXIS;
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
					nextcoordYAXIS = --headcoordYAXIS;
					nextcoord = nextcoordXAXIS + "" + nextcoordYAXIS;

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

	// done testing required
	public void MoveSE(Marble m, String... coords) {
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
			char nextcoordYAXIS = --headcoordYAXIS;
			String nextcoord = headcoordXAXIS + "" + nextcoordYAXIS;
			Marble HeadMarbleVal = Board.getMarble(headcoord);
			Board.map.replace(headcoord, m, Marble.EE);
			while (Board.map.get(nextcoord) != null && Board.map.get(nextcoord) != Marble.EE) {
				Marble NextMarbleVal = Board.getMarble(nextcoord);
				Marble temp = Board.getMarble(nextcoord);
				Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
				HeadMarbleVal = temp;
				headcoord = nextcoord;
				headcoordXAXIS = headcoord.charAt(0);
				nextcoordYAXIS = --headcoordYAXIS;
				nextcoord = headcoordXAXIS + "" + nextcoordYAXIS;
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
				char nextcoordYAXIS = --headcoordYAXIS;
				String nextcoord = headcoordXAXIS + "" + nextcoordYAXIS;
				Marble HeadMarbleVal = Board.getMarble(headcoord);
				Board.map.replace(headcoord, m, Marble.EE);
				while (Board.map.get(nextcoord) != null && Board.map.get(nextcoord) != Marble.EE) {
					Marble NextMarbleVal = Board.getMarble(nextcoord);
					Marble temp = Board.getMarble(nextcoord);
					Board.map.replace(nextcoord, NextMarbleVal, HeadMarbleVal);
					HeadMarbleVal = temp;
					headcoord = nextcoord;
					headcoordXAXIS = headcoord.charAt(0);
					nextcoordYAXIS = --headcoordYAXIS;
					nextcoord = headcoordXAXIS + "" + nextcoordYAXIS;

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

}
