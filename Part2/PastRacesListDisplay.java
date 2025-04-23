import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.ArrayList;

public class PastRacesListDisplay {
    JFrame frame = new JFrame("Past races...");
    JPanel panel1 = new JPanel(new GridLayout(1, 1));
    JPanel panel2 = new JPanel(new GridLayout(1, 0));
    
    PastRacesFile pastRacesFile = new PastRacesFile();
    ArrayList<PastRace> pastRacesList = pastRacesFile.getPastRaces();

    JList<PastRace> pastRacesListDisplay = new JList<PastRace>();
    JScrollPane scrollPane = new JScrollPane(pastRacesListDisplay);

    PastRacesFiltersWindow filtersWindow;

    public PastRacesListDisplay() {
        loadPastRacesFrame();
    }

    public void loadListPanel() {
        // Convert the past races ArrayList to a fixed size array
        PastRace[] pastRacesArr = pastRacesList.toArray(new PastRace[pastRacesList.size()]);

        this.pastRacesListDisplay.setListData(pastRacesArr);

        this.panel1.add(scrollPane);
    }

    public void loadOptionsPanel() {
        JButton viewBtn = new JButton("View results");
        JButton filtersBtn = new JButton("Filter results");
        JButton closeBtn = new JButton("Close");
        
        viewBtn.addActionListener(e -> {
            PastRace selectedPastRace = pastRacesListDisplay.getSelectedValue();

            if(selectedPastRace != null) {
                PastRaceDetailsDisplay detailsWindow = new PastRaceDetailsDisplay(selectedPastRace);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a race you want to view from the list first.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        filtersBtn.addActionListener(e -> {
            if(this.filtersWindow == null)
                this.filtersWindow = new PastRacesFiltersWindow(this);
            
            this.filtersWindow.showWindow();
        });

        closeBtn.addActionListener(e -> {
            this.frame.dispose();
        });

        this.panel2.add(viewBtn);
        this.panel2.add(filtersBtn);
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

    public void applyFilters(TrackType trackTypeFilter) {
        pastRacesFile.resetFilters();

        if(!trackTypeFilter.getId().equals("NONE")) {
            pastRacesFile.filterByTrackType(trackTypeFilter.getId());
            ArrayList<PastRace> pastRacesFiltered = pastRacesFile.getPastRacesFiltered();
            pastRacesListDisplay.setListData(pastRacesFiltered.toArray(new PastRace[pastRacesFiltered.size()]));
        } else {
            pastRacesListDisplay.setListData(pastRacesList.toArray(new PastRace[pastRacesList.size()]));
        }
        
    }
}
