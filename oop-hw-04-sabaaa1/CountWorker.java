import javax.swing.*;

public class CountWorker extends Thread {
    private final int endPoint;
    private final JLabel value;
    public CountWorker(int endPoint, JLabel value) {
        this.endPoint = endPoint;
        this.value = value;
    }
    @Override
    public void run(){
        for(int i = 0 ; i <= endPoint ; i++){
            if(isInterrupted())break;
            if(i % 10000 == 0){
                try {
                    Thread.sleep(100);
                    int finalI = i;
                    SwingUtilities.invokeLater(() -> value.setText(String.valueOf(finalI)));
                } catch (InterruptedException e) {
                    break;
                }
            }

        }
    }
}