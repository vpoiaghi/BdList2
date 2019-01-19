package project.donnees.wrapper;

import java.util.ArrayList;
import java.util.List;

import project.donnees.bo.botypes.SqlDate;

/**
 * Created by VINCENT on 16/12/2017.
 */
public class PressItemsList {

    public PressItemsList(SqlDate parutionDate) {
        this.parutionDate = parutionDate;
    }

    private SqlDate parutionDate;
    private List<PressItem> pressItemsList = new ArrayList<>();

    public SqlDate getParutionDate() {return parutionDate;}
    public void setParutionDate(SqlDate parutionDate) {this.parutionDate = parutionDate;}

    public List<PressItem> getPressItemsList() {return pressItemsList;}
    public void setPressItemsList(List<PressItem> pressItemsList) {this.pressItemsList = pressItemsList;}

}
