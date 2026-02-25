import junit.framework.TestCase;
import java.util.*;

public class TetrisGridTest extends TestCase {
	
	// Provided simple clearRows() test
	// width 2, height 3 grid
	public void testClear1() {
		boolean[][] before =
		{	
			{true, true, false, },
			{false, true, true, }
		};
		
		boolean[][] after =
		{	
			{true, false, false},
			{false, true, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void test1xN() {
		boolean[][] before =
				{
						{true},
						{true},
						{true}
				};

		boolean[][] after =
				{{false},
				 {false},
				{false},
				};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void test1xNFalse() {
		boolean[][] before =
				{
						{true},
						{false},
						{true}
				};

		boolean[][] after =
				{{true},
						{false},
						{true},
				};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void testNx1() {
		boolean[][] before =
				{
						{true, true, true, }
				};

		boolean[][] after =
				{{false, false, false, }};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void testNx1False() {
		boolean[][] before =
				{
						{true, false, true, }
				};

		boolean[][] after =
				{{false, false, false, }};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void testEmpty() {
		boolean[][] before =
				{{}};

		boolean[][] after =
				{{}};
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();
		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void testTrues() {
		boolean[][] before =
				{
						{true, true, true, },
						{true, true, true, }
				};

		boolean[][] after =
				{
						{false, false, false},
						{false, false, false}
				};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void testFalse() {
		boolean[][] before =
				{
						{false, false, false, },
						{false, false, false, }
				};

		boolean[][] after =
				{
						{false, false, false},
						{false, false, false}
				};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	public void testBig() {
		boolean[][] before = {
				{true, true, false, false, true},
				{false, true, true, true, true},
				{false, true, true, false, true},
				{true, true, false, false, true},
				{false, true, true, true, true},
				{false, true, true, false, true}
		};

		boolean[][] after = {
				{true, false, false, false, false},
				{false, true, true, false, false},
				{false, true, false, false, false},
				{true, false, false, false, false},
				{false, true, true, false, false},
				{false, true, false, false, false}
		};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}

}
