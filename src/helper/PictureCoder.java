package helper;

 /*
 @author Jens Krueger
 @version 1.0
 */


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
//ES MUSS NOCH BEI DEM SELBST EINGEGEBENEN BILD KOMPLETT AUF GRAUSKALA UMGESTELLT WERDEN

public class PictureCoder {
    private static File mnistFolder = new File(Paths.get("").toAbsolutePath().toString() + "\\src\\mnist");
    private static int[] labelArray;
    private static byte[] byteArray;
    private static double[] pixelArray;


    public static void decode() {
        //alle bilder aus der Ubyte Datei werden in %appdata%\mnist entpackt
        scanLabels();
        scanImages();
        createImages();
    }

    private static void scanLabels() {
        //die Daten aus der UByte Label Datei werden in das LabelArray geladen
        byteArray = new byte[0];
        try {
            byteArray = Files.readAllBytes(new File(mnistFolder.getAbsolutePath() + "\\train-labels.idx1-ubyte").toPath());
            labelArray = new int[byteArray.length - 8];
            for (int i = 8; i <= byteArray.length - 1; i++) {
                labelArray[i - 8] = byteArray[i];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scanImages() {
        //die Daten aus der UByte images Datei werden in das pixelArray geladen
        byteArray = new byte[0];
        try {
            byteArray = Files.readAllBytes(new File(mnistFolder.getAbsolutePath() + "\\train-images.idx3-ubyte").toPath());
            pixelArray = new double[byteArray.length - 16];
            for (int i = 0; i <= pixelArray.length - 1; i++) {
                pixelArray[i] = (0.00390625d *  ((double)(int)(byte)/*hoffentlich sieht das keiner*/ (byteArray[i + 16] - 128)))  + 0.5d;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] getImageWithLabel(int imageNumber) { //das erste bild hat imageNumber = 0
        // gibt ein Array zurück. auf dem 0. platz ist das label, das angibt welche zahl es wirklich ist
        // auf dem 1. platz ist ein weiteres Array vom Typ Boolean[]. hier wird das bild gespeichert. true ist schwarz und false ist weiss
        if (labelArray == null) scanLabels();
        if (pixelArray == null)scanImages();
        Object[] resultArray = new Object[2];
        resultArray[0] = labelArray[imageNumber];
        Double[] pixelPartArray = new Double[784];
        for (int i = 0; i < 784; i++) {
            pixelPartArray[i] = pixelArray[i + (784 * imageNumber)];
        }
        resultArray[1] = pixelPartArray;
        return  resultArray;
    }
        private static void createImages () {
            //diese Funktion erstellt aus dem labelarray und dem pixelarray echte Bilder
            int colorInt;
            long startTime = System.currentTimeMillis();
            int timeRemaining = Integer.MAX_VALUE;
            for (int ip = 0; ip <= (pixelArray.length / (28 * 28)) - 1; ip++) {
                if ((ip + 1) % 100 == 0) {
                    int percentage = ((int) ((ip + 1) * 100 / ((pixelArray.length / (28 * 28)))));

                    if (percentage > 0) {
                        int newTimeRemaining = (int) ((((System.currentTimeMillis() - startTime) / ((ip + 1) * 100 / ((pixelArray.length / (28 * 28))))) * (100 - ((ip + 1) * 100 / ((pixelArray.length / (28 * 28)))))) / 1000);
                        if (newTimeRemaining < timeRemaining) {
                            timeRemaining = newTimeRemaining;
                        }
                    } else {
                        timeRemaining = Integer.MAX_VALUE;
                    }
                    Debug.log("Bild " + (ip + 1) + " von " + ((pixelArray.length / (28 * 28))) + ". " + percentage + "% abgeschlossen. " + timeRemaining + " Sekunden verbleibend.");
                }
                BufferedImage bufferedImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB); // hoehe auf 28x28 festgelegt, da nur mit dem mnist datensatz gearbeitet wird
                for (int iy = 0; iy <= 27; iy++) {
                    for (int ix = 0; ix <= 27; ix++) {
                        colorInt = (int)pixelArray[ix + (iy * 28) + (ip * 28 * 28)]; // das hier muss noch komplett neu gemacht werden !!!!!!!!!!!!!!!!!!!!!!
                        int pixel = (colorInt << 24 | colorInt << 16 | colorInt << 8 | colorInt); // bit shifting um den standards zu entsprchen. red green und blue sind immer 0
                        bufferedImage.setRGB(ix, iy, pixel);
                    }
                }
                try {

                    new File(System.getenv("APPDATA") + "\\mnist\\images\\").mkdirs();
                    File file = new File(System.getenv("APPDATA") + "\\mnist\\images\\" + labelArray[ip] + "_" + (ip + 1) + ".png");
                    ImageIO.write(bufferedImage, "png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void shrinkImage(){
        //verkleinert das bild aus der gui von 196*196 auf 28*28 pixel
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(System.getenv("APPDATA") + "\\mnist\\" + "paint.png"));
            } catch (IOException e) {
            }
            HashMap<Integer,Integer> hMap= new HashMap<Integer, Integer>();
            for(int i1 = 0; i1 < 196; i1++) {
                for (int i2 = 0; i2 < 196; i2++) {
                    Integer colorInt = null;
                    if (img.getRGB(i1, i2) > -2) { // minus 2 weil weiss = -1 und schwarz gleich minus ganz viel
                        colorInt = 0;
                    } else {
                        colorInt = 1;
                    }
                    hMap.put(i1 + 196 * i2, colorInt);
                }
            }
            BufferedImage shrunkImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB); // hoehe auf 28x28 festgelegt, da nur mit dem mnist datensatz gearbeitet wird
            for(int i1 = 0; i1 < 28; i1++){
                for(int i2 = 0; i2 < 28; i2++){
                    int currentBlackSum = 0;
                        for(int i3 = 0; i3 < 7; i3++){ // x sub cood
                            for(int i4 = 0; i4 < 7; i4++){ //y sub cood
                                int currentPixel = i1 * 7 + i2 * 1372 + i3 + 196 * i4;
                                if(hMap.get(currentPixel) == 1){
                                    currentBlackSum++;
                                }
                            }
                        }
                        Integer colorInt  = null;
                        if(currentBlackSum < 25){
                            colorInt = 255;
                        } else {
                            colorInt = 0;
                        }
                        int pixel = (colorInt << 24 | colorInt << 16 | colorInt << 8 | colorInt);
                        shrunkImage.setRGB(i1, i2, pixel);
                }
            }
            File file = new File(System.getenv("APPDATA") + "\\mnist\\" + "shrunk.png");
            try {
                ImageIO.write(shrunkImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static boolean[] getShrunkImage(){
        boolean[] resultArray = new boolean[784];
        BufferedImage img = null;
            try {
                img = ImageIO.read(new File(System.getenv("APPDATA") + "\\mnist\\" + "shrunk.png"));
            } catch (Exception e) {
            }

            for(int i1 = 0 ; i1 < 28; i1++){ // x coords
                for(int i2 = 0; i2 < 28; i2++){ // y coords
                   int pixelValue = img.getRGB(i1,i2);
                    if(pixelValue <  -2) { // if white else schwarz
                        resultArray[i1 + i2 * 28] = true; // weiss
                    } else {
                        resultArray[i1 + i2 * 28] = false;//schwarz
                    }
                }
            }
            return resultArray;
        }
    }

