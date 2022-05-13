package helpers;

import beans.Settings;
import jakarta.xml.bind.JAXBException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface XmlHelper {

    void fromJavaToXml(Object obj, String path, String filename, Class<Settings> objectClass) throws FileNotFoundException, jakarta.xml.bind.JAXBException;

    Settings fromXmlToJava(String path, String filename) throws JAXBException;

    void pomModifier(String basedir, String version) throws IOException, XmlPullParserException;
}
