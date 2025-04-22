import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.ArrayList;

public class PastRacesListDisplay {
    JFrame frame = new JFrame("Past races...");
    JPanel panel1 = new JPanel(new GridLayout(1, 1));
    JPanel panel2 = new JPanel(new GridLayout(1, 0));
    PastRacesFile pastRaces = new PastRacesFile();
    ArrayList<PastRace> pastRacesList = pastRaces.getPastRaces();

    JList<PastRace> pastRacesListDisplay = new JList<PastRace>();
    JScrollPane scrollPane = new JScrollPane(pastRacesListDisplay);

    public PastRacesListDisplay() {
        loadPastRacesFrame();
    }

    public void loadListPanel() {
        // Convert the past races ArrayList to a fixed size array
        PastRace[] pastRacesArr = new PastRace[this.pastRacesList.size()];
        for(int i = 0; i < this.pastRacesList.size(); i++) {
            pastRacesArr[i] = this.pastRacesList.get(i);
        }

        this.pastRacesListDisplay.setListData(pastRacesArr);

        this.panel1.add(scrollPane);
    }

    public void loadOptionsPanel() {
        JButton viewBtn = new JButton("View");
        JButton closeBtn = new JButton("Close");
        
        viewBtn.addActionListener(e -> {
            PastRace selectedPastRace = pastRacesListDisplay.getSelectedValue();

            if(selectedPastRace != null) {
                PastRaceDetailsDisplay detailsWindow = new PastRaceDetailsDisplay(selectedPastRace);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a race you want to view from the list first.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        closeBtn.addActionListener(e -> {
            this.frame.dispose();
        });
        
        this.panel2.add(viewBtn);
        this.panel2.add(closeBtn);
    }

    
    public void loadPastRacesFrame() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 500);
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);

        this.loadListPanel();
        this.loadOptionsPanel();

        this.frame.add(this.panel1, BorderLayout.CENTER);
        this.frame.add(this.panel2, BorderLayout.SOUTH);

        this.frame.setVisible(true);
    }
}
