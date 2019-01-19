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

/**
 *
 * Created by VINCENT on 28/02/2016.
 */
public class EditorSearchFilterAdapter extends ArrayAdapter<Editor> {

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<Editor> editorsList;

    public EditorSearchFilterAdapter(final FragmentManagerActivity activity, final int resource, final List<Editor> editorsList) {
        super(activity, resource, editorsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.editorsList = editorsList;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, final View convertView, final ViewGroup parent) {

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

        TextView txt = (TextView) row.findViewById(R.id.itm_editor_search_filter_name);
        txt.setText(editor.getName());
    }

}
