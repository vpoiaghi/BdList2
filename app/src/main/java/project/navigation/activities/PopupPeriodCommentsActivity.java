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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bdlist.bdlist.R;
import framework.dialogboxes.IPopupListener;
import framework.tools.PhoneUtils;
import project.utils.ImageUtils;
import project.utils.PossessionStates;

/**
 * Created by VINCENT on 25/11/2018.
 * https://www.youtube.com/watch?v=fn5OlqQuOCk
 */
public class PopupPeriodCommentsActivity extends Activity {

    public static final int POPUP_REQUEST_CODE = 36;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_period_comments);

        final Point size = PhoneUtils.getScreenSizeInPixel(this);
        final int popup_width = (int) (size.x * 0.8);
        final int popup_height = (int) (size.y * 0.8);

        getWindow().setLayout(popup_width, popup_height);

        final Button btnOk = (Button) findViewById(R.id.pop_period_comments_btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itt = getIntent();
                setResult(Activity.RESULT_OK, itt);
                finish();
            }
        });

        final Intent itt = getIntent();
        final Uri u = itt.getData();
        final String data = u.toString();

        String[] dataArray = data.split("[|]");
        String[] itemArray;
        Map<String, String> dataMap = new HashMap<>();
        for (String item : dataArray) {
            itemArray = item.split(":=");
            dataMap.put(itemArray[0], itemArray[1]);
        }

        ((TextView) findViewById(R.id.pop_period_comments_txt_title)).setText(dataMap.get("title"));
        ((TextView) findViewById(R.id.pop_period_comments_txt_begin)).setText("Du " + dataMap.get("begin"));
        ((TextView) findViewById(R.id.pop_period_comments_txt_end)).setText("Au " + dataMap.get("end"));
        ((TextView) findViewById(R.id.pop_period_comments_txt_comments)).setText(dataMap.get("comments"));
    }
}
