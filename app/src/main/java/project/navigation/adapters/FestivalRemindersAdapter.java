package project.navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import project.donnees.extendedBo.FestivalReminder;
import project.services.ServiceEditors;
import project.services.FactoryServices;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class FestivalRemindersAdapter extends ArrayAdapter<FestivalReminder> {

    private static final ServiceEditors svcEditors = FactoryServices.get(ServiceEditors.class);

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<FestivalReminder> festivalRemindersList;

    public FestivalRemindersAdapter(final FragmentManagerActivity activity, final int resource, final List<FestivalReminder> festivalRemindersList) {
        super(activity, resource, festivalRemindersList);

        this.parentActivity = activity;
        this.resource = resource;
        this.festivalRemindersList = festivalRemindersList;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final View row;

        if (convertView != null) {
            row = convertView;
        } else {
            final LayoutInflater inflater = parentActivity.getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
        }

        initRow(row, festivalRemindersList.get(position));

        return row;
    }

    private void initRow(final View row, final FestivalReminder festivalReminder) {

        // Reminder name
        String txt = festivalReminder.getName();
        final TextView txtName = (TextView) row.findViewById(R.id.itm_festival_reminder_txt_name);
        txtName.setText(txt);

        // Reminder editor
        Long idEditor = festivalReminder.getIdEditor();
        final TextView txtEditor = (TextView) row.findViewById(R.id.itm_festival_reminder_txt_editor);
        if (idEditor == null) {
            txtEditor.setText("");
            txtEditor.setVisibility(View.GONE);
        } else {
            txtEditor.setText(svcEditors.getById(idEditor).getName());
            txtEditor.setVisibility(View.VISIBLE);
        }

        // Reminder comments
        final TextView txtComments = (TextView) row.findViewById(R.id.itm_festival_reminder_txt_comments);
        txtComments.setText(festivalReminder.getComments());

        // Reminder image type
        final ImageView img = (ImageView) row.findViewById(R.id.itm_festival_reminder_img);
        switch (festivalReminder.getKind()) {
            case FestivalReminder.FESTIVAL_REMINDER_SPOT:
                img.setImageResource(R.drawable.icn_spot);
                break;
            case FestivalReminder.FESTIVAL_REMINDER_QUESTION:
                img.setImageResource(R.drawable.icn_question);
                break;
            case FestivalReminder.FESTIVAL_REMINDER_EXHIBITION:
                img.setImageResource(R.drawable.icn_exhibition);
                break;
        }

    }

}