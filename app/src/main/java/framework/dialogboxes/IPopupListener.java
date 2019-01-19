package framework.dialogboxes;

import android.app.Activity;

/**
 * Created by VINCENT on 27/11/2018.
 *
 */
public interface IPopupListener {
    void popupResult(final int popupId, final int status, final Object data);
    Activity getCurentActivity();
}
