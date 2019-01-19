package project.navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import project.donnees.extendedBo.Author;
import project.donnees.extendedBo.InSigning;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.FragmentAuthor;
import project.services.ServiceAuthors;
import project.services.FactoryServices;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class InSigningsAdapter extends ArrayAdapter<InSigning> {

    private static final ServiceAuthors svcAuthors = FactoryServices.get(ServiceAuthors.class);

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<InSigning> inSigningsList;

    public InSigningsAdapter(final FragmentManagerActivity activity, final int resource, final List<InSigning> inSigningsList) {
        super(activity, resource, inSigningsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.inSigningsList = inSigningsList;
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

        initRow(row, inSigningsList.get(position));

        return row;
    }

    private void initRow(final View row, final InSigning inSigning) {

        final Author author = svcAuthors.getById(inSigning.getIdAuthor());

        String txt = author.getName();
        final TextView txtAuthorName = (TextView) row.findViewById(R.id.itm_author_txt_name);
        txtAuthorName.setText(txt);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_AUTHOR, author.getId());
                parentActivity.showFragment(FragmentAuthor.class.getName());
            }
        });
    }

}