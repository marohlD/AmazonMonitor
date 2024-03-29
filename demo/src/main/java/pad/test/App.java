package pad.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX iPad Monitor App
 * Written by Dane Marohl for evaluation by ECFx
 */
public class App extends Application {

   // private static Scene scene;

    //global controller var for the ui
    static guiController controller;

    @Override
    public void start(Stage stage) throws IOException {
        //Set up instance instead of using static load() method, allowing controller access from other classes
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
        
        Parent root = loader.load();

        //Create an instance of the GUI controller so that other classes may access its methods
        controller = (guiController)loader.getController();

        //Set up javaFX GUI window, the structure of which is desrcibed in resources\pad\test\gui.fxml
        Scene scene = new Scene(root);
        stage.setTitle("ipad Monitor");
        stage.setScene(scene);
        stage.show();

        //Initilaize GUI with data from log and config files
        controller.initGuiValues();
    }

    public static void main(String[] args) {
        launch();
    }

}