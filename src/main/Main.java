package main;

 /*
 @author Jens Krueger
 @version 1.0
 */


import helper.Debugger;
import helper.MathHelper;
import helper.UbyteCoder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../res/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Debugger.log("Program started");
        //launch(args);
        MathHelper.start();
        //System.out.println(MathHelper.sigmoidApprox(1f));
        //UbyteCoder.decode();
        NetworkController networkController = new NetworkController();
        networkController.initializeNetwork();
        networkController.startLearning();
        Debugger.log("Program finished");
        Debugger.flush();
        System.exit(0);
    }
}
