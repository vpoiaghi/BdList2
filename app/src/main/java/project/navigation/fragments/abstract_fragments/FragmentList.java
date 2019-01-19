package project.navigation.fragments.abstract_fragments;

import android.os.Parcelable;
import android.text.Editable;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import project.navigation.Listeners.AfterTextChangedWatcher;

/**
 *
 * Created by VINCENT on 19/04/2016.
 */
public abstract class FragmentList extends AbstractFragment {

    Parcelable listViewState;

    protected abstract BaseAdapter getAdapter();
    protected abstract BaseAdapter getAdapter(final String filter);


    public FragmentList() {
        super(R.layout.abs_frg_list);
    }

    @Override
    protected void loadFragment() {
    }

    @Override
    protected void refreshFragment() {

        EditText filter = (EditText) findViewById(R.id.frg_list_edt_filter);
        filter.addTextChangedListener(new AfterTextChangedWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateList(s.toString());
            }
        });

        updateList(filter.getText().toString());

        if(listViewState != null) {
            ListView listView = (ListView) findViewById(R.id.frg_list_list);
            listView.onRestoreInstanceState(listViewState);
        }
    }

    private void updateList(final String filter) {

        if (filter == null) {
            updateList(getAdapter());
        } else {
            final String f = filter.trim();

            if (f.length() == 0) {
                updateList(getAdapter());
            } else {
                updateList(getAdapter(f));
            }
        }

    }

    private void updateList(final BaseAdapter adapter) {
        ListView listView = (ListView) findViewById(R.id.frg_list_list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        ListView listView = (ListView) findViewById(R.id.frg_list_list);
        listViewState = listView.onSaveInstanceState();
        super.onPause();
    }

}
