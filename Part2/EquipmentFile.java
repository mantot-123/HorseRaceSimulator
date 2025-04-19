import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
public class EquipmentFile {
    private HashMap<String, Equipment> equipmentList = new HashMap<String, Equipment>();
    private final String FILENAME = "EquipmentList.csv";

    public EquipmentFile() {
        loadEquipment();
    }

    // Updates the equipment with the new contents in the horses list file
    public void loadEquipment() {
        final int NO_OF_COLUMNS = 4;

        try(BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            equipmentList.clear();
            String line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                String[] equipmentData = line.split(",");
                if(equipmentData.length == NO_OF_COLUMNS) {
                    String id = equipmentData[0];
                    String name = equipmentData[1];
                    int movementAmp = Integer.parseInt(equipmentData[2]);
                    int stabilityAmp = Integer.parseInt(equipmentData[3]);

                    Equipment eq = new Equipment(id, name, movementAmp, stabilityAmp);
                    this.equipmentList.put(id, eq);
                } else {
                    throw new IllegalArgumentException("Invalid data format in line '" + line + "' of the CSV file");
                }
                line = reader.readLine();
            }
            reader.close();

        } catch(IllegalArgumentException  e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: " + e.getMessage());
            } else {
                System.out.println("ERROR: An error has occurred while reading the file " + FILENAME + ". The contents of the file might have been corrupted. Possible that the amplification values are not integers?");
            }
            e.printStackTrace();

        } catch(IOException e) {
            if(e.getMessage() != null) {
                System.out.println("ERROR: " + e.getMessage());
            } else {
                System.out.println("ERROR: An error has occurred while reading the file " + FILENAME + ". Possible that the file does not exist or is empty.");
            }
            e.printStackTrace();
        }
    }


    public HashMap<String, Equipment> getEquipmentList() {
        return this.equipmentList;
    }
}
