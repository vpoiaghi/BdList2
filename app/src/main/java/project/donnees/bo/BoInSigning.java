package project.donnees.bo;

import framework.donnees.bo.Bo;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 18/05/2016.
 */
public class BoInSigning extends Bo {

    private Long idFestival;
    private Long idAuthor;
    private Long idEditor;
    private SqlDate begin;
    private SqlDate end;
    private String comments;
    private String idListEditions;

    public Long getIdFestival() {
        return idFestival;
    }

    public void setIdFestival(Long idFestival) {
        this.idFestival = idFestival;
    }

    public Long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public Long getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(Long idEditor) {
        this.idEditor = idEditor;
    }

    public SqlDate getBegin() {
        return begin;
    }

    public void setBegin(SqlDate begin) {
        this.begin = begin;
    }

    public SqlDate getEnd() {
        return end;
    }

    public void setEnd(SqlDate end) {
        this.end = end;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getIdListEditions() {
        return idListEditions;
    }

    public void setIdListEditions(String idListEditions) {
        this.idListEditions = idListEditions;
    }
}
