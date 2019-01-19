package project.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import bdlist.bdlist.R;
import framework.tools.DateUtils;
import project.donnees.extendedBo.Autograph;
import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Goody;
import project.donnees.wrapper.GlobalEvent;

/**
 * Created by VINCENT on 12/03/2016.
 *
 */
public abstract class ImageUtils {

    public static void loadImage(final String filePath, final ImageView view) {

        boolean fileLoaded = false;

        if (filePath != null) {
            File file = new File(filePath);

            if (file.exists()) {

                int targetWidth = view.getLayoutParams().width;

                if (targetWidth > 0) {
                    Picasso.with(view.getContext()).load(file).resize(targetWidth, 0).into(view);
                    fileLoaded = true;
                } else {
                    Picasso.with(view.getContext()).load(file).into(view);
                    fileLoaded = true;
                }
            }
        }

        if (!fileLoaded) {
            cleanImage(view);
        }

    }

    public static void loadImage(final Integer resource, final ImageView view) {

        if (resource == null) {
            cleanImage(view);
        } else {
            int targetWidth = 0;

            if (view.getLayoutParams() != null) {
                targetWidth = view.getLayoutParams().width;
            }

            if (targetWidth > 0) {
                Picasso.with(view.getContext()).load(resource).resize(targetWidth, 0).into(view);
            } else {
                Picasso.with(view.getContext()).load(resource).into(view);
            }
        }
    }

    public static void loadEditionFrontCoverImage(final ImageView imgView, final Edition edition) {

        final String filePath = Constantes.PATH_COVERS + String.format(Locale.FRANCE, "%06d", edition.getId()) + ".jpg";
        ImageUtils.loadImage(filePath, imgView);

    }

    public static void loadGoodyImage(final ImageView imgView, final Goody goody) {

        final String filePath = Constantes.PATH_GOODIES + String.format(Locale.FRANCE, "%06d", goody.getId()) + ".jpg";
        ImageUtils.loadImage(filePath, imgView);

    }

    public static void loadEventImage(final ImageView imgView, final GlobalEvent globalEvent) {

        final String filePath = Constantes.PATH_EVENTS + String.format(Locale.FRANCE, "%06d", globalEvent.getId()) + ".jpg";
        ImageUtils.loadImage(filePath, imgView);

    }

    public static void loadEventReminderImage(final ImageView imgView, final GlobalEvent globalEvent) {

        final Integer resourceId;

        final int state = globalEvent.getState();

        if (state == 1) {
            resourceId = null;

        } else {

            final int remenderDays = globalEvent.getReminderDays();

            final Date today = DateUtils.getToday();
            final Date startDate = globalEvent.getStartDate();
            final Date endDate = globalEvent.getEndDate();
            final Date firstOrangeAlertDay = DateUtils.getDayBeforeADay(startDate, remenderDays);
            final Date firstRedAlertDay = DateUtils.getDayBeforeADay(startDate, Math.round(remenderDays * 20 / 100));

            if (DateUtils.isBefore(today, firstOrangeAlertDay)) {
                resourceId = null;
            } else if (DateUtils.isBefore(today, firstRedAlertDay)) {
                resourceId = R.drawable.icn_event_reminder_orange_alert;
            } else if (DateUtils.isBefore(today,startDate)) {
                resourceId = R.drawable.icn_event_reminder_red_alert;
            } else if (DateUtils.isInclude(today, startDate, endDate)) {
                resourceId = R.drawable.icn_event_reminder_in_progress;
            } else {
                resourceId = R.drawable.icn_event_reminder_deprecated;
            }

        }

        loadImage(resourceId, imgView);

    }

    public static void loadAutographImage(final ImageView imgView, final Autograph autograph) {

        final String filePath = Constantes.PATH_AUTOGRAPHS + String.format(Locale.FRANCE, "%05d", autograph.getId()) + "-01.jpg";
        ImageUtils.loadImage(filePath, imgView);
    }

    public static void loadPossessionImage(final ImageView imgView, final Edition edition) {
        loadPossessionImage(imgView, edition.getPossessionState());
    }

    public static void loadPossessionImage(final ImageView imgView, final Goody goody) {
        loadPossessionImage(imgView, goody.getPossessionState());
    }

    public static void loadPossessionImage(final ImageView imgView, final int possesionState) {

        final Integer resourceId;

        switch (possesionState) {
            case PossessionStates.IN_POSSESSION:
                resourceId = R.drawable.icn_possession_in;
                break;
            case PossessionStates.WANTED:
                resourceId = R.drawable.icn_possession_wanted;
                break;
            case PossessionStates.NOT_WANTED:
                resourceId = R.drawable.icn_possession_not_wanted;
                break;
            case PossessionStates.IN_DELIVERY:
                resourceId = R.drawable.icn_possession_delivery;
                break;
            case PossessionStates.RESERVED:
                resourceId = R.drawable.icn_possession_reserved;
                break;
            case PossessionStates.TO_ORDER_AT_BDFUGUE:
                resourceId = R.drawable.icn_possession_to_order_at_bdfugue;
                break;
            case PossessionStates.TO_RESERVE_AT_CULTURA:
                resourceId = R.drawable.icn_possession_to_reserve_at_cultura;
                break;
            case PossessionStates.PRESENT:
                resourceId = R.drawable.icn_possession_present;
                break;
            default:
                resourceId = null;
                break;
        }

        loadImage(resourceId, imgView);
    }

    public static void cleanImage(final ImageView view) {
        view.setImageDrawable(null);
    }

}
