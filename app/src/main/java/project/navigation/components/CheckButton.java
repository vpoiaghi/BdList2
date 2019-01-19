package project.navigation.components;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import bdlist.bdlist.R;

/**
 *
 * Created by VINCENT on 14/04/2016.
 */
public class CheckButton extends ImageButton implements View.OnClickListener {

    private static final int[] STATE_BTN_CHECKED = {R.attr.state_btn_checked};
    private static final int[] STATE_BTN_UNCHECKED = {R.attr.state_btn_unchecked};

    private boolean isChecked = true;

    public CheckButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOnClickListener(this);
        setChecked(true);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(final boolean checked) {

        isChecked = checked;
        refreshDrawableState();

     }

    @Override
    public void onClick(View v) {
        setChecked(!isChecked());
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {

        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);

        if (isChecked) {
            mergeDrawableStates(drawableState, STATE_BTN_CHECKED);
        } else {
            mergeDrawableStates(drawableState, STATE_BTN_UNCHECKED);
        }

        return drawableState;
    }
}
