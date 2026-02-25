import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class CrackerTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private SecurityManager originalSecurityManager;

    @Before
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        originalSecurityManager = System.getSecurityManager();
        System.setSecurityManager(new NoExitSecurityManager());
    }
    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(java.security.Permission perm) {}
        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new SecurityException("System.exit called with status: " + status);
        }
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setSecurityManager(originalSecurityManager);
    }
    @Test
    public void testDefault() throws Exception {
        String hash = "86f7e437faa5a7fce15d1ddcb9eaeaea377667b8";
        String[] args = {hash, "1", "1"};
        Cracker.main(args);
        String output = outContent.toString();
        assertTrue(output.contains("a"));
        assertTrue(output.contains("all done"));
    }

    @Test
    public void testNoArgs() {
        try {
            Cracker.main(new String[0]);
            fail("Expected SecurityException due to System.exit");
        } catch (SecurityException e) {
            assertTrue(e.getMessage().contains("System.exit called"));
            String output = outContent.toString();
            assertTrue(output.contains("Args: target length [workers]"));
        } catch (NoSuchAlgorithmException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testBoomerang() throws NoSuchAlgorithmException, InterruptedException {
        String original = "saba";
        byte[] bytes = original.getBytes();
        String hex = Cracker.hexToString(bytes);
        byte[] back = Cracker.hexToArray(hex);
        assertArrayEquals(bytes, back);
        String[] args = {"abc"};
        Cracker.main(args);
        String output = outContent.toString().trim();
        assertEquals("a9993e364706816aba3e25717850c26c9cd0d89d", output);
    }
}
