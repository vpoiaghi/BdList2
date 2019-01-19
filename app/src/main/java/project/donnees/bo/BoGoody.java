package project.donnees.bo;

import java.util.ArrayList;
import java.util.List;

import framework.donnees.bo.Bo;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class BoGoody extends Bo {

    private Long idEditor;
    private Long idSerie;
    private String idSeries;
    private Long idAuthor;
    private String idAuthors;
    private String name;
    private String searchName;
    private String serieName;
    private String serieSearchName;
    private Integer possessionState;
    private SqlDate parutionDate;
    private Boolean withAutograph;
    private String editorName;
    private String kindName;
    private Integer sizeX;
    private Integer sizeY;
    private Integer sizeZ;
    private String copyNumber;
    private Integer copyCount;
    private Integer state;
    private Integer orderNumber;

    public Long getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(Long idEditor) {
        this.idEditor = idEditor;
    }

    public Long getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(Long idSerie) {
        this.idSerie = idSerie;
    }

    public String getIdSeries() {
        return idSeries;
    }

    public void setIdSeries(String idSeries) {
        this.idSeries = idSeries;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSerieName() {
        return serieName;
    }

    public void setSerieName(String serieName) {
        this.serieName = serieName;
    }

    public String getSerieSearchName() {
        return serieSearchName;
    }

    public void setSerieSearchName(String serieSearchName) {
        this.serieSearchName = serieSearchName;
    }

    public Integer getPossessionState() {
        return possessionState;
    }

    public void setPossessionState(Integer possessionState) {
        this.possessionState = possessionState;
    }

    public SqlDate getParutionDate() {
        return parutionDate;
    }

    public void setParutionDate(SqlDate parutionDate) {
        this.parutionDate = parutionDate;
    }

    public Boolean isWithAutograph() {
        return withAutograph;
    }

    public void setWithAutograph(Boolean withAutograph) {
        this.withAutograph = withAutograph;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public Integer getSizeX() {
        return sizeX;
    }

    public void setSizeX(Integer sizeX) {
        this.sizeX = sizeX;
    }

    public Integer getSizeY() {
        return sizeY;
    }

    public void setSizeY(Integer sizeY) {
        this.sizeY = sizeY;
    }

    public Integer getSizeZ() {
        return sizeZ;
    }

    public void setSizeZ(Integer sizeZ) {
        this.sizeZ = sizeZ;
    }

    public String getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(String copyNumber) {
        this.copyNumber = copyNumber;
    }

    public Integer getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(Integer copyCount) {
        this.copyCount = copyCount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

}
