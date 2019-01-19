package project.navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import project.donnees.wrapper.AuthorRole;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.FragmentAuthor;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class AuthorsRolesAdapter extends ArrayAdapter<AuthorRole> {

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<AuthorRole> authorsList;

    public AuthorsRolesAdapter(final FragmentManagerActivity activity, final int resource, final List<AuthorRole> authorsList) {
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

    private void initRow(final View row, final AuthorRole authorRole) {

        TextView txtAuthor = (TextView) row.findViewById(R.id.itm_edition_author_txt_author_name);
        txtAuthor.setText(authorRole.getAuthorName());

        TextView txtRole = (TextView) row.findViewById(R.id.itm_edition_author_txt_author_role);
        txtRole.setText(authorRole.getRole());

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_AUTHOR, authorRole.getIdAuthor());
                parentActivity.showFragment(FragmentAuthor.class.getName());
            }
        });

    }
}
