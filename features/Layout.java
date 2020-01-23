package features;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Class responsible for different marble layouts depending on the number of players
 */
public class Layout {
	private static final char[] XAXIS = "123456789".toCharArray();
	private static final char[] YAXIS = "ABCDEFGHI".toCharArray();
	private Marble[] values;
	private String players;
	private Map<String, Marble> LayoutMap = new LinkedHashMap<String, Marble>();

	public Layout(int layout) {
		/**
		 * Coordinate scheme for the board To create a blank board, the YAXIS has been
		 * split into two parts A-E and F-I.
		 */
		// For X AXIS coordinates A-E
		for (char a : YAXIS) {
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
				LayoutMap.put(i + "" + a, Marble.EE);
			}
		}
		// For Y AXIS coordinates F-I
		int incrementer = 2;
		int starting = 2;
		for (char a = 'F'; a <= 'I'; a++) {
			while (incrementer <= 9) {
				LayoutMap.put(incrementer + "" + a, Marble.EE);
				incrementer++;
			}
			starting++;
			incrementer = starting;
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
					LayoutMap.replace(k, Marble.EE, Marble.BB);
				} else if (k.charAt(0) == '8' || k.charAt(0) == '9') {
					LayoutMap.replace(k, Marble.EE, Marble.WW);
				} else if (k.equals("1D") || k.equals("1E") || k.equals("2E") || k.equals("2F") || k.equals("3F")
						|| k.equals("3G") || k.equals("4G") || k.equals("4H") || k.equals("5H") || k.equals("5I")
						|| k.equals("6I")) {
					LayoutMap.replace(k, Marble.EE, Marble.YY);
				}
			}
			players = "three";
			break;
		case 4:// 4-Player Game Formation
			for (String k : LayoutMap.keySet()) {
				if (k.charAt(1) == 'A' && k.charAt(0) != '1' || k.equals("3B") || k.equals("4B") || k.equals("5B")
						|| k.equals("4C") || k.equals("5C")) {
					LayoutMap.replace(k, Marble.EE, Marble.BB);
				} else if (k.charAt(0) == '1' && k.charAt(1) != 'A' || k.equals("2C") || k.equals("2D")
						|| k.equals("2E") || k.equals("3E") || k.equals("3D")) {
					LayoutMap.replace(k, Marble.EE, Marble.WW);
				} else if (k.charAt(0) == '9' && k.charAt(1) != 'I' || k.equals("8E") || k.equals("8F")
						|| k.equals("8G") || k.equals("7E") || k.equals("7F")) {
					LayoutMap.replace(k, Marble.EE, Marble.RR);
				} else if (k.charAt(1) == 'I' && k.charAt(0) != '9' || k.equals("5H") || k.equals("6H")
						|| k.equals("7H") || k.equals("5G") || k.equals("6G")) {
					LayoutMap.replace(k, Marble.EE, Marble.YY);
				}
			}
			players = "four";
			break;
		}
	}

	public Map<String, Marble> returnLayoutMap() {
		return LayoutMap;
	}
	
	public String returnPlayers() {
		return players;
	}
}
