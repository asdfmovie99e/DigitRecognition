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

public class UbyteEncoder {
    private File mnistFolder;
    private int[] labelArray;
    private byte[] byteArray;
    private boolean[] pixelArray;

    public UbyteEncoder(){
        mnistFolder = new File(Paths.get("").toAbsolutePath().toString() +"\\src\\mnist");

    }

    public void decode() {
        scanLabels();
        scanImages();
        createImages();
    }

    private void scanLabels(){
        byteArray = new byte[0];
        try {
            byteArray = Files.readAllBytes(new File(mnistFolder.getAbsolutePath() + "\\train-labels.idx1-ubyte").toPath());
            labelArray = new int[byteArray.length - 8];
            for(int i = 8; i <= byteArray.length - 1; i++)
            {
                labelArray[i - 8] = byteArray[i];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scanImages(){
        byteArray = new byte[0];
        try{
            byteArray = Files.readAllBytes(new File(mnistFolder.getAbsolutePath() + "\\train-images.idx3-ubyte").toPath());
            pixelArray = new boolean[byteArray.length - 16];
            for(int i = 0; i <= pixelArray.length - 1;i++){
                pixelArray[i] = (byteArray[i + 16] == 0);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(pixelArray.length);
    }

    private void createImages(){

        int colorInt;
        for(int ip = 0; ip <= (pixelArray.length / (28*28)) - 1; ip++) {
            if((ip + 1) % 100 == 0 )
            {
                System.out.println("Bild " + (ip + 1) + " von " + ((pixelArray.length / (28 * 28))) + ". " + ((int) ((ip + 1) * 100 / ((pixelArray.length / (28 * 28))))) + "% abgeschlossen.");
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
                File file = new File(mnistFolder.getAbsolutePath() + "\\images\\" + labelArray[ip] + "_" + (ip+1) + ".png");
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
