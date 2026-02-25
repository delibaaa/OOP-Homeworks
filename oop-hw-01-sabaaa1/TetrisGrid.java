//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	private boolean[][] grid;
	private int rowLen;
	private int colLen;
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
		this.rowLen = grid.length;
		this.colLen = grid[0].length;
	}

	/**
	 * Does row-clearing on the grid (see handout).
	 */

	// gadmomeca
	// 1 0 1 1
	// 1 0 1 0
	// 1 1 1 1

	// 1 1 1
	// 0 0 1
	// 1 1 1
	// 1 0 1

	// rowlen = 4
	// collen = 3

	public void clearRows() {
		boolean[][] res = new boolean[rowLen][colLen];
		int dest = 0;
		for (int j = 0; j < colLen; j++) {
			boolean isFullCol = true;
			for (int i = 0; i < rowLen; i++) {
				if (!grid[i][j]) {
					isFullCol = false;
					break;
				}
			}
			if (!isFullCol) {
				for (int i = 0; i < rowLen; i++) {
					res[i][dest] = grid[i][j];
				}
				dest++;
			}
		}
		grid = res;
	}

	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return this.grid;
	}
}
