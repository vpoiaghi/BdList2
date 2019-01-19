package project.navigation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import framework.tools.DateUtils;
import project.donnees.wrapper.PressItemsList;
import project.navigation.adapters.PressAdapter;
import project.services.FactoryServices;
import project.services.ServicePress;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPress extends AbstractFragment {

    private static final ServicePress svcPress = FactoryServices.get(ServicePress.class);

    private List<PressItemsList> pressItemsList;
    private PressAdapter adapter;

    public FragmentPress() {
        super(R.layout.frg_press);
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_press;
    }

    @Override
    public String getFragmentTitle() {
        return "Articles en presse";
    }

    @Override
    protected void loadFragment() {
        pressItemsList = svcPress.getAll();
        adapter = new PressAdapter(getParentActivity(), R.layout.itm_press_by_day, pressItemsList);
    }

    @Override
    protected void refreshFragment() {

        final ListView pressItemsListView = (ListView) findViewById(R.id.press_list);
        pressItemsListView.setVisibility(View.VISIBLE);
        pressItemsListView.setAdapter(adapter);

        moveToToday(pressItemsList, pressItemsListView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    /**
     * Scroll la liste sur l'item corrspondant à la date du jour
     * @param pressItemsList
     * @param pressItemsListView
     */
    private void moveToToday(final List<PressItemsList> pressItemsList, final ListView pressItemsListView) {

        if (pressItemsList.size() > 0) {

            int i = 0;
            Date dte = DateUtils.getToday();

            // Recherche de l'indice du dernier item avant la date du jour
            while ((i < pressItemsList.size()) && (DateUtils.isAfter(pressItemsList.get(i).getParutionDate().getDate(), dte))) {
                i++;
            }

            if (i >= pressItemsList.size()) {
                // Si aucun item avant la date du jour, alors positionnement sur le dernier item de la liste
                i = pressItemsList.size() - 1;
            } else if (i > 0) {
                // Si il existe un item après la date du jour, positionnement de l'indice sur le premier après la date du jour
                i--;
            }

            // Scroll de la liste sur l'indice calculé
            final int index = i;
            pressItemsListView.post(new Runnable() {
                @Override
                public void run() {
                    pressItemsListView.setSelection(index);
                }
            });
        }
    }

}
