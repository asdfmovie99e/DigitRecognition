package helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UbyteEncoder {
    private File destinationFolder;

    public UbyteEncoder(){
        destinationFolder = new File(Paths.get("").toAbsolutePath().toString() + "\\images");
    }

    public void decode() {
        try {
            byte[] array = Files.readAllBytes(new File(destinationFolder.getAbsolutePath() + "\\train-labels.idx3-ubyte").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
