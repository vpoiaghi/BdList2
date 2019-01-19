package project.navigation.fragments;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import framework.fragments.AbstractFragment;
import project.donnees.extendedBo.Author;
import project.donnees.extendedBo.Goody;
import project.donnees.search.SearchAuthorsParameters;
import project.navigation.adapters.AuthorsAdapter;
import project.navigation.adapters.SocietiesAdapter;
import project.navigation.constants.ParametersCodes;
import project.services.ServiceAuthors;
import project.services.factory.ServicesFactory;

/**
 *
 * Created by VINCENT on 28/03/2016.
 */
public class FragmentSearchAuthorsResult extends AbstractFragment {

    private static final ServiceAuthors svcAuthor = ServicesFactory.get(ServiceAuthors.class);

    private static final int INDEX_AUTHORS = 0;
    private static final int INDEX_SOCIETIES = 1;

    private final List<List> dataLists = new ArrayList<>();
    private final List<BaseAdapter> adapters = new ArrayList<>();
    private final List<String> btnText = new ArrayList<>();
    private final List<Integer> btnTextId = new ArrayList<>();

    public FragmentSearchAuthorsResult() {
        super(R.layout.frg_search_authors_result);
    }

    @Override
    protected void loadFragment() {

        initializeDataLists();
        initializeAdapters();
    }

    @Override
    protected void refreshFragment() {

        initializeButtons();
        initializeViewList();
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_authors;
    }

    @Override
    public String getFragmentTitle() {
        return "Recherche";
    }

    private void initializeDataLists() {

        final SearchAuthorsParameters searchParameters = getSearchParameters();

        dataLists.clear();

        final List<Author> authorsList = svcAuthor.search(searchParameters);

        final List<Goody> societiesList = new ArrayList<Goody>();

        dataLists.add(authorsList);
        dataLists.add(societiesList);
    }

    private void initializeAdapters() {

        adapters.clear();

        FragmentManagerActivity parentActivity = getParentActivity();

        adapters.add(new AuthorsAdapter(parentActivity, R.layout.itm_author, dataLists.get(INDEX_AUTHORS)));
        adapters.add(new SocietiesAdapter(parentActivity, R.layout.itm_society, dataLists.get(INDEX_SOCIETIES)));
    }

    private void initializeButtons() {

        btnText.clear();
        btnText.add("Auteurs");
        btnText.add("Sociétés");

        btnTextId.clear();
        btnTextId.add(R.id.searchresult_btn_authors);
        btnTextId.add(R.id.searchresult_btn_societies);

        for (int i = 0; i < dataLists.size(); i++) {
            initializeButton(i);
        }

    }

    private void initializeButton(final int listIndex) {

        final List list = dataLists.get(listIndex);
        final int textViewId = btnTextId.get(listIndex);
        final String text = btnText.get(listIndex);

        final int s = list.size();
        final String txt = s + " " + text;
        final TextView txtView = (TextView) findViewById(textViewId);

        txtView.setText(txt);

        if (s > 0) {
            txtView.setTextColor(Color.BLACK);
            txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showList(listIndex);
                }
            });
        } else {
            txtView.setTextColor(Color.LTGRAY);
        }

    }

    private void initializeViewList() {

        Integer tabIndex = (Integer) getParameterIn(ParametersCodes.SEARCH_AUTHORS_RESULT_SELECTED_TAB_INDEX);
        if (tabIndex == null) {
            tabIndex = 0;
        }

        final int listsCount = dataLists.size();
        final List<Integer> priorityList = new ArrayList<>();

        int listIndex;

        for (listIndex = 0; listIndex < listsCount; listIndex++) {
            priorityList.add(listIndex);
        }

        for (listIndex = tabIndex; listIndex > 0; listIndex--) {
            priorityList.set(listIndex, priorityList.get(listIndex - 1));
        }
        priorityList.set(0, tabIndex);

        boolean canShowList = false;
        listIndex = 0;

        while ((listIndex < listsCount) && (!canShowList)) {

            if (dataLists.get(priorityList.get(listIndex)).size() > 0) {
                canShowList = true;
            } else {
                listIndex++;
            }
        }

        if (!canShowList) {
            final ListView searchresultsListView = (ListView) findViewById(R.id.searchresult_list);
            searchresultsListView.setVisibility(View.INVISIBLE);
            addParameterOut(ParametersCodes.SEARCH_AUTHORS_RESULT_SELECTED_TAB_INDEX, null);
        } else {
            showList(priorityList.get(listIndex));
        }

    }

    protected SearchAuthorsParameters getSearchParameters() {
        return (SearchAuthorsParameters) getParameterIn(ParametersCodes.SEARCH_AUTHORS_PARAMETERS);
    }

    private void showList(final int selectedListIndex) {

        for (int listIndex = 0; listIndex < dataLists.size(); listIndex++) {

            if (listIndex == selectedListIndex) {

                refreshList(adapters.get(listIndex));

                TextView txt = ((TextView) findViewById(btnTextId.get(listIndex)));

                SpannableString uText = new SpannableString(txt.getText());
                uText.setSpan(new UnderlineSpan(), 0, txt.length(), 0);

                txt.setText(uText);

                addParameterOut(ParametersCodes.SEARCH_AUTHORS_RESULT_SELECTED_TAB_INDEX, listIndex);

            } else if (dataLists.get(listIndex).size() > 0) {

                TextView txt = ((TextView) findViewById(btnTextId.get(listIndex)));
                txt.setText(txt.getText().toString());
            }

        }
    }

    private void refreshList(final BaseAdapter adapter) {

        final ListView searchresultsListView = (ListView) findViewById(R.id.searchresult_list);
        searchresultsListView.setVisibility(View.VISIBLE);
        searchresultsListView.setAdapter(adapter);

    }
}
