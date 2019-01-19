package project.donnees.bo;

import framework.donnees.bo.Bo;

/**
 *
 * Created by VINCENT on 16/10/2016.
 */
public class BoAuthor extends Bo {

    String name;
    String searchName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

}
