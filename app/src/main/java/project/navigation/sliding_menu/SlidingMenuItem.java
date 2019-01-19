package project.navigation.sliding_menu;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by VINCENT on 29/12/2018.
 *
 */
public class SlidingMenuItem implements AdapterView.OnItemClickListener {

    private final Integer imageId;
    private final String text;

    public SlidingMenuItem(final Integer imageId, final String text) {
        this.imageId = imageId;
        this.text = text;
    }

    public SlidingMenuItem(final Integer imageId) {
        this(imageId, null);
    }

    public SlidingMenuItem(final String text) {
        this(null, text);
    }

    public Integer getImageId() {
        return imageId;
    }

    public String getText() {
        return text;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

}
