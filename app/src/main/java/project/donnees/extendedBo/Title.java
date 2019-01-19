package project.donnees.extendedBo;

import framework.donnees.bo.Bo;
import project.donnees.bo.BoTitle;
import project.utils.PossessionStates;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class Title extends BoTitle {

    public int getPossessionState() {

        final int possessionState;

        if (isInPossession()) {
            possessionState = PossessionStates.IN_POSSESSION;
        } else {
            possessionState = PossessionStates.NOT_WANTED;
        }

        return possessionState;
    }

}
