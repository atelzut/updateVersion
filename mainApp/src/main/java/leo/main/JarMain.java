package leo.main;

import constants.Constants;
import jakarta.xml.bind.JAXBException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import services.FilesService;
import services.impl.FileServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class JarMain {

    private static final Logger LOG = Logger.getLogger(JarMain.class.getName());

    public static void main(String[] args) throws Exception {
        FilesService filesService = new FileServiceImpl();
        if (filesService.checkSettings()) {
            try {
                filesService.genereteSettings();
                LOG.info("generated template for file settings");
            } catch (FileNotFoundException | JAXBException e) {
                e.printStackTrace();
                LOG.info(e.getMessage());
            }
        } else {
            try {

                filesService.updateVersion(new File(Constants.CURRENT_ROOT.concat(Constants.PATH_SEPARATOR).concat(Constants.SETTINGS_XML)), Constants.PACKAGE_INFO);

            } catch (JAXBException | IOException | XmlPullParserException e) {
                e.printStackTrace();
                LOG.info("error: " + e.getMessage());
            }
        }

    }
}
