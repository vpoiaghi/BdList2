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
import project.donnees.extendedBo.Author;
import project.donnees.extendedBo.Autograph;
import project.navigation.fragments.FragmentImageAutograph;
import project.navigation.constants.ParametersCodes;
import project.services.ServiceAuthors;
import project.services.factory.ServicesFactory;
import project.utils.ImageUtils;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class AutographsAdapter extends ArrayAdapter<Autograph> {

    private static final ServiceAuthors svcAuthors = ServicesFactory.get(ServiceAuthors.class);

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<Autograph> autographsList;

    public AutographsAdapter(final FragmentManagerActivity activity, final int resource, final List<Autograph> autographsList) {
        super(activity, resource, autographsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.autographsList = autographsList;
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

        initRow(row, autographsList.get(position));

        return row;
    }

    private void initRow(final View row, final Autograph autograph) {

        Long idAuthor = autograph.getIdAuthor();
        Author author = svcAuthors.getById(idAuthor);

        String s = author.toString();
        TextView txtAuthor = (TextView) row.findViewById(R.id.autograph_txt_author);
        txtAuthor.setText(s);

        s = autograph.getEvent();
        TextView txtEvent = (TextView) row.findViewById(R.id.autograph_txt_event);
        txtEvent.setText(s);

        s = "";
        if (autograph.getDate() != null) {
            s += autograph.getDate().toString();
        }
        s += " - ";
        if (autograph.getPlace() != null) {
            s += autograph.getPlace();
        }
        TextView txtDateAndPlace = (TextView) row.findViewById(R.id.autograph_txt_date_and_place);
        txtDateAndPlace.setText(s);

        s = autograph.getComments();
        if (s == null) { s = ""; }
        TextView txtComments = (TextView) row.findViewById(R.id.autograph_txt_comments);
        txtComments.setText(s);

        addFrontPicture(row, autograph);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_AUTOGRAPH, autograph.getId());
                parentActivity.showFragment(FragmentImageAutograph.class.getName());
            }
        });
    }

    private void addFrontPicture(final View row, final Autograph autograph) {

        ImageView imgAutograph = (ImageView) row.findViewById(R.id.autograph_img);
        ImageUtils.loadAutographImage(imgAutograph, autograph);


    }

}
