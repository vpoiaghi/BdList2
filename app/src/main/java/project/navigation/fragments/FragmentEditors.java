package project.navigation.fragments;

import android.widget.BaseAdapter;

import java.util.List;

import bdlist.bdlist.R;
import project.navigation.fragments.abstract_fragments.FragmentList;
import project.navigation.adapters.EditorsAdapter;
import project.donnees.extendedBo.Editor;
import project.services.ServiceEditors;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 27/02/2016.
 *
 */
public class FragmentEditors extends FragmentList {

    private static final ServiceEditors svcEditors = ServicesFactory.get(ServiceEditors.class);

    public FragmentEditors() {
        super();
    }

    @Override
    protected BaseAdapter getAdapter() {
        List<Editor> editorsList = svcEditors.getAll();
        return new EditorsAdapter(getParentActivity(), R.layout.itm_editor, editorsList);
    }

    @Override
    protected BaseAdapter getAdapter(final String filter) {
        List<Editor> EditorsList = svcEditors.getByName(filter);
        return new EditorsAdapter(getParentActivity(), R.layout.itm_editor, EditorsList);
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_editors;
    }

    @Override
    public String getFragmentTitle() {
        return "Editeurs";
    }

}