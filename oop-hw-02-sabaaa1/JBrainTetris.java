import javax.swing.*;
import java.awt.*;

public class JBrainTetris extends JTetris {
    private final Brain brain;
    private JCheckBox brainOn;
    private int currCount;
    private Brain.Move move;
    private JSlider adv;
    private JLabel status;

    public JBrainTetris(int pixels){
        super(pixels);
        brain = new DefaultBrain();
        currCount = -1;
        move = null;
    }
    @Override
    public void tick(int verb) {
        if (brainOn.isSelected() && verb == DOWN) {
            if (count != currCount) {
                board.undo();
                currCount = count;
                move = brain.bestMove(board, currentPiece, board.getHeight(), move);
            }
            if (move == null) {
                super.tick(verb);
                return;
            }
            if (currentX > move.x) {
                tick(LEFT);
            }
            else if (currentX < move.x) {
                tick(RIGHT);
            }
            else if (!currentPiece.equals(move.piece)) {
                tick(ROTATE);
            }
        }
        super.tick(verb);
    }
    @Override
    public Piece pickNextPiece(){
        int currVal = adv.getValue();
        if(currVal != 0 || random.nextInt(100) < currVal) {
            status.setText("*ok*");
            board.commit();
            double worstScore = Double.NEGATIVE_INFINITY;
            Piece ret = null;
            for (Piece p : pieces) {
                Brain.Move pieceMove = brain.bestMove(board, p, board.getHeight(), null);
                if (pieceMove == null) {
                    ret = p;
                    break;
                }

                if (worstScore < pieceMove.score) {
                    worstScore = pieceMove.score;
                    ret = p;
                }

            }
            if (ret != null) return ret;
        }
        else{
            status.setText("ok");
        }
        return super.pickNextPiece();
    }
    @Override
    public JComponent createControlPanel() {
        JComponent panel = super.createControlPanel();
        panel.add(new JLabel("Brain:"));
        brainOn = new JCheckBox("Brain active :)");
        status = new JLabel("ok");
        panel.add(brainOn);
        JPanel little = new JPanel();
        little.add(status);
        panel.add(little);
        little.add(new JLabel("Adversary:"));
        adv = new JSlider(0, 100, 0);
        adv.setPreferredSize(new Dimension(100, 15));
        little.add(adv);
        return panel;

    }
    public static void main(String[] args) {
        JFrame frame = JTetris.createFrame(new JBrainTetris(16));
        frame.setVisible(true);
    }
}