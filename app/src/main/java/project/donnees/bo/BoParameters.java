package project.donnees.bo;

import framework.donnees.bo.Bo;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 09/03/2016.
 */
public class BoParameters extends Bo {

    private Integer newsDaysCount;
    private String appVersion;
    private SqlDate dataUpdateDate;
    private SqlDate firstComingDate;
    private Boolean useDefaultComingDate;
    private Integer eventsPastDaysCount;

    public Integer getNewsDaysCount() {
        return newsDaysCount;
    }

    public void setNewsDaysCount(Integer newsDaysCount) {
        this.newsDaysCount = newsDaysCount;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public SqlDate getDataUpdateDate() {
        return dataUpdateDate;
    }

    public void setDataUpdateDate(SqlDate dataUpdateDate) {
        this.dataUpdateDate = dataUpdateDate;
    }

    public SqlDate getFirstComingDate() {
        return firstComingDate;
    }

    public void setFirstComingDate(SqlDate firstComingDate) {
        this.firstComingDate = firstComingDate;
    }

    public Boolean getUseDefaultComingDate() {
        return useDefaultComingDate;
    }

    public void setUseDefaultComingDate(Boolean useDefaultComingDate) {
        this.useDefaultComingDate = useDefaultComingDate;
    }

    public Integer getEventsPastDaysCount() {
        return eventsPastDaysCount;
    }

    public void setEventsPastDaysCount(Integer eventsPastDaysCount) {
        this.eventsPastDaysCount = eventsPastDaysCount;
    }
}
