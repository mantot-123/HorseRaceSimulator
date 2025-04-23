import java.awt.*;
import java.util.Vector;
import javax.swing.*;

public class PastRacesFiltersWindow {
    PastRacesListDisplay parent;

    private JFrame frame = new JFrame("Set filters");
    private JPanel panel1 = new JPanel(new GridLayout(1, 2));
    private JPanel panel2 = new JPanel(new GridLayout(1, 2));

    private TrackTypesFile trackTypesFile = new TrackTypesFile();
    private Vector<TrackType> trackTypes = new Vector<TrackType>(trackTypesFile.getTrackTypes().values());

    private JComboBox<TrackType> trackComboBox = new JComboBox<TrackType>();

    private TrackType trackFilter;

    public PastRacesFiltersWindow(PastRacesListDisplay parent) {
        this.parent = parent;
        loadFiltersWindow();
    }

    public void loadFilterComboBoxes() {
        // Loads track type information into a combobox
        this.trackComboBox.addItem(new TrackType("NONE", "None", 0, 0));
        for(TrackType t: this.trackTypes) {
            this.trackComboBox.addItem(t);
        } 

        this.panel1.add(new JLabel("Track type:"));
        this.panel1.add(trackComboBox);
    }

    public void loadFilterOptionButtons() {
        JButton applyBtn = new JButton("Apply filters...");
        JButton closeBtn = new JButton("Close");

        applyBtn.addActionListener(e -> {
            this.trackFilter = (TrackType)this.trackComboBox.getSelectedItem();

            this.parent.applyFilters(trackFilter);
            this.frame.dispose();
        });

        closeBtn.addActionListener(e -> {
            this.frame.dispose();
        });

        this.panel2.add(applyBtn);
        this.panel2.add(closeBtn);
    }

    public void loadFiltersWindow() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(300, 90);
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);

        this.loadFilterComboBoxes();
        this.loadFilterOptionButtons();
        
        this.frame.add(panel1, BorderLayout.CENTER);
        this.frame.add(panel2, BorderLayout.SOUTH);
    }

    public void showWindow() {
        this.frame.setVisible(true);
    }

    public TrackType getTrackTypeFilter() {
        return this.trackFilter;
    }
}
