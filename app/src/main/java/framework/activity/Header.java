package framework.activity;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by VINCENT on 28/02/2016.
 *
 */
public class Header {

    private Activity parentActivity;
    private Integer headerId;
    private Integer fragmentTitleId;
    private Integer fragmentIconId;
    private Integer goMainFragmentId;

    private View menu = null;

    private IOpenMenuClickListener openMenuClickListener = null;

    public Header(final Activity activity, final int headerId) {

        this.parentActivity = activity;
        this.headerId = headerId;
        this.fragmentTitleId = null;
        this.fragmentIconId = null;
        this.goMainFragmentId = null;
    }

    public View getHeader() {
        return getHeaderViewById(headerId);
    }

    public View getFragmentTitle() {
        return getViewById(fragmentTitleId);
    }

    public void setFragmentTitleId(int fragmentTitleId) {
        this.fragmentTitleId = fragmentTitleId;
    }

    public void setFragmentIconId(int fragmentIconId) {
        this.fragmentIconId = fragmentIconId;
    }

    public View getGoMainFragment() {
        return getViewById(goMainFragmentId);
    }

    public void setText(final String text) {

        TextView titleFragment = (TextView) getViewById(fragmentTitleId);

        if (titleFragment != null) {
            titleFragment.setText(text);
        }
    }

    public void setGoMainFragmentId(int goMainFragmentId) {
        this.goMainFragmentId = goMainFragmentId;
    }

    public void setFragmentIcon(Integer iconId) {

        ImageView iconFragment = (ImageView) getViewById(fragmentIconId);
        if ((iconFragment != null) && (iconId != null)) {
            iconFragment.setImageResource(iconId);
        }
    }

    public void setMenuId(int menuId) {

        this.menu = getViewById(menuId);

        this.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openMenuClickListener != null) {
                    openMenuClickListener.onOpenMenuClick();
                }
            }
        });
    }

    public void setMenuVisible(final boolean visible) {

        if (menu != null) {
            menu.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private View getHeaderViewById(Integer id) {

        View result = null;

        if ((id != null) && (parentActivity != null)) {
            try {
                result = parentActivity.findViewById(id);
            }
            catch(Exception e) {
                result = null;
            }
        }

        return result;
    }

    private View getViewById(Integer id) {

        View result = null;
        View header = getHeader();

        if ((id != null) && (header != null)) {
            try {
                result = header.findViewById(id);
            }
            catch(Exception e) {
                result = null;
            }
        }

        return result;
    }

    public void setOpenMenuClickListener(IOpenMenuClickListener openMenuClickListener) {
        this.openMenuClickListener = openMenuClickListener;
    }

    public interface IOpenMenuClickListener {
        void onOpenMenuClick();
    }

}
