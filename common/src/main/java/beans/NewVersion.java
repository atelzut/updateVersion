package beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;



@XmlRootElement
public class NewVersion {
    private List<Modules> modules;
    private String root;
    private String version;

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
}



