import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


public class SudokuFrame extends JFrame {
	private JTextArea input;
	private JTextArea result;
	private Box control;
	private JButton check;
	private JCheckBox autoCheck;
	public SudokuFrame() {
		super("Sudoku Solver");
		setFrame();
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solve();
			}
		});
		input.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(autoCheck.isSelected()) solve();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(autoCheck.isSelected()) solve();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(autoCheck.isSelected()) solve();
			}
		});

		add(input, BorderLayout.CENTER);
		add(result, BorderLayout.EAST);
		add(control, BorderLayout.SOUTH);
		// Could do this:
		// setLocationByPlatform(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private void setFrame(){
		setLayout(new BorderLayout(4, 4));
		input= new JTextArea(15, 20);
		input.setBorder(new TitledBorder("Puzzle"));
		result = new JTextArea(15, 20);
		result.setBorder(new TitledBorder("Solution"));
		result.setEditable(false);
		control = Box.createHorizontalBox();
		check = new JButton("Check");
		control.add(check);
		autoCheck = new JCheckBox("Auto Check");
		autoCheck.setSelected(true);
		control.add(autoCheck);
	}
	private void solve(){
		try {
			String inputString = input.getText();
			Sudoku sudoku = new Sudoku(inputString);
			int sol = sudoku.solve();
			if(sol == 0) {
				result.setText("No solution found");
				return;
			}
			result.setText(sudoku.getSolutionText() + "solutions:" + sol + "\n" + "elapsed:" + sudoku.getElapsed() + "ms\n" );
		}
		catch(Exception e){
			result.setText("Parsing problem");
		}
	}

	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }

		SudokuFrame frame = new SudokuFrame();
	}

}