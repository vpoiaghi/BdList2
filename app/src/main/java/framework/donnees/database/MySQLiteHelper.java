package framework.donnees.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import framework.donnees.dao.IDao;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private List<IDao> daoList = null;

    private static MySQLiteHelper INSTANCE;

    private MySQLiteHelper(final Context context, final String dbFilePath, final int dbVersion) {
        super(context, dbFilePath, null, dbVersion);
    }

    public static MySQLiteHelper initInstance(final Context context, final String dbFilePath, final int dbVersion) {
        INSTANCE = new MySQLiteHelper(context, dbFilePath, dbVersion);
        return INSTANCE;
    }

    public static MySQLiteHelper getInstance() throws NullPointerException {

        if (INSTANCE == null) {
            throw new NullPointerException("MySQLiteHelper n'a pa été initialisé.");
        }

        return INSTANCE;
    }

    public void setDaoList(final List<IDao> daoList) {
        this.daoList = daoList;
    }

    @Override
    public void onCreate(final android.database.sqlite.SQLiteDatabase database) {

        for (IDao dao : daoList) {
            database.execSQL(dao.getCreateRequest());
        }

    }

    @Override
    public void onUpgrade(final android.database.sqlite.SQLiteDatabase db, final int oldVersion, final int newVersion) {

        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        for (IDao dao : daoList) {
            db.execSQL("DROP TABLE IF EXISTS " + dao.getTableName());
        }

        onCreate(db);
    }
}
