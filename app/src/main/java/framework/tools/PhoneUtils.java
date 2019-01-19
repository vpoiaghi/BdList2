package framework.tools;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by VINCENT on 25/12/2018.
 *
 */
public class PhoneUtils {

    public static Point getScreenSizeInPixel(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        return size;
    }

}
