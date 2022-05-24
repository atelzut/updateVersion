package services;

import beans.Settings;
import exceptions.MissingValueException;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FilesService {

    boolean checkSettings();
    void genereteSettings() throws  FileNotFoundException, JAXBException;
    void updateVersion(File settingFile, String fileToUpdate) throws Exception;
    List<File> searchFiles(String path, String fileName);
    void modifyVersion(String version, File file, boolean isParentPom) throws IOException;
    Settings getSettings (File inputFile) throws MissingValueException, JAXBException;
}
