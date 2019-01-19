package project.navigation.activities;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import framework.activity.Header;
import project.donnees.database.SQLiteDbDef;
import project.navigation.fragments.FragmentAuthor;
import project.navigation.fragments.FragmentEditor;
import project.navigation.fragments.FragmentFestival;
import project.navigation.fragments.FragmentFestivals;
import project.navigation.fragments.FragmentGoody;
import project.navigation.fragments.FragmentInSignings;
import project.navigation.fragments.FragmentPress;
import project.navigation.fragments.FragmentSearchAuthorsResult;
import project.navigation.fragments.FragmentEvent;
import project.navigation.fragments.FragmentEvents;
import project.navigation.fragments.FragmentImageAutograph;
import project.navigation.fragments.FragmentComings;
import project.navigation.fragments.FragmentEdition;
import project.navigation.fragments.FragmentImageEdition;
import project.navigation.fragments.FragmentEditors;
import project.navigation.fragments.FragmentImageEvent;
import project.navigation.fragments.FragmentImageGoody;
import project.navigation.fragments.FragmentMenu;
import project.navigation.fragments.FragmentNews;
import project.navigation.fragments.FragmentSearch;
import project.navigation.fragments.FragmentSearchAuthors;
import project.navigation.fragments.FragmentSearchResult;
import project.navigation.fragments.FragmentSerie;
import project.navigation.fragments.FragmentParameters;

public class MainActivity extends FragmentManagerActivity {

    public MainActivity() {
        super();
        SQLiteDbDef.initialize(this);
    }

    @Override
    protected Header addHeader() {

        Header header = new Header(this, R.id.app_header);
        header.setGoMainFragmentId(R.id.header_layout2);
        header.setFragmentIconId(R.id.header_img_currentfragment);
        header.setFragmentTitleId(R.id.header_txt_currentfragment);
        header.setMenuId(R.id.header_img_menu);

        return header;
    }

    @Override
    protected void addFragments() {
        addFragment(new FragmentMenu(), true);
        addFragment(new FragmentSearch());
        addFragment(new FragmentSearchResult());
        addFragment(new FragmentComings());
        addFragment(new FragmentNews());
        addFragment(new FragmentEditors());
        addFragment(new FragmentParameters());
        addFragment(new FragmentEdition());
        addFragment(new FragmentGoody());
        addFragment(new FragmentSerie());
        addFragment(new FragmentImageEdition());
        addFragment(new FragmentImageAutograph());
        addFragment(new FragmentImageGoody());
        addFragment(new FragmentImageEvent());
        addFragment(new FragmentEvents());
        addFragment(new FragmentEvent());
        addFragment(new FragmentSearchAuthors());
        addFragment(new FragmentSearchAuthorsResult());
        addFragment(new FragmentAuthor());
        addFragment(new FragmentPress());
        addFragment(new FragmentEditor());
        addFragment(new FragmentFestivals());
        addFragment(new FragmentFestival());
        addFragment(new FragmentInSignings());
    }

}
