package helpers;

import java.io.FileNotFoundException;

public interface XmlHelper {

    public void fromJavaToXml(Object obj, String path, String filename) throws  FileNotFoundException, jakarta.xml.bind.JAXBException;
    public void fromXmlToJava(String path, String filename) ;
}
