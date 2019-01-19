package project.services;

import java.util.List;

import project.donnees.extendedBo.Editor;
import project.donnees.extendedBo.Festival;
import project.donnees.dao.DaoFestivalReminder;
import project.donnees.extendedBo.FestivalReminder;
import project.services.abs.Service;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceFestivalReminders extends Service<FestivalReminder, DaoFestivalReminder> {

    protected ServiceFestivalReminders() {
        super();
    }

    public List<FestivalReminder> getByFestival(final Festival festival, final Editor editor) {
        return getDao().getByFestival(festival, editor);
    }


}
