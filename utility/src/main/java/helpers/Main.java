package helpers;

import helpers.impl.FileHelperImpl;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.logging.Logger;

import static constants.Constants.*;

public class Main {

    // retrieve the logger for the current class
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {FilesHelper filesHelper = new FileHelperImpl();
        if (!filesHelper.checkSettings()) {
            try {
                filesHelper.genereteSettings();
            } catch ( FileNotFoundException | JAXBException e) {
                e.printStackTrace();
                LOG.info(e.getMessage());
            }
        }

        try {
            LOG.info("file list");
            filesHelper.updateVersion(CURRENT_ROOT, SETTINGS_XML, PACKAGE_INFO);

        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }


    }
}
