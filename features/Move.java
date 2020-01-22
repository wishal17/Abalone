package features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Move {
	public char nextYAXIS(char a) {
		return ++a;
	}

	public char previousYAXIS(char a) {
		return --a;
	}

	public String nextCoordinates(String pos, int direction) {
		String newpos = "";
		if (Board.isValidCoordinate(pos)) {
			int num = Character.getNumericValue(pos.charAt(0));
			char YAXIS = pos.charAt(1);

			switch (direction) {
			case 1:
				newpos = "" + num + nextYAXIS(YAXIS);
				break;
			case 2:
				newpos = "" + (num + 1) + nextYAXIS(YAXIS);
				break;
			case 3:
				newpos = "" + (num + 1) + YAXIS;
				break;
			case 4:
				newpos = "" + num + previousYAXIS(YAXIS);
				break;
			case 5:
				newpos = "" + (num - 1) + previousYAXIS(YAXIS);
				break;
			case 6:
				newpos = "" + (num - 1) + YAXIS;
				break;
			default:
				newpos = null;
			}
		}
		if (Board.isValidCoordinate(newpos)) {
			return newpos;
		}
		return null;
	}
	
	public int pushtwovsEnemyMarbles(String pos, int direction, Marble m) {
			String onenexttohead = nextCoordinates(pos, direction);
			String twonexttohead = nextCoordinates(onenexttohead, direction);
			if(!m.isTeammate(Board.getMarble(onenexttohead)) && !m.isTeammate(Board.getMarble(twonexttohead))) {
				return 2;
			} else if ((!m.isTeammate(Board.getMarble(onenexttohead)))) {
				return 1;
			}else if ((!m.isTeammate(Board.getMarble(twonexttohead)))) {
				return 1;
			} else {
				return 0;
			}
	}
	public int pushthreevsEnemyMarbles() {
		return 1;
	}

	public boolean moveWithoutPushing(String pos, int direction, Marble m) {
		String nextcoordspos = nextCoordinates(pos, direction);
		System.out.println(nextcoordspos);
		if(Board.map.get(pos)== m && !Board.hasMarble(nextcoordspos)) {
			Board.map.put(nextcoordspos, m);
			Board.map.put(pos,Marble.EE);
			return true;
		}
		return false;
	}

	public boolean moveWithoutPushing(String pos1, String pos2, int direction, Marble m) {
		String headcoord; String endcoord;
		if(nextCoordinates(pos1, direction).equals(pos2)) {
			headcoord = pos2;
			endcoord = pos1;
		} else {
			headcoord = pos1;
			endcoord = pos2;
		}
		String nextheadcoord = nextCoordinates(headcoord, direction);
		String nextendcoord = nextCoordinates(endcoord, direction);
		if (Board.map.get(pos1)== m && Board.map.get(pos2)==m && (!Board.hasMarble(nextheadcoord))) {
			Board.map.put(nextheadcoord, m);
			Board.map.put(nextendcoord, m);
			Board.map.put(endcoord,Marble.EE);
			return true;
		} 
		return false;
	}
	
	public boolean moveWithoutPushing(String pos1, String pos2, String pos3, int direction, Marble m) {
		List<String> list = new ArrayList<String>();
		String headcoord; String endcoord;
		list.add(pos1); list.add(pos2); list.add(pos3);
		Collections.sort(list);
		if(nextCoordinates(list.get(1), direction).equals(list.get(0))){
			headcoord = list.get(0);
			endcoord = list.get(2);
		} else {
			headcoord = list.get(2);
			endcoord = list.get(0);
		}
		String nextheadcoord = nextCoordinates(headcoord, direction);
		String nextendcoord = nextCoordinates(endcoord, direction);
		if (Board.map.get(pos1)== m && Board.map.get(pos2)==m && Board.map.get(pos3)==m && (!Board.hasMarble(nextheadcoord))) {
			Board.map.put(nextheadcoord, m);
			Board.map.put(nextendcoord,m);
			Board.map.put(endcoord, Marble.EE);
			return true;
		}
		return false;
	}
	
	public boolean MoveWithPushing(String pos1, String pos2, int direction, Marble m) {
		String headcoord; String endcoord;
		if(nextCoordinates(pos1, direction) == pos2) {
			headcoord = pos2;
			endcoord = pos1;
		} else {
			headcoord = pos1;
			endcoord = pos2;
		}
		String nextheadcoord = nextCoordinates(headcoord, direction);
		
		if (Board.map.get(pos1)== m && Board.map.get(pos2)==m  && (Board.hasMarble(nextheadcoord))) {
			if(pushtwovsEnemyMarbles(headcoord, direction, m) >= 2) {
				return false;
			} else if(pushtwovsEnemyMarbles(headcoord, direction, m) <= 1) {
				Board.map.put(endcoord,Marble.EE);
				String nextcoord = nextheadcoord;
				Marble headMarble = Board.getMarble(headcoord);
				System.out.println(headcoord);
				while(Board.getMarble(nextcoord)!=Marble.EE && Board.getMarble(nextcoord)!=null) {
					Marble temp = Board.getMarble(nextcoord);
					Board.map.put(nextcoord,headMarble);
					headMarble = temp;
					headcoord = nextcoord;
					nextcoord = nextCoordinates(headcoord, direction);
				}
				if(Board.getMarble(nextcoord)==null) {
					
				}
			}
			
		}
		return false;
		
	}
	

	// done testing required
	public void MoveW(Marble m, String... coords) {
		List<String> sortedlist = new ArrayList<String>();
		for (String s : coords) {
			sortedlist.add(s);
		}
		Collections.sort(sortedlist);
		
		if (sortedlist.size() > 1) { // If multiple coordinates are selected to move
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
