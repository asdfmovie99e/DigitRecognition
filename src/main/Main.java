package main;

 /*
 @author Jens Krueger
 @version 1.0
 */


import helper.Debug;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
private static Stage primaryStage = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Zahlenerkennung");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    public static void closeStage(){
        primaryStage.close();
    }

    public static void main(String[] args) {
        Debug.log("Program started");
        launch(args);
        //MathHelper.start();
        //WeightSaver.initialize(0, 0);
        //WeightSaver.chooseFile();
        //NetworkController.initializeNetwork();
        //NetworkController.startLearning();

        Debug.log("Program finished");
        Debug.flush();
        System.exit(0);
    }
}
