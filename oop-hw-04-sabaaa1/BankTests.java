import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTests {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    @Before
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    @Test
    public void testAcc() {
        Account acc = new Account(null, 1, 1000);
        acc.add(1);
        acc.withDraw(2);
        assertEquals(acc.toString(), "acct:" + 1 + " bal:" + acc.getBalance() + " trans:" + acc.getTransactions());
    }

    @Test
    public void testTransaction() {
        Transaction transaction = new Transaction(7, 8, 78);
        assertEquals(7, transaction.getFrom());
        assertEquals(8, transaction.getTo());
        assertEquals(78, transaction.getAmount());
        assertEquals("from:" + transaction.getFrom() + " to:" + transaction.getTo() + " amt:" + transaction.getAmount(), transaction.toString());
    }
    @Test
    public void testBank() throws InterruptedException {
        Bank bank = new Bank();
        Bank.transactions = new java.util.concurrent.ArrayBlockingQueue<>(20);
        bank.accounts = new Account[Bank.ACCOUNTS];

        for(int i = 0; i < Bank.ACCOUNTS; i++) {
            bank.accounts[i] = new Account(bank, i, 1000);
        }
        for(int i = 0; i < Bank.ACCOUNTS; i++) {
            assertEquals(1000, bank.accounts[i].getBalance());
            assertEquals(0, bank.accounts[i].getTransactions());
        }
        try {
            bank.readFile("small.txt");
        } catch (Exception e) {
            System.out.println("Note: small.txt file not found for testing");
        }
    }
    @Test
    public void testWorker() throws InterruptedException {

        Bank bank = new Bank();
        Bank.transactions = new java.util.concurrent.ArrayBlockingQueue<>(20);
        bank.accounts = new Account[Bank.ACCOUNTS];
        for(int i = 0; i < Bank.ACCOUNTS; i++) {
            bank.accounts[i] = new Account(bank, i, 1000);
        }


        bank.latch = new java.util.concurrent.CountDownLatch(1);
        Bank.transactions.put(new Transaction(0, 1, 100));
        Bank.transactions.put(new Transaction(-1, 0, 0));
        Thread worker = new Thread(bank.new Worker());
        worker.start();

        bank.latch.await();
        assertEquals(1100, bank.accounts[1].getBalance());
        assertEquals(1, bank.accounts[0].getTransactions());
    }
    @Test
    public void testNoArgs() {

        SecurityManager originalSecurityManager = System.getSecurityManager();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
            }
            @Override
            public void checkExit(int status) {
                super.checkExit(status);
                throw new SecurityException("hae");
            }

        });
        try {
            Bank.main(new String[]{});

        } catch (SecurityException e) {
            assertTrue(outContent.toString().contains("Args: transaction-file"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.setSecurityManager(originalSecurityManager);
        }
    }
    @Test
    public void testMain() throws Exception {


        File tempFile = File.createTempFile("bank-transactions", ".txt");
        List<String> lines = Arrays.asList(
                "0 1 100",
                "1 0 50"
        );
        Files.write(tempFile.toPath(), lines);
        String[] args = new String[2];
        args[0] = tempFile.getAbsolutePath();
        args[1] = "2";
        Bank.main(args);
        String output = outContent.toString();
        assertTrue(output.contains("acct:0 bal:950"));
        assertTrue(output.contains("acct:1 bal:1050"));
    }

}
