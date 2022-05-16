package leo.main;

import constants.Constants;
import jakarta.xml.bind.JAXBException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import services.FilesService;
import services.impl.FileServiceImpl;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        FilesService filesService = new FileServiceImpl();
        if (!filesService.checkSettings()) {
            try {
                filesService.genereteSettings();
            } catch ( FileNotFoundException | JAXBException e) {
                e.printStackTrace();
                LOG.info(e.getMessage());
            }
        }

        try {
                filesService.updateVersion(Constants.CURRENT_ROOT, Constants.SETTINGS_XML, Constants.PACKAGE_INFO);

        } catch (JAXBException | IOException | XmlPullParserException e ) {
            e.printStackTrace();
            LOG.info("error: " +  e.getMessage());
        }


    }
}
