package project.donnees.extendedBo;

import framework.donnees.bo.Bo;
import project.donnees.bo.BoFestival;
import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class Festival extends BoFestival {

    @Override
    public String toString() {
        return getName();
    }

}
