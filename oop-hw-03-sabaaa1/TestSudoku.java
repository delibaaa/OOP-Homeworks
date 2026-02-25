import org.junit.Test;
import static org.junit.Assert.*;

public class TestSudoku {
    private Sudoku easy = new Sudoku(Sudoku.easyGrid);
    private Sudoku medium = new Sudoku(Sudoku.mediumGrid);
    private Sudoku hardGrid = new Sudoku(Sudoku.hardGrid);
    private String extraGrid = "1 0 3 4 5 6 7 8 9 1 " +
            "2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 " +
            "3 4 5 6 7 8 0 1 2 3 4 5 6 7 0 9 1 2 3 4" +
            " 5 6 7 8 9 0 2 3 4 5 6 7 8 9 zzzzz 1 2 3 0 5" +
            " -0 7 8 9 1 2 3 4 5 6 0 8 9";
    int[][] emptyGrid = Sudoku.stringsToGrid(
            "0 , , , 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 x 0 0 0 0 0 0",
            "0 0 0 0 z 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 y 0 0 0 0 0 0 0",
            "0 0 0 0 0 , t 0 0 0 0",
            "0 0 0 0 0 0z 0 0 0",
            "0 0 0 0 0 0 0 0 0");
    private Sudoku empty = new Sudoku(emptyGrid);
    @Test
    public void testSudokuEmpty() {
        Sudoku sudoku = new Sudoku(emptyGrid);
        int solutions = sudoku.solve();
        assertEquals(100, solutions);
    }

    @Test
    public void testSudokuMedium() {
        Sudoku sudoku = new Sudoku(Sudoku.mediumGrid);
        int solutions = sudoku.solve();
        String text = sudoku.getSolutionText();
        assertEquals(1, solutions);
        assertFalse(text.contains("0"));
    }

    @Test
    public void testSudokuHard() {
        Sudoku sudoku = new Sudoku(Sudoku.hardGrid);
        int solutions = sudoku.solve();
        assertEquals(1, solutions);
        assertTrue(sudoku.getElapsed() > 0);
    }
    @Test
    public void testTextToGridInvalid() {
        try{
            Sudoku.textToGrid("1 2  3?");
        }catch(Exception e){
            assertTrue(e.getMessage().contains("Needed 81 numbers, but got:3"));
        }
    }
    @Test
    public void ctor() {
        Sudoku s = new Sudoku(" 0 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9");
        assertNotNull(s);
    }
    @Test
    public void testMain() {
        String[] args = new String[0];
        Sudoku.main(args);
    }
    @Test
    public void testTexToGrid() {
        int [][] test = Sudoku.textToGrid(extraGrid);
        assertEquals(9, test.length);
        assertEquals(9, test[0].length);
    }

}
