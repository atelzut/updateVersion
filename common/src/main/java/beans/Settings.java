package beans;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringUtils;

@XmlRootElement
public class Settings {
    private List<Modules> modules;
    private String root;
    private String version;

    public Settings() {
    }

    public Settings(List<Modules> modules, String root, String version) {
        this.modules = modules;
        this.root = root;
        this.version = version;
    }
    public void createTemplate() {
        this.modules = new ArrayList<>();
        this.modules.add(new Modules());
        this.modules.add(new Modules());
        this.root = StringUtils.EMPTY;
        this.version = StringUtils.EMPTY;
    }

    @XmlElement
    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(List<Modules> modules) {
        this.modules = modules;
    }

    @XmlElement
    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "modules=" + modules +
                ", root='" + root + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}



