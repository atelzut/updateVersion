package beans;

import org.apache.commons.lang3.StringUtils;

public class Modules {
    private String modulename;
    private String version;


    public Modules() {
        this.modulename = StringUtils.EMPTY;
        this.version = StringUtils.EMPTY;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
