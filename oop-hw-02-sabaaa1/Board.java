// Board.java

import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
 */
public class Board	{
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	int[] widths; // 0 - bottom
	int[] heights; // 0 - left
	int maxHeight;

	private boolean  [][] savedGrid;
	private int[] savedWidths;
	private  int[] savedHeights;
	private int savedMaxHeight;


	// Here a few trivial methods are provided:

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height]; //   [height] [width] would be better :)
		committed = true;
		widths = new int [height];
		heights = new int [width];
		savedHeights = new int[width];
		savedWidths = new int[height];
		savedGrid = new boolean[width][height];
		this.maxHeight = 0;
		this.savedMaxHeight = 0;
	}

	/**
	 Returns the width of the board in blocks.
	 */
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	 */
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	 */
	public int getMaxHeight() {
		return this.maxHeight;
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	 */
	public void sanityCheck() {
		if (DEBUG) {
			int[] checkedWidths = new int[height];
			int[] checkedHeights = new int[width];
			int checkedMaxHeight = 0;
			for (int i = 0; i < grid.length; i++) { // Rows
				for (int j = 0; j < grid[i].length; j++) { // Columns
					if (grid[i][j]) {
						checkedHeights[i] = Math.max(j + 1, checkedHeights[i]);
						checkedMaxHeight = Math.max(checkedMaxHeight, checkedHeights[i]);
						checkedWidths[j]++;
					}
				}
			}
			assert (checkedMaxHeight == maxHeight);
			assert(Arrays.equals(checkedHeights,heights));
			assert (Arrays.equals(checkedWidths,widths));
		}
	}

	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	 */
	public int dropHeight(Piece piece, int x) {
		int res = 0;
		for(int i = x; i <piece.getWidth() + x; i++ ){
			// skirt -> bottom
			// getColHeight -> column height
			//  highest y
			int colHeight = this.getColumnHeight(i);
			if(colHeight >  piece.getSkirt()[i - x]){
				res = Math.max(res, colHeight- piece.getSkirt()[i - x]);
			}
		}
		return res;
	}

	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	 */
	public int getColumnHeight(int x) {
		return this.heights[x];
	}


	/**
	 Returns the number of filled blocks in
	 the given row.
	 */
	public int getRowWidth(int y) {
		return this.widths[y];
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	 */
	public boolean getGrid(int x, int y) {

		if(x >= width || x < 0 || y >= height || y < 0) return true;
		return grid[x][y];
	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	 */

	public void backUp() {
		savedMaxHeight = maxHeight;
		System.arraycopy(heights, 0, savedHeights, 0, heights.length);
		System.arraycopy(widths, 0, savedWidths, 0, widths.length);
		for (int i = 0; i < width; i++){
			System.arraycopy(grid[i], 0, savedGrid[i], 0, height);
		}
	}

	public int place(Piece piece, int x, int y) {
		if (!committed) throw new RuntimeException("place commit problem");
		backUp();
		int result = PLACE_OK;
		if(piece.getWidth() + x > width || x < 0) return PLACE_OUT_BOUNDS;
		else if(piece.getHeight() + y > height || y < 0) return PLACE_OUT_BOUNDS;

		for(TPoint p : piece.getBody()){
			int curX = p.x + x;
			int curY = p.y + y;
			if(grid[curX][curY]) return PLACE_BAD;
		}
		for(TPoint p : piece.getBody()){
			int curX = p.x + x;
			int curY = p.y + y;
			grid[curX][curY] = true;
			widths[curY]++;
			if (widths[curY] == width) result = PLACE_ROW_FILLED;
			if(curY + 1 > heights[curX]) heights[curX] = curY + 1;
			if (heights[curX] > this.maxHeight) this.maxHeight = heights[curX];
		}
		committed = false;
		sanityCheck();
		return result;
	}
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	 */
	public int clearRows() {
		int rowsCleared = 0;
		boolean smthCleared = false;
		for(int from = 0; from < height; from++){
			if(widths[from] == width) {
				rowsCleared++;
				smthCleared = true;
				continue;
			}
			// to ->  from - rowsCleared
			if (smthCleared) {
				for(int i = 0 ; i < width; i++){
					grid[i][from - rowsCleared] = grid[i][from];
				}
				widths[from - rowsCleared] = widths[from];
			}
		}
		for(int i = 0 ; i < rowsCleared ; i++){
			for(int j = 0; j < width; j++){
				grid[j][height - rowsCleared + i] = false;
				heights[j]--;
			}
			widths[height - rowsCleared + i] = 0;
			this.maxHeight--;
		}
		committed = false;
		return rowsCleared;
	}

	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	 */
	public void undo() {
		if(!committed){
			int[] tmpHeights = heights;
			heights = savedHeights;
			savedHeights = tmpHeights;

			int[] tmpWidths = widths;
			widths = savedWidths;
			savedWidths = tmpWidths;
			maxHeight = savedMaxHeight;

			committed = true;
			boolean tmp[][] = grid;
			grid = savedGrid;
			savedGrid = tmp;
		}
	}


	/**
	 Puts the board in the committed state.
	 */
	public void commit() {
		committed = true;
	}

	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}