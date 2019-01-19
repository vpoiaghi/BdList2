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
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.FragmentAuthor;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class AuthorsAdapter extends ArrayAdapter<Author> {

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<Author> authorsList;

    public AuthorsAdapter(final FragmentManagerActivity activity, final int resource, final List<Author> authorsList) {
        super(activity, resource, authorsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.authorsList = authorsList;
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

        initRow(row, authorsList.get(position));

        return row;
    }

    private void initRow(final View row, final Author author) {

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