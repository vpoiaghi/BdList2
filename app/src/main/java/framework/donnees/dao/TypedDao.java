package framework.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import framework.donnees.bo.Bo;
import framework.tools.DateUtils;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public abstract class TypedDao<BoType extends Bo> extends Dao {

    private final HashMap<Long, BoType> hBo = new HashMap<>();

    // Technical id
    public static final String COL_ID = "id";
    protected static final int POS_ID = 0;

    // Timestamp of last modification
    public static final String COL_TSP = "tsp";
    protected static final int POS_TSP = 1;

    protected abstract void setContentValues(final ContentValues values, final BoType bo);
    protected abstract BoType cursorToBo(Cursor cursor);

    protected TypedDao() {
        super();
    }

    protected List<BoType> executeRawQuery(final String query) {

        final List<BoType> boList = new ArrayList<>();

        final boolean alreadyOpened = openDatabase();
        final Cursor cursor = doRawQuery(query);

        BoType bo;

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            bo = cursorToBo(cursor);
            bo.setInbase(true);
            boList.add(bo);
            hBo.put(bo.getId(), bo);
            cursor.moveToNext();
        }

        cursor.close();

        if (!alreadyOpened) {
            closeDatabase();
        }

        return boList;

    }

    public long save(final BoType bo) {

        final ContentValues values = new ContentValues();
        final long id;

        values.put(TypedDao.COL_TSP, new SqlDate(DateUtils.getNow()).getValue());
        setContentValues(values, bo);

        if (bo.isInbase()) {
            id = bo.getId();
            doUpdate(values, id);
        } else {
            final String tableName = getTableName();
            id = getNewId(tableName);
            values.put(TypedDao.COL_ID, id);
            doInsert(values);
        }

        bo.setInbase(true);
        hBo.put(bo.getId(), bo);

        return id;
    }

    private void doUpdate(final ContentValues values, long id) {

        final String whereClause = "(" + COL_ID + "=" + id + ")";

        super.doUpdate(values, whereClause, null);

    }

    @Override
    public String getCreateRequest() {

        return "create table "
                + getTableName() + "("
                + COL_ID + " long primary key, "
                + COL_TSP + " text not null, "
                + getColumnsCreateRequest().trim()
                + ");";
    }

    public BoType getById(final long id) {

        BoType bo = hBo.get(id);

        if (bo == null) {

            List<BoType> resultList = executeRawQuery("SELECT * FROM " + getTableName() + " WHERE id=" + id);

            if ((resultList != null) && (resultList.size() > 0)) {
                bo = resultList.get(0);
                hBo.put(id, bo);
            }
        }

        return bo;
    }

    public List<BoType> getById(final List<Long> idList) {

        final List<BoType> resultList = new ArrayList<>();

        if (idList != null && idList.size() > 0) {

            final List<Long> unknownBoIdList = new ArrayList<>();
            BoType bo;

            for (Long id : idList) {
                bo = hBo.get(id);

                if (bo == null) {
                    unknownBoIdList.add(id);
                }
            }

            if (unknownBoIdList.size() > 0) {

                int index;
                int startIndex = 0;
                int endIndex = 399;
                int listLastIndex = unknownBoIdList.size() - 1;

                StringBuffer stringIds;

                boolean ended = false;

                while (!ended) {

                    stringIds = new StringBuffer();

                    if (listLastIndex < endIndex) {
                        endIndex = listLastIndex;
                        ended = true;
                    }

                    for (index = startIndex; index <= endIndex; index++) {
                        stringIds.append(",").append(unknownBoIdList.get(index));
                    }

                    resultList.addAll(executeRawQuery("SELECT * FROM " + getTableName() + " WHERE " + COL_ID + " IN (" + stringIds.toString().substring(1) + ")"));

                    startIndex += 400;
                    endIndex += 400;
                }

                for (BoType boResult : resultList) {
                    hBo.put(boResult.getId(), boResult);
                }

                resultList.clear();
            }

            for (Long id : idList) {
                resultList.add(hBo.get(id));
            }

        }

        return resultList;
    }

    public List<BoType> getAll() {

        final List<BoType> resultList = executeRawQuery("SELECT * FROM " + getTableName());
        for (BoType bo : resultList) {
            hBo.put(bo.getId(), bo);
        }

        return resultList;
    }

    private long getNewId(final String tableName) {
        long lastId = executeLongResultQuery("SELECT MAX(Id) FROM " + tableName);
        return lastId + 1;
    }

}
