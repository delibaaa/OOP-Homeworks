import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Launcher extends Thread{
    private final Semaphore semaphore;
    private long start;
    WebFrame frame;
    private final List<WebWorker> workers;
    public Launcher(int maxConcurrency, WebFrame frame) {
        semaphore  = new Semaphore(maxConcurrency);
        this.frame = frame;
        workers = new LinkedList<>();

    }
    @Override
    public void interrupt() {
        super.interrupt();
        synchronized(this) {
            for (WebWorker worker : workers) {
                worker.interrupt();
            }
        }
    }
    @Override
    public void run(){
        start = System.currentTimeMillis();
        //frame.running++;
        checkEachUrl(0);
    }
    private  synchronized void checkEachUrl(int ind){
        if (ind >= frame.model.getRowCount() || isInterrupted()) {
            return;
        }
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            semaphore.release();
            return;
        }
        if(isInterrupted()) {
            semaphore.release() ;
            return;
        }
        String url = (String) frame.model.getValueAt(ind, 0);
        WebWorker worker = new WebWorker(url, ind, semaphore, this);
        workers.add(worker);
        worker.start();
        frame.running++;
        update();
        checkEachUrl(ind + 1);
    }
    void update(){
        SwingUtilities.invokeLater(() -> {
            frame.runningLabel.setText("Running: " + frame.running);
            frame.completedLabel.setText("Completed: " + frame.completed);
            frame.progressBar.setValue(frame.completed);
        });
    }

    public synchronized void workerDone(int row) {
        frame.running--;
        frame.completed++;
        update();
        //semaphore.release();
        if (frame.completed == frame.model.getRowCount()) {
            String time = "Elapsed: " + (System.currentTimeMillis() - start) + "ms";
            restart(time);
        } else {
            semaphore.release();
            checkEachUrl(row + 1);
        }
    }

    private void restart(String time){
        SwingUtilities.invokeLater(() -> {
            frame.elapsedLabel.setText(time);
            frame.stopButton.setEnabled(false);
            frame.singleFetchButton.setEnabled(true);
            frame.concurrentFetchButton.setEnabled(true);
        });
    }

}