package project.navigation.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import framework.tools.DateUtils;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.wrapper.PressItem;
import project.utils.Constantes;
import project.utils.ImageUtils;
import project.utils.PossessionStates;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class PressAdapterByDay extends ArrayAdapter<PressItem> {

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<PressItem> pressItemsList;

    public PressAdapterByDay(final FragmentManagerActivity activity, final int resource, final List<PressItem> pressItemsList) {
        super(activity, resource, pressItemsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.pressItemsList = pressItemsList;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final View row;

        if (convertView != null) {
            row = convertView;
        } else {
            final LayoutInflater inflater = parentActivity.getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
        }

        initRow(row, pressItemsList.get(position));

        return row;
    }

    private void initRow(final View row, final PressItem pressItem) {

        final boolean isOld = DateUtils.isBefore(pressItem.getParutionDate().getDate(), DateUtils.getToday());

        final TextView txtSerieName = (TextView) row.findViewById(R.id.press_txt_seriename);
        final String serieName = pressItem.getSerieName();
        if (serieName == null) {
            txtSerieName.setText("serie inconnue");
        } else {
            txtSerieName.setText(serieName);
        }

        final TextView txtItemName = (TextView) row.findViewById(R.id.press_txt_name);
        final String itemName = pressItem.getItemName();
        if (itemName == null) {
            txtItemName.setText("");
        } else {
            txtItemName.setText(itemName);
        }

        final TextView txtEditorName = (TextView) row.findViewById(R.id.press_txt_editor);
        final String editorName = pressItem.getEditorName();
        if (editorName == null) {
            txtEditorName.setText("");
        } else {
            txtEditorName.setText(editorName);
        }

        ImageView img = (ImageView) row.findViewById(R.id.press_img_item);
        if (pressItem.getEdition() != null) {
            ImageUtils.loadEditionFrontCoverImage(img, pressItem.getEdition());
        } else if (pressItem.getGoody() != null) {
            ImageUtils.loadGoodyImage(img, pressItem.getGoody());
        } else {
            ImageUtils.cleanImage(img);
        }

        RelativeLayout imgLayout = (RelativeLayout) row.findViewById(R.id.press_lyt_imgBack);
        if (pressItem.getPossessionState() == PossessionStates.IN_POSSESSION) {
            imgLayout.setBackgroundColor(Color.GREEN);
        } else {
            imgLayout.setBackgroundColor(Color.RED);
        }

        LinearLayout back = (LinearLayout) row.findViewById(R.id.press_lyt_back);
        if (isOld) {
            back.setBackgroundColor(Color.LTGRAY);
        } else {
            back.setBackgroundColor(Color.WHITE);
        }

    }

}
