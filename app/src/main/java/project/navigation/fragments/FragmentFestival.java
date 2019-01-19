package project.navigation.fragments;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import project.donnees.extendedBo.Editor;
import project.donnees.extendedBo.Festival;
import project.donnees.extendedBo.FestivalReminder;
import project.navigation.adapters.FestivalRemindersAdapter;
import project.navigation.adapters.StringAdapter;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentMultiList;
import project.services.ServiceEditors;
import project.services.ServiceFestivalReminders;
import project.services.ServiceFestivals;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 10/12/2018.
 *
 */
public class FragmentFestival extends FragmentMultiList {

    private static final ServiceEditors svcEditors = ServicesFactory.get(ServiceEditors.class);
    private static final ServiceFestivals svcFestivals = ServicesFactory.get(ServiceFestivals.class);
    private static final ServiceFestivalReminders svcFestivalReminders = ServicesFactory.get(ServiceFestivalReminders.class);

    private Festival festival = null;
    private Editor editor = null;

    public FragmentFestival() {
        super();
    }

    @Override
    protected void doLoadFragment() {

        final long idFestival = (long) getParameterIn(ParametersCodes.ID_FESTIVAL);
        festival = svcFestivals.getById(idFestival);

        final Long idEditor = (Long) getParameterIn(ParametersCodes.ID_EDITOR);
        if (idEditor != null) {
            editor = svcEditors.getById(idEditor);
        } else {
            editor = null;
        }

    }

    @Override
    protected void doRefreshFragment() {

        initEditorFilterView();

        TextView txtFestivalName = (TextView)findViewById(R.id.festival_txt_name);
        txtFestivalName.setText(festival.getName());
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_festival;
    }

    @Override
    public String getFragmentTitle() {
        return "Festival";
    }

    @Override
    protected int getFragmentHeaderLayoutId() {
        return R.layout.frp_header_festival;
    }

    @Override
    protected final List<ListDescriptor> getListDescriptors() {

        List<ListDescriptor> listDescriptors = new ArrayList<>();

        ListDescriptor authorsListDescriptor = new ListDescriptor("Auteurs en dédicace", null, R.drawable.icn_page_authors);
        authorsListDescriptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addParameterOut(ParametersCodes.ID_FESTIVAL, festival.getId());
                if (editor != null) {
                    addParameterOut(ParametersCodes.ID_EDITOR, editor.getId());
                }
                getParentActivity().showFragment(FragmentInSignings.class.getName());
            }
        });

        listDescriptors.add(new ListDescriptor("Informations", getInformationsAdapter(), R.drawable.icn_information));
        listDescriptors.add(authorsListDescriptor);
        listDescriptors.add(new ListDescriptor("Pense-bête", getReminderAdapter(), R.drawable.icn_reminder));

        return listDescriptors;
    }

    private BaseAdapter getInformationsAdapter() {

        List<String> infosList = new ArrayList<>();
        infosList.add("Nom : " + festival.getName());
        infosList.add("Du " + festival.getBeginDate().toString() + " au " + festival.getEndDate().toString());
        return new StringAdapter(getParentActivity(), R.layout.itm_string_info, infosList);

    }

    private BaseAdapter getReminderAdapter() {

        BaseAdapter adapter = null;

        final List<FestivalReminder> festivalRemindersList = svcFestivalReminders.getByFestival(festival, editor);

        if (festivalRemindersList.size() > 0) {
            adapter = new FestivalRemindersAdapter(getParentActivity(), R.layout.itm_festival_reminder, festivalRemindersList);
        }

        return adapter;

    }

    private void initEditorFilterView() {

        ImageView imgEditorFilter = (ImageView) findViewById(R.id.festival_img_editor_filter);
        TextView txtEditorFilter = (TextView) findViewById(R.id.festival_txt_editor_filter);

        if (editor == null) {
            txtEditorFilter.setVisibility(View.GONE);
            imgEditorFilter.setVisibility(View.GONE);
        } else {
            txtEditorFilter.setVisibility(View.VISIBLE);
            imgEditorFilter.setVisibility(View.VISIBLE);
            txtEditorFilter.setText(editor.getName());
        }
    }
}
