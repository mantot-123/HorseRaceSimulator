import javax.swing.*;
import java.awt.*;
public class BetHistoryDisplay {
    JFrame frame = new JFrame("Current bets");
    JPanel panel1 = new JPanel(new GridLayout(1, 7));
    JPanel panel2 = new JPanel(new GridLayout(1, 1));

    JTextField nameInput = new JTextField(15);
    JComboBox<HorseV2> horseComboBox = new JComboBox<HorseV2>();
    JTextField stakeInput = new JTextField(15);

    JList<Bet> betsListDisplay = new JList<Bet>();
    JScrollPane scrollPane = new JScrollPane(betsListDisplay);

    HorsesListFile horsesFile = new HorsesListFile();
    BetHistoryFile betHistoryFile = new BetHistoryFile();

    public BetHistoryDisplay()  {
        loadFrame();
    }
    
    public void loadPlaceBetForm() {
        JButton placeBetBtn = new JButton("Place bet");

        this.panel1.add(new JLabel("Better name:"));
        this.panel1.add(this.nameInput);

        this.panel1.add(new JLabel("Horse to bet:"));
        this.panel1.add(this.horseComboBox);
    
        this.panel1.add(new JLabel("Amount:"));
        this.panel1.add(this.stakeInput);

        this.panel1.add(placeBetBtn);
    }

    public void loadBetHistoryList() {
        this.panel2.add(betsListDisplay);
    }

    public void loadFrame() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 450);
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);

        this.loadPlaceBetForm();
        this.loadBetHistoryList();

        this.frame.add(this.panel1, BorderLayout.NORTH);
        this.frame.add(this.scrollPane, BorderLayout.CENTER);

        this.frame.setVisible(true);
    }
}
