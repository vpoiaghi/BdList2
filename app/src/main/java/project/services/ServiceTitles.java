package project.services;

import java.util.List;

import project.donnees.extendedBo.Serie;
import project.donnees.extendedBo.Title;
import project.donnees.dao.DaoTitle;
import project.services.abs.Service;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceTitles extends Service<Title, DaoTitle> {

    protected ServiceTitles() {
        super();
    }

    public List<Title> getBySerie(Serie serie) {
        return getDao().getBySerie(serie.getId());
    }

    public void setInPossession(final Title title, final boolean inPossession) {

        if (title.isInPossession() != inPossession) {

            title.setInPossession(inPossession);
            save(title);

        }

    }
}
