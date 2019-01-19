package project.donnees.wrapper.insigning;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VINCENT on 23/12/2018.
 *
 */
public class PeriodsRow {

    final private List<IPeriod> periods;
    final private String name;

    public PeriodsRow(@NonNull final String name) {
        this.name = name;
        periods = new ArrayList<>();
    }

    public List<IPeriod> getPeriods() {
        return periods;
    }

    public String getName() {
        return name;
    }

    public void add(IPeriod period) {
        periods.add(period);
    }
}
