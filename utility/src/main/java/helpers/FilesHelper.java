package helpers;

import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FilesHelper {

    boolean checkSettings();
    void genereteSettings() throws  FileNotFoundException, JAXBException;
    void updateVersion(String path, String fileSetting, String fileToUpdate) throws JAXBException, IOException;
    List<File> searchFiles(String path, String fileName);
    void modifyVersion(String version, File file) throws IOException;
}
