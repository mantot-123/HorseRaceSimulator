import java.awt.*;
import javax.swing.*;

public class PastRaceDetailsDisplay {
    PastRace pastRaceToView;
    JFrame frame = new JFrame("Race details");
    JPanel panel1 = new JPanel(new GridLayout(5, 2));
    JPanel panel2 = new JPanel(new GridLayout(1, 1));

    public PastRaceDetailsDisplay(PastRace pastRace) {
        this.pastRaceToView = pastRace;
        loadPastRaceDetailsFrame();
    }

    public void loadPastRaceDetailsFrame() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(400, 300);
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);

        this.panel1.add(new JLabel("Track type:"));
        this.panel1.add(new JLabel(this.pastRaceToView.getTrackType().toString()));

        this.panel1.add(new JLabel("Winning horse:"));
        this.panel1.add(new JLabel(this.pastRaceToView.getWinningHorse().getName()));

        this.panel1.add(new JLabel("Track type:"));
        this.panel1.add(new JLabel(this.pastRaceToView.getTrackType().toString()));

        this.panel1.add(new JLabel("Elapsed time:"));
        this.panel1.add(new JLabel(this.pastRaceToView.getElapsedTime() + " seconds"));
        
        this.panel1.add(new JLabel("Date completed:"));
        this.panel1.add(new JLabel(this.pastRaceToView.getDateCompleted()));
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> {
            // TODO ADD CLOSE OPERATION HERE...
        });

        this.panel2.add(closeBtn);

        this.frame.add(panel1, BorderLayout.CENTER);
        this.frame.add(panel2, BorderLayout.SOUTH);

        this.frame.setVisible(true);
    }
}
