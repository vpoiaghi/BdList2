package project.donnees.search;

import java.io.Serializable;

/**
 *
 * Created by VINCENT on 08/03/2016.
 */
public class SearchAuthorsParameters implements Serializable {

    protected String name = null;
    protected String realName = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}
