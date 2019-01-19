package project.navigation.fragments.abstract_fragments;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import project.navigation.components.ImageViewButton;

/**
 *
 * Created by VINCENT on 19/04/2016.
 */
public abstract class FragmentMultiList extends AbstractFragment {

    protected abstract void doLoadFragment();
    protected abstract int getFragmentHeaderLayoutId();

    protected abstract List<ListDescriptor> getListDescriptors();
    protected abstract void doRefreshFragment();

    private List<ListDescriptor> listDescriptors = null;
    private List<ListViewDescriptor> listViewDescriptors = null;
    private List<Parcelable> listViewStates = null;

    private int selectedListIndex = -1;

    private ViewGroup childLayout = null;

    public FragmentMultiList() {
        super(R.layout.abs_frg_multi_list);
    }

    @Override
    protected final void loadFragment() {

        childLayout = (ViewGroup) View.inflate(getParentActivity(), getFragmentHeaderLayoutId(), null);

        // Call load function of specific fragment
        doLoadFragment();
    }

    @Override
    protected final void refreshFragment() {

        // Add specific fragment header area to global multi-list fragment
        // Only if not already added
        ViewGroup parentlayout = (ViewGroup)findViewById(R.id.multi_list_layout_top_left);
        if (parentlayout.getChildCount() == 0) {
            parentlayout.addView(childLayout);
        }


        // If this fragment is call from another (not by back button)
        // then list index is forced to -1
        if(isOnGoTo()) {
            selectedListIndex = -1;
        }

        // Call refresh function of specific fragment
        doRefreshFragment();

        // Init lists of specific fragment
        initLists();
        initButtons();

        // Init next/prev buttons
        initPrevNextButtons();

        // Show leaving list, otherwise first list
        showList(selectedListIndex);
    }

    @Override
    public void onPause() {
        saveListState();
        super.onPause();
    }

    private void initLists() {

        listDescriptors = getListDescriptors();

        // Indexation des items de la liste
        if (listDescriptors != null) {
            int i = 0;
            for (ListDescriptor ld : listDescriptors) {
                ld.setIndex(i++);
            }

            if ((isOnGoTo()) || (listViewStates == null) || (listViewStates.size() != listDescriptors.size())) {
                listViewStates = new ArrayList<>();
                for (i = 0; i < listDescriptors.size(); i++) {
                    listViewStates.add(null);
                }
            }

        } else {
            listViewDescriptors = null;
            listViewStates = null;
        }
    }

    // Construction des boutons d'affichage des listes
    private void initButtons() {

        cleanSelectionButton();
        listViewDescriptors = new ArrayList<>(listDescriptors.size());

        for (ListDescriptor ld : listDescriptors) {
            listViewDescriptors.add(buildSelectionButton(ld));
        }

    }

    private void initPrevNextButtons() {

        final LinearLayout lnlPrev = (LinearLayout)findViewById(R.id.multi_list_lnl_prev);
        final TextView txtPrev = (TextView)findViewById(R.id.multi_list_txt_prev);
        final ImageView imgPrev = (ImageView)findViewById(R.id.multi_list_img_prev);

        final LinearLayout lnlNext = (LinearLayout)findViewById(R.id.multi_list_lnl_next);
        final TextView txtNext = (TextView)findViewById(R.id.multi_list_txt_next);
        final ImageView imgNext = (ImageView)findViewById(R.id.multi_list_img_next);

        if (hasPrev()) {

            lnlPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beforeGoPrev();
                    loadFragment();
                    refreshFragment();
                }
            });

            txtPrev.setVisibility(View.VISIBLE);
            imgPrev.setVisibility(View.VISIBLE);
            txtPrev.setText(getPrevText());
        } else {
            txtPrev.setVisibility(View.GONE);
            imgPrev.setVisibility(View.GONE);
            lnlPrev.setOnClickListener(null);
        }

        if (hasNext()) {

            lnlNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beforeGoNext();
                    loadFragment();
                    refreshFragment();
                }
            });

            txtNext.setVisibility(View.VISIBLE);
            imgNext.setVisibility(View.VISIBLE);
            txtNext.setText(getNextText());
        } else {
            txtNext.setVisibility(View.GONE);
            imgNext.setVisibility(View.GONE);
            lnlNext.setOnClickListener(null);
        }

    }

    // Called before reload fragment to show previous fragment item
    // Must be redefined in child fragment if necessary
    protected void beforeGoPrev() {
    }

    // Called before reload fragment to show next fragment item
    // Must be redefined in child fragment if necessary
    protected void beforeGoNext() {
    }

    private void cleanSelectionButton() {

        // Parent LinearLayout defined on xml frgment.
        LinearLayout layout = (LinearLayout)findViewById(R.id.multi_list_layout_right);

        // Delete potential existing views before add new
        layout.removeAllViews();

    }

    private ListViewDescriptor buildSelectionButton(final ListDescriptor ld) {

        // Parent LinearLayout defined on xml fragment.
        LinearLayout layout = (LinearLayout)findViewById(R.id.multi_list_layout_right);

        // New layout content for button
        RelativeLayout rl = new RelativeLayout(getFragmentContext());
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(rl, rlp);

        // New back ImageView (will be return by this function)
        ImageView iv = new ImageView(getFragmentContext());
        iv.setImageResource(R.drawable.bck_selection);
        RelativeLayout.LayoutParams ivp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.addView(iv, ivp);

        // New ImageViewButton
        ImageViewButton ivb = new ImageViewButton(getFragmentContext());
        ivb.setImageResource(ld.getImageId());
        RelativeLayout.LayoutParams ivbp = new RelativeLayout.LayoutParams(90, 90);
        ivbp.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl.addView(ivb, ivbp);

        if (ld.getOnClickListener() != null) {
            ivb.setOnClickListener(ld.getOnClickListener());
            rl.setVisibility(View.VISIBLE);
        } else {
            if (ld.getAdapter() != null) {
                rl.setVisibility(View.VISIBLE);

                ivb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClick(ld.getIndex());
                    }
                });
            } else {
                rl.setVisibility(View.GONE);
            }
        }

        return new ListViewDescriptor(rl, iv);
    }

    protected void onButtonClick(final int buttonId) {
        showList(buttonId);
    }

    private void showList(int index) {

        if (index == -1) {
            index = 0;
        }

        if ((index >= 0) && (index < listDescriptors.size())) {

            if (index != selectedListIndex) {
                saveListState();
            }

            final ListDescriptor listDescriptor = listDescriptors.get(index);
            final BaseAdapter adapter = listDescriptor.getAdapter();

            if (adapter != null) {
                ListView lstSubItems = (ListView) findViewById(R.id.multi_list_lst_sub_items);
                lstSubItems.setAdapter(adapter);

                for (int i = 0; i < listViewDescriptors.size(); i++) {
                    if (i == index) {
                        listViewDescriptors.get(i).getImage().setVisibility(View.VISIBLE);
                    } else {
                        listViewDescriptors.get(i).getImage().setVisibility(View.INVISIBLE);
                    }
                }

                TextView txtSubItemsTitle = (TextView) findViewById(R.id.multi_list_txt_subItems_title);
                txtSubItemsTitle.setText(listDescriptor.getTitle());

            }

            selectedListIndex = index;
            restoreListState();
        }
    }

    private void saveListState() {

        if ((selectedListIndex >= 0) && (selectedListIndex < listViewStates.size())) {
            ListView listView = (ListView) findViewById(R.id.multi_list_lst_sub_items);
            listViewStates.set(selectedListIndex, listView.onSaveInstanceState());
        }
    }

    private void restoreListState() {

        if ((selectedListIndex >= 0) && (selectedListIndex < listViewStates.size())) {
            ListView listView = (ListView) findViewById(R.id.multi_list_lst_sub_items);
            Parcelable listViewState = listViewStates.get(selectedListIndex);

            if (listViewState != null) {
                listView.onRestoreInstanceState(listViewState);
            }
        }
    }

    protected boolean hasNext() {
        return false;
    }

    protected boolean hasPrev() {
        return false;
    }

    protected String getNextText() {
        return "Suivant";
    }

    protected String getPrevText() {
        return "Précédent";
    }


    // ---------------------------------------------------- classes internes

    protected class ListDescriptor {

        private final String title;
        private final BaseAdapter adapter;
        private final int imageId;
        private int index = -1;
        private View.OnClickListener onClickListener = null;

        public ListDescriptor(String title, BaseAdapter adapter, int imageId) {
            this.title = title;
            this.adapter = adapter;
            this.imageId = imageId;
        }

        public String getTitle() {
            return title;
        }

        public BaseAdapter getAdapter() {
            return adapter;
        }

        public int getImageId() {
            return imageId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public View.OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
    }

    private class ListViewDescriptor {

        final private ImageView image;
        final private RelativeLayout layout;

        public ListViewDescriptor(RelativeLayout layout, ImageView image) {
            this.image = image;
            this.layout = layout;
        }

        public ImageView getImage() {
            return image;
        }

        public RelativeLayout getLayout() {
            return layout;
        }
    }
}
