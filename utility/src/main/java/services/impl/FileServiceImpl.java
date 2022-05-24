package services.impl;

import beans.Modules;
import beans.Settings;
import exceptions.MissingValueException;
import helpers.XmlHelper;
import helpers.impl.XmlHelperImpl;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import services.FilesService;

import static constants.Constants.*;

public class FileServiceImpl implements FilesService {

    private static final Logger LOG = Logger.getLogger(FileServiceImpl.class.getName());

    @Override
    public boolean checkSettings() {
        File settings = new File(CURRENT_ROOT + PATH_SEPARATOR + SETTINGS_XML);
        return !settings.isFile();
    }

    @Override
    public void genereteSettings() throws JAXBException, FileNotFoundException {

        Settings settings = new Settings();
        settings.createTemplate();
        XmlHelper xmlHelper = new XmlHelperImpl();
        xmlHelper.fromJavaToXml(settings, CURRENT_ROOT, SETTINGS_XML, Settings.class);
    }

    @Override
    public void updateVersion(File settingsFile, String fileToUpdate) throws MissingValueException, JAXBException {

        Settings settings = getSettings(settingsFile);
        final File parentPom = new File(settings.getRoot() + PATH_SEPARATOR + "pom.xml");
        for (Modules module : settings.getModules()) {
            if (StringUtils.isNotEmpty(module.getModulename()))
                for (File file : searchFiles(settings.getRoot() + PATH_SEPARATOR + module.getModulename(), fileToUpdate)) {
                    modifyVersion(StringUtils.isNotEmpty(module.getVersion()) ? module.getVersion() : settings.getVersion(), file, Boolean.FALSE);
                }
        }
        modifyVersion(settings.getVersion(), parentPom, Boolean.TRUE);
    }

    public void modifyVersion(String version, File file, boolean isParentPom) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String line = reader.readLine();
            String versionUpdated = "";
            String[] versionArray = version.split("\\.");
            while (line != null) {
                versionUpdated = versionUpdated.concat(isParentPom ? pomVersionUpdater(line, versionArray) : packageInfoUpdeter(version, line));
                line = reader.readLine();
                if (line != null) {
                    versionUpdated = versionUpdated.concat(System.lineSeparator());
                }
            }
            writeToFile(file, versionUpdated);
        } catch (IOException e) {
            LOG.info("error: " + e.getMessage());
        }
    }

    private String packageInfoUpdeter(String version, String line) {
        final String VERSION_STRING = "@Version(\"";
        if (line.contains(VERSION_STRING)) {
            String stringToReplace = line.substring(VERSION_STRING.length(), line.indexOf("\")"));
            line = line.replace(stringToReplace, version);
        }
        return line;
    }


    private void writeToFile(File file, String fileUpdated) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(fileUpdated);
        }
    }

    private String pomVersionUpdater(String line, String[] versionArray) {
        String stringToReplace;
        String[] replacer;
        final String UDC_BUILD_NUMBER = "<UDC.BUILD.NUMBER>";
        final String RELEASE = "<RELEASE>";
        final String UDC_BUILD_VERSION = "<UDC.BUILD.VERSION>";
        int position;
        String versionSection;

        if (line.contains(UDC_BUILD_NUMBER)) {
            versionSection = UDC_BUILD_NUMBER;
            position = 2;
        } else if (line.contains(RELEASE)) {
            versionSection = RELEASE;
            position = 1;
        } else if (line.contains(UDC_BUILD_VERSION)) {
            versionSection = UDC_BUILD_VERSION;
            position = 0;
        } else {
            return line;
        }

        int start = StringUtils.indexOf(line, versionSection) + versionSection.length();
        int end = StringUtils.indexOf(line, "</");
        stringToReplace = line.substring(start, end);
        replacer = line.substring(start, end).split("\\.");
        replacer[0] = versionArray[position];
        line = line.replace(stringToReplace, String.join(".", replacer));

        return line;
    }

    @Override
    public List<File> searchFiles(String dirString, String fileName) {
        File dir = new File(dirString);
        List<File> resultList = new ArrayList<>();
        File[] list = dir.listFiles();
        if (list != null)
            for (File fil : list) {
                if (fil.isDirectory()) {
                    resultList.addAll(searchFiles(fil.getAbsolutePath(), fileName));
                } else if (fileName.equalsIgnoreCase(fil.getName())) {
                    resultList.add(fil);
                }
            }
        return resultList;
    }

    @Override
    public Settings getSettings(File fileSetting) throws JAXBException, MissingValueException {
        XmlHelper xmlHelper = new XmlHelperImpl();
        Settings settings = xmlHelper.fromXmlToJava(fileSetting);
        if (StringUtils.isEmpty(settings.getVersion())) {
            throw new MissingValueException("New version not set");
        }
        if (StringUtils.isEmpty(settings.getRoot())) {
            throw new MissingValueException("Root path not set");
        }
        return settings;
    }

}
