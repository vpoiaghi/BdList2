package framework.donnees.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import framework.donnees.dao.IDao;

/**
 *
 * Created by VINCENT on 14/05/2016.
 */
public abstract class DatabaseDefinition {

    private MySQLiteHelper dbHelper;

    protected abstract void buildDaosList(final List<IDao> daosList);

    protected DatabaseDefinition(final Context context, final String dbFilePath, final int dbVersion) {

        dbHelper = MySQLiteHelper.initInstance(context, dbFilePath, dbVersion);

        final List<IDao> daoList = new ArrayList();
        buildDaosList(daoList);

        dbHelper.setDaoList(daoList);

    }

    public void finalize() {
        dbHelper = null;
    }

}
