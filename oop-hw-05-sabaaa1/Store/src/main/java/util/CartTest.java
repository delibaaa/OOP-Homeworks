package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }
    @Test
    void testDefault() {
        assertTrue(cart.isEmpty());
        assertEquals(0, cart.getTotal());
    }
    @Test
    void testSimple(){
        cart.addProduct("1");
        assertFalse(cart.isEmpty());
        assertEquals(1, cart.getQuantity("1"));
        assertEquals(1, cart.getTotal());

        cart.addProduct("1");
        assertEquals(2, cart.getQuantity("1"));
        assertEquals(0, cart.getQuantity("2"));

        cart.deleteAll();
        assertTrue(cart.isEmpty());

    }
    @Test
    void testHard(){
        cart.addProduct("1", 3);
        assertEquals(3, cart.getTotal());

        cart.addProduct("2", 5);
        assertEquals(8, cart.getTotal());
        assertEquals(2, cart.getIds().size());
        cart.deleteAll();
        for(int i = 0 ; i <= 100; i++){
            cart.addProduct(Integer.toString(i), i);
        }
        assertEquals(100, cart.getIds().size());

    }
}
