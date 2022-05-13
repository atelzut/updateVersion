package helpers;

import jakarta.xml.bind.JAXBException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import services.FilesService;
import services.impl.FileServiceImpl;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.logging.Logger;

import static constants.Constants.*;

public class Main {

    // retrieve the logger for the current class
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        FilesService FilesService = new FileServiceImpl();
        if (!FilesService.checkSettings()) {
            try {
                FilesService.genereteSettings();
            } catch ( FileNotFoundException | JAXBException e) {
                e.printStackTrace();
                LOG.info(e.getMessage());
            }
        }

        try {
            try {
                FilesService.updateVersion(CURRENT_ROOT, SETTINGS_XML, PACKAGE_INFO);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }


    }
}
