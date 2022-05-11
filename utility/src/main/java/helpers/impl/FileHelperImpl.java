package helpers.impl;

import beans.Modules;
import beans.Settings;
import helpers.FilesHelper;
import helpers.XmlHelper;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import constants.*;
import org.apache.commons.lang3.StringUtils;

import static constants.Constants.*;

public class FileHelperImpl implements FilesHelper {

    private static final Logger LOG = Logger.getLogger(FileHelperImpl.class.getName());
    private static final String VERSION_STRING = "@Version(\"";

    @Override
    public boolean checkSettings() {
        File settings = new File(CURRENT_ROOT + PATH_SEPARATOR + SETTINGS_XML);
        return settings.isFile();
    }

    @Override
    public void genereteSettings() throws JAXBException, FileNotFoundException {

        Settings settings = new Settings();
        settings.createTemplate();
        XmlHelper xmlHelper = new XmlHelperImpl();
        xmlHelper.fromJavaToXml(settings, CURRENT_ROOT, SETTINGS_XML, Settings.class);
    }

    @Override
    public void updateVersion(String path, String fileSetting, String fileToUpdate) throws JAXBException, IOException {

        XmlHelper xmlHelper = new XmlHelperImpl();
        Settings settings = xmlHelper.fromXmlToJava(CURRENT_ROOT, SETTINGS_XML);
        for (Modules module : settings.getModules()) {
            if (StringUtils.isNotEmpty(module.getModulename()))
                for (File file : searchFiles(settings.getRoot() + PATH_SEPARATOR + module.getModulename(), PACKAGE_INFO)) {
                    modifyVersion(StringUtils.isNotEmpty(module.getVersion()) ? module.getVersion() : settings.getVersion(), file);
                }
        }
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

    public void modifyVersion(String version, File file) {
        BufferedReader reader =null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String oldContent = "";
            while (line != null) {
                if (line.contains(VERSION_STRING)) {
                    String stringToReplace = line.substring(VERSION_STRING.length(), line.indexOf("\")"));
                    line= line.replace(stringToReplace, version);
                }

                oldContent = oldContent.concat(line).concat(System.lineSeparator());
                line = reader.readLine();
            }
            writer = new FileWriter(file);
            writer.write(oldContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Closing the resources
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
