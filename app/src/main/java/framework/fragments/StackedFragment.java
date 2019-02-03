package framework.fragments;

import android.view.View;

import java.io.Serializable;

/**
 * Created by VINCENT on 12/01/2019.
 *
 */
public class StackedFragment implements Serializable {

    private final String fragmentName;
    private final FragmentParameters parameters;
    private final View fragmentView;

    public StackedFragment(String fragmentName, FragmentParameters parameters, View fragmentView) {
        this.fragmentName = fragmentName;
        this.parameters = parameters;
        this.fragmentView = fragmentView;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public FragmentParameters getParameters() {
        return parameters;
    }

    public View getFragmentView() {
        return fragmentView;
    }
}
