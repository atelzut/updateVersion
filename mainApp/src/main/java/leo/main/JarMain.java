package leo.main;

import leo.services.FileService;
import leo.services.impl.UpdaterServiceImpl;

public class JarMain {

    public static void main(String[] args) throws Exception {
        FileService updateService = new UpdaterServiceImpl();
        updateService.updateFromFileSettings();
    }
}
