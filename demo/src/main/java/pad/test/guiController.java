package pad.test;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.control.Button;


public class guiController  {

    // Reference variables to the FXML document, allowing manipulation of the GUI
    @FXML
    private TextField inputText;

    @FXML
    private Label outputLbl;
    @FXML
    private RadioButton emailButton;
    @FXML
    private RadioButton popupButton;
    @FXML
    private Button monitorButton;

    //return an op code for the notification class
    public int getNotificationMethod(){
        if (emailButton.isSelected()) return 1;
        return 0;
    }

    public void initGuiValues(){

        double lastPrice = log.getLatestPrice();
        updateGuiPrice(lastPrice);


        popupButton.fire();
    }

    @FXML
    private void startMonitor() 
    {
        //try to get user inputted delay value from gui, default to 24(one day) if empty or incorrectly filled
        int delay;
        try {
            delay = Integer.parseInt(inputText.getText());
        }
        catch (Exception ex){
            delay = 24;
        }
        //start a new thread in the monitor class which handles the automatic checking and updating of ipad price.
        monitor.setDelay(delay);
        monitor.startMonitor();
       
        //change the START button into a STOP button
        monitorButton.setText("STOP");
        monitorButton.setOnAction(e -> stopMonitor());

    }  
    @FXML
    private void stopMonitor() 
    {
        monitor.stopMonitor();
        System.out.println("Stopped monitoring item");
        //revert STOP button to START button
        monitorButton.setText("START");
        monitorButton.setOnAction(e -> startMonitor());

    } 
 
    //update the GUI with a string or double
    public void updateGuiPrice(String price) 
    {
        outputLbl.setText("Last Known Price : " + price);
    }

    
    public void updateGuiPrice(double price) 
    {
        String newText = String.valueOf(price); 
        outputLbl.setText("Last Known Price : " + newText);
       // outputText.setText(newText);
    }

    @FXML
    //sends a notification based on what radio button is toggled
    public void testNotification(){
        notification.sendNotification();
    }

    @FXML
    //creates a popup window that alerts the user a price change has been detected
    public void popup() 
    {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                VBox dialogVbox = new VBox(20);
                Label popupText = new Label("Congratulations!");
                dialogVbox.getChildren().add(popupText);

                Label explanatoryText = new Label("Your item has gotten cheaper! Check it out by pasting this link into your web browser: ");
                explanatoryText.setWrapText(true);
                dialogVbox.getChildren().add(explanatoryText);

                TextArea linkText = new TextArea(scrape.getItemUrl());
                linkText.setWrapText(true);
                dialogVbox.getChildren().add(linkText);

                Scene dialogScene = new Scene(dialogVbox, 400, 300);
                dialog.setTitle("Alert!");
                dialog.setScene(dialogScene);
                dialog.show();


    } 

}