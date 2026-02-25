package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsTest {
    private Products product;

    @BeforeEach
    void setUp() {
        product = new Products();
    }

    @Test
    void testDefault() {
        assertNull(product.getName());
        assertNull(product.getProductId());
        assertNull(product.getImageFile());
        assertEquals(0.0, product.getPrice());
    }
    @Test
    void testSimple() {
        Products p = new Products(7, "Test", "1", "test.jpg");
        assertNotEquals(7.1, p.getPrice());
        assertEquals("Test", p.getName());
        assertEquals("1", p.getProductId());
        assertNotEquals("test.jpgg", p.getImageFile());
    }
    @Test
    void testSetGet() {
        product.setPrice(77);
        assertEquals(77, product.getPrice());
        product.setPrice(10);
        assertEquals(10, product.getPrice());

        product.setName("");
        assertEquals("", product.getName());
        product.setName(null);
        assertNull(product.getName());

        product.setProductId("777");
        assertEquals("777", product.getProductId());
        product.setProductId(null);
        assertNull(product.getProductId());

        product.setImageFile("smth.jpg");
        assertNotEquals("smth.png", product.getImageFile());
        product.setImageFile("");
        assertEquals("", product.getImageFile());
    }

}
