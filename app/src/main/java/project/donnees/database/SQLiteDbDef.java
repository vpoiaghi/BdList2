package project.donnees.database;

import android.content.Context;

import java.util.List;

import framework.donnees.dao.IDao;
import framework.donnees.database.DatabaseDefinition;
import project.donnees.dao.DaoAuthor;
import project.donnees.dao.DaoAutograph;
import project.donnees.dao.DaoEdition;
import project.donnees.dao.DaoEditor;
import project.donnees.dao.DaoEvent;
import project.donnees.dao.DaoFestival;
import project.donnees.dao.DaoFestivalReminder;
import project.donnees.dao.DaoGoody;
import project.donnees.dao.DaoInSigning;
import project.donnees.dao.DaoParameters;
import project.donnees.dao.DaoSerie;
import project.donnees.dao.DaoTitle;
import project.utils.Constantes;

/**
 *
 * Created by VINCENT on 14/05/2016.
 */
public class SQLiteDbDef extends DatabaseDefinition {

    private static final int DATABASE_VERSION = 27;
    private static final String DB_FILE_PATH = Constantes.PATH_DATABASE_FILE;

    private static SQLiteDbDef INSTANCE = null;

    private SQLiteDbDef(Context context) {
        super(context, DB_FILE_PATH, DATABASE_VERSION);
    }

    public static synchronized SQLiteDbDef initialize(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new SQLiteDbDef(context);
        }

        return INSTANCE;
    }

    @Override
    public void buildDaosList(final List<IDao> daoList) {

        daoList.add(new DaoParameters());
        daoList.add(new DaoEditor());
        daoList.add(new DaoSerie());
        daoList.add(new DaoEdition());
        daoList.add(new DaoTitle());
        daoList.add(new DaoAuthor());
        daoList.add(new DaoGoody());
        daoList.add(new DaoAutograph());
        daoList.add(new DaoEvent());
        daoList.add(new DaoFestival());
        daoList.add(new DaoInSigning());
        daoList.add(new DaoFestivalReminder());

    }
}
