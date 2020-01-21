package Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import features.Board;
import features.Layout;
import features.Marble;
import features.Move;

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
