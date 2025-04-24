import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BetHistoryDisplay {
    JFrame frame = new JFrame("Current bets");
    JPanel panel1 = new JPanel(new GridLayout(1, 7));
    JPanel panel2 = new JPanel(new GridLayout(1, 1));

    JTextField nameInput = new JTextField(15);
    JComboBox<HorseV2> horseComboBox = new JComboBox<HorseV2>();
    JTextField stakeInput = new JTextField(15);

    DefaultListModel<Bet> betsListModel = new DefaultListModel<>();
    JList<Bet> betsListDisplay = new JList<Bet>(betsListModel);
    JScrollPane scrollPane = new JScrollPane(betsListDisplay);

    HorsesListFile horsesFile = new HorsesListFile();
    BetHistoryFile betHistoryFile = new BetHistoryFile();

    ArrayList<Bet> bets = betHistoryFile.getBetsList();

    public BetHistoryDisplay()  {
        loadFrame();
    }
    
    public void loadPlaceBetForm() {
        // Load horse combo box
        for(HorseV2 h: this.horsesFile.getHorsesList())
            horseComboBox.addItem(h);

        JButton placeBetBtn = new JButton("Place bet");

        this.panel1.add(new JLabel("Better name:"));
        this.panel1.add(this.nameInput);

        this.panel1.add(new JLabel("Horse to bet:"));
        this.panel1.add(this.horseComboBox);
    
        this.panel1.add(new JLabel("Amount:"));
        this.panel1.add(this.stakeInput);

        this.panel1.add(placeBetBtn);

        placeBetBtn.addActionListener(e -> {
            try {
                String betterName = this.nameInput.getText();
                
                HorseV2 selectedHorse = (HorseV2)this.horseComboBox.getSelectedItem();

                double betStake = 0.0;
                if(!this.stakeInput.getText().isEmpty()) {
                    betStake = Double.parseDouble(this.stakeInput.getText());
                }

                double betOdds;
                if(selectedHorse.getWinRating() > 0.0) {
                    // Betting odds calculated by taking the reciprocal of the chosen horse's win wate
                    betOdds = 1 / selectedHorse.getWinRating();
                } else {
                    // Use the horse's confidence as a metric for betting odds if they have not won a single race yet
                    betOdds = 1 / selectedHorse.getConfidence();
                }

                Bet bet = new Bet(betterName, selectedHorse, betStake, betOdds);
                this.betsListModel.addElement(bet);

                betHistoryFile.saveBet(bet);

            } catch(IllegalArgumentException exception) {
                if(exception.getMessage() != null)
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "An error has occurred while placing a bet. Possible that you might have entered an invalid stake number?", "ERROR", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        });
    }

    public void loadBetHistoryList() {
        betsListModel.clear(); // Clear the list before loading the bets

        for(Bet b: this.bets) {
            betsListModel.addElement(b);
        }

        this.panel2.add(scrollPane);
    }

    public void loadFrame() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(950, 450);
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);

        this.loadPlaceBetForm();
        this.loadBetHistoryList();

        this.frame.add(this.panel1, BorderLayout.NORTH);
        this.frame.add(this.panel2, BorderLayout.CENTER);
    }

    public void showFrame() {
        this.frame.setVisible(true);
    }
}
