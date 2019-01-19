package project.donnees.bo;

import framework.donnees.bo.Bo;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 24/04/2016.
 */
public class BoAutograph extends Bo {

    private Long idEdition;
    private Long idGoody;
    private Long idAuthor;
    private String idAuthors;
    private SqlDate date;
    private String place;
    private String event;
    private String comments;

    public Long getIdEdition() {
        return idEdition;
    }

    public void setIdEdition(Long idEdition) {
        this.idEdition = idEdition;
    }

    public Long getIdGoody() {
        return idGoody;
    }

    public void setIdGoody(Long idGoody) {
        this.idGoody = idGoody;
    }

    public Long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getIdAuthors() {
        return idAuthors;
    }

    public void setIdAuthors(String idAuthors) {
        this.idAuthors = idAuthors;
    }

    public SqlDate getDate() {
        return date;
    }

    public Long getAutographDateToTime() {
        return getDate().getTime();
    }

    public void setDate(SqlDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
