package features;


public class Move {


	
	Board board = new Board(2);
	public boolean MovePossible() {
		return true;//false;
	}
	public void MoveNW(Marble m, String ...s) {
		for(String string : s) {
			board.replace(string, Marble.EMPTY,m);
			board.replace(string, m, Marble.EMPTY);
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
