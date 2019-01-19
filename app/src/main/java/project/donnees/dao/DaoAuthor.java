package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import framework.donnees.dao.DaoFilters;
import framework.donnees.dao.TypedDao;
import project.donnees.extendedBo.Author;
import project.donnees.search.DaoSearchAuthorsParameters;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoAuthor extends TypedDao<Author> {

    private static int COL_POS = 2;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public static final String COL_SEARCH_NAME = "searchName";
    public static final int POS_SEARCH_NAME = COL_POS++;


    public DaoAuthor() {
        super();
    }

    @Override
    public String getTableName() {
        return "author";
    }

    @Override
    public void setContentValues(final ContentValues values, final Author author) {

        values.put(COL_NAME, author.getName());
        values.put(COL_SEARCH_NAME, author.getSearchName());
    }

    protected Author cursorToBo(Cursor cursor) {

        Author bo = new Author();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setName(cursorToString(cursor, POS_NAME));
        bo.setSearchName(cursorToString(cursor, POS_SEARCH_NAME));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_NAME + " text not null,"
                + COL_SEARCH_NAME + " text not null";

    }

    public long searchCount(final DaoSearchAuthorsParameters parameters) {

        long result;

        try {
            final String filters = getFilters(parameters);
            result = executeCountQuery("SELECT COUNT(*) FROM " + getTableName() + filters);

        } catch (IllegalArgumentException e) {
            result = 0;
        }


        return result;

    }

    public List<Author> search(final DaoSearchAuthorsParameters parameters) {

        List<Author> authorsList;

        try {
            final String filters = getFilters(parameters);
            authorsList = executeRawQuery(" SELECT * FROM " + getTableName() + filters);

        } catch (IllegalArgumentException e) {
            authorsList = getAll();
        }

        return authorsList;

    }

    private String getFilters(final DaoSearchAuthorsParameters parameters){

        DaoFilters filters = new DaoFilters();

        filters.add(getNameFilter(parameters));

        return filters.toString();
    }

    private String getNameFilter(final DaoSearchAuthorsParameters parameters) {

        String nameFilter = parameters.getName();
        String filter = null;

        if (nameFilter != null) {
            if (nameFilter.length() == 1) {
                filter = "(" + COL_SEARCH_NAME + " LIKE '" + nameFilter + "%')";
            } else {
                filter = "(" + COL_SEARCH_NAME + " LIKE '%" + nameFilter + "%')";
            }
        }

        return filter;
    }

}
