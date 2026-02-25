import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * connects to a database and displays data. dynamically updates the table.
 */
public class TableModel extends AbstractTableModel {
    ///
    ///
    /// !!!! adikam util da dao araa aucilebelio da agar davkavi klasebad
    ///
    ///
    private final Connection connection;
    private List<String[]> curr = new ArrayList<>();
    private final String[] cols = {"Metropolis", "Continent", "Population"};

    /**
     * constructs new tableModel object and connects to database,
     * username, database and password can be modified by the user.
     */
    public TableModel() {
        try{
            String username = "root";  // optional
            String database = "metropolis_db"; // optional
            String pass = "root"; //optional
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database,
                    username, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return number of rows
     */
    @Override
    public int getRowCount() {
        return curr.size();
    }

    /**
     *
     * @return number of columns
     */
    @Override
    public int getColumnCount() {
        return cols.length;
    }

    /**
     *
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return value object on this point
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return curr.get(rowIndex)[columnIndex];
    }

    /**
     *
     * @param column  the column being queried
     * @return the name of the column
     */
    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    /**
     * inserts new record into the table with given values and refreshes
     * @param metropolis metropolis name
     * @param continent continent name
     * @param population population name
     * @throws SQLException if database fails
     */
    public void insert(String metropolis, String continent, int population) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into metropolises (metropolis, continent, population) values (?, ?, ?)");
            statement.setString(1, metropolis);
            statement.setString(2, continent);
            statement.setInt(3, population);
            statement.executeUpdate();
            try{
                Statement state = connection.createStatement();
                ResultSet resultSet = state.executeQuery("select * from metropolises");
                reload(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * search the metropolis based on the given parameters.
     *
     * @param metropolis metropolis name
     * @param continent  continent name
     * @param population population name
     * @param exact      is parameters strings exactly same as in tables value, false - if it can be a substring
     * @param larger     if true, population must be greater than given value.
     * @throws SQLException if database fails
     */
    public void search(String metropolis, String continent, String population, boolean exact, boolean larger) throws SQLException {
        ArrayList<String[]> res = new ArrayList<>();
        String query = "select * from metropolises where 5=5"; // 5 is my fav
        ArrayList<String> options = new ArrayList<>();
        if(!metropolis.isEmpty()) {
            if (exact) {
                options.add(metropolis);
                query += " and metropolis = ?";
            } else {
                options.add("%" + metropolis + "%");
                query += " and metropolis like ?";
            }
        }
        if(!continent.isEmpty()) {
            if (exact) {
                options.add(continent);
                query += " and continent = ?";
            } else {
                options.add("%" + continent + "%");
                query += " and continent like ?";
            }
        }
        if(!population.isEmpty()) {
            if (larger) {
                query += " and population > ?";
            } else {
                query += " and population <= ?";
            }
            options.add(String.valueOf(Integer.parseInt(population)));
        }

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            int i = 1;
            for(Object param : options) {
                if(param instanceof String) {
                    statement.setString(i, (String) param);
                }
                else if(param instanceof Integer) {
                    statement.setInt(i, (Integer) param);
                }
                i++;
            }
            curr.clear();
            ResultSet resultSet = statement.executeQuery();
            reload(resultSet);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * reloads the table with given data
     * update rows and throw fire about data change
     * @param resultSet  new data table
     * @throws SQLException if reading fails
     */
    private void reload(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String[] tmp = new String[3];
            tmp[0] = resultSet.getString("metropolis");
            tmp[1] = resultSet.getString("continent");
            tmp[2] = String.valueOf(resultSet.getInt("population"));
            curr.add(tmp);
        }
        fireTableDataChanged();
    }
}
