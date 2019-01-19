package project.navigation.fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import project.donnees.search.SearchAuthorsParameters;
import project.navigation.components.ImageViewButton;
import project.navigation.constants.ParametersCodes;
import project.utils.StringUtils;

/**
 *
 * Created by VINCENT on 27/02/2016.
 */
public class FragmentSearchAuthors extends AbstractFragment {

    private SearchAuthorsParameters searchParameters = null;

    public FragmentSearchAuthors() {
        super(R.layout.frg_search_authors);
    }

    @Override
    protected void loadFragment() {

        loadParameters();

    }

    @Override
    protected void refreshFragment() {

        if (searchParameters != null) {
            TextView txtAuthorOrSociety = (TextView) findViewById(R.id.search_txt_authororsociety);
            txtAuthorOrSociety.setText(searchParameters.getRealName());
        }

        initButtons();
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_authors;
    }

    @Override
    public String getFragmentTitle() {
        return "Auteurs";
    }


    private void loadParameters() {

        searchParameters = (SearchAuthorsParameters) getParameterIn(ParametersCodes.SEARCH_AUTHORS_PARAMETERS);

    }

    private void initButtons() {

        ImageViewButton btnStartSearch = (ImageViewButton) findViewById(R.id.search_img_search);
        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });

        ImageViewButton btnBean = (ImageViewButton) findViewById(R.id.search_img_bean);
        btnBean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();

            }
        });
    }

    private void clearAll() {
        ((EditText) findViewById(R.id.search_txt_authororsociety)).setText(null);
    }

    private void showResult() {

        final SearchAuthorsParameters searchParameters = new SearchAuthorsParameters();

        final String name = ((EditText) findViewById(R.id.search_txt_authororsociety)).getText().toString();
        if (name.length() > 0) {
            searchParameters.setRealName(name);
            searchParameters.setName(StringUtils.ToSearchString(name));
        }

        addParameterOut(ParametersCodes.SEARCH_AUTHORS_PARAMETERS, searchParameters);
        getParentActivity().showFragment(FragmentSearchAuthorsResult.class.getName());
    }

}