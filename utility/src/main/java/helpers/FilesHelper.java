package helpers;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;

public interface FilesHelper {

    public boolean checkSettings();
    public void genereteSettings() throws  FileNotFoundException, JAXBException;
    void updateVersion() throws JAXBException;
}
