package framework.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import framework.donnees.database.MySQLiteHelper;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 02/04/2016.
 */
public abstract class Dao implements IDao {

    private static android.database.sqlite.SQLiteDatabase database = null;
    private static MySQLiteHelper dbHelper = MySQLiteHelper.getInstance();

    public abstract String getTableName();
    protected abstract String getColumnsCreateRequest();


    protected Dao() {}

    public boolean openDatabase() throws SQLException {

        boolean databaseAlreadyOpened;

        if (database == null) {
            databaseAlreadyOpened = false;
            database = dbHelper.getWritableDatabase();

        } else {
            databaseAlreadyOpened = true;
        }

        return databaseAlreadyOpened;
    }

    public void closeDatabase() {
        dbHelper.close();
        database = null;
    }

    public String getDatabaseVersion() {

        String result = "inconnue";

        if (dbHelper != null) {
            result = dbHelper.getReadableDatabase().getVersion() + "";
        }

        return result;
    }


    protected Cursor doRawQuery(final String query) {
        System.out.println("###### doRawQuery : " + query);

        return database.rawQuery(query, null);

    }

    protected void doInsert(final ContentValues values) {
        System.out.println("###### doInsert : "
                + " values := " + (values == null ? "" : values.toString()) + ";");

        final boolean alreadyOpened = openDatabase();

        database.insert(getTableName(), null, values);

        if (!alreadyOpened) {
            closeDatabase();
        }

    }

    protected void doUpdate(final ContentValues values, String whereClause, String[] whereArgs) {
        System.out.println("###### doUpdate :"
                + " values := " + (values == null ? "" : values.toString()) + ";"
                + " whereClause := " + (whereClause == null ? "" : whereClause) + ";"
                + " whereArgs := " + (whereArgs == null ? "" : whereArgs));

        final boolean alreadyOpened = openDatabase();

        database.update(getTableName(), values, whereClause, whereArgs);

        if (!alreadyOpened) {
            closeDatabase();
        }

     }

    public String getCreateRequest() {

        return "create table "
                + getTableName() + "("
                + getColumnsCreateRequest().trim()
                + ");";
    }

    protected long executeLongResultQuery(final String rqt) {
        System.out.println("###### executeLongResultQuery : " + rqt);

        long result = 0;

        final boolean alreadyOpened = openDatabase();
        final Cursor cursor = database.rawQuery(rqt, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            result = cursor.getLong(0);
        }

        cursor.close();

        if (!alreadyOpened) {
            closeDatabase();
        }

        return result;
    }

    protected List<String> executeStringListResultQuery(final String query) {

        final List<String> stringsList = new ArrayList<>();

        final boolean alreadyOpened = openDatabase();
        final Cursor cursor = doRawQuery(query);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            stringsList.add(cursorToString(cursor, 0));
            cursor.moveToNext();
        }

        cursor.close();

        if (!alreadyOpened) {
            closeDatabase();
        }

        return stringsList;

    }

    protected long executeCountQuery(final String rqt) {
        return executeLongResultQuery(rqt);
    }

    public void deleteAll() {

        final boolean alreadyOpened = openDatabase();
        database.delete(getTableName(), null, null);
        if (!alreadyOpened) {
            closeDatabase();
        }

    }

    public long getAllCount() {
        return executeCountQuery("SELECT COUNT(*) FROM " + getTableName() + ";");
    }

    public long getLastId() {
        return executeLongResultQuery("SELECT MAX(Id) FROM " + getTableName() + ";");
    }

    protected Boolean cursorToBoolean(final Cursor cursor, final int position) {

        if (cursor.isNull(position)) {
            return null;
        } else {
            return (cursor.getInt(position) == 1);
        }

    }

    protected SqlDate cursorToDate(final Cursor cursor, final int position) {

        if (cursor.isNull(position)) {
            return null;
        } else {
            return new SqlDate(cursor.getString(position));
        }

    }

    protected Integer cursorToInteger(final Cursor cursor, final int position) {

        if (cursor.isNull(position)) {
            return null;
        } else {
            return cursor.getInt(position);
        }

    }

    protected Long cursorToLong(final Cursor cursor, final int position) {

        if (cursor.isNull(position)) {
            return null;
        } else {
            return cursor.getLong(position);
        }

    }

    protected String cursorToString(final Cursor cursor, final int position) {
        return cursor.getString(position);
    }

}
