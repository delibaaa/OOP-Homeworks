// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}

	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int left = Integer.MAX_VALUE;
		int top = Integer.MAX_VALUE;
		int right = -1;
		int bottom = -1;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == ch) {
					top = Math.min(top, i);
					left = Math.min(left, j);
					bottom = Math.max(bottom, i);
					right = Math.max(right, j);
				}
			}
		}
		if (right == -1) {
			return 0;
		}
		return (bottom - top + 1) * (right - left + 1);
	}

	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	private int currentCenter(int row, int col, char currentChar) {
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;

		for (int i = col + 1; i < grid[row].length; i++) {
			if (grid[row][i] != currentChar) break;
			right++;
		}
		for (int i = row + 1; i < grid.length; i++) {
			if (col >= grid[i].length || grid[i][col] != currentChar) break;
			down++;
		}
		for (int i = row - 1; i >= 0; i--) {
			if ( col >= grid[i].length || grid[i][col] != currentChar) break;
			up++;
		}
		for (int i = col - 1; i >= 0; i--) {
			if (grid[row][i] != currentChar) break;
			left++;
		}
		if (  right>=1 && up== down && up == left &&
				up == right) {
			return 1;
		}
		return 0;
	}

	public int countPlus() {
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				count += currentCenter(i, j, grid[i][j]);
			}
		}
		return count;
	}
}
