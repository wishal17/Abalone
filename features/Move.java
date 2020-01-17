package features;

import java.util.Map;

public class Move {
	Board board = createBoard(2);
	Map<String, Marble> map = board.getMap();
	public boolean MovePossible() {
		return true;//false;
	}
	public void MoveNW(Marble m, String ...s) {
		for(String string : s) {
			string.charAt(index)
			map.replace(string, Marble.EMPTY,m);
			map.replace(string, m, Marble.EMPTY);
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
		
		board.printBoardCoords();
		board.printBoardValues();
	}
}
