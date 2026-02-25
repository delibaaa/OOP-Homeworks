import junit.framework.TestCase;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;
	Piece stick, square, l1, l2;

	protected void setUp() throws Exception {
		b = new Board(3, 6);

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		stick = new Piece(Piece.STICK_STR);
		square = new Piece(Piece.SQUARE_STR);
		l1 = new Piece(Piece.L1_STR);
		l2 = new Piece(Piece.L2_STR);

		b.place(pyr1, 0, 0);
	}

	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertNotEquals(3, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertNotEquals(2, b.getRowWidth(1));
	}

	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));

		assertNotEquals(1, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}

	public void testBasics() {
		Board newBoard = new Board(10, 20);
		assertEquals(10, newBoard.getWidth());

		assertEquals(20, newBoard.getHeight());
		assertEquals(0, newBoard.getMaxHeight());
	}

	public void testGetGrid() {
		assertFalse(b.getGrid(0, 1));
		assertFalse(b.getGrid(2, 1));

		assertFalse(b.getGrid(0, 2));
		assertTrue(b.getGrid(1, 0));
	}

	public void testPlace() {
		b.commit();
		assertEquals(Board.PLACE_OK, b.place(pyr2, 0, 2));
		b.undo();
		b.commit();
		assertEquals(Board.PLACE_OUT_BOUNDS, b.place(pyr1, 0, -1));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.place(pyr1, 2, 0));
		assertEquals(Board.PLACE_BAD, b.place(pyr1, 0, 0));
		b.commit();
	}

	public void testUndo() {
		b.commit();
		b.place(sRotated, 1, 1);

		assertEquals(4, b.getMaxHeight());

		b.undo();
		assertEquals(2, b.getMaxHeight());
		assertEquals(1, b.getColumnHeight(0));
		assertNotEquals(1, b.getColumnHeight(1));
		assertNotEquals(0, b.getRowWidth(1));
	}

	public void testCommit() {
		int currMax = b.getMaxHeight();
		b.commit();
		b.place(sRotated, 1, 1);
		b.commit();
		b.undo();

		assertNotEquals(currMax, b.getMaxHeight());
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(2));
	}

	public void testClearRows() {
		Board board = new Board(3, 6);
		board.place(pyr1, 0, 0);
		board.commit();
		board.place(pyr1, 0, 1);
		assertEquals(1, board.clearRows());
		assertEquals(1, board.getMaxHeight());

		assertNotEquals(2, board.getColumnHeight(2));
		assertEquals(0, board.getRowWidth(1));
	}

	public void testPartlyRows() {
		Board board = new Board(3, 6);
		board.place(pyr1, 0, 0);
		board.commit();
		board.place(l1, 0, 1);

		assertNotEquals(1, board.getColumnHeight(1));
		assertNotEquals(2, board.getColumnHeight(2));

		assertEquals(1, board.clearRows());
	}

	public void testDropHeight() {
		Board emptyBoard = new Board(10, 20);
		assertEquals(0, emptyBoard.dropHeight(pyr1, 0));
		b.commit();
		assertEquals(2, b.dropHeight(square, 0));

		assertEquals(2, b.dropHeight(square, 1));
	}

	public void testSanityCheck() {
		b.commit();
		b.place(pyr2, 0, 2);
	}

	public void testToString() {
		String expected = "|   |\n|   |\n|   |\n|   |\n| + |\n|+++|\n-----";
		assertEquals(expected, b.toString());
	}
}
