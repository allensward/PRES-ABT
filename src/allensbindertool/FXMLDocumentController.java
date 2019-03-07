
package allensbindertool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Allen
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ImageView imageView;
    @FXML
    private ChoiceBox<String> choiceBox1;
    @FXML
    private ChoiceBox<String> choiceBox2;
    @FXML
    private ChoiceBox<String> choiceBox3;
    @FXML
    private ChoiceBox<String> choiceBox31;
    @FXML
    private ChoiceBox<String> choiceBoxRem;
    @FXML
    private ChoiceBox<String> choiceBox4;
    @FXML
    private TextField textField;
    @FXML
    private Button saveButton;
    @FXML
    private Label pathLabel;
    @FXML
    private Button folderBtn;
    @FXML
    private Label dirLabel;
    @FXML
    private CheckBox appendBox;
    @FXML
    private Label picNum;
    @FXML
    private Label remLabel;
    @FXML
    private Label viewLabel;
    @FXML
    private Label condLabel;
    @FXML
    private Label picNumTitle;
    @FXML
    private CheckBox checkDelete;
    @FXML
    private Button sortButton;
   
    Image img;
    String imgPath;
    String folderPath;
    String genAppPath;
    String safetyPath;
    int currentPicNumVal = 1;
    int secOnePicNumVal = 1;
    int secTwoPicNumVal = 1;
    int MAX_FILES = 500;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        textField.setVisible(false);
        picNum.setVisible(true);
        appendBox.setVisible(false);
        checkDelete.setVisible(false);
        sortButton.setVisible(false);
        
        ObservableList<String> choice1 = FXCollections.observableArrayList();
        choice1.addAll("","1", "2");
        choiceBox1.setItems(choice1);
        
        ObservableList<String> choice2 = FXCollections.observableArrayList();
        ObservableList<String> choice3 = FXCollections.observableArrayList();
        ObservableList<String> choice31 = FXCollections.observableArrayList();
        ObservableList<String> choice4 = FXCollections.observableArrayList();
        ObservableList<String> choiceRem = FXCollections.observableArrayList();
        
        
        //listener which changes values of UI based on section selected
        choiceBox1.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            if( "1".equals(newValue)){
                choice3.removeAll(choice3);
                checkDelete.setVisible(true);
                choiceBox4.setValue(null);
                choiceBoxRem.setValue(null);
                choiceBox31.setVisible(false);
                appendBox.setVisible(true);
                sortButton.setVisible(false);
                saveButton.setVisible(true);
                currentPicNumVal = secOnePicNumVal;
                picNum.setText(""+currentPicNumVal);
                viewLabel.setText("View");
                choice3.addAll("Placard Location", "Placard Data", "Aircraft Data Plate", "Radio Call Plate",
                        "BUNO on Tail", "Nose_Front", "Nose Landing Gear", "Nose Landing Gear Bay",
                        "Nose Strut Lock","Nose Strut Pin", "Nose Tires & Wheels","Nose Tire_Wheel PORTSIDE",
                        "Nose Tire_Wheel STARBOARD SIDE","Nose Tire_Wheel PORT OUTBOARD",
                        "Nose Tire_Wheel PORT INBOARD","Nose Tire_Wheel STARBOARD OUTBOARD",
                        "Nose Tire_Wheel STARBOARD INBOARD",
                        "Port Forward Quarter","Port Abeam","Port Main Landing Gear Bay",
                        "Port Main Strut Lock","Port Main Strut with Pin","Port Main Tires and Wheels",
                        "Port Main Tire_Wheel OUTBOARD","Port Main Tire_Wheel INBOARD",
                        "Port Wing","Port Wing Position","Port Wing Underside","Port Wing External Stores Stations",
                        "Port Wing Topside","Port Wing Lift Points","Port Fuselage","Port Gun(s)","Port Aft Quarter","Tail","Lift Points",
                        "Vertical Stabilizer and Rudder","Horizontal Stabilizer and Elevators","Tailhook",
                        "Other Landing Gear","Starboard Aft Quarter","Starboard Abeam","Starboard Main Landing Gear",
                        "Starboard Main Landing Gear Bay","Starboard Main Strut Lock","Starboard Main Strut with Pin",
                        "Starboard Main Tires and Wheels",
                        "Starboard Main Tire_Wheel OUTBOARD","Starboard Main Tire_Wheel INBOARD",
                        "Starboard Wing","Starboard Wing Position","Starboard Wing Underside",
                        "Starboard Wing External Stores Stations","Starboard Wing Topside","Starboard Wing Lift Points",
                        "Starboard Fuselage","Starboard Gun(s)","Starboard Forward Quarter","Fuselage Topside","Lift Points",
                        "Fuselage Belly",
                        "Belly External Stores Stations","Display Infrastructure","Display Pedestal","Display Pad",
                        "Tie Downs_Suspension Cabling","Grounding Strap","Lighting","Aircraft Display Placard",
                        "Display Attributed to NNAM");
            }else if("2".equals(newValue)){
                appendBox.setVisible(true);
                checkDelete.setVisible(true);
                choice3.removeAll(choice3);
                choiceBox4.setValue(null);
                choiceBoxRem.setValue(null);
                sortButton.setVisible(false);
                saveButton.setVisible(true);
                currentPicNumVal = secTwoPicNumVal;
                picNum.setText(""+currentPicNumVal);
                viewLabel.setText("Category");
                choice3.addAll("Radiation Survey","Armament","Cockpit and Interior","Liquids","Gasses",
                        "Airframe", "Landing Gear","Propulsion","Display Infrastructure","Misc");
                
                //adds drop down with specific values when selected
                choiceBox3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                choiceBox31.setVisible(true);
                choice31.removeAll(choice31);
                try{
                    switch(choiceBox3.getValue()){
                        case "Radiation Survey":
                            choice31.addAll("External Sweep","Internal Sweep","Location of Gamma Radiation",
                            "Circuit Breakers","Engaged","Pulled Out (Disengaged)","Instruments","Other, Debris");
                            break;
                        case "Armament":
                            choice31.addAll("CADS (Installed_Not Installed_Empty Breech Caps)","Weapons Systems",
                            "Chaff, Flares Dispenser","Fire Supression System","Ejection Seat(s)",
                            "Canopy Jettison","Gear Blow Down","Cable Jettison","Other",
                            "External Stores (Fuel Tanks, Simulated Weapons, etc)", "Safety Pins with Flags",
                            "General Condition (Inert Markings, Hazards, etc)","Properly Secured (Locked in Lugs etc)",
                            "Internal Stores (Removed, Status if Installed, etc)","Guns","Ammo Cans",
                            "Barrels_Mechanisms","Panels Secured");
                            break;
                        case "Cockpit and Interior":
                            choice31.addAll("Cockpit","Accessibility",
                            "Jettison System","General Condition","Data Plate (Installed_Not Installed)",
                            "Cleanliness_Indications of Leaks (Water Intrusion)","Instrument Panel_Instruments",
                            "Gunsight_Heads Up Display (HUD)","Consoles","Seats","Flight Controls",
                            "Windscreen and Canopy","Circuit Breakers","Canopy Secured_Sealed","Doors, Hatches","Interior_Cabin",
                            "Galley","Head(s)");
                            break;
                        case "Liquids":
                            choice31.addAll("Liquids","Fuel Remaining","Gauges_Site Glasses","Tank_Bladder_Line Condition",
                                    "Drained (Pencil Drains, Drilled Holes, etc)","Hydraulic Fluid","Reservoirs_Tanks_Filters_Lines",
                                    "Drained(Fittings, Plugs, etc)","Oil Remaining","Water","Heads","Galleys",
                                    "Windshield Wash System");
                            break;
                        case "Gasses":
                            choice31.addAll("Oxygen","LOX Bottles","Seat Pans","Other","Nitrogen",
                                    "Canopy Jettsion Bottles","Landing Gear Blow Down Bottles","Tailhook Accumulator",
                                    "Radar","Landing Gear Struts","Halon","Fire Supression System","CO2 Fire Extinguishers");
                            break;
                        case "Airframe":
                            choice31.addAll("Nose_Radome","Port Side Access Panels","Port Wing","Port Ailerons","Port Flaps",
                                    "Port Slats","Exhaust Nozzles","Horizontal Stabilizer","Elevators_Trim Tabs",
                                    "Vertical Stabilizer","Rudder_Trim Tabs","Tailhook",
                                    "Starboard Side Access Panels","Starboard Wing","Starboard Ailerons","Starboard Flaps",
                                    "Starboard Slats","Belly Access Panels");
                            break;
                        case "Landing Gear":
                            choice31.addAll("Nose Gear","Tire Condition","Strut Condition","Pins Secured",
                                    "Port Main Mount(s)","Port Tire Condition","Port Strut Condition",
                                    "Port Pins Secured","Starboard Main Mount(s)","Starboard Tire Condition",
                                    "Starboard Strut Condition","Starboard Pins Secured","Other");
                            break;
                        case "Propulsion":
                            choice31.addAll("Blades","Main Rotor Blades","Tail Rotor Blades","Engines","Engine Bays",
                                    "Intakes","Exhausts","JATO System","Engine Serial #","Other");
                            break;
                        case "Display Infrastructure":
                            choice31.addAll("Display Pedestal","Display Pad","Tie Downs-Suspension Cables",
                                    "Grounding Strap","Lighting","Aircraft Description Placard");
                            break;
//                        case "Miscellaneous":
//                            choice31.addAll("");
//                            break;
                        default:
                            choiceBox31.setVisible(false);                              
                }
                }catch(Exception e){
                    //do nothing
                }
                choiceBox31.setItems(choice31);
            }
            
        });
            }else{
                viewLabel.setVisible(false);
                choice3.removeAll(choice3);
                choiceBox4.getSelectionModel().selectFirst();
                choiceBoxRem.getSelectionModel().selectFirst();
                choiceBox31.setVisible(false);
                appendBox.setVisible(false);
                checkDelete.setVisible(false);
//                sortButton.setVisible(true);
//                saveButton.setVisible(false);
            }
            choiceBox3.setItems(choice3);
        });
        
        
        //populate picNum drop down values
        for(int i = 1; i <= MAX_FILES; i++){
            choice2.add(""+i);
        }
        choiceBox2.setItems(choice2);
        choiceBox2.setVisible(false);
        
        //changes UI based on selection
        appendBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                choice2.removeAll();
                if(appendBox.isSelected()){
                    choiceBox2.setVisible(true);
                    picNum.setVisible(false);
                }else{
                    choiceBox2.setVisible(false);
                    picNum.setVisible(true);
                }
            }
            
        });
        
        //changes UI based on selection
        checkDelete.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(checkDelete.isSelected()){
                    saveButton.setVisible(false);
                    sortButton.setVisible(true);
                }else{
                    saveButton.setVisible(true);
                    sortButton.setVisible(false);
                }
            }
            
        });
        
        //enables picNum drop down when selected
        choiceBox2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentPicNumVal = Integer.parseInt(newValue);
                picNum.setText(""+currentPicNumVal);
            }
        });
        
        choiceBox31.setVisible(false);
        
        choice4.addAll(null,"Animal Nest or Waste","Armament_Weapons NOT Removed",
                "Broken, Corroded, Loose, Missing",
                "Brittle_Broken",
                "Closed, Locked, Sealed, Inaccessible",
                "Open, Ajar, Leaking",
                "Scratched or Broken","Circuit Breakers",
                "Corrosion",
                "Damage From Movement of the Aircraft",
                "Dirt, Dust","Disintegrating, Rotting",
                "Engines Not Installed",
                "Fabricated Weapons Installed (guns, missiles, bombs, etc)",
                "Folded","FME Covers - Installed, Secured",
                "FME Covers - Missing, Broken, Loose",
                "Fuel in Tanks, Bladders, Filters, Lines",
                "Graffiti","Halon Bottles Inside Aircraft",
                "Halon in Fire Suppression System",
                "Hydraulic Fluid in reservoirs, Tanks, Filters, Lines",
                "Incorrectly Labeled_Marked","Installed",
                "Leaks_Water Intrusion", "Leaves, Sticks, or Trash",
                "Missing Inert Sticker_Label",
                "Missing Labels_Markings",
                "Missing Parts, Pieces, or Sections","Mold",
                "Nitrogen in Bottles, Accumulators, Lines","Not Installed",
                "Oil in Reservoirs, Tanks, Filters, Lines",
                "Other - See REMARKS",
                "Oxygen in LOX Bottles, Seat Pans, Other","Paint Damage",
                "Pedestal_Pad - Broken, Chipped, Cracked, Decaying",
                "Physical Distortion",
                "Propeller_Blade - Cracked, Broken",
                "Propeller_Blade - Improperly Secured",
                "Remove Before Flight Pins_Flags Missing",
                "Repairs Inadequate (Failing, Not Done Correctly)",
                "Stains (Hydraulic Fluid, Oil, Grease, Paint, Markings, etc)",
                "Structural Cracking",
                "Survival Equipment Not Removed (Flares_Smokes, Parachutes, Rafts, etc)",
                "Tie Downs - Broken, Corroded, Loose, Missing",
                "Tires Flat, Cut, Rotting, etc.",
                "Water in Heads, Galleys, Winshield Wash System",
                "Radiation Reading Too High or Out of Limits",
                "Radiation - GAMMA","Station #1","Station #2","Station #3","Station #4"
                ,"Station #5","Station #6","Station #7","Station #8","Station #9","Station #10",
                "Station #11","Station #1a","Station #1b");
        choiceBox4.setItems(choice4);

        choiceRem.addAll(null,"Added Rebar", "Cleaned", "Cleaned and Removed",
                "Closed","Closed and Sealed","Destroyed","Disposed of",
                "Drained","Filled","Have Waiver","Inflated","Locked","Painted, Touch Up",
                "Patched","Poured New Concrete","Pulled","Removed","Removed and Cleaned",
                "Re-Painted, Made New","Repaired","Replaced","Sealed","Secured",
                "Straigtened","Strengthened","Turned In","Waiver Requested");
        choiceBoxRem.setItems(choiceRem);
        
        //enables textBox when selected
        choiceBoxRem.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            textField.setVisible(false);
            if("Custom".equals(newValue)){
                textField.setVisible(true);
            }
 
        });

        
    }    

    @FXML
    private void handleDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.COPY);
        }
    }

    @FXML
    private void handleDrop(DragEvent event) throws FileNotFoundException{
        List<File> files = event.getDragboard().getFiles();
        img = new Image(new FileInputStream(files.get(0)));
        imageView.setImage(img);
    }
    
    //this function builds the filename string and saves it to the previously
    //selected folder
    private void saveImage(Image image) throws IOException{
 
        imgPath = "";
        BufferedImage bImg = SwingFXUtils.fromFXImage(img, null);
        File f;

        imgPath += choiceBox1.getValue()+"."+currentPicNumVal;
        
        if("1".equals(choiceBox1.getValue()))
            imgPath += "."+choiceBox3.getValue();
        else
            imgPath += "."+choiceBox31.getValue();
        
        if(choiceBox4.getValue() != null)
            imgPath += "-"+choiceBox4.getValue();

        if(choiceBoxRem.getValue() != null){
            if(!"Custom".equals(choiceBoxRem.getValue()))
                imgPath += "."+choiceBoxRem.getValue();
            else if("Custom".equals(choiceBoxRem.getValue()))
                imgPath += "."+textField.getText();
        }
        imgPath += ".jpg";
        
            
        
        if(appendBox.isSelected()){
            cascade(bImg);
        }else{
            try{
                currentPicNumVal++;
                picNum.setText(""+currentPicNumVal);
                if("1".equals(choiceBox1.getValue())){
                    f = new File(genAppPath+File.separator+imgPath);
                    f.createNewFile();
                    ImageIO.write(bImg, "jpg", f);
                    pathLabel.setText("saved as "+f.getAbsolutePath());
                    secOnePicNumVal = currentPicNumVal;
                }else if("2".equals(choiceBox1.getValue())){
                    f = new File(safetyPath+File.separator+imgPath);
                    f.createNewFile();
                    ImageIO.write(bImg, "jpg", f);
                    pathLabel.setText("saved as "+f.getAbsolutePath());
                    secTwoPicNumVal = currentPicNumVal;
                }

            }catch(IOException e){
                pathLabel.setText("Could not save Image");
            }
        }
        
        imageView.setImage(null);
        img = null;
        }
    

    @FXML
    private void handleFolderButtonAction(ActionEvent event) {
        folderPath = "";
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select folder");
        File destFolder;
        destFolder = dc.showDialog(new Stage());
        if(destFolder != null){
            dirLabel.setText(destFolder.getAbsolutePath());
            folderPath = destFolder.getAbsolutePath();
            
            File genApp = new File(destFolder.getAbsolutePath()+File.separator+"GENCON&APP");
            genApp.mkdir();
            genAppPath = genApp.getAbsolutePath();
                
            File safety = new File(destFolder.getAbsolutePath()+File.separator+"SAFETY&SECURITY");
            safety.mkdir();
            safetyPath = safety.getAbsolutePath();   
        }
    }

    //this function provides safety measures where saving would create issues
    @FXML
    private void handleSaveButtonAction(ActionEvent event) throws IOException {
        if(img == null)
            pathLabel.setText("No Image to Save");
        else if(choiceBox1.getValue() == null)
            pathLabel.setText("No Section Selected");
        else if(choiceBox3.getValue() == null)
            pathLabel.setText("No View/Category Selected");
        else if("2".equals(choiceBox1.getValue()) && choiceBox31.getValue() == null)
            pathLabel.setText("No Description Selected");
        else if(folderPath == null)
            pathLabel.setText("No Destination Folder Selected");
        else
            saveImage(img);
    }
    
    //this function changes the picNum val for all files >= the picNum val
    //being appended to folder. Then saves file to folder.
    public void cascade(BufferedImage bg){
   
        String s,sRebuilt;
        String[] tokens;
        int temp,i,j;
        File fParent,fTemp;
        
        if("1".equals(choiceBox1.getValue()))
            fParent = new File(genAppPath);
        else
            fParent = new File(safetyPath);
        
        File[] files = fParent.listFiles();
       
        //increases pic num vals >= num val of pic being appended
        for(i=0;i<files.length;i++){
            
            sRebuilt = "";
            s = files[i].toString();
            tokens = s.split("\\.");
            
            if(s.contains("DS_Store")){
                //do nothing
            }else{
                temp = Integer.parseInt(tokens[1]);
                if(temp >= currentPicNumVal){
                    
                    temp++;
                    tokens[1] = Integer.toString(temp);
                    
                    for(j = 0; j < tokens.length; j++){
                        sRebuilt += tokens[j];
                        if(j < tokens.length-1)
                            sRebuilt += ".";
                    }
                    
                    files[i].renameTo(new File(sRebuilt));  
                    
                    }
                }
            }
        
        //saves appended file now that a space has been made
        try{
        fTemp = new File(fParent+File.separator+imgPath);
        fTemp.createNewFile();
        ImageIO.write(bg, "jpg", fTemp);
        pathLabel.setText("saved as "+fTemp.getAbsolutePath());
        }catch(Exception e){
            pathLabel.setText("File not saved");
        }
    }

    @FXML
    private void handleSortButton(ActionEvent event) {
        
        if(folderPath == null){
            pathLabel.setText("No folder chosen");
        }else{
            checkDeletes();
        }
        
    }
    
    private void checkDeletes(){
        int temp,i,j,k;
        File fParent;
        String s,sRebuilt;
        String[] tokens;
        
        //determine parent folder path
        if("1".equals(choiceBox1.getValue()))
            fParent = new File(genAppPath);
        else
            fParent = new File(safetyPath);
       
        File[] files = fParent.listFiles();
        //initialize sortedFiles to the max number of files possible
        File[] sortedFiles = new File[MAX_FILES];
        
        for(i = 0; i < files.length; i++){
            
            s = files[i].toString();
            tokens = s.split("\\.");
            
            if(s.contains("DS_Store")){
                //do nothing
            }else{
                //sort files in ascending order into sortedFiles
                temp = Integer.parseInt(tokens[1]);
                sortedFiles[temp] = files[i];
            }
        }
        
        //initialize k value to one in order to rename pics sequentially
        k=1;
        for(i = 0; i < sortedFiles.length; i++){
            sRebuilt = "";
            if(sortedFiles[i] != null){
                s = sortedFiles[i].toString();
                if(s.contains("DS_Store")){
                    //do nothing
                }else{
                    tokens = s.split("\\.");
                    tokens[1] = Integer.toString(k++);
                    //rebuild string with new pic num
                    for(j = 0; j < tokens.length; j++){
                        sRebuilt += tokens[j];
                        if(j < tokens.length-1){
                            sRebuilt += ".";
                        }
                    }
                    
                    sortedFiles[i].renameTo(new File(sRebuilt));
                }
            }
        }
        
    }
}
