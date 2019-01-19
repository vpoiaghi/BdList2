package project.navigation.sliding_menu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;

/**
 * Created by VINCENT on 29/12/2018.
 *
 */
public class SlidingMenuAdapter extends ArrayAdapter<SlidingMenuItem> {

    private final Activity activity;
    private final int resource;
    private final List<SlidingMenuItem> slidingMenuItemsList;

    public SlidingMenuAdapter(final Activity activity, final int resource, List<SlidingMenuItem> slidingMenuItemsList) {
        super(activity, resource, slidingMenuItemsList);

        this.activity = activity;
        this.resource = resource;
        this.slidingMenuItemsList = slidingMenuItemsList;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row;

        if (convertView != null) {
            row = convertView;
        } else {
            final LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
        }

        initRow(row, slidingMenuItemsList.get(position));

        return row;
    }

    private void initRow(final View row, final SlidingMenuItem slidingMenuItem) {

        ImageView img = (ImageView) row.findViewById(R.id.item_sliding_menu_img);
        Integer imageId = slidingMenuItem.getImageId();
        if (imageId == null) {
            img.setVisibility(View.GONE);
        } else {
            img.setImageResource(imageId);
            img.setVisibility(View.VISIBLE);
        }

        TextView txt = (TextView) row.findViewById(R.id.item_sliding_menu_txt);
        String text = slidingMenuItem.getText();
        if ((text == null) || (text.trim().length() ==0)) {
            txt.setVisibility(View.GONE);
        } else {
            txt.setText(text.trim());
            txt.setVisibility(View.VISIBLE);
        }

    }
}
