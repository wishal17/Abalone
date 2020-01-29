package game;

public class ComputerPlayer extends Player{
	public Strategy strategy;
	private Marble currentMarble;
	public ComputerPlayer(Marble marble, Strategy strategy) {
		super(strategy.getName(),marble);
		this.strategy = strategy;
		this.currentMarble = marble;
	}
	
	public void determineMove(Board board) {
		strategy.determineMove(board, currentMarble);
	}
	
}
