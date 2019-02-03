package project.navigation.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import framework.tools.PhoneUtils;
import project.utils.ImageUtils;
import project.utils.PossessionStates;

/**
 * Created by VINCENT on 25/11/2018.
 *
 * https://www.youtube.com/watch?v=fn5OlqQuOCk
 */
public class PopupPossessionStatesActivity extends Activity {

    public static final int POPUP_REQUEST_CODE = 15;

    private final List<View> backButtonsList = new ArrayList<>();
    private final List<View> buttonsList = new ArrayList<>();

    private Integer selectedState = -1;
    private int popup_width = 0;
    private int popup_height = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_possession_states);

        Point size = PhoneUtils.getScreenSizeInPixel(this);
        popup_width = (int) (size.x * 0.8);
        popup_height = (int) (size.y * 0.8);
        getWindow().setLayout(popup_width, popup_height);

        Button btnOk = (Button) findViewById(R.id.pop_possession_states_btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itt = getIntent();
                Uri u = Uri.parse(selectedState + "");
                itt.setData(u);
                setResult(Activity.RESULT_OK, itt);
                finish();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.pop_possession_states_btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itt = getIntent();
                setResult(Activity.RESULT_CANCELED, itt);
                finish();
            }
        });

        Intent itt = getIntent();
        Uri u = itt.getData();
        String strPossessionState = u.toString();

        addStatesButtons();
        changeSelection(Integer.parseInt(strPossessionState));
    }

    @Override
    public void finishActivityFromChild(Activity child, int requestCode) {
        super.finishActivityFromChild(child, requestCode);
    }

    private void addStatesButtons() {

        final int maxButtonsByRow = 4;

        int c = 0;
        TableRow tr = null;
        ImageView iv;

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.pop_possession_states_table);
        tableLayout.removeAllViews();
        backButtonsList.clear();
        buttonsList.clear();

        for(int i = 0; i < PossessionStates.getStatesCount(); i++) {

            if (c == 0) {
                tr = new TableRow(getApplicationContext());
                tableLayout.addView(tr);
            }

            FrameLayout fm = new FrameLayout(getApplicationContext());
            fm.setVisibility(View.VISIBLE);
            fm.setMinimumWidth((int)(popup_width / maxButtonsByRow));
            fm.setMinimumHeight((int)(popup_width / maxButtonsByRow));
            fm.setPadding(10,10,10,10);

            iv = new ImageView(getApplicationContext());
            iv.setVisibility(View.VISIBLE);
            iv.setMinimumWidth((int)(popup_width / maxButtonsByRow) - 20);
            iv.setMinimumHeight((int)(popup_width / maxButtonsByRow) - 20);
            iv.setTag(new Integer(i));

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeSelection(((Integer)(v.getTag())).intValue());
                }
            });

            ImageUtils.loadPossessionImage(iv, i);

            tr.addView(fm);
            fm.addView(iv);

            backButtonsList.add(fm);
            buttonsList.add(iv);

            if (++c == maxButtonsByRow) {
                c = 0;
            }
        }

    }

    private void changeSelection(int selectedState) {

        ImageView iv = null;
        FrameLayout fm = null;
        int state = -1;

        for(int i = 0; i < buttonsList.size(); i++) {
            fm = (FrameLayout) backButtonsList.get(i);
            iv = (ImageView) buttonsList.get(i);
            state = ((Integer)(iv.getTag())).intValue();

            if (state == selectedState) {
                fm.setBackgroundColor(Color.BLUE);
            } else {
                fm.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        this.selectedState = selectedState;
    }

}
