package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class AccManagerTest {
    private AccManager accountManager;

    @BeforeEach
    void setUp() {
        accountManager = new AccManager();
    }

    @Test
    void testDefault() {
        assertTrue(accountManager.accExist("Patrick", "1234"));
        assertTrue(accountManager.nameUsed("Molly", "wrong pass"));
        assertFalse(accountManager.accExist("s", "a"));
        assertEquals(2, accountManager.accNum());
    }
    @Test
    void testMul(){
        accountManager.addAcc("user", "user");
        accountManager.addAcc("user", "user");
        assertEquals(3, accountManager.accNum());

        accountManager.addAcc("root", "root");
        assertTrue(accountManager.nameUsed("user", "user"));

        accountManager.addAcc("s", "a");
        assertTrue(accountManager.nameUsed("s","a"));
        assertFalse(accountManager.addAcc("user","user"));
        assertEquals(5, accountManager.accNum());
    }
}
