package game;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Class responsible for different marble layouts depending on the number of players
 */
public class Layout {
	private Marble[] values;
	private Map<String, Marble> LayoutMap = new LinkedHashMap<String, Marble>();
	private String players;

	/*
	 * Constructor for Layout. Creates layouts depending on the number of players
	 */
	public Layout(int layout) {
		/**
		 * Coordinate scheme for the board To create a blank board, the YAXIS has been
		 * split into two parts A-E and F-I.
		 */
		// For X AXIS coordinates A-E

		// "letter" is the string that temporarily holds the letter
		String letter = "";

		// "number" is the string that temporarily holds the number
		String number = "";

		// the boolean yComplete will be set to false when all x values for one y axis
		// are added to LayoutMap
		boolean yIncomplete = true;

		// the integer n holds the value that will be put into the string "number"
		int n = 1;

		for (int l = 1; l <= 9; l++) {
			yIncomplete = true;
			n = 1;
			while (yIncomplete == true) {
				switch (l) {
				case 1:
					if (n <= 4) {
						n = 5;
					}
					letter = "I";
					number = "" + n;
					break;
				case 2:
					if (n <= 3) {
						n = 4;
					}
					letter = "H";
					number = "" + n;
					break;
				case 3:
					if (n <= 2) {
						n = 3;
					}
					letter = "G";
					number = "" + n;
					break;
				case 4:
					if (n <= 1) {
						n = 2;
					}
					letter = "F";
					number = "" + n;
					break;
				case 5:
					letter = "E";
					number = "" + n;
					break;
				case 6:
					if (n >= 9) {
						break;
					}
					letter = "D";
					number = "" + n;
					break;
				case 7:
					if (n >= 8) {
						break;
					}
					letter = "C";
					number = "" + n;
					break;
				case 8:
					if (n >= 7) {
						break;
					}
					letter = "B";
					number = "" + n;
					break;
				case 9:
					if (n >= 6) {
						break;
					}
					letter = "A";
					number = "" + n;
					break;
				}
				LayoutMap.put(number + letter, Marble.EE);
				n++;
				if (n > 9) {
					yIncomplete = false;
				}
			}
		}

		switch (layout) {

		case 2: // 2-Player Game Formation.
			values = new Marble[61];
			for (int i = 0; i < 61; i++) {
				if (i < 16 && i != 11 && i != 12) {
					values[i] = Marble.BB;
				} else if (i > 44 && i != 48 && i != 49) {
					values[i] = Marble.WW;
				} else {
					values[i] = Marble.EE;
				}
			}
			int twoplayerlayout = 0;
			for (String k : LayoutMap.keySet()) {
				LayoutMap.replace(k, Marble.EE, values[twoplayerlayout]);
				twoplayerlayout++;
			}
			players = "two";
			break;

		case 3:// 3-Player Game Formation

			for (String k : LayoutMap.keySet()) {
				if (k.charAt(1) == 'A' || k.charAt(1) == 'B') {
					LayoutMap.replace(k, Marble.EE, Marble.YY);
				} else if (k.charAt(0) == '8' || k.charAt(0) == '9') {
					LayoutMap.replace(k, Marble.EE, Marble.BB);
				} else if (k.equals("1D") || k.equals("1E") || k.equals("2E") || k.equals("2F") || k.equals("3F")
						|| k.equals("3G") || k.equals("4G") || k.equals("4H") || k.equals("5H") || k.equals("5I")
						|| k.equals("6I")) {
					LayoutMap.replace(k, Marble.EE, Marble.WW);
				}
			}
			players = "three";
			break;
		case 4:// 4-Player Game Formation
			for (String k : LayoutMap.keySet()) {
				if (k.charAt(1) == 'A' && k.charAt(0) != '1' || k.equals("3B") || k.equals("4B") || k.equals("5B")
						|| k.equals("4C") || k.equals("5C")) {
					LayoutMap.replace(k, Marble.EE, Marble.YY);
				} else if (k.charAt(0) == '1' && k.charAt(1) != 'A' || k.equals("2C") || k.equals("2D")
						|| k.equals("2E") || k.equals("3E") || k.equals("3D")) {
					LayoutMap.replace(k, Marble.EE, Marble.WW);
				} else if (k.charAt(0) == '9' && k.charAt(1) != 'I' || k.equals("8E") || k.equals("8F")
						|| k.equals("8G") || k.equals("7E") || k.equals("7F")) {
					LayoutMap.replace(k, Marble.EE, Marble.BB);
				} else if (k.charAt(1) == 'I' && k.charAt(0) != '9' || k.equals("5H") || k.equals("6H")
						|| k.equals("7H") || k.equals("5G") || k.equals("6G")) {
					LayoutMap.replace(k, Marble.EE, Marble.RR);
				}
			}
			players = "four";
			break;
		}
	}

	/* Returns the LinkedHashMap of the board */
	public Map<String, Marble> returnLayoutMap() {
		return LayoutMap;
	}

	/**
	 * Returns the number of players as a String
	 * 
	 * @return "two" if int layout was 2, "three" if int layout was 3, "four"if int
	 *         layout was 4.
	 */
	public String returnPlayers() {
		return players;
	}
}
