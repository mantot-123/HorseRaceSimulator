import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class HorsesListDisplay {
    JFrame frame = new JFrame("Horses list");
    HorsesListFile horsesFile = new HorsesListFile();

    JPanel panel1 = new JPanel(new GridLayout(1, 1));
    JPanel panel2 = new JPanel(new GridLayout(1, 2));
    ArrayList<HorseV2> horses = horsesFile.getHorsesList();

    JList<HorseV2> horsesListDisplay = new JList<HorseV2>();
    JScrollPane scrollPane = new JScrollPane(horsesListDisplay);

    public HorsesListDisplay() {
        loadHorsesListFrame();
    }

    private void loadHorsesListPanel() {
        // Convert the horses ArrayList to a fixed size array
        HorseV2[] horsesArr = horses.toArray(new HorseV2[horses.size()]);

        this.horsesListDisplay.setListData(horsesArr);
        this.panel1.add(scrollPane);
    }

    private void loadOptionsPanel() {
        JButton viewBtn = new JButton("View stats");
        JButton closeBtn = new JButton("Close");

        viewBtn.addActionListener(e -> {
            HorseV2 selectedHorse = horsesListDisplay.getSelectedValue();

            if(selectedHorse != null) {
                HorseStatsDisplay horseStatsDisplay = new HorseStatsDisplay(selectedHorse);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a horse you want to view from the list first", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        closeBtn.addActionListener(e -> {
            // TODO ADD A CLOSE FUNCTION HERE
            this.frame.dispose();
        });

        this.panel2.add(viewBtn);
        this.panel2.add(closeBtn);
    }

    private void loadHorsesListFrame() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(400, 300);
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);

        this.loadHorsesListPanel();
        this.loadOptionsPanel();

        this.frame.add(this.panel1, BorderLayout.CENTER);
        this.frame.add(this.panel2, BorderLayout.SOUTH);

        this.frame.setVisible(true);
    }
}
