package project.navigation.fragments.abstract_fragments;

import android.widget.ImageView;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import project.utils.ImageUtils;

/**
 *
 * Created by VINCENT on 12/04/2016.
 */
public abstract class FragmentImage extends AbstractFragment {

    public abstract String getImageFilePath();

    public FragmentImage() {
        super(R.layout.frg_image);
    }

    @Override
    protected void loadFragment() {
    }

    @Override
    protected void refreshFragment() {
        ImageUtils.loadImage(getImageFilePath(), (ImageView) findViewById(R.id.image_img));
    }

    @Override
    public boolean allowShowHeader() {
        return false;
    }

}
