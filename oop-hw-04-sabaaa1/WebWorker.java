import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;
import javax.swing.*;

public class WebWorker extends Thread {
    private final Launcher launcher;
    private final String urlString;
    private final int row;

    public WebWorker(String url, int ind, Semaphore semaphore, Launcher launcher) {
        this.urlString = url;
        this.row = ind;
        this.launcher = launcher;
    }

    @Override
    public void run() {
        launcher.frame.running++;
        launcher.update();
        InputStream input = null;
        StringBuilder contents = null;
        try {
            long startTime = System.currentTimeMillis();
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);

            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                if (isInterrupted()) {

                    SwingUtilities.invokeLater(() -> {
                        launcher.frame.model.setValueAt("interrupted", row, 1);
                    });
                    return;

                }
                contents.append(array, 0, len);
                Thread.sleep(100);
            }
            String status = "Fetched: " + contents.length() + " chars, Time: " +
                    String.format("%.3f", (System.currentTimeMillis() - startTime) / 1000.0) + "s";
            String finalStatus = status;
            SwingUtilities.invokeLater(() -> {
                launcher.frame.model.setValueAt(finalStatus, row, 1);
            });


        }
        catch (InterruptedException | IOException  e) {
            final String errorMsg = "error";

            SwingUtilities.invokeLater(() -> {
                launcher.frame.model.setValueAt(errorMsg, row, 1);
            });
        }
        finally {
            try {
                if (input != null) input.close();
                launcher.workerDone(row);
                //semaphore.release();
            } catch (IOException ignored) {
            }
        }

    }
}
