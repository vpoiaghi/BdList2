package project.donnees.wrapper.insigning;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by VINCENT on 25/12/2018.
 *
 */
public abstract class UsedPeriod extends Period {

    protected UsedPeriod(@NonNull final String name, @NonNull final Date beginning, @NonNull final Date end) {
        super(name, beginning, end);
    }

    public String getComments() {
        return null;
    }

}
