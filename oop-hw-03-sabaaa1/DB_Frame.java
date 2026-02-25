import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB_Frame extends JFrame {
    private JTextField metropolisField;
    private JTextField continentField;
    private JTextField populationField;
    private DefaultTableModel tableModel;
    private JComboBox<String> populationCombo;
    private JComboBox<String> matchTypeCombo;
    private final TableModel model = new TableModel();
    JPanel buttonPanel;

    public DB_Frame() {
        super("Metropolis Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        addInput();
        addButtons();
        addOptions();
        setMinimumSize(new Dimension(800, 600));
        pack();
        setVisible(true);
    }

    private void addOptions() {
        JPanel searchOptionsPanel = new JPanel();
        searchOptionsPanel.setLayout(new BoxLayout(searchOptionsPanel, BoxLayout.Y_AXIS));
        String[] popOptions = {"Population Larger Than", "Population Smaller Than"};
        String[] matchOptions = {"Exact Match", "Partial Match"};
        populationCombo = new JComboBox<>(popOptions);
        matchTypeCombo = new JComboBox<>(matchOptions);
        searchOptionsPanel.add(populationCombo);
        searchOptionsPanel.add(matchTypeCombo);
        buttonPanel.add(searchOptionsPanel);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void addButtons() {
        JTable dataTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton addButton = new JButton("Add");
        JButton searchButton = new JButton("Search");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    add();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    search();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
    }

    private void addInput() {
        JPanel input = new JPanel(new FlowLayout(FlowLayout.LEFT));
        input.add(new JLabel("Metropolis:"));
        metropolisField = new JTextField(15);
        input.add(metropolisField);
        input.add(new JLabel("Continent:"));
        continentField = new JTextField(15);
        input.add(continentField);
        input.add(new JLabel("Population:"));
        populationField = new JTextField(15);
        input.add(populationField);
        add(input, BorderLayout.NORTH);
    }

    private void add() throws SQLException {
        String metropolis = metropolisField.getText().trim();
        String continent = continentField.getText().trim();
        String population = populationField.getText().trim();
        try {
            model.insert(metropolis, continent, Integer.parseInt(population));
            metropolisField.setText("");
            continentField.setText("");
            populationField.setText("");

        } catch (SQLException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void search() throws SQLException {
        String metropolis = metropolisField.getText().trim();
        String continent = continentField.getText().trim();
        String population = populationField.getText().trim();
        boolean isExact = matchTypeCombo.getSelectedIndex() == 0;
        boolean isLarger = populationCombo.getSelectedIndex() == 0;
        try {
            model.search(metropolis, continent, population, isExact, isLarger);
            metropolisField.setText("");
            continentField.setText("");
            populationField.setText("");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new DB_Frame();
    }
}