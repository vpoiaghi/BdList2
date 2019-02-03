package framework.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import framework.fragments.FragmentParameters;
import framework.fragments.StackedFragment;
import project.navigation.sliding_menu.SlidingMenuAdapter;
import project.navigation.sliding_menu.SlidingMenuItem;

/**
 * Created by VINCENT on 21/11/2015.
 *
 */
public abstract class FragmentManagerActivity extends FragmentActivity implements Header.IOpenMenuClickListener {

    private List<SlidingMenuItem> slidingMenuItemsList;
    private ListView menuListView;
    private DrawerLayout drawerLayoutMain;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private boolean onGoBack = false;
    private boolean onGoTo = false;

    private Header header = null;

    private static final String SAVE_BUNDLE_PRM_PARAMETERS = "1";
    private static final String SAVE_BUNDLE_PRM_FRAGMENTS_STACK = "2";

    private String defaultFragmentName = null;

    private static AbstractFragment currentFragment;
    private static FragmentParameters fragmentParameters = null;

    private static Stack<StackedFragment> fragmentsStack = null;
    private Map<String, AbstractFragment> fragmentsMap = null;

    protected abstract Header addHeader();
    protected abstract void addFragments();

    public FragmentManagerActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_main);

        if (savedInstanceState != null) {
            fragmentParameters = (FragmentParameters) savedInstanceState.getSerializable(SAVE_BUNDLE_PRM_PARAMETERS);

            List fragmentsList = (List) savedInstanceState.getSerializable(SAVE_BUNDLE_PRM_FRAGMENTS_STACK);
            fragmentsStack = new Stack<>();

            if (fragmentsList != null) {
                for (Object s : fragmentsList) {
                    fragmentsStack.push((StackedFragment) s);
                }
            }
        }

        fragmentsMap = new HashMap<>();

        addFragments();
        setHeader(addHeader());
        createSlidingMenu();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        onGoBack = false;
        onGoTo = false;
        super.onResume();
        startShowFragment(getCurrentFragmentName(), getCurrentFragmentParameters());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(SAVE_BUNDLE_PRM_PARAMETERS, getFragmentParameters());
        outState.putSerializable(SAVE_BUNDLE_PRM_FRAGMENTS_STACK, getFragmentsStack());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onGoToDefaultFragment() {

        if (defaultFragmentName != null) {
            showFragment(defaultFragmentName);
        }
    }


    protected void addFragment(final AbstractFragment fragment) {
        addFragment(fragment, false);
    }

    protected void addFragment(final AbstractFragment fragment, final boolean isDefaultFragment) {
        fragment.setParentActivity(this);
        fragmentsMap.put(fragment.getClass().getName(), fragment);

        if (isDefaultFragment) {
            defaultFragmentName = fragment.getClass().getName();
        }

    }

    public void showFragment(String fragmentName) {
        onGoTo = true;
        onGoBack = false;
        startShowFragment(fragmentName, getFragmentParameters());
    }

    private void startShowFragment(String fragmentName, final FragmentParameters prms) {

        final AbstractFragment fragmentToShow = findFragmentByName(fragmentName);
        final boolean isCurrentFragment = isAlreadyStacked(fragmentName);

        if (fragmentToShow != null) {

            currentFragment = fragmentToShow;

            hideKeyboard();

            if (!isCurrentFragment) {

                prms.add(FragmentParameters.PRM_FROM_FRAGMENT, getCurrentFragmentName());

                StackedFragment sf = new StackedFragment(fragmentName, prms, fragmentToShow.getView());
                getFragmentsStack().push(sf);
                fragmentParameters = null;

                final FragmentManager fragmentManager = this.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.app_mainContent, fragmentToShow);
                fragmentTransaction.commit();
            }

            setHeaderVisible(fragmentToShow);
            refreshSlidingMenu(fragmentToShow);
        }

    }

    private AbstractFragment findFragmentByName(final String fragmentName) {
        return fragmentsMap.get(fragmentName);
    }

    private boolean isAlreadyStacked(final String fragmentName) {

        Stack<StackedFragment> fStack = getFragmentsStack();
        return ((!fStack.isEmpty()) && fStack.peek().getFragmentName().equals(fragmentName));
    }

    public Object getParameterIn(final int key) {

        Stack<StackedFragment> fStack = getFragmentsStack();
        Object prmValue = null;
        if (!fStack.isEmpty()) {
            FragmentParameters fParameters = fStack.peek().getParameters();
            if (fParameters != null) {
                prmValue = fParameters.get(key);
            }
        }

        return prmValue;
    }

    public void addParameterOut(final Integer key, final Serializable value) {
        getFragmentParameters().add(key, value);
    }

    protected void goBack() {

        Stack<StackedFragment> fStack = getFragmentsStack();

        if (fStack.size() > 1) {
            onGoBack = true;
            onGoTo = false;
            fStack.pop();
            StackedFragment sFragment = fStack.pop();
            startShowFragment(sFragment.getFragmentName(), sFragment.getParameters());
        }
    }

    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.mainBackPage).getWindowToken(), 0);
    }

    private String getCurrentFragmentName() {

        String currentFragmentName = null;
        Stack<StackedFragment> fStack = getFragmentsStack();

        if (!fStack.isEmpty()) {
            currentFragmentName = getFragmentsStack().peek().getFragmentName();
        }

        if (currentFragmentName == null) {
            currentFragmentName = defaultFragmentName;
        }

        return currentFragmentName;
    }

    private FragmentParameters getCurrentFragmentParameters() {

        FragmentParameters currentFragmentParameters = null;
        Stack<StackedFragment> fStack = getFragmentsStack();

        if (!fStack.isEmpty()) {
            currentFragmentParameters = getFragmentsStack().peek().getParameters();
        }

        if (currentFragmentParameters == null) {
            currentFragmentParameters = new FragmentParameters();
        }

        return currentFragmentParameters;
    }

    private FragmentParameters getFragmentParameters() {

        if (fragmentParameters == null) {
            fragmentParameters = new FragmentParameters();
        }

        return fragmentParameters;
    }

    private Stack<StackedFragment> getFragmentsStack() {

        if (fragmentsStack == null) {
            fragmentsStack = new Stack<>();
        }

        return fragmentsStack;
    }


    protected void setHeader(final Header header) {
        this.header = header;
    }

    public void setHeaderVisible(final AbstractFragment fragment) {

        if (header != null) {

            View headerView = header.getHeader();

            if (headerView != null) {

                if (fragment.allowShowHeader()) {
                    showHeader(fragment, headerView);
                } else {
                    hideHeader(headerView);
                }

            }
        }
    }

    private void showHeader(final AbstractFragment fragment, final View headerView) {

        headerView.setVisibility(View.VISIBLE);
        headerView.refreshDrawableState();

        View goMainFragmentView = header.getGoMainFragment();
        if (goMainFragmentView != null) {
            goMainFragmentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGoToDefaultFragment();
                }
            });
        }

        header.setText(fragment.getFragmentTitle());
        header.setFragmentIcon(fragment.getFragmentIcon());
        header.setOpenMenuClickListener(this);
    }

    private void refreshSlidingMenu(final AbstractFragment fragment) {

        if (slidingMenuItemsList != null) {

            slidingMenuItemsList.clear();
            fragment.getSlidingMenuItems(slidingMenuItemsList);

            final SlidingMenuAdapter slidingMenuAdapter = new SlidingMenuAdapter(this, R.layout.itm_sliding_menu, slidingMenuItemsList);
            menuListView.setAdapter(slidingMenuAdapter);

            header.setMenuVisible(slidingMenuItemsList.size() > 0);
        } else {
            header.setMenuVisible(false);
        }
    }

    private void hideHeader(final View headerView) {
        headerView.setVisibility(View.GONE);
        headerView.refreshDrawableState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public boolean isOnGoBack() {
        return onGoBack;
    }

    public boolean isOnGoTo() {
        return onGoTo;
    }

    @Override
    public void onOpenMenuClick() {
        drawerLayoutMain.openDrawer(menuListView);
    }

    private void createSlidingMenu() {


        menuListView = (ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayoutMain = (DrawerLayout) findViewById(R.id.mainBackPage);

        slidingMenuItemsList = new ArrayList<>();

        menuListView.setItemChecked(0, true);
        drawerLayoutMain.closeDrawer(menuListView);

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuListView.setItemChecked(position, true);
                slidingMenuItemsList.get(position).onItemClick(parent, view, position, id);
                drawerLayoutMain.closeDrawer(menuListView);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutMain, R.string.sliding_menu_opened, R.string.sliding_menu_opened) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayoutMain.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

    }

}
