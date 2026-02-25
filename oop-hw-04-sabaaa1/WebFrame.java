import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.Semaphore;

public class WebFrame extends JFrame {
    JProgressBar progressBar;
    JButton singleFetchButton;
    JButton concurrentFetchButton;
    JButton stopButton;
    private JTextField maxWorkers;
    JLabel runningLabel;
    JLabel completedLabel;
    JLabel elapsedLabel;
    DefaultTableModel model;
    int completed;
    int running;
    private Launcher launcher;


    public WebFrame(){
        super("WebLoader");
        addComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        try (BufferedReader reader = new BufferedReader(new FileReader("links.txt"))) {
            String line;
            while (true) {

                line =reader.readLine();
                if(line ==null) break ;
                model.addRow(new Object[]{line, ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load links.txt", "Error", JOptionPane.ERROR_MESSAGE);
        }
        addActions();
    }

    private void addActions() {
        singleFetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchWithLimit(1);
            }
        });
        concurrentFetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchWithLimit(Integer.parseInt(maxWorkers.getText().trim()));
            }
        });
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (launcher != null) launcher.interrupt();
            }
        });
    }

    private void addComponents() {

        maxWorkers = new JTextField("400", 3);
        maxWorkers.setMaximumSize(new Dimension(50, 25));
        completed =0;
        running =0;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel(new String[] { "url", "status"}, 0);
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(600,300));
        Box box = new Box(BoxLayout.Y_AXIS);

        singleFetchButton = new JButton("Single Thread Fetch");
        box.add(singleFetchButton);

        concurrentFetchButton = new JButton("Concurrent Fetch");
        box.add(concurrentFetchButton);


        box.add(maxWorkers);

        completedLabel = new JLabel("Completed: 0");
        box.add(completedLabel);

        runningLabel = new JLabel("Running: 0");
        box.add(runningLabel);

        elapsedLabel = new JLabel("Elapsed: ");
        box.add(elapsedLabel);

        progressBar = new JProgressBar(0,100);
        box.add(progressBar);

        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        box.add(stopButton);

        add(scrollpane, BorderLayout.CENTER);
        add(box, BorderLayout.SOUTH);
    }

    private void fetchWithLimit(int numWorkersInt) {
        completed = 0;
        running = 0;
        SwingUtilities.invokeLater(this::setDefaults);
        launcher = new Launcher(numWorkersInt, this);
        launcher.start();
    }

    private void setDefaults() {
        progressBar.setMaximum(model.getRowCount());
        progressBar.setValue(0);
        elapsedLabel.setText("Elapsed: ");
        runningLabel.setText("Running: " + 0);
        completedLabel.setText("Completed: " + 0);
        updateStatuses();
        stopButton.setEnabled(true);
        singleFetchButton.setEnabled(false);
        concurrentFetchButton.setEnabled(false);
    }

    private void updateStatuses() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt("", i, 1);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WebFrame::new);
    }
}
