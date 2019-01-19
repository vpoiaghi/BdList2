package project.services;

import project.donnees.extendedBo.Parameters;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.dao.DaoParameters;
import framework.tools.DateUtils;
import project.services.abs.Service;
import project.utils.Constantes;

/**
 *
 * Created by VINCENT on 09/03/2016.
 */
public class ServiceParameters extends Service<Parameters, DaoParameters> {

    protected ServiceParameters() {
        super();
    }

    public Parameters getParameters() {

        final DaoParameters dao = getDao();

        dao.openDatabase();

        Parameters parameters = dao.getParameters();

        if (parameters == null) {

            parameters = new Parameters();
            parameters.setDataUpdateDate(new SqlDate("19000101_000000"));
            parameters.setNewsDaysCount(15);
            parameters.setFirstComingDate(new SqlDate(DateUtils.getTomorrow()));
            parameters.setUseDefaultComingDate(true);
            parameters.setEventsPastDaysCount(10);
            parameters.setAppVersion(Constantes.APP_VERSION);

            dao.save(parameters);

        } else if (parameters.getUseDefaultComingDate()) {

            SqlDate tomorrow = new SqlDate(DateUtils.getTomorrow());

            if (parameters.getFirstComingDate().compareTo(tomorrow) != 0) {
                parameters.setFirstComingDate(tomorrow);
                saveParameters(parameters);
            }
        }

        dao.closeDatabase();

        return parameters;
    }

    public void saveParameters(final Parameters parameters) {

        final DaoParameters dao = getDao();

        // Ouverture de la base
        dao.openDatabase();

        // Suppression de l'éventuel bo parameters présent en base
        dao.deleteAll();
        parameters.setInbase(false);

        // Création du nouveau bo parameters en base
        dao.save(parameters);

        // Fermeture de la base
        dao.closeDatabase();

    }

    public String getDatabaseVersion() {
        return getDao().getDatabaseVersion();
    }

}
