package project.donnees.extendedBo;

import project.donnees.bo.BoFestivalReminder;

/**
 * Created by VINCENT on 16/12/2018.
 *
 */
public class FestivalReminder extends BoFestivalReminder {

    public static final int FESTIVAL_REMINDER_SPOT = 1; // Opération marketing, chose à voir ou vérifier
    public static final int FESTIVAL_REMINDER_QUESTION = 2; // Une question aux vendeurs du stand
    public static final int FESTIVAL_REMINDER_EXHIBITION = 3;   // Exposition à voir

    public Integer getKind() {
        return super.getKind();
    }

    public void setKind(Integer kind) {

        if ((kind == null)
                || (kind == FESTIVAL_REMINDER_SPOT)
                || (kind == FESTIVAL_REMINDER_QUESTION)
                || (kind == FESTIVAL_REMINDER_EXHIBITION)) {
            super.setKind(kind);
        }
    }

}
