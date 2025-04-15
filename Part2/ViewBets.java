import javax.swing.JFrame;

public class ViewBets {
    JFrame frame = new JFrame("Current bets");

    public ViewBets()  {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(500, 300);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }
    
}
