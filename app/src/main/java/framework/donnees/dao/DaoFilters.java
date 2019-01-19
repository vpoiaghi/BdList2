package framework.donnees.dao;

/**
 *
 * Created by VINCENT on 17/04/2016.
 */
public class DaoFilters {

    private static final String EMPTY_FILTER = "";

    private String filters = null;

    public void add(final String value) {

        if (value != null) {

            final String newFilter = value.trim();

            if (newFilter.length() > 0) {

                if (filters == null) {
                    filters = " WHERE " + newFilter;
                } else {
                    filters += " AND " + newFilter;
                }
            }
        }

    }

    public void clean() {
        filters = null;
    }

    public String toString() {
        return filters == null ? EMPTY_FILTER : filters;
    }

}
