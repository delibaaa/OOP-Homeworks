package DAO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.DatabaseUtil;
import util.Products;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

///         !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
///         !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
///
///
///  after run ur table can be empty, so fill table before run the server
///  also tests are for empty tables!!!!!
///
///
/// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
class readBeforeTest {

    @BeforeAll
    static void setup() throws Exception {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS products (" +
                    "productid VARCHAR(255) PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "imagefile VARCHAR(255), " +
                    "price DOUBLE)");
            stmt.execute("INSERT INTO products VALUES ('1', '1', '1.jpg', 1)");
            stmt.execute("INSERT INTO products VALUES ('2', '1', '1.jpg', 1)");
        }
    }

    @AfterAll
    static void cleanup() throws Exception {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE products");
        }
    }

    @Test
    void testSimple() {
        int count = ProductsDAO.getProductCount();
        assertEquals(2, count);

        List<Products> products = ProductsDAO.getAll();
        assertEquals(2, products.size());

        List<Products> results = ProductsDAO.searchProduct("1");
        assertFalse(results.isEmpty());
        assertEquals("1", results.get(0).getName());
    }

    @Test
    void hard() {
        List<Products> results = ProductsDAO.searchProduct("XYZ");
        assertTrue(results.isEmpty());
        results = ProductsDAO.searchProduct("");
        assertEquals(2, results.size());
        assertTrue(ProductsDAO.productInStock("1"));
        assertFalse(ProductsDAO.productInStock("999"));
    }

    @Test
    void testNull() {
        assertNull(ProductsDAO.getProductById(null));
        assertNull(ProductsDAO.getProductById(""));
        assertNull(ProductsDAO.getProductById("   "));
    }
}