package project.donnees.bo;

import framework.donnees.bo.Bo;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 18/05/2016.
 */
public class BoEvent extends Bo {

    private SqlDate startDate;
    private SqlDate endDate;
    private String name;
    private String place;
    private String comments;
    private int state;
    private int reminderDays;
    private int persistenceDays;

    public SqlDate getStartDate() {
        return startDate;
    }

    public void setStartDate(SqlDate startDate) {
        this.startDate = startDate;
    }

    public SqlDate getEndDate() {

        if (endDate == null) {
            return getStartDate();
        } else {
            return endDate;
        }
    }

    public void setEndDate(SqlDate endDate) {
        this.endDate = endDate;
    }

    public int getReminderDays() {
        return reminderDays;
    }

    public void setReminderDays(int reminderDays) {
        this.reminderDays = reminderDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPersistenceDays() {
        return persistenceDays;
    }

    public void setPersistenceDays(int persistenceDays) {
        this.persistenceDays = persistenceDays;
    }

}
