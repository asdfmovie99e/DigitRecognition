package helper;

 /*
 @author Jens Krueger
 @version 1.0
 */


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.server.ExportException;

public class Debug {

    private static File logFile;
    private static boolean fileCreated = false;
    private static BufferedOutputStream stream;

    public static void log(String stringToLog){
        //Der String stringToLog wird in die Output.log Datei und in die Konsole mit Timestamp geschrieben.
    if (!fileCreated) fileCreated = createLogFile();
    try {
        String outputString = "[" + java.time.LocalTime.now() + "] " + stringToLog+"\n";
        stream.write(outputString.getBytes());
        System.out.print(outputString);
    } catch(Exception e){
        e.printStackTrace();
    }

    }

    private static boolean createLogFile(){
        //Erstellt die Log Datei und den OutputStream. Muss normalerweise nur einmal aufgerufen werden am Anfang.
        try{
            String path = new File("src/Helper/Output.log").getAbsolutePath();
            logFile = new File(path);
            logFile.createNewFile();
            stream = new BufferedOutputStream(
            new FileOutputStream(logFile));
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void flush(){
        //Wird am ende ausgeführt um den Output Buffer zu leeren.
        try {
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
