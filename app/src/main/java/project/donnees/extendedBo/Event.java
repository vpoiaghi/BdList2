package project.donnees.extendedBo;

import project.donnees.bo.BoEvent;

/**
 * Created by VINCENT on 28/10/2017.
 * Convert an specific state edition or goody, or basic event in usable event for events fragment
 */
public class Event extends BoEvent {

    @Override
    public String toString() {
        return getName();
    }

}
