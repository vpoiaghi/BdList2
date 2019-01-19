package project.navigation.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by VINCENT on 08/04/2017.
 */
public class ExtendedListView extends ListView {

    public ExtendedListView(Context context) {
        super(context);
    }
    public ExtendedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }
}