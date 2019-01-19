package project.donnees.search;

import java.io.Serializable;

import framework.tools.DateUtils;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 08/03/2016.
 */
public class DaoSearchParameters extends SearchParameters implements Serializable {

    private SqlDate today = new SqlDate(DateUtils.getToday());
    private Integer newsDaysCount = 0;

    public DaoSearchParameters() {
        super();
    }

    public DaoSearchParameters(final SearchParameters searchParameters) {
        super();

        this.name = searchParameters.getName();
        this.idEditor = searchParameters.getIdEditor();
        this.kindOfGoody = searchParameters.getKindOfGoody();

        this.inPossession = searchParameters.getInPossession();
        this.wanted = searchParameters.getWanted();
        this.notWanted = searchParameters.getNotWanted();

        this.comings = searchParameters.getComings();
        this.news = searchParameters.getNews();
        this.others = searchParameters.getOthers();

        this.order = searchParameters.getOrder();
    }

    public SqlDate getToday() {
        return today;
    }

    public void setToday(SqlDate today) {
        this.today = today;
    }

    public Integer getNewsDaysCount() {
        return newsDaysCount;
    }

    public void setNewsDaysCount(Integer newsDaysCount) {
        this.newsDaysCount = newsDaysCount;
    }
}
