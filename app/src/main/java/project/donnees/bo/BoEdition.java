package project.donnees.bo;

import framework.donnees.bo.Bo;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class BoEdition extends Bo {

    private Long idEditor;
    private Long idSerie;
    private String idSeries;
    private Long idTitle;
    private String idTitles;
    private String serieName;
    private String serieSearchName;
    private Integer orderNumber;
    private String collection;
    private Integer state;
    private String editionNumber;
    private String isbn;
    private Integer possessionState;
    private Boolean Integeregral;
    private Boolean set;
    private String name;
    private String searchName;
    private SqlDate parutionDate;
    private Integer versionNumber;
    private String specialEdition;
    private Integer boardsCount;
    private Integer printingMax;
    private String boardsColor;
    private String textAuthorName;
    private String drawAuthorName;
    private String colorAuthorName;
    private Boolean withAutograph;
    private Boolean anotherEditions;
    private String editorName;

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

    public Long getIdTitle() {
        return idTitle;
    }

    public void setIdTitle(Long idTitle) {
        this.idTitle = idTitle;
    }

    public String getIdTitles() {
        return idTitles;
    }

    public void setIdTitles(String idTitles) {
        this.idTitles = idTitles;
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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(String editionNumber) {
        this.editionNumber = editionNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPossessionState() {
        return possessionState;
    }

    public void setPossessionState(Integer possessionState) {
        this.possessionState = possessionState;
    }

    public Boolean isIntegral() {
        return Integeregral;
    }

    public void setIntegral(Boolean Integeregral) {
        this.Integeregral = Integeregral;
    }

    public Boolean isSet() {
        return set;
    }

    public void setSet(Boolean set) {
        this.set = set;
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

    public SqlDate getParutionDate() {
        return parutionDate;
    }

    public void setParutionDate(SqlDate parutionDate) {
        this.parutionDate = parutionDate;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getSpecialEdition() {
        return specialEdition;
    }

    public void setSpecialEdition(String specialEdition) {
        this.specialEdition = specialEdition;
    }

    public Integer getBoardsCount() {
        return boardsCount;
    }

    public void setBoardsCount(Integer boardsCount) {
        this.boardsCount = boardsCount;
    }

    public Integer getPrintingMax() {
        return printingMax;
    }

    public void setPrintingMax(Integer printingMax) {
        this.printingMax = printingMax;
    }

    public String getBoardsColor() {
        return boardsColor;
    }

    public void setBoardsColor(String boardsColor) {
        this.boardsColor = boardsColor;
    }

    public String getTextAuthorName() {
        return textAuthorName;
    }

    public void setTextAuthorName(String textAuthorName) {
        this.textAuthorName = textAuthorName;
    }

    public String getDrawAuthorName() {
        return drawAuthorName;
    }

    public void setDrawAuthorName(String drawAuthorName) {
        this.drawAuthorName = drawAuthorName;
    }

    public String getColorAuthorName() {
        return colorAuthorName;
    }

    public void setColorAuthorName(String colorAuthorName) {
        this.colorAuthorName = colorAuthorName;
    }

    public Boolean isWithAutograph() {
        return withAutograph;
    }

    public void setWithAutograph(Boolean withAutograph) {
        this.withAutograph = withAutograph;
    }

    public Boolean hasAnotherEditions() {
        return anotherEditions;
    }

    public void setHasAnotherEditions(Boolean anotherEditions) {
        this.anotherEditions = anotherEditions;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

}