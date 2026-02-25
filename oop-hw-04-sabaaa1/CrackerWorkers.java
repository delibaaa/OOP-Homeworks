import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class CrackerWorkers extends Thread {
    private byte[] targetHash;
    private CountDownLatch latch;
    private int startIndex, endIndex, length;
    MessageDigest md;

    public CrackerWorkers(byte[] targetHash, CountDownLatch latch, int startIndex, int endIndex, int length, MessageDigest md) {
        this.targetHash = targetHash;
        this.latch = latch;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.length = length;
        this.md = md;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            crack(String.valueOf(Cracker.CHARS[i]));
        }


        latch.countDown();
    }

    private void crack(String curr) {
        byte[] hash = md.digest(curr.getBytes());
        if (Arrays.equals(hash, targetHash)) {
            System.out.println(curr);
        }
        if (curr.length() >= length) return;
        for (char c : Cracker.CHARS) {
            crack(curr + c);
        }
    }
}