package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Canvas canvas;


    @FXML
    private ProgressBar pb0;

    @FXML
    private ProgressBar pb1;

    @FXML
    private ProgressBar pb2;

    @FXML
    private ProgressBar pb3;

    @FXML
    private ProgressBar pb4;

    @FXML
    private ProgressBar pb5;

    @FXML
    private ProgressBar pb6;

    @FXML
    private ProgressBar pb8;

    @FXML
    private ProgressBar pb7;

    @FXML
    private ProgressBar pb9;


    @FXML
    void onAuswertenClicked(ActionEvent event) {
       try {
            Image snapshot = canvas.snapshot(null,null);
           ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null), "png", new File("paint.png"));
           showpb(); //Anzeigen der Balken
        }   catch (Exception e) {
           System.out.println("Failed to save Image: " + e);
       }

       /*try {
                java.awt.Image img = new ImageIcon(ImageIO.read(new File("paint.png")))
                        .getImage();
                System.out.println(img.getWidth(null));
                System.out.println(img.getHeight(null));

                int wNew = 28, hNew = 28;

                Image scaledImage = img.getScaledInstance(wNew, hNew, Image.); //Ergänzen

                BufferedImage outImg = new BufferedImage(wNew, hNew,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = outImg.getGraphics();
                g.drawImage(scaledImage, 0, 0, null);
                g.dispose();

                ImageIO.write(outImg, "png", new File("paint2.png"));

            } catch (IOException e) {
                e.printStackTrace();}
        */

    }

    @FXML
    void onDeleteAction(ActionEvent event) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(),canvas.getHeight());
    }

    @FXML
    void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(event ->{
            double size = 20 ;
            double x = event.getX() - size / 2;
            double y = event.getY() - size / 2;
            g.fill();
            g.fillRect(x, y, size, size);

        } );


    }

    void showpb()
    {
        pb0.setProgress(0.2);
        pb1.setProgress(0.8);
        pb2.setProgress(0.1);
        pb3.setProgress(0.2);
        pb4.setProgress(0.2);
        pb5.setProgress(0.2);
        pb6.setProgress(0.2);
        pb7.setProgress(0.2);
        pb8.setProgress(0.2);
        pb9.setProgress(0.2);
    }
}
