package helper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UbyteEncoder {
    private File destinationFolder;

    public UbyteEncoder(){
        destinationFolder = new File(Paths.get("").toAbsolutePath().toString() + "\\images");
    }

    public void decode() {

    }
}
