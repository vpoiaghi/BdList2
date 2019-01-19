package project.donnees.dao;

import java.util.HashMap;

import framework.donnees.dao.Dao;

/**
 * Created by VINCENT on 01/01/2019.
 *
 */
public final class FactoryDao {

    private static final HashMap<Object, Object> h = new HashMap<>();

    private FactoryDao(){}

    // Retourne une instance unique d'un Dao
    // Usage : DaoEdition d = FactoryDao.get(DaoEdition.class);
    public static <T extends Dao> T get(final Class<T> type) {

        T dao = null;

        try {
            @SuppressWarnings("unchecked")
            T tmpDao = (T) h.get(type);
            dao = tmpDao;
        } catch(ClassCastException e) {
            // rien à faire
        }

        if (dao == null) {

            try {
                dao = type.newInstance();
                h.put(type, dao);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        return dao;

    }

}
