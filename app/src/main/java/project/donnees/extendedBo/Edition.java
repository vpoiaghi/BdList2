package project.donnees.extendedBo;

import java.util.ArrayList;
import java.util.List;

import project.donnees.bo.BoEdition;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class Edition extends BoEdition {

    public List<Long> getIdSeriesList() {

        List<Long> idSeriesList = new ArrayList<>();

        if (getIdSerie() == null) {

            for (String idSerie : getIdSeries().split(";")) {
                if (idSerie.length() > 0) {
                    idSeriesList.add(Long.parseLong(idSerie));
                }
            }

        } else {
            idSeriesList.add(getIdSerie());
        }

        return idSeriesList;
    }

    public List<Long> getIdTitlesList() {

        List<Long> idTitlesList = new ArrayList<>();

        if (getIdTitle() == null) {

            for (String idTitle : getIdTitles().split(";")) {
                if (idTitle.length() > 0) {
                    idTitlesList.add(Long.parseLong(idTitle));
                }
            }

        } else {
            idTitlesList.add(getIdTitle());
        }

        return idTitlesList;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Edition) && this.getId().equals(((Edition)obj).getId());
    }

}