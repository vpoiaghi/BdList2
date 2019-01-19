package framework.fragments;

import java.io.Serializable;

/**
 * Created by VINCENT on 12/01/2019.
 *
 */
public class StackedFragment implements Serializable {

    private final String fragmentName;
    private final FragmentParameters parameters;

    public StackedFragment(String fragmentName, FragmentParameters parameters) {
        this.fragmentName = fragmentName;
        this.parameters = parameters;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public FragmentParameters getParameters() {
        return parameters;
    }
}
