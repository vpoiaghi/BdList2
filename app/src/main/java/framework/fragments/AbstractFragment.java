package framework.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.activity.FragmentManagerActivity;
import project.navigation.sliding_menu.SlidingMenuItem;


public abstract class AbstractFragment extends Fragment {

    private FragmentManagerActivity parentActivity;

    private int viewLayoutId = -1;

    abstract protected void loadFragment();
    abstract protected void refreshFragment();

    protected AbstractFragment(int layoutId) {
        super();
        this.viewLayoutId = layoutId;
    }

    public Context getFragmentContext() {
        return parentActivity;
    }

    protected FragmentManagerActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(FragmentManagerActivity parentActivity) {
        this.parentActivity = parentActivity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(viewLayoutId, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveFragmentState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            restoreFragmentState(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof FragmentManagerActivity) {
            parentActivity = (FragmentManagerActivity) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet AbstractFragment.IFragmentManagerActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentActivity = null;
    }

    public void load() {
        loadFragment();
    }

    public void refresh() {
        refreshFragment();
    }

    public String getFragmentTitle() {
        return "";
    }

    protected View findViewById(int id) {

        View viewToFind = null;
        View fragmentView = getView();

        if (fragmentView != null) {
            viewToFind = fragmentView.findViewById(id);
        }

        return viewToFind;
    }

    protected Object getParameterIn(int key) {
        return parentActivity.getParameterIn(key);
    }

    protected void addParameterOut(Integer key, Serializable value) {
        parentActivity.addParameterOut(key, value);
    }

    public Integer getFragmentIcon() {
        return null;
    }

    public boolean allowShowHeader() {
        return true;
    }

    public void getSlidingMenuItems(List<SlidingMenuItem> slidingMenuItemsList) {
    }

    public void showFragment(final String targetFragmentName) {
        parentActivity.showFragment(targetFragmentName);
    }

    public void showFragment(final String targetFragmentName, Integer parameterKey, Serializable parameterValue) {

        addParameterOut(parameterKey, parameterValue);

        parentActivity.showFragment(targetFragmentName);
    }

    protected void initLink(final int idView, final String targetFragmentName) {
        initLink(idView, targetFragmentName, null);
    }

    protected void initLink(final int idView, final String targetFragmentName, final Integer parameterKey, final Serializable parameterValue) {

        final Map<Integer, Serializable> parameters = new HashMap<>();
        parameters.put(parameterKey, parameterValue);

        initLink(idView, targetFragmentName, parameters);
    }

    protected void initLink(final int idView, final String targetFragmentName, final Map<Integer, Serializable> parameters) {

        View view = findViewById(idView);

        if (view != null) {
            new LinkView(this, view, targetFragmentName, parameters);
        }

    }

    protected boolean isOnGoBack() {
        return parentActivity.isOnGoBack();
    }

    protected boolean isOnGoTo() {
        return parentActivity.isOnGoTo();
    }

    protected void saveFragmentState(final Bundle dataToSave) {
    }

    public void restoreFragmentState(final @NonNull Bundle savedData) {
    }


}