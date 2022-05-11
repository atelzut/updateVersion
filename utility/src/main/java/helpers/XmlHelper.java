package helpers;

import beans.Settings;
import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;

public interface XmlHelper {

    public void fromJavaToXml(Object obj, String path, String filename, Class<Settings> objectClass) throws  FileNotFoundException, jakarta.xml.bind.JAXBException;
    public Settings fromXmlToJava(String path, String filename) throws JAXBException;
}
