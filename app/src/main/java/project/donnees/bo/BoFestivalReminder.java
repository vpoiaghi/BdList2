package project.donnees.bo;

import framework.donnees.bo.Bo;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class BoFestivalReminder extends Bo {

    private Long idFestival;
    private Long idEditor;
    private String name;
    private Integer kind;
    private String comments;

    public Long getIdFestival() {
        return idFestival;
    }

    public void setIdFestival(Long idFestival) {
        this.idFestival = idFestival;
    }

    public Long getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(Long idEditor) {
        this.idEditor = idEditor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
