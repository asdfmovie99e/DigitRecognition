package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class WeightSaver {

    private static byte[] weightArray = new byte[254080]; // die datei wird 254080 Byte groß
    private static FileOutputStream fos;

    public static void initialize(){
        new File(System.getenv("APPDATA") + "\\mnist\\temp\\").mkdirs();
        File file = new File(System.getenv("APPDATA") + "\\mnist\\temp\\test.weights");
        try  {
            fos = new FileOutputStream(file.getAbsoluteFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void receiveWeight(double weight, int offset){
        byte[] tempByteArray = toByteArray(weight);
        for(int i = 0 ; i < 8; i++) weightArray[offset + i] = tempByteArray[0 + i];
    }

    public static void writeArrayToFile(){
        try {
            fos.write(weightArray);
            fos.close();
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    private static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }


}
