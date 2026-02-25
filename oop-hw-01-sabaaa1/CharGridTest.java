
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class CharGridTest extends TestCase {

	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	public void testCharAreaEdgeCase() {
		char[][] grid = new char[][] {
				{' '}
		};
		CharGrid cg = new CharGrid(grid);

		assertEquals(0, cg.charArea('a'));
		assertEquals(1, cg.charArea(' '));
	}
	public void testCharArea() {
		char[][] grid = new char[][] {
				{'c', 'a', ' ' , 'b'},
				{'b', ' ', 'b', 'c'},
				{' ', ' ', 'a' , 'b'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(0, cg.charArea('$'));
		assertEquals(12, cg.charArea('b'));
		assertEquals(0, cg.charArea('B'));
		assertEquals(9, cg.charArea(' '));
	}
	public void testRectangularAreaSame() {
		char[][] grid = new char[][] {
				{'a', 'a', 'a'},
				{'a', 'a', 'a'},
				{'a', 'a', 'a'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(9, cg.charArea('a'));
	}
	public void testRectangularAreaUpperCase() {
		char[][] grid = new char[][] {
				{'a', 'a', 'A'},
				{'a', 'a', 'a'},
				{'a', 'a', 'A'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(9, cg.charArea('a'));
		assertEquals(3, cg.charArea('A'));
	}
	public void testDiagonal() {
		char[][] grid = new char[][] {
				{'d', 'x', 'x'},
				{'x', 'd', 'x'},
				{'x', 'x', 'd'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(9, cg.charArea('d'));

	}
	public void testEmptyGrid() {
		char[][] grid = new char[][] {};

		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.charArea('W'));

	}
	public void testPlusEmpty() {
		char[][] grid = new char[][]{};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
	public void testPlusSingle() {
		char[][] grid = new char[][]{
				{'+'}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
	public void testTrickySimple() {
		char[][] grid = {
				{' ', ' ', 'x', ' ', ' '},
				{' ', ' ', 'x', ' ', ' '},
				{'x', 'x', 'x', 'x', 'x'},
				{' ', ' ', 'x', ' ', ' '},
				{' ', ' ', 'x', ' ', ' '},
				{' ', ' ', 'x', ' ', ' '}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}

	public void testPlusMultiple() {
		char[][] grid = new char[][] {
				{' ', ' ', 'x', ' ', ' ', ' ', ' ', 'y', ' ', ' ', ' ', ' ', ' ', },
				{' ', ' ', 'x', ' ', ' ', ' ', ' ', 'y', ' ', ' ', ' ', ' ', ' ',  },
				{'x', 'x', 'x', 'x', 'x', 'y', 'y', 'y', 'y', 'y', ' ',  ' ',  ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', 'x', ' ', ' ', ' ', ' ', 'y', ' ', ' ', ' ', ' ', ' ' },
				{' ', ' ', 'x', ' ', ' ', ' ', ' ', 'y', ' ', ' ', ' ', ' ', ' '  },
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
	}
	public void testSimple() {
		char[][] grid = new char[][] {
				{'x', 'x', 'x'},
				{'x', 'x', 'x'},
				{'x', 'x', 'x'}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}

	public void testPlusSensitivity() {
		char[][] grid = new char[][] {
				{'a', 'a', 'A'},
				{'a', 'a', 'a'},
				{'a', 'a', 'a', 'a', 'a'},
				{'a', 'a', 'a'},
				{'a', 'a', 'a'}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
	public void testDifSizes() {
		char[][] grid = new char[][] {
				{'x', 'x', 'x', 'x'},
				{'x', 'x', 'x', ' '},
				{'x', 'x'},
				{'x', 'x', 'x', 'x'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
}
