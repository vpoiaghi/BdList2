package project.donnees.search;

import java.io.Serializable;


/**
 *
 * Created by VINCENT on 08/03/2016.
 */
public class SearchParameters implements Serializable {

    protected String name = null;
    protected String realName = null;
    protected Long idEditor = null;
    protected String kindOfGoody = null;
    protected Long idSerie = null;

    protected boolean inPossession = true;
    protected boolean wanted = true;
    protected boolean notWanted = true;

    protected boolean comings = true;
    protected boolean news = true;
    protected boolean others = true;

    protected SearchOrder order;


    public static SearchParameters getNewsParameters() {
        SearchParameters sp = new SearchParameters();
        sp.setComings(false);
        sp.setOthers(false);
        sp.setOrder(SearchOrder.ParutionDateAsc);
        return sp;
    }

    public static SearchParameters getComingsParameters() {
        SearchParameters sp = new SearchParameters();
        sp.setNews(false);
        sp.setOthers(false);
        sp.setOrder(SearchOrder.ParutionDateDesc);
        return sp;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(Long idEditor) {
        this.idEditor = idEditor;
    }

    public String getKindOfGoody() {
        return kindOfGoody;
    }

    public void setKindOfGoody(String kindOfGoody) {
        this.kindOfGoody = kindOfGoody;
    }

    public Long getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(Long idSerie) {
        this.idSerie = idSerie;
    }

    public boolean getInPossession() {
        return inPossession;
    }

    public void setInPossession(boolean inPossession) {
        this.inPossession = inPossession;
    }

    public boolean getWanted() {
        return wanted;
    }

    public void setWanted(boolean wanted) {
        this.wanted = wanted;
    }

    public boolean getNotWanted() {
        return notWanted;
    }

    public void setNotWanted(boolean notWanted) {
        this.notWanted = notWanted;
    }

    public boolean getComings() {
        return comings;
    }

    public void setComings(boolean comings) {
        this.comings = comings;
    }

    public boolean getNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public boolean getOthers() {
        return others;
    }

    public void setOthers(boolean others) {
        this.others = others;
    }

    public SearchOrder getOrder() {
        return order;
    }

    public void setOrder(SearchOrder order) {
        this.order = order;
    }

}
