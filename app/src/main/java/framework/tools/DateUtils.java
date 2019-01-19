package framework.tools;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public abstract class DateUtils {

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public static Date stringToDate(final String strDate) {

        Date date = null;

        if ((strDate != null) && (strDate.length() > 0)) {
            try {
                date = dateFormatter.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public static String dateToString(final Date date) {

        String strDate = null;

        if (date != null) {
            try {
                strDate = dateFormatter.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return strDate;
    }

    /**
     * Date du jour à 0 heure, 0 minute, 0 secondes.
     * @return Date du jour
     */
    public static Date getToday() {

        Calendar cal = new GregorianCalendar(Locale.FRANCE);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();

    }

    /**
     * Date du lendemain à 0 heure, 0 minute, 0 secondes.
     * @return Date du lendemain
     */
    public static Date getTomorrow() {

        Calendar cal = new GregorianCalendar(Locale.FRANCE);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();

    }

    /**
     * Date et heure au moment de l'appel à la méthode.
     * @return Date et heure courante
     */
    public static Date getNow() {

        Calendar cal = new GregorianCalendar(Locale.FRANCE);

        return cal.getTime();

    }

    /**
     * Retourne la date du jour moins N jour.
     * @param daysBeforeCount nombre de jours à soustraire
     * @return la date calculée
     */
    public static Date getDayBeforeToday(final int daysBeforeCount) {
        return getDayBeforeADay(getToday(), daysBeforeCount);
    }

    /**
     * Retourne la date correspondante à une date donnée moins N jour.
     * @param date la date de départ
     * @param daysBeforeCount nombre de jours à soustraire
     * @return la date calculée
     */
    public static Date getDayBeforeADay(final Date date, final int daysBeforeCount) {

        final Calendar c = new GregorianCalendar(Locale.FRANCE);
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, -daysBeforeCount);

        return c.getTime();
    }

    /**
     * Retourne la date correspondante à une date donnée plus N jour.
     * @param date la date de départ
     * @param daysAfterCount nombre de jours à ajouter
     * @return la date calculée
     */
    public static Date getDayAfterADay(final Date date, final int daysAfterCount) {

        final Calendar c = new GregorianCalendar(Locale.FRANCE);
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, daysAfterCount);

        return c.getTime();
    }

    /**
     * Retourne la date correspondante à une date donnée moins N mois.
     * @param date la date de départ
     * @param monthsBeforeCount nombre de moiss à soustraire
     * @return la date calculée
     */
    public static Date getMonthBeforeADay(final Date date, final int monthsBeforeCount) {

        final Calendar c = new GregorianCalendar(Locale.FRANCE);
        c.setTime(date);
        c.add(Calendar.MONTH, -monthsBeforeCount);

        return c.getTime();
    }

    /**
     * Retourne la date du premier jour du mois d'une date donnée.
     * @param date la date de départ
     * @return la date calculée
     */
    public static Date getFirstOfMonth(final Date date) {

        final Calendar c = new GregorianCalendar(Locale.FRANCE);
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, -(c.get(Calendar.DAY_OF_MONTH) - 1));

        return c.getTime();
    }

    /**
     * Indique si une date est incluse dans une période (>= date de début et <= date de fin).
     * @param date date à tester
     * @param firstDate date de début
     * @param lastDate date de fin
     * @return true si incluse, false sinon.
     */
    public static boolean isInclude(final Date date, final Date firstDate, final Date lastDate) {

        final Date d1 = getDayBeforeADay(firstDate, 1);
        final Date d2 = getDayAfterADay(lastDate, 1);

        return (isAfter(date, d1) && isBefore(date, d2));

    }

    /**
     * Indique si la date date1 est strictement avant la date date2.
     * Les informations d'heure, minute, seconde, millisecondes ne sont pas prises en compte.
     * @param date1 date à comparer.
     * @param date2 date à comparer.
     * @return true si date1 est srictement avant date2, false si date1 est égale à date2 ou après date2.
     */
    public static boolean isBefore(final Date date1, final Date date2) {

        boolean result = false;

        final int year1 = getYear(date1);
        final int year2 = getYear(date2);

        if (year1 < year2) {
            result = true;

        } else if (year1 == year2) {

            final int month1 = getMonth(date1);
            final int month2 = getMonth(date2);

            if (month1 < month2) {
                result = true;

            } else if (month1 == month2) {

                final int day1 = getDayOfMonth(date1);
                final int day2 = getDayOfMonth(date2);

                if (day1 < day2) {
                    result = true;
                }
            }
        }

        return result;

    }

    /**
     * Indique si la date date1 est strictement après la date date2.
     * Les informations d'heure, minute, seconde, millisecondes ne sont pas prises en compte.
     * @param date1 date à comparer.
     * @param date2 date à comparer.
     * @return true si date1 est srictement après date2, false si date1 est égale à date2 ou avant date2.
     */
    public static boolean isAfter(final Date date1, final Date date2) {

        boolean result = false;

        final int year1 = getYear(date1);
        final int year2 = getYear(date2);

        if (year1 > year2) {
            result = true;

        } else if (year1 == year2) {

            final int month1 = getMonth(date1);
            final int month2 = getMonth(date2);

            if (month1 > month2) {
                result = true;

            } else if (month1 == month2) {

                final int day1 = getDayOfMonth(date1);
                final int day2 = getDayOfMonth(date2);

                if (day1 > day2) {
                    result = true;
                }
            }
        }

        return result;

    }

    /**
     * Indique si la date date1 est égale la date date2.
     * Les informations d'heure, minute, seconde, millisecondes ne sont pas prises en compte.
     * @param date1 date à comparer.
     * @param date2 date à comparer.
     * @return true si date1 est srictement après date2, false si date1 est égale à date2 ou avant date2.
     */
    public static boolean isSame(final Date date1, final Date date2) {

        boolean result = false;

        if ((date1 == null) && (date2 == null)) {
            result = true;

        } else if ((date1 != null) && (date2 != null)) {
            result = (getYear(date1) == getYear(date2))
                    && (getMonth(date1) == getMonth(date2))
                    && (getDayOfMonth(date1) == getDayOfMonth(date2));
        }

        return result;
    }

    /**
     * Retourne le numéro du jour dans le mois pour la date passée en paramètre.
     * @param date la date
     * @return le numéro du jour dans le mois. Ex : 7 pour 07/06/2015.
     */
    public static int getDayOfMonth(final Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.DAY_OF_MONTH);

    }

    /**
     * Retourne le numéro du mois pour la date passée en paramètre.
     * @param date la date
     * @return le numéro du mois. Ex : 6 pour 07/06/2015.
     */
    public static int getMonth(final Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.MONTH);

    }

    /**
     * Retourne le numéro de l'année pour la date passée en paramètre.
     * @param date la date
     * @return le numéro de l'année. Ex : 2015 pour 07/06/2015.
     */
    public static int getYear(final Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.YEAR);

    }
}
