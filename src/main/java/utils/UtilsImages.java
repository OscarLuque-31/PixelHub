package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UtilsImages {

    /**
     * Convierte un archivo de imagen a un array de bytes.
     */
    public static byte[] fileToByteArray(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}