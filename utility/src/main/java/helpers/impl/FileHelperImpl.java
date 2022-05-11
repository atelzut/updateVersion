package helpers.impl;

import beans.Settings;
import helpers.FilesHelper;
import helpers.XmlHelper;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;

public class FileHelperImpl implements FilesHelper {
    @Override
    public boolean checkSettings() {
        File settings = new File(System.getProperty("user.dir")+"/settings.xml");
        return settings.isFile();
    }

    @Override
    public void genereteSettings() throws JAXBException, FileNotFoundException {

        Settings settings = new Settings();
        settings.createTemplate();
        XmlHelper xmlHelper= new XmlHelperImpl();
        xmlHelper.fromJavaToXml(settings, System.getProperty("user.dir"), "settings.xml",Settings.class);
    }
}
