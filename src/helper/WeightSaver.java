package helper;

import main.NetworkController;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class WeightSaver {

    private static byte[] weightArray = new byte[254080]; // die datei wird 254080 Byte groß
    private static FileOutputStream fos;
    private static File file;
    private static String timeString = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString().replace(":", "");
    private static boolean openFileDialogFinished = false;
    public static boolean fileChoosen = false;

    public static void initialize() {
        new File(System.getenv("APPDATA") + "\\mnist\\temp\\").mkdirs();
        file = new File(System.getenv("APPDATA") + "\\mnist\\temp\\" + timeString + ".weights");
        if(!openFileDialogFinished) {
            chooseFile();
            openFileDialogFinished = true;
        }

        try {
            fos = new FileOutputStream(file.getAbsoluteFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void receiveWeight(double weight, int offset) {
        byte[] tempByteArray = toByteArray(weight);
        for (int i = 0; i < 8; i++) weightArray[offset + i] = tempByteArray[0 + i];
    }

    public static void writeArrayToFile() {
        try {
            fos.write(weightArray);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readWeightsFromFile(String filePathToReadFrom) {
        try {
            weightArray = Files.readAllBytes(Paths.get(filePathToReadFrom));

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

    public static byte[] getWeightArray() {
        return weightArray;
    }

    private static void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        // Dialog zum Oeffnen von Dateien anzeigen
        int rueckgabeWert = chooser.showOpenDialog(null);

        /* Abfrage, ob auf "Öffnen" geklickt wurde */
        if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            readWeightsFromFile(chooser.getSelectedFile().getAbsolutePath());
            fileChoosen = true;

    }
    }

    public boolean getFileChoosen() {
        return fileChoosen;
    }
}
