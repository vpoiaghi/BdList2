package project.donnees.search;

import java.io.Serializable;

/**
 *
 * Created by VINCENT on 08/03/2016.
 */
public class DaoSearchAuthorsParameters extends SearchAuthorsParameters implements Serializable {

    public DaoSearchAuthorsParameters() {
        super();
    }

    public DaoSearchAuthorsParameters(final SearchAuthorsParameters searchParameters) {
        super();
        this.name = searchParameters.getName();
    }

}
