package project.donnees.extendedBo;

import java.util.ArrayList;
import java.util.List;

import project.donnees.bo.BoGoody;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class Goody extends BoGoody {

    public List<Long> getIdSeriesList() {

        List<Long> idSeriesList = new ArrayList<>();

        if (getIdSerie() == null) {

            if (getIdSeries() != null) {
                for (String idStringSerie : getIdSeries().split(";")) {
                    if (idStringSerie.length() > 0) {
                        idSeriesList.add(Long.parseLong(idStringSerie));
                    }
                }
            }

        } else {
            idSeriesList.add(getIdSerie());
        }

        return idSeriesList;
    }

    public List<Long> getIdAuthorsList() {

        List<Long> idAuthorsList = new ArrayList<>();

        if (getIdAuthor() == null) {

            if (getIdAuthors() != null) {
                for (String idStringAuthors : getIdAuthors().split(";")) {
                    if (idStringAuthors.length() > 0) {
                        idAuthorsList.add(Long.parseLong(idStringAuthors));
                    }
                }
            }

        } else {
            idAuthorsList.add(getIdAuthor());
        }

        return idAuthorsList;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Goody) && this.getId().equals(((Goody)obj).getId());
    }

}
