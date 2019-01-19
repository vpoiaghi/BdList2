package project.donnees.bo;

import framework.donnees.bo.Bo;
import project.utils.PossessionStates;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class BoTitle extends Bo {

    Long idSerie;
    String name;
    Integer orderNumber;
    String story;
    Boolean outSerie;
    Boolean inPossession;

    public Long getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(Long idSerie) {
        this.idSerie = idSerie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Boolean isOutSerie() {
        return outSerie;
    }

    public void setOutSerie(Boolean outSerie) {
        this.outSerie = outSerie;
    }

    public Boolean isInPossession() {
        return inPossession;
    }

    public void setInPossession(Boolean inPossession) {
        this.inPossession = inPossession;
    }

}
