package helpers.impl;

import beans.Settings;
import helpers.FilesHelper;
import helpers.XmlHelper;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class FileHelperImpl implements FilesHelper {
    public static final String SETTINGS_XML = "settings.xml";
    private final String getCurrentRoot = System.getProperty("user.dir");

    private static final Logger LOG = Logger.getLogger(FileHelperImpl.class.getName());

    @Override
    public boolean checkSettings() {
        File settings = new File(getCurrentRoot + "/" + SETTINGS_XML);
        return settings.isFile();
    }

    @Override
    public void genereteSettings() throws JAXBException, FileNotFoundException {

        Settings settings = new Settings();
        settings.createTemplate();
        XmlHelper xmlHelper = new XmlHelperImpl();
        xmlHelper.fromJavaToXml(settings, getCurrentRoot, SETTINGS_XML, Settings.class);
    }

    @Override
    public void updateVersion() throws JAXBException {

        XmlHelper xmlHelper = new XmlHelperImpl();
        Settings settings = xmlHelper.fromXmlToJava(getCurrentRoot, SETTINGS_XML);
        LOG.info(settings.toString());
    }


}
