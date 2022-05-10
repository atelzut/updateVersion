package helpers.impl;

import beans.Settings;
import helpers.XmlHelper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XmlHelperImpl implements XmlHelper {
    @Override
    public void fromJavaToXml(Object obj, String path, String filename) throws JAXBException, FileNotFoundException {
        JAXBContext contextObj = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] {Settings.class}, null);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshallerObj.marshal(obj, new FileOutputStream(path+"/"+filename));


    }

    @Override
    public void fromXmlToJava(String path, String filename)  {
       //TODO
    }
}
