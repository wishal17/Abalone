package game;
public interface Strategy {
	public String getName();
	public void determineMove(Board board, Marble marble);
}