/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allensbindertool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
    private ChoiceBox<String> choiceBoxRem;
    @FXML
    private ChoiceBox<String> choiceBox4;
    @FXML
    private ChoiceBox<String> choiceBox5;
    @FXML
    private TextField textField;
    @FXML
    private Label label2;
    @FXML
    private Button saveButton;
    @FXML
    private Label pathLabel;
    @FXML
    private Button folderBtn;
    
    Image img;
    String imgPath;
    @FXML
    private Label dirLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String sOne = "";
        
        textField.setVisible(false);
        
        ObservableList<String> choice1 = FXCollections.observableArrayList();
        choice1.addAll("1", "2");
        choiceBox1.setItems(choice1);
       
        ObservableList<String> choice2 = FXCollections.observableArrayList();
        
        ObservableList<String> choiceRem = FXCollections.observableArrayList();
        
        choiceBox1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue){
                        choice2.removeAll(choice2);
                        choiceRem.removeAll(choiceRem);
                    if(newValue == "1"){
                        choice2.addAll("1", "2", "3", "4", "5", "6", "7", "8", "9","10","11","12");
                        choiceRem.addAll("comment1", "comment2", "comment3", "custom");
                        label2.setText("Comments");
                    }else{
                        choice2.addAll("1", "2", "3", "4", "5", "6", "7", "8");
                        choiceRem.addAll("Cleaned", "Removed", "Secured", "custom");
                        label2.setText("Remediation");
                    }
                    choiceBox2.setItems(choice2);
                    choiceBoxRem.setItems(choiceRem);
            }
        });
        
        ObservableList<String> choice3 = FXCollections.observableArrayList();
        choice3.addAll("1", "2", "3", "4", "5", "6", "7", "8");
        choiceBox3.setItems(choice3);
        
        
        ObservableList<String> choice4 = FXCollections.observableArrayList();
        choice4.addAll("1", "2", "3", "4", "5", "6", "7", "8");
        choiceBox4.setItems(choice4);
        
        
        ObservableList<String> choice5 = FXCollections.observableArrayList();
        choice5.addAll("1", "2", "3", "4", "5", "6", "7", "8");
        choiceBox5.setItems(choice5);
        

        choiceBoxRem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue){
                textField.setVisible(false);
                if(newValue == "custom"){
                    textField.setVisible(true);
                }
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
    
//    private void handleButtonAction(ActionEvent event) throws IOException {
//        
//    }
    
    private void saveImage(Image image) throws IOException{
        
        imgPath = "";
        BufferedImage bImg = SwingFXUtils.fromFXImage(img, null);
        Alert a = new Alert(AlertType.ERROR);
        int i = 0;
//        imgPath = choiceBox1.getValue()+"."+choiceBox2.getValue()+"."+choiceBox3.getValue()+
//                "."+choiceBox4.getValue()+"."+choiceBox5.getValue();
        if(choiceBox5.getValue()!=null)
            imgPath = "."+choiceBox5.getValue();
        if(choiceBox4.getValue()!=null)
            imgPath = "."+choiceBox4.getValue()+imgPath;
        if(choiceBox3.getValue()!=null)
            imgPath = "."+choiceBox3.getValue()+imgPath;
        if(choiceBox2.getValue()!=null)
            imgPath = "."+choiceBox2.getValue()+imgPath;
        if(choiceBox1.getValue()!=null)
            imgPath = choiceBox1.getValue()+imgPath;

        if(choiceBoxRem != null){
            if(choiceBoxRem.getValue() != "custom")
                imgPath += "."+choiceBoxRem.getValue();
            else if(choiceBoxRem.getValue() == "custom")
                imgPath += "."+textField.getText();
        }
        imgPath += ".jpg";

        
        try{
            File f = new File("/Users/Allen/Desktop/"+imgPath);
            f.createNewFile();
            ImageIO.write(bImg, "jpg", f);
            pathLabel.setText("saved as "+f.getAbsolutePath());
        }catch(IOException e){
            a.setContentText("Could not save Image");
        }
        
        imageView.setImage(null);
        
        }

    @FXML
    private void handleFolderButtonAction(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select folder");
        File destFolder;
        destFolder = dc.showDialog(new Stage());
        if(destFolder != null)
            dirLabel.setText(destFolder.getAbsolutePath());
    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event) throws IOException {
        saveImage(img);
    }
    
    
}
