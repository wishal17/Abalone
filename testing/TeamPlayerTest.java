package testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import game.Board;
import game.Layout;
import game.Marble;

public class TeamPlayerTest {
	Board b;

	@Before
	public void setUpTwoPlayers() {
		b = new Board(new Layout(4));

	}



	/**
	 * Test to check if marbles can push opponent marbles out of the board.
	 * Also checks if the eliminated list is increased
	 */
	@Test
	public void testPush() {
		b.reset();
		b.makeMove("1B","2C","3D", 2, Marble.WW);
		b.makeMove("8I", 3, Marble.RR);
		b.makeMove("4E","2C","3D", 2, Marble.WW);
		b.makeMove("4E","5F","3D", 2, Marble.WW);
		b.makeMove("4E", "5F", "6G", 2, Marble.WW);
		System.out.println(b.printBoardValues()+"\n"+b.printBoardCoords());
		b.makeMove("7H", "5F", "6G", 2, Marble.WW);
		assertEquals(2, b.eliminated.size());
	}
	
	/**
	 * Test to check for game ending conditions.
	 */
	@Test
	public void testGameEnd() {
		//To check if the game ends if 6 opponent marbles (BB) have been eliminated
		b.reset();
		b.eliminated.add(Marble.BB);
		b.eliminated.add(Marble.WW);
		assertFalse(b.gameOver());
		b.eliminated.add(Marble.BB);
		b.eliminated.add(Marble.BB);
		b.eliminated.add(Marble.RR);
		b.eliminated.add(Marble.RR);
		b.eliminated.add(Marble.YY);
		b.eliminated.add(Marble.RR);
		b.eliminated.add(Marble.YY);
		b.eliminated.add(Marble.YY);
		assertTrue(b.gameOver());
		assertTrue(b.isWinner(Marble.WW));
		assertTrue(b.isWinner(Marble.BB));
		//To check if the game ends if 96 turns have been played. The turns are automatically 
		//incremented in the Room class.
		b.reset();
		b.turns = 95;
		b.makeMove("3D", 2, Marble.WW); //When a move is performed the number of turns increase.
		b.turns++;
		assertTrue(b.gameOver());
		assertFalse(b.isWinner(Marble.WW));
		assertFalse(b.isWinner(Marble.RR));
	}
	/**
	 * Test to check if the black marbles have been set up properly
	 */
	@Test
	public void testSetUpBlacks() {
		assertEquals(Marble.BB, b.getMarble("9H"));
		assertEquals(Marble.BB, b.getMarble("9H"));
		assertEquals(Marble.BB, b.getMarble("9F"));
		assertEquals(Marble.BB, b.getMarble("9E"));
		assertEquals(Marble.BB, b.getMarble("8G"));
		assertEquals(Marble.BB, b.getMarble("8F"));
		assertEquals(Marble.BB, b.getMarble("8E"));
		assertEquals(Marble.BB, b.getMarble("7E"));
		assertEquals(Marble.BB, b.getMarble("7F"));
	}
	
	/**
	 * Test to check if the white marbles have been set up properly
	 */
	@Test
	public void testSetUpWhites() {
		assertEquals(Marble.WW, b.getMarble("1B"));
		assertEquals(Marble.WW, b.getMarble("2C"));
		assertEquals(Marble.WW, b.getMarble("3D"));
		assertEquals(Marble.WW, b.getMarble("1C"));
		assertEquals(Marble.WW, b.getMarble("2D"));
		assertEquals(Marble.WW, b.getMarble("3E"));
		assertEquals(Marble.WW, b.getMarble("1D"));
		assertEquals(Marble.WW, b.getMarble("2E"));
		assertEquals(Marble.WW, b.getMarble("1E"));
	}
	
	/**
	 * Test to check if the red marbles have been set up properly
	 */
	@Test
	public void testSetUpReds() {
		assertEquals(Marble.RR, b.getMarble("5I"));
		assertEquals(Marble.RR, b.getMarble("6I"));
		assertEquals(Marble.RR, b.getMarble("7I"));
		assertEquals(Marble.RR, b.getMarble("8I"));
		assertEquals(Marble.RR, b.getMarble("5H"));
		assertEquals(Marble.RR, b.getMarble("6H"));
		assertEquals(Marble.RR, b.getMarble("7H"));
		assertEquals(Marble.RR, b.getMarble("5G"));
		assertEquals(Marble.RR, b.getMarble("6G"));
	}
	
	/**
	 * Test to check if the yellow marbles have been set up properly
	 */
	@Test
	public void testSetUpYellows() {
		assertEquals(Marble.YY, b.getMarble("2A"));
		assertEquals(Marble.YY, b.getMarble("3A"));
		assertEquals(Marble.YY, b.getMarble("4A"));
		assertEquals(Marble.YY, b.getMarble("5A"));
		assertEquals(Marble.YY, b.getMarble("3B"));
		assertEquals(Marble.YY, b.getMarble("4B"));
		assertEquals(Marble.YY, b.getMarble("5B"));
		assertEquals(Marble.YY, b.getMarble("4C"));
		assertEquals(Marble.YY, b.getMarble("5C"));
	}
	
}


