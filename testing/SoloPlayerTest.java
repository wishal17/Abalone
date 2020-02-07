package testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.Test;

import game.Board;
import game.Layout;
import game.Marble;

public class SoloPlayerTest {
	Board b;

	@Before
	public void setUpTwoPlayers() {
		b = new Board(new Layout(2));

	}

	

	/**
	 * Test to check if marbles can push opponent marbles out of the board.
	 * Also checks if the eliminated list is increased
	 */
	@Test
	public void testPush() {
		b.reset();
		b.makeMove("1A","2B","3C", 2, Marble.WW);
		b.makeMove("9I","9H", 4, Marble.BB);
		b.makeMove("4D","2B","3C", 2, Marble.WW);
		b.makeMove("4D","5E","3C", 2, Marble.WW);
		b.makeMove("4D","5E","6F", 2, Marble.WW);
		b.makeMove("7G","5E","6F", 2, Marble.WW);
		b.makeMove("7G","5E","6F", 2, Marble.WW);
		b.makeMove("7G","8H","6F", 2, Marble.WW);
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
		b.eliminated.add(Marble.BB);
		b.eliminated.add(Marble.WW);
		assertFalse(b.gameOver());
		b.eliminated.add(Marble.BB);
		b.eliminated.add(Marble.BB);
		b.eliminated.add(Marble.WW);
		b.eliminated.add(Marble.BB);
		b.eliminated.add(Marble.BB);
		assertTrue(b.gameOver());
		assertTrue(b.isWinner(Marble.WW));
		//To check if the game ends if 96 turns have been played. The turns are automatically 
		//incremented in the room class.
		b.reset();
		b.turns = 95;
		b.makeMove("3C", 1, Marble.WW); //When a move is performed the number of turns increase.
		b.turns++;
		assertTrue(b.gameOver());
	}
	/**
	 * Test to check if the black marbles have been set up properly
	 */
	@Test
	public void testSetUpBlacks() {
		assertEquals(Marble.BB, b.getMarble("5I"));
		assertEquals(Marble.BB, b.getMarble("6I"));
		assertEquals(Marble.BB, b.getMarble("7I"));
		assertEquals(Marble.BB, b.getMarble("8I"));
		assertEquals(Marble.BB, b.getMarble("9I"));
		assertEquals(Marble.BB, b.getMarble("4H"));
		assertEquals(Marble.BB, b.getMarble("5H"));
		assertEquals(Marble.BB, b.getMarble("6H"));
		assertEquals(Marble.BB, b.getMarble("7H"));
		assertEquals(Marble.BB, b.getMarble("8H"));
		assertEquals(Marble.BB, b.getMarble("9H"));
		assertEquals(Marble.BB, b.getMarble("5G"));
		assertEquals(Marble.BB, b.getMarble("6G"));
		assertEquals(Marble.BB, b.getMarble("7G"));
	}
	
	/**
	 * Test to check if the white marbles have been set up properly
	 */
	@Test
	public void testSetUpWhites() {
		assertEquals(Marble.WW, b.getMarble("3C"));
		assertEquals(Marble.WW, b.getMarble("4C"));
		assertEquals(Marble.WW, b.getMarble("5C"));
		assertEquals(Marble.WW, b.getMarble("1B"));
		assertEquals(Marble.WW, b.getMarble("2B"));
		assertEquals(Marble.WW, b.getMarble("3B"));
		assertEquals(Marble.WW, b.getMarble("4B"));
		assertEquals(Marble.WW, b.getMarble("5B"));
		assertEquals(Marble.WW, b.getMarble("6B"));
		assertEquals(Marble.WW, b.getMarble("1A"));
		assertEquals(Marble.WW, b.getMarble("2A"));
		assertEquals(Marble.WW, b.getMarble("3A"));
		assertEquals(Marble.WW, b.getMarble("4A"));
		assertEquals(Marble.WW, b.getMarble("5A"));
	}
	
	/**
	 * Used to test moving marbles
	 */
	@Test
	public void testBasicMoves() {
		b.reset();
		b.makeMove("3C", 2, Marble.WW);
		assertEquals(Marble.WW, b.getMarble("4D"));
		b.makeMove("5G", "6G" , "7G", 5, Marble.BB);
		assertEquals(Marble.BB, b.getMarble("4F"));
		assertEquals(Marble.BB, b.getMarble("5F"));
		assertEquals(Marble.BB, b.getMarble("6F"));
		b.makeMove("2B", "2A", 1, Marble.WW);
		assertEquals(Marble.WW, b.getMarble("2B"));
		assertEquals(Marble.WW, b.getMarble("2C"));
		b.makeMove("6F", 3, Marble.BB);
		assertEquals(Marble.BB, b.getMarble("7F"));
		b.makeMove("2B", 4, Marble.WW);
		assertEquals(Marble.WW, b.getMarble("2A"));
		b.makeMove("7F", 6, Marble.BB);
		assertEquals(Marble.BB, b.getMarble("6F"));
	}

}


