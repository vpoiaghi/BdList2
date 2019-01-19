package framework.donnees.dao;

import android.database.SQLException;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public interface IDao {

    boolean openDatabase() throws SQLException;
    void closeDatabase();

    String getTableName();
    String getCreateRequest();
}
