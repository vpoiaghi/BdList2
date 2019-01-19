package project.navigation.fragments;

import android.widget.TextView;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import project.donnees.extendedBo.Parameters;
import project.navigation.components.ImageViewEventButton;
import project.navigation.constants.ParametersCodes;
import project.donnees.search.SearchParameters;
import project.services.ServiceEditions;
import project.services.ServiceParameters;
import project.services.factory.ServicesFactory;

/**
 *
 * Created by VINCENT on 27/02/2016.
 */
public class FragmentMenu extends AbstractFragment {

    private static final ServiceEditions svcEditions = ServicesFactory.get(ServiceEditions.class);
    private static final ServiceParameters svcParameters = ServicesFactory.get(ServiceParameters.class);

    int newsDaysCount;
    int eventsPastDaysCount;

    public FragmentMenu() {
        super(R.layout.frg_menu);
    }

    @Override
    protected void loadFragment() {

        Parameters parameters = svcParameters.getParameters();
        newsDaysCount = parameters.getNewsDaysCount();
        eventsPastDaysCount = parameters.getEventsPastDaysCount();
    }

    @Override
    protected void refreshFragment() {

        initMenuItems();

        final ImageViewEventButton imgEvents = (ImageViewEventButton) findViewById(R.id.menu_img_events);
        String txt = Integer.valueOf(eventsPastDaysCount).toString();
        imgEvents.setText(txt);

        final TextView txtSearch = (TextView) findViewById(R.id.menu_txt_search);
        txt = "Recherche";
        txtSearch.setText(txt);

        final TextView txtComing = (TextView) findViewById(R.id.menu_txt_coming);
        txt = "A paraître (" + svcEditions.getComingCount() + ")";
        txtComing.setText(txt);

        final TextView txtNews = (TextView) findViewById(R.id.menu_txt_news);
        txt = "Nouveautés à " + newsDaysCount + " jours (" + svcEditions.getNewsCount() + ")";
        txtNews.setText(txt);
    }

    @Override
    public boolean allowShowHeader() {
        return false;
    }

    private void initMenuItems() {

        initLink(R.id.menu_lay_item_search, FragmentSearch.class.getName());
        initLink(R.id.menu_lay_item_coming, FragmentComings.class.getName(), ParametersCodes.DIRECT_SEARCH_PARAMETERS, SearchParameters.getComingsParameters());
        initLink(R.id.menu_lay_item_news, FragmentNews.class.getName(), ParametersCodes.DIRECT_SEARCH_PARAMETERS, SearchParameters.getNewsParameters());
        initLink(R.id.menu_lay_item_editors, FragmentEditors.class.getName());
        initLink(R.id.menu_lay_item_authors, FragmentSearchAuthors.class.getName());
        initLink(R.id.menu_lay_item_events, FragmentEvents.class.getName());
        initLink(R.id.menu_lay_item_press, FragmentPress.class.getName());
        initLink(R.id.menu_lay_item_festivals, FragmentFestivals.class.getName(), ParametersCodes.ID_EDITOR, null);
        initLink(R.id.menu_lay_item_tools, FragmentParameters.class.getName());

    }

}