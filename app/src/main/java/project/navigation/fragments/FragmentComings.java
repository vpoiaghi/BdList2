package project.navigation.fragments;

import bdlist.bdlist.R;
import project.navigation.constants.ParametersCodes;
import project.donnees.search.SearchParameters;

/**
 * Created by VINCENT on 05/04/2016.
 *
 */
public class FragmentComings extends FragmentSearchResult {

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_comings;
    }

    @Override
    public String getFragmentTitle() {
        return "A paraître";
    }

    @Override
    protected SearchParameters getSearchParameters() {
        return (SearchParameters) getParameterIn(ParametersCodes.DIRECT_SEARCH_PARAMETERS);
    }

}
