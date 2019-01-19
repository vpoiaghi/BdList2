package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import framework.donnees.dao.TypedDao;
import project.donnees.extendedBo.Event;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoEvent extends TypedDao<Event> {

    private static int COL_POS = 2;

    public static final String COL_START_DATE = "startDate";
    public static final int POS_START_DATE = COL_POS++;

    public static final String COL_END_DATE = "endDate";
    public static final int POS_END_DATE = COL_POS++;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public static final String COL_PLACE = "place";
    public static final int POS_PLACE = COL_POS++;

    public static final String COL_COMMENTS = "comments";
    public static final int POS_COMMENTS = COL_POS++;

    public static final String COL_STATE = "state";
    public static final int POS_STATE = COL_POS++;

    public static final String COL_REMINDER_DAYS = "reminderDays";
    public static final int POS_REMINDER_DAYS = COL_POS++;

    public static final String COL_PERSISTENCE_DAYS = "persistenceDays";
    public static final int POS_PERSISTENCE_DAYS = COL_POS++;

    public DaoEvent() {
        super();
    }

    @Override
    public String getTableName() {
        return "event";
    }

    @Override
    public void setContentValues(final ContentValues values, final Event event) {

        values.put(COL_START_DATE, event.getStartDate().getValue());
        values.put(COL_END_DATE, event.getEndDate().getValue());
        values.put(COL_NAME, event.getName());
        values.put(COL_PLACE, event.getPlace());
        values.put(COL_COMMENTS, event.getComments());
        values.put(COL_STATE, event.getState());
        values.put(COL_REMINDER_DAYS, event.getReminderDays());
        values.put(COL_PERSISTENCE_DAYS, event.getPersistenceDays());

    }

    protected Event cursorToBo(Cursor cursor) {

        Event bo = new Event();

        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setStartDate(cursorToDate(cursor, POS_START_DATE));
        bo.setEndDate(cursorToDate(cursor, POS_END_DATE));
        bo.setName(cursorToString(cursor, POS_NAME));
        bo.setPlace(cursorToString(cursor, POS_PLACE));
        bo.setComments(cursorToString(cursor, POS_COMMENTS));
        bo.setState(cursorToInteger(cursor, POS_STATE));
        bo.setReminderDays(cursorToInteger(cursor, POS_REMINDER_DAYS));
        bo.setPersistenceDays(cursorToInteger(cursor, POS_PERSISTENCE_DAYS));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_START_DATE + " text not null,"
                + COL_END_DATE + " text,"
                + COL_NAME + " text not null,"
                + COL_PLACE + " text,"
                + COL_COMMENTS + " text,"
                + COL_STATE + " int not null,"
                + COL_REMINDER_DAYS + " int not null,"
                + COL_PERSISTENCE_DAYS + " int not null";
    }

    public List<Event> getAll() {
        return executeRawQuery("SELECT * FROM " + getTableName() + " ORDER BY " + COL_START_DATE + " ASC");
    }

    public Event getById(final long id) {

        List<Event> eventsList = executeRawQuery("SELECT * FROM " + getTableName() + " WHERE " + COL_ID + "=" + id);

        if (eventsList.size() > 0) {
            return eventsList.get(0);
        } else {
            return null;
        }

    }

}
