package main;

import helper.MathHelper;
import helper.UbyteEncoder;
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
        //launch(args);
        MathHelper.start();
        System.out.println(MathHelper.sigmoidApprox(1f));
        //UbyteEncoder.decode();
        NetworkController networkController = new NetworkController();
        networkController.initializeNetwork();
        System.exit(0);
    }
}
