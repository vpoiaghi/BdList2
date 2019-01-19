package framework.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by VINCENT on 25/12/2018.
 * 
 */
public class DateTimeUtils {

    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);

    public static boolean eq(final Date d1, final Date d2) {
        return (compare(d1, d2) == 0);
    }

    public static boolean gt(final Date d1, final Date d2) {
        return (compare(d1, d2) > 0);
    }

    public static boolean st(final Date d1, final Date d2) {
        return (compare(d1, d2) < 0);
    }

    public static boolean gte(final Date d1, final Date d2) {
        return (compare(d1, d2) >= 0);
    }

    public static boolean ste(final Date d1, final Date d2) {
        return (compare(d1, d2) <= 0);
    }

    // Compare deux date sans tenir compte des millisecondes
    // Retourne
    //    >0 si d1 > d2
    //    <0 si d1 < d2
    //    =0 si d1 = d2
    private static int compare(final Date date1, final Date date2) {

        int result = 0;

        GregorianCalendar gc1 = new GregorianCalendar(Locale.FRANCE);
        gc1.setTime(date1);

        GregorianCalendar gc2 = new GregorianCalendar(Locale.FRANCE);
        gc2.setTime(date2);

        int y1 = gc1.get(Calendar.YEAR);
        int y2 = gc2.get(Calendar.YEAR);

        if (y1 == y2 ) {
            int M1 = gc1.get(Calendar.MONTH);
            int M2 = gc2.get(Calendar.MONTH);

            if (M1 == M2 ) {
                int d1 = gc1.get(Calendar.DAY_OF_MONTH);
                int d2 = gc2.get(Calendar.DAY_OF_MONTH);

                if (d1 == d2 ) {
                    int h1 = gc1.get(Calendar.HOUR_OF_DAY);
                    int h2 = gc2.get(Calendar.HOUR_OF_DAY);

                    if (h1 == h2 ) {
                        int m1 = gc1.get(Calendar.MINUTE);
                        int m2 = gc2.get(Calendar.MINUTE);

                        if (m1 == m2 ) {
                            int s1 = gc1.get(Calendar.SECOND);
                            int s2 = gc2.get(Calendar.SECOND);

                            if (s1 == s2 ) {
                                result = 0;
                            } else {
                                result = s1 - s2;
                            }
                        } else {
                            result = m1 - m2;
                        }
                    } else {
                        result = h1 - h2;
                    }
                } else {
                    result = d1 - d2;
                }
            } else {
                result = M1 - M2;
            }
        } else {
            result = y1 - y2;
        }

        return result;
    }

    /**
     * Retourne les heures pour la datetime passée en paramètre.
     * @param date la date (date + heure)
     * @return les heures. Ex : 16 pour 07/06/2015 16:32:57.
     */
    public static int getHours(final Date date) {

        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTime(date);

        return cal.get(Calendar.HOUR_OF_DAY);

    }

    /**
     * Retourne les minutes pour la datetime passée en paramètre.
     * @param date la date (date + heure)
     * @return les minutes. Ex : 32 pour 07/06/2015 16:32:57.
     */
    public static int getMinutes(final Date date) {

        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTime(date);

        return cal.get(Calendar.MINUTE);

    }

    /**
     * Retourne les secondes pour la datetime passée en paramètre.
     * @param date la date (date + heure)
     * @return les secondes. Ex : 57 pour 07/06/2015 16:32:57.
     */
    public static int getSeconds(final Date date) {

        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTime(date);

        return cal.get(Calendar.SECOND);

    }

    public static String dateTimeToString(final Date date) {

        String strDate = null;

        if (date != null) {
            try {
                strDate = dateTimeFormatter.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return strDate;
    }

}
