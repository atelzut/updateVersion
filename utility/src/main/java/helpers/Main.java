package helpers;

import helpers.impl.FileHelperImpl;
import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;

import java.util.logging.Logger;

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


    }
}
