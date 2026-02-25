import org.junit.jupiter.api.*;;
import java.lang.reflect.Field;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
///
///
///
/// !!! If it doesn't work, you should add the H2 jar to connect to a temporary database
///     I choose H2 because it doesn't change the original one during testing
///
///
///
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestMetropolises {
    private TableModel model;
    private Connection connection;
    @BeforeAll
    public void setup() throws SQLException, ClassNotFoundException {
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String user = "test";
        String pass = "test";
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(url, user, pass);
        String q = "CREATE TABLE metropolises (" +
                "metropolis CHAR(64)," +
                "continent CHAR(64)," +
                "population BIGINT)";
        PreparedStatement statement = connection.prepareStatement(q);
        statement.executeUpdate();
    }
    @BeforeEach
    public void reset() throws SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM metropolises");
            statement.executeUpdate("INSERT INTO metropolises VALUES " +
                    "('Gurjaani','kaxeti',9999999)," +
                    "('walenjixa','samargalo',8888888)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        model = new TableModel();
        Field connectionField = TableModel.class.getDeclaredField("connection");
        connectionField.setAccessible(true);
        connectionField.set(model, connection);
    }
    @Test
    public void TestDefault() throws SQLException {
        Assertions.assertEquals("Metropolis", model.getColumnName(0));
        Assertions.assertEquals("Continent", model.getColumnName(1));
        Assertions.assertEquals("Population", model.getColumnName(2));
        model.search("", "", "", true, true);
        Assertions.assertEquals(2, model.getRowCount());
    }
    @Test
    public void testAdd() throws SQLException {
        model.insert("telavi", "kaxeti", 7777777);
        model.search("", "", "", true, true);
        Assertions.assertEquals(3, model.getRowCount());
        model.search("telavi", "", "", true, true);
        Assertions.assertEquals(1, model.getRowCount());
        Assertions.assertEquals("telavi", model.getValueAt(0, 0));
        Assertions.assertEquals("kaxeti", model.getValueAt(0, 1));
    }
    @Test
    public void testGetValue() throws SQLException {
        model.search("", "", "9000000", true, true);
        Assertions.assertEquals(1, model.getRowCount());
        Assertions.assertEquals("Gurjaani", model.getValueAt(0, 0));
    }
    @Test
    public void testContains() throws SQLException {
        model.search("ji", "", "", false, true);
        Assertions.assertEquals(1, model.getRowCount());
    }
    @Test
    public void testExact() throws SQLException {
        model.search("W", "", "9000000", false, false);
        Assertions.assertEquals(0, model.getRowCount());
    }
    @Test
    public void testEmpty() throws SQLException {
        model.search("", "", "", true, true);
        Assertions.assertEquals(2, model.getRowCount());
        Assertions.assertThrows(NumberFormatException.class, () -> {
            model.search("", "", "notInt", true, true);
        });
        model.search("Freeuni", "", "", true, true);
        Assertions.assertEquals(0, model.getRowCount());
        model.search("Gurjaani", "", "", true, true);
        Assertions.assertEquals("Gurjaani", model.getValueAt(0, 0));
        Assertions.assertEquals("kaxeti", model.getValueAt(0, 1));
        Assertions.assertEquals("9999999", model.getValueAt(0, 2));
    }
    @Test
    public void testContinent() throws SQLException {
        model.search("", "kaxeti", "", true, true);
        Assertions.assertEquals(1, model.getRowCount());
        Assertions.assertEquals("kaxeti", model.getValueAt(0, 1));
        model.search("", "xet", "", false, true);
        Assertions.assertEquals(1, model.getRowCount());
        Assertions.assertEquals("kaxeti", model.getValueAt(0, 1));
    }
    @Test
    public void testNum() throws SQLException {
        model.search("", "", "8000000", true, false);
        Assertions.assertEquals(0, model.getRowCount());
        Assertions.assertEquals(3, model.getColumnCount());
        model.search("", "", "8000000", true, true);
        Assertions.assertEquals(2, model.getRowCount());
    }
    @Test
    public void testSearch() throws SQLException {
        model.search("", "", "80000000", true, false);
        Assertions.assertEquals(2, model.getRowCount());
        Assertions.assertEquals("Gurjaani", model.getValueAt(0, 0));
    }
}
