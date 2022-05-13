package services.impl;

import beans.Modules;
import beans.Settings;
import helpers.XmlHelper;
import helpers.impl.XmlHelperImpl;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import services.FilesService;

import static constants.Constants.*;

public class FileServiceImpl implements FilesService {

    private static final Logger LOG = Logger.getLogger(FileServiceImpl.class.getName());
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
    public void updateVersion(String path, String fileSetting, String fileToUpdate) throws JAXBException, IOException, XmlPullParserException {

        XmlHelper xmlHelper = new XmlHelperImpl();
        Settings settings = xmlHelper.fromXmlToJava(path, fileSetting);
        for (Modules module : settings.getModules()) {
            if (StringUtils.isNotEmpty(module.getModulename()))
                for (File file : searchFiles(settings.getRoot() + PATH_SEPARATOR + module.getModulename(), fileToUpdate)) {
                    modifyVersion(StringUtils.isNotEmpty(module.getVersion()) ? module.getVersion() : settings.getVersion(), file);
                }
        }
        modifyPom(settings.getRoot(), settings.getVersion());
    }

    private void modifyPom(String root, String version) {
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            final File file = new File(root + PATH_SEPARATOR + "pom.xml");
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String[] versionArray = version.split("\\.");
            StringBuilder oldContent = new StringBuilder();
            while (line != null) {

                oldContent.append(updatePomVersion(line, versionArray));
                line = reader.readLine();
                if (line != null) {
                    oldContent.append(System.lineSeparator());
                }

            }
            writer = new FileWriter(file);
            writer.write(String.valueOf(oldContent));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Closing the resources
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String updatePomVersion(String line, String[] versionArray) {
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
        replacer[0]=versionArray[position];
        line = line.replace(stringToReplace,String.join(".",replacer ));

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

    public void modifyVersion(String version, File file) {
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String oldContent = "";
            while (line != null) {
                if (line.contains(VERSION_STRING)) {
                    String stringToReplace = line.substring(VERSION_STRING.length(), line.indexOf("\")"));
                    line = line.replace(stringToReplace, version);
                }

                oldContent = oldContent.concat(line);
                line = reader.readLine();
                if (line != null) {
                    oldContent = oldContent.concat(System.lineSeparator());
                }
            }
            writer = new FileWriter(file);
            writer.write(oldContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Closing the resources
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
