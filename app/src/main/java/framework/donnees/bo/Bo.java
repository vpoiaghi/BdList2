package framework.donnees.bo;

import project.donnees.bo.botypes.SqlDate;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public abstract class Bo {

    // technical id.
    protected Long id = null;

    // timestamp of last modification.
    protected SqlDate tsp;

    // indique si le bo existe en base ou si il s'agit d'un nouveau
    // bo non encore enregistré.
    // Cet attribut ne correspond pas à un champ en base.
    protected boolean inbase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SqlDate getTsp() {
        return tsp;
    }

    public void setTsp(SqlDate tsp) {
        this.tsp = tsp;
    }

    public boolean isInbase() {
        return inbase;
    }

    public void setInbase(boolean inbase) {
        this.inbase = inbase;
    }

}
