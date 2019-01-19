package project.donnees.wrapper;

import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Goody;
import project.donnees.bo.botypes.SqlDate;

/**
 * Created by VINCENT on 16/12/2017.
 *
 */
public class PressItem {

    private Long idSerie;
    private Long idEditor;

    private Edition edition;
    private Goody goody;

    private String itemName;
    private String serieName;
    private String editorName;
    private SqlDate parutionDate;
    private Integer possessionState;

    public Long getIdSerie() {
        return idSerie;
    }
    public void setIdSerie(Long idSerie) {
        this.idSerie = idSerie;
    }

    public Edition getEdition() {return edition;}
    public void setEdition(Edition edition) {this.edition = edition;}

    public Goody getGoody() {return goody;}
    public void setGoody(Goody goody) {this.goody = goody;}

    public Long getIdEditor() {return idEditor;}
    public void setIdEditor(Long idEditor) {this.idEditor = idEditor;}

    public String getItemName() {return itemName;}
    public void setItemName(String itemName) {this.itemName = itemName;}

    public String getSerieName() {return serieName;}
    public void setSerieName(String serieName) {this.serieName = serieName;}

    public String getEditorName() {return editorName;}
    public void setEditorName(String editorName) {this.editorName = editorName;}

    public SqlDate getParutionDate() {return parutionDate;}
    public void setParutionDate(SqlDate parutionDate) {this.parutionDate = parutionDate;}

    public Integer getPossessionState() {return possessionState;}
    public void setPossessionState(Integer possessionState) {this.possessionState = possessionState;}
}
