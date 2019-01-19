package project.donnees.extendedBo;

import project.donnees.bo.BoSerie;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class Serie extends BoSerie implements Comparable<Serie> {

    @Override
    public int compareTo(Serie another) {
        return this.getName().compareTo(another.getName());
    }
}
