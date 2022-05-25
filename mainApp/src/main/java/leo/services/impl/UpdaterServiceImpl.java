package leo.services.impl;

import constants.Constants;
import exceptions.MissingValueException;
import jakarta.xml.bind.JAXBException;
import leo.services.FileService;
import helpers.FilesHelper;
import helpers.impl.FileHelperImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class UpdaterServiceImpl implements FileService {
    private static final Logger LOG = Logger.getLogger(UpdaterServiceImpl.class.getName());


    @Override
    public void updateFromFileSettings()  {
        FilesHelper fileHelper = new FileHelperImpl();
        if (fileHelper.checkSettings()) {
            try {
                fileHelper.genereteSettings();
                LOG.info("generated template for file settings");
            } catch (FileNotFoundException | JAXBException e) {
                e.printStackTrace();
                LOG.info(e.getMessage());

            }
        } else {
            try {

                fileHelper.updateVersion(new File(Constants.CURRENT_ROOT.concat(Constants.PATH_SEPARATOR).concat(Constants.SETTINGS_XML)), Constants.PACKAGE_INFO);

            } catch (JAXBException | MissingValueException e) {
                e.printStackTrace();
                LOG.info("error: " + e.getMessage());
            }
        }
    }

    @Override
    public void updateFromFileSettingsVisual() {

    }
}
