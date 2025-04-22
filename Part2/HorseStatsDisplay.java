import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class HorseStatsDisplay {
    HorseV2 horseToView;
    JFrame frame = new JFrame("Horse details");
    JPanel panel1 = new JPanel(new GridLayout(8, 2));
    JPanel panel2 = new JPanel(new GridLayout(1, 1));
    JPanel panel3 = new JPanel(new GridLayout(1, 1));

    PastRacesFile pastRacesFile = new PastRacesFile();
    ArrayList<PastRace> pastRacesFiltered;

    JList<PastRace> pastRacesListDisplay = new JList<PastRace>();
    JScrollPane scrollPane = new JScrollPane(pastRacesListDisplay);

    public HorseStatsDisplay(HorseV2 horse) {
        this.horseToView = horse;
        pastRacesFiltered = pastRacesFile.filterByHorseWon(horseToView.getId());
        loadHorseStatsFrame();
    }

    public void loadTopPanel() {
        this.panel1.add(new JLabel("Name:"));
        this.panel1.add(new JLabel(this.horseToView.getName()));

        this.panel1.add(new JLabel("Symbol:"));
        this.panel1.add(new JLabel(Character.toString(this.horseToView.getSymbol())));

        this.panel1.add(new JLabel("Confidence level:"));
        this.panel1.add(new JLabel(String.format("%.2f", this.horseToView.getConfidence() * 100.0) + "%"));

        this.panel1.add(new JLabel("Equipment:"));
        this.panel1.add(new JLabel(this.horseToView.getEquipment().toString()));

        this.panel1.add(new JLabel("Games played:"));
        this.panel1.add(new JLabel(Integer.toString(this.horseToView.getGamesPlayed())));

        this.panel1.add(new JLabel("Games won:"));
        this.panel1.add(new JLabel(Integer.toString(this.horseToView.getGamesWon())));

        this.panel1.add(new JLabel("Win rate:"));
        this.panel1.add(new JLabel(String.format("%.2f", this.horseToView.getWinRating() * 100.0) + "%"));
        this.panel1.add(new JLabel("List of games won:"));
    }

    public void loadPastRacesWonList() {
        PastRace[] pastRacesFilteredArr = new PastRace[pastRacesFiltered.size()];

        for(int i = 0; i < pastRacesFiltered.size(); i++) {
            pastRacesFilteredArr[i] = pastRacesFiltered.get(i);
        }

        this.pastRacesListDisplay.setListData(pastRacesFilteredArr);
        this.panel2.add(scrollPane);
    }

    public void loadOptionsPanel() {
        JButton viewRaceBtn = new JButton("View race results");
        JButton closeBtn = new JButton("Close");
        viewRaceBtn.addActionListener(e -> {
            PastRace selectedPastRace = pastRacesListDisplay.getSelectedValue();

            if(selectedPastRace != null) {
                PastRaceDetailsDisplay detailsWindow = new PastRaceDetailsDisplay(selectedPastRace);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a race you want to view from the list first.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        closeBtn.addActionListener(e -> {
            // TODO ADD A CLOSE FUNCTION HERE
        });

        this.panel3.add(viewRaceBtn);
        this.panel3.add(closeBtn);
    }

    public void loadHorseStatsFrame() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(600, 400);
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);

        this.loadTopPanel();
        this.loadPastRacesWonList();
        this.loadOptionsPanel();

        this.frame.add(panel1, BorderLayout.NORTH);
        this.frame.add(panel2, BorderLayout.CENTER);
        this.frame.add(panel3, BorderLayout.SOUTH);
        this.frame.setVisible(true);
    }
}
