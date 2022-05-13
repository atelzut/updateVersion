package helpers.impl;

import beans.Settings;
import helpers.XmlHelper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.*;

import static constants.Constants.PATH_SEPARATOR;

public class XmlHelperImpl implements XmlHelper {
    @Override
    public void fromJavaToXml(Object obj, String path, String filename, Class<Settings> objectClass) throws JAXBException, FileNotFoundException {
        JAXBContext contextObj = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] {objectClass}, null);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshallerObj.marshal(obj, new FileOutputStream(path+PATH_SEPARATOR+filename));


    }

    @Override
    public Settings fromXmlToJava(String path, String filename) throws JAXBException {
        JAXBContext contextObj = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] {Settings.class}, null);
       File inputFile = new File(path+PATH_SEPARATOR+filename);
        Unmarshaller jaxbUnmarshaller = contextObj.createUnmarshaller();
        return (Settings) jaxbUnmarshaller.unmarshal(inputFile);
    }

    @Override
    public void pomModifier(String baseDir, String version) throws IOException, XmlPullParserException {

//Reading
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileInputStream(new File(baseDir, "/pom.xml")));

//Editing
        String[] versionArray = version.split("\\.");
        model.getProperties().setProperty("UDC.BUILD.VERSION",versionArray[0].concat(".${RELEASE}"));
        model.getProperties().setProperty("RELEASE",versionArray[1]);
        model.getProperties().setProperty("UDC.BUILD.NUMBER",versionArray[2]);

//Writing
        MavenXpp3Writer writer = new MavenXpp3Writer();
        writer.write(new FileOutputStream(new File(baseDir, "/pom.xml")), model);
    }


}
