package project.donnees.bo;

import framework.donnees.bo.Bo;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class BoFestival extends Bo {

    String name;
    SqlDate beginDate;
    SqlDate endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SqlDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(SqlDate beginDate) {
        this.beginDate = beginDate;
    }

    public SqlDate getEndDate() {
        return endDate;
    }

    public void setEndDate(SqlDate endDate) {
        this.endDate = endDate;
    }
}
