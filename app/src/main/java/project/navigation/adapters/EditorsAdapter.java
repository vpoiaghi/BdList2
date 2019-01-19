package project.navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import project.donnees.extendedBo.Editor;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.FragmentEditor;

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class EditorsAdapter extends ArrayAdapter<Editor> {

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<Editor> editorsList;

    public EditorsAdapter(final FragmentManagerActivity activity, final int resource, final List<Editor> editorsList) {
        super(activity, resource, editorsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.editorsList = editorsList;
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

        initRow(row, editorsList.get(position));

        return row;
    }

    private void initRow(final View row, final Editor editor) {

        TextView txt = (TextView) row.findViewById(R.id.itm_editor_name);
        txt.setText(editor.getName());

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_EDITOR, editor.getId());
                parentActivity.showFragment(FragmentEditor.class.getName());
            }
        });
    }
}
