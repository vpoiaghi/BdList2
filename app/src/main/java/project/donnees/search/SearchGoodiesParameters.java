package project.donnees.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by VINCENT on 08/03/2016.
 */
public class SearchGoodiesParameters implements Serializable {

    protected List<String> kindsNames = null;
    protected Long idAuthor = null;

    public List<String> getKindsNames() {
        return kindsNames;
    }

    public void setKindsNames(List<String> kindsNames) {
        this.kindsNames = kindsNames;
    }

    public void addKindName(String kindName) {

        if (kindsNames == null) {
            kindsNames = new ArrayList<>();
        }

        kindsNames.add(kindName);
    }

    public Long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Long idAuthor) {
        this.idAuthor = idAuthor;
    }

}
