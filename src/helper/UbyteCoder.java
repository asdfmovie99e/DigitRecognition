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

public class UbyteCoder {
    private static File mnistFolder = new File(Paths.get("").toAbsolutePath().toString() + "\\src\\mnist");
    private static int[] labelArray;
    private static byte[] byteArray;
    private static boolean[] pixelArray;


    public static void decode() {
        scanLabels();
        scanImages();
        createImages();
    }

    private static void scanLabels() {
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
        byteArray = new byte[0];
        try {
            byteArray = Files.readAllBytes(new File(mnistFolder.getAbsolutePath() + "\\train-images.idx3-ubyte").toPath());
            pixelArray = new boolean[byteArray.length - 16];
            for (int i = 0; i <= pixelArray.length - 1; i++) {
                pixelArray[i] = (byteArray[i + 16] == 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pixelArray.length);
    }

    public static Object[] getImageWithLabel(int imageNumber) {
        // gibt ein Array zurück. auf dem 0. platz ist das label, das angibt welche zahl es wirklich ist
        // auf dem 1. platz ist ein weiteres Array vom Typ Boolean[]. hier wird das bild gespeichert. true ist schwarz und false ist weiss
        scanLabels();
        scanImages();
        Object[] resultArray = new Object[2];
        resultArray[0] = labelArray[imageNumber];
        Boolean[] pixelPartArray = new Boolean[784];
        for (int i = 0; i < 784; i++) {
            pixelPartArray[i] = pixelArray[i + (784 * imageNumber)];
        }
        resultArray[1] = pixelPartArray;
        return  resultArray;
    }
        private static void createImages () {

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
                    System.out.println("Bild " + (ip + 1) + " von " + ((pixelArray.length / (28 * 28))) + ". " + percentage + "% abgeschlossen. " + timeRemaining + " Sekunden verbleibend.");
                }
                BufferedImage bufferedImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB); // hoehe auf 28x28 festgelegt, da nur mit dem mnist datensatz gearbeitet wird
                for (int iy = 0; iy <= 27; iy++) {
                    for (int ix = 0; ix <= 27; ix++) {
                        if (pixelArray[ix + (iy * 28) + (ip * 28 * 28)] == true) {
                            colorInt = 255;
                        } else {
                            colorInt = 0;
                        }
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
    }

