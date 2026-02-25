// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javafx.scene.paint.Stop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {
	private  JLabel value;
	private JButton startBut;
	private JButton stopBut;
	private JTextField input;
	private Thread workerThread;

	public JCount() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addComponents();
		startBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interrupt();
				int newVal = Integer.parseInt(input.getText());
				value.setText("0");
				workerThread = new Thread(new CountWorker(newVal,value));
				workerThread.start();
			}
		});
		stopBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interrupt();
			}
		});
	}
	private void addComponents(){
		value = new JLabel("0");
		startBut = new JButton("Start");
		stopBut = new JButton("Stop");
		input = new JTextField("1000000000",11);
		Box box = Box.createVerticalBox();
		box.add(input);
		box.add(value);
		box.add(startBut);
		box.add(stopBut);
		add(box);
		add(Box.createRigidArea(new Dimension(0, 40)));
	}
	private void interrupt(){
		if(workerThread!= null &&workerThread.isAlive()){
			workerThread.interrupt();
		}
	}
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	static public void main(String[] args)  {
		SwingUtilities.invokeLater(JCount::createAndShowGUI);
	}
}

