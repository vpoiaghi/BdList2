package framework.fragments;

import android.view.View;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * Created by VINCENT on 10/03/2016.
 */
public class LinkView {

    private final View linkView;

    public LinkView(final AbstractFragment parentFragment, final View linkView, final String targetFragmentName, final Map<Integer, Serializable> parameters) {

        this.linkView = linkView;

        linkView.setOnClickListener(new LinkViewOnClickListener(parentFragment, targetFragmentName, parameters));

    }

    private class LinkViewOnClickListener implements View.OnClickListener {

        private final AbstractFragment parentFragment;
        private final String targetFragmentName;
        private final Map<Integer, Serializable> parameters;

        public LinkViewOnClickListener(final AbstractFragment parentFragment, final  String targetFragmentName, final Map<Integer, Serializable> parameters) {

            super();

            this.parentFragment = parentFragment;
            this.targetFragmentName = targetFragmentName;
            this.parameters = parameters;
        }

        @Override
        public void onClick(View v) {

            if (parameters != null) {
                for (Map.Entry<Integer, Serializable> parameter : parameters.entrySet()) {
                    parentFragment.addParameterOut(parameter.getKey(), parameter.getValue());
                }
            }

            parentFragment.showFragment(targetFragmentName);

        }
    }

}
