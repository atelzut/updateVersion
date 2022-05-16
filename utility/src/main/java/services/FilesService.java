package services;

import jakarta.xml.bind.JAXBException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FilesService {

    boolean checkSettings();
    void genereteSettings() throws  FileNotFoundException, JAXBException;
    void updateVersion(String path, String fileSetting, String fileToUpdate) throws JAXBException, IOException, XmlPullParserException;
    List<File> searchFiles(String path, String fileName);
    void modifyVersion(String version, File file, boolean isParentPom) throws IOException;
}
