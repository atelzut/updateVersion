package helpers.impl;

import beans.Settings;
import helpers.XmlHelper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
}
