package project.donnees.wrapper.insigning;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 *
 * Created by VINCENT on 24/12/2018.
 */
public class Period extends Cell implements IPeriod {

    private Date beginning;
    private Date end;

    public Period(@NonNull final Date beginning, @NonNull final Date end) {
        this(null, beginning, end);
    }

    protected Period(final String name, @NonNull final Date beginning, @NonNull final Date end) {
        super(name);
        this.beginning = beginning;
        this.end = end;
    }

    @Override
    public Date getBeginning() {
        return beginning;
    }

    @Override
    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    @Override
    public Date getEnd() {
        return end;
    }

    @Override
    public void setEnd(Date end) {
        this.end = end;
    }

}
