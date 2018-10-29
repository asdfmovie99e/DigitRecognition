package main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import helper.MathHelper;
import helper.PictureCoder;
import helper.WeightSaver;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

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
    private TextField textausgabe;




    @FXML
    void onAuswertenClicked(ActionEvent event) {
       try {
            Image snapshot = canvas.snapshot(null,null);
           ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null), "png", new File("src\\helper\\paint.png"));
           showpb(); //Anzeigen der Balken
           shownumber();//Anzeigen des Ergebnisses
           PictureCoder.shrinkImage();
           NetworkController.analyzeShrunkImage();
        }   catch (Exception e) {
           e.printStackTrace();

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


        pb0.setProgress(0);
        pb1.setProgress(0);
        pb2.setProgress(0);
        pb3.setProgress(0);
        pb4.setProgress(0);
        pb5.setProgress(0);
        pb6.setProgress(0);
        pb7.setProgress(0);
        pb8.setProgress(0);
        pb9.setProgress(0);

        textausgabe.setText("");
    }

    @FXML
    void onGewichteladenclicked(ActionEvent event) {
        MathHelper.start();
        NetworkController.initializeNetwork();
        WeightSaver.initialize(0, 0);
        WeightSaver.chooseFile();
    }

    @FXML
    void onLernenClicked(ActionEvent event) {
        Main.closeStage();
        MathHelper.start();
        WeightSaver.initialize(0, 0);
        WeightSaver.chooseFile();
        NetworkController.initializeNetwork();
        NetworkController.startLearning();
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


    private void shownumber()
    {
       textausgabe.setText("2");
    }


    double [] pbarray = new double [10];


    private void showpb()
    {

        pb0.setProgress(pbarray[0]);
        pb1.setProgress(pbarray[1]);
        pb2.setProgress(pbarray[2]);
        pb3.setProgress(pbarray[3]);
        pb4.setProgress(pbarray[4]);
        pb5.setProgress(pbarray[5]);
        pb6.setProgress(pbarray[6]);
        pb7.setProgress(pbarray[7]);
        pb8.setProgress(pbarray[8]);
        pb9.setProgress(pbarray[9]);
    }
}
