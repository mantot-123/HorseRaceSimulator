import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BetHistoryDisplay {
    private JFrame frame = new JFrame("Current bets");
    private JPanel panel1 = new JPanel(new GridLayout(1, 7));
    private JPanel panel2 = new JPanel(new GridLayout(1, 1));

    private JTextField nameInput = new JTextField(15);
    private JComboBox<HorseV2> horseComboBox = new JComboBox<HorseV2>();
    private JTextField stakeInput = new JTextField(15);

    private DefaultListModel<Bet> betsListModel = new DefaultListModel<>();
    private JList<Bet> betsListDisplay = new JList<Bet>(betsListModel);
    private JScrollPane scrollPane = new JScrollPane(betsListDisplay);

    private HorsesListFile horsesFile = new HorsesListFile();
    private BetHistoryFile betHistoryFile = new BetHistoryFile();

    private ArrayList<Bet> bets = betHistoryFile.getBetsList();

    public BetHistoryDisplay()  {
        loadFrame();
    }

    public ArrayList<Bet> getBetsArrayList() {
        return this.bets;
    }

    public BetHistoryFile getBetHistoryFile() {
        return this.betHistoryFile;
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
                if(betterName.isEmpty())
                    throw new IllegalArgumentException("Please enter the name of the better. The better name must not be empty.");


                HorseV2 selectedHorse = (HorseV2)this.horseComboBox.getSelectedItem();

                double betStake = 0.0;
                if(!this.stakeInput.getText().isEmpty()) {
                    betStake = Double.parseDouble(this.stakeInput.getText());
                    if(betStake < 0.0)
                        throw new IllegalArgumentException("Please enter a valid stake amount. The stake amount must not be zero or negative.");
                }

                double betOdds;
                if(selectedHorse.getWinRating() > 0.0)
                    // Betting odds calculated by taking the reciprocal of the chosen horse's win wate
                    betOdds = 1 / selectedHorse.getWinRating();
                else
                    // Use the horse's confidence as a metric for betting odds if they have not won a single race yet
                    betOdds = 1 / selectedHorse.getConfidence();

                Bet bet = new Bet(betterName, selectedHorse, betStake, betOdds);
                this.betsListModel.addElement(bet);
                this.bets.add(bet); 

                betHistoryFile.saveBet(bet, true);

            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid stake amount. It has to be a number.", "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
    
            } catch(IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }

    public void loadBetHistoryList() {
        this.betsListModel.clear(); // Clear the list before loading the bets

        for(Bet b: this.bets) {
            this.betsListModel.addElement(b);
        }

        this.panel2.add(scrollPane);
    }

    public void reloadPlaceBetForm() {
        this.horseComboBox.removeAllItems(); // Clear existing horses from the combo box

        this.panel1.removeAll(); // Clears all of the widgets from the form
        this.panel1.revalidate();
        this.panel1.repaint();

        this.horsesFile.loadHorses(); // Reload the horses with the new data

        this.loadPlaceBetForm(); 
    }

    public void reloadBetHistoryList() {
        this.betsListModel.clear(); // Clear the list before loading the bets

        // Reload the bet history ArrayList from the file with the new contents
        this.betHistoryFile.loadBets();
        this.bets = this.betHistoryFile.getBetsList();

        for(Bet b: this.bets) {
            betsListModel.addElement(b);
        }
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
