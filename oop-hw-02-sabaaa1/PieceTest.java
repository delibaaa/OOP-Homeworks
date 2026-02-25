import junit.framework.TestCase;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PieceTest extends TestCase {
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, s2, stick, square, sRotated, s2Rotated, stickRotated, squareRotated;
	private Piece l1, l1Rotated, l2, l2Rotated;
	private Piece pyrSec, sSec, s2Sec, stickSec, squareSec, l1Sec, l2Sec;
	protected void setUp() throws Exception {
		super.setUp();
		Piece[] pieces = Piece.getPieces();

		TPoint[] squarePoints = new TPoint[] {
				new TPoint(0, 0),
				new TPoint(0, 1),
				new TPoint(1, 0),
				new TPoint(1, 1)
		};
		square = new Piece(squarePoints);
		squareRotated = square.computeNextRotation();

		pyr1 = pieces[Piece.PYRAMID];
		pyr2 = pyr1.fastRotation();
		pyr3 = pyr2.fastRotation();
		pyr4 = pyr3.fastRotation();

		l1 = new Piece(Piece.L1_STR);
		l1Rotated = l1.computeNextRotation();
		l2 = pieces[Piece.L2];
		l2Rotated = l2.fastRotation();

		TPoint[] sPoints = new TPoint[] {
				new TPoint(0, 0),
				new TPoint(1, 0),
				new TPoint(1, 1),
				new TPoint(2, 1)
		};
		s = new Piece(sPoints);
		sRotated = s.computeNextRotation();
		s2 = pieces[Piece.S2];
		s2Rotated = s2.fastRotation();

		TPoint[] stickPoints = new TPoint[] {
				new TPoint(0, 0),
				new TPoint(0, 1),
				new TPoint(0, 2),
				new TPoint(0, 3)
		};
		stick = new Piece(stickPoints);
		stickRotated = stick.computeNextRotation();
	}

	public void testException(){
		String invalidString = "x";
		try {
			new Piece(invalidString);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("Could not parse x,y string:"));
		}
	}
	public void testSampleSize() {
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());

		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}


	public void testSampleSkirt() {
		assertArrayEquals(new int[]{0, 0, 0}, pyr1.getSkirt());
		assertArrayEquals(new int[]{1, 0, 1}, pyr3.getSkirt());

		assertArrayEquals(new int[]{0, 0, 1}, s.getSkirt());
		assertArrayEquals(new int[]{1, 0}, sRotated.getSkirt());

	}

	public void testWeight(){
		assertEquals(2, sRotated.getWidth());
		assertNotEquals(s2.getWidth(), s2Rotated.getWidth());
		assertNotEquals(0, stick.getWidth());
		assertEquals(4, stickRotated.getWidth());
		assertNotEquals(3, pyr2.getWidth());
		assertEquals(3, pyr3.getWidth());
	}
	public void testHeight(){
		assertEquals(2, pyr1.getHeight());
		assertNotEquals(2, pyr4.getHeight());
		assertEquals(2, s.getHeight());
		assertNotEquals(2, l1.getHeight());
		assertEquals(2, l1Rotated.getHeight());;
		assertNotEquals(l2.getHeight(), l2Rotated.getHeight());
	}

	public void testSkirt(){
		assertArrayEquals(new int[]{0}, stick.getSkirt());
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, stickRotated.getSkirt()));
		assertTrue(Arrays.equals(square.getSkirt(), squareRotated.getSkirt()));
		assertArrayEquals(l1.getSkirt(), l2.getSkirt());
		assertFalse(Arrays.equals(l1Rotated.getSkirt(), l2Rotated.getSkirt()));
		TPoint[] testPoints = {
				new TPoint(1, 2),
				new TPoint(2, 3)
		};
		Piece piece = new Piece(testPoints);
		int[] skirt = piece.getSkirt();
		assertEquals(-1, skirt[0]);
	}
	public void testRotation() {
		assertNotNull(l2.fastRotation());
		assertNotNull(s.computeNextRotation());
		assertNull(s.fastRotation());
		assertEquals(s2, s2.fastRotation().computeNextRotation());
		assertNotEquals(pyr1.computeNextRotation(), pyr3.fastRotation());

	}
	public void testBody(){

		assertFalse(Arrays.equals(l1.getBody(), l2.getBody()));
		assertArrayEquals(pyr1.getBody(), pyr4.fastRotation().getBody());

		TPoint[] stickBody = {
				new TPoint(0, 0),
				new TPoint(0, 1),
				new TPoint(0, 2),
				new TPoint(0, 3)
		};
		assertFalse(Arrays.equals(stickBody, stickRotated.getBody()));

		TPoint[] s1Body = {
				new TPoint(0, 0),
				new TPoint(1, 0),
				new TPoint(1, 1),
				new TPoint(2, 1)
		};
		assertFalse(Arrays.equals(s2.getBody(),s1Body));
		TPoint[] customPoints = { new TPoint(0, 0), new TPoint(1, 0), new TPoint(1, 1), new TPoint(2, 0) };
		Piece customPiece = new Piece(customPoints);
		assertArrayEquals(customPoints, customPiece.getBody());
	}
}