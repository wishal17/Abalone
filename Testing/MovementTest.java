package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import game.Board;
import game.Layout;
import game.Marble;
import game.Move;

import org.junit.jupiter.api.BeforeEach;

public class MovementTest {

	public void testMoveWest() {

	}

	/**
	 * Test <tt>testMoveWestEast</tt>
	 */
	@Test
	public void testMoveWestEast() {
		Board board = new Board(new Layout(2));
		Move m = new Move();
		m.MoveW(Marble.BB, "3C", "5C", "4C");
		m.MoveE(Marble.BB, "3C", "2C", "4C");
		assertEquals(Marble.BB, Board.getMarble("3C"));
	}

}
