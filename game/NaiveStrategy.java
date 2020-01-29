package game;

import java.util.ArrayList;

public class NaiveStrategy implements Strategy{
	ArrayList<String> list = new ArrayList<String>();
	
	public void determineMove(Board board,Marble marble) {
		for(String k: Board.map.keySet()) {
			if(Board.map.get(k) == marble) {
				list.add(k);
			}
		}
		
		while(!valid) {
			int i=0;
			switch(i) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			
			}
		}
		
	}

	@Override
	public String getName() {
		return "Naive";
	}
}
