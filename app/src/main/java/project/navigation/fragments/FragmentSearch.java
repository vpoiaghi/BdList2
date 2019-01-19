package project.navigation.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import project.donnees.extendedBo.Editor;
import project.navigation.adapters.EditorSearchFilterAdapter;
import project.navigation.adapters.KindOfGoodiesSearchFilterAdapter;
import project.navigation.components.CheckButton;
import project.navigation.components.ImageViewButton;
import project.navigation.constants.ParametersCodes;
import project.donnees.search.SearchOrder;
import project.donnees.search.SearchParameters;
import project.services.FactoryServices;
import project.services.ServiceEditors;
import project.services.ServiceGoodies;
import project.utils.StringUtils;

/**
 *
 * Created by VINCENT on 27/02/2016.
 */
public class FragmentSearch extends AbstractFragment {

    private static final ServiceEditors svcEditors = FactoryServices.get(ServiceEditors.class);
    private static final ServiceGoodies svcGoodies = FactoryServices.get(ServiceGoodies.class);

    public FragmentSearch() {
        super(R.layout.frg_search);
    }

    @Override
    protected void loadFragment() {
    }

    @Override
    protected void refreshFragment() {
        loadParameters();
        initFragment();
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_search;
    }

    @Override
    public String getFragmentTitle() {
        return "Recherche";
    }

    private void initEditorsSpinner() {

        final Spinner editorsCombo = (Spinner) findViewById(R.id.search_spn_editor);

        if (editorsCombo.getSelectedItem() == null) {

            List<Editor> editorsList = svcEditors.getAll();
            editorsList.add(0, new Editor());

            if (editorsList.size() > 0) {
                EditorSearchFilterAdapter editorsAdaptater = new EditorSearchFilterAdapter(getParentActivity(), R.layout.itm_editor_search_filter, editorsList);

                editorsCombo.setAdapter(editorsAdaptater);
                editorsCombo.setSelection(0);
            }
        }

        editorsCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                addParameterOut(ParametersCodes.SEARCH_SPINNER_EDITOR_SELECTION_POSITION, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initKindOfGoodiesSpinner() {

        final Spinner kindOfGoodiesCombo = (Spinner) findViewById(R.id.search_spn_kind_of_goodies);

        if (kindOfGoodiesCombo.getSelectedItem() == null) {

            List<String> kindOfGoodiesList = svcGoodies.getKinds();
            kindOfGoodiesList.add(0, "");

            if (kindOfGoodiesList.size() > 0) {
                KindOfGoodiesSearchFilterAdapter kindOfGoodiesAdapter = new KindOfGoodiesSearchFilterAdapter(getParentActivity(), R.layout.itm_kind_of_goodies_search_filter, kindOfGoodiesList);

                kindOfGoodiesCombo.setAdapter(kindOfGoodiesAdapter);
                kindOfGoodiesCombo.setSelection(0);
            }
        }

        kindOfGoodiesCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                addParameterOut(ParametersCodes.SEARCH_SPINNER_KIND_OF_GOODIES_SELECTION_POSITION, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadParameters() {

        final SearchParameters searchParameters = (SearchParameters) getParameterIn(ParametersCodes.SEARCH_PARAMETERS);

        if (searchParameters != null) {

            TextView txtTitleOrName = (TextView) findViewById(R.id.search_txt_titleorname);
            txtTitleOrName.setText(searchParameters.getRealName());

            try {
                int selectedEditorPosition = (int) getParameterIn(ParametersCodes.SEARCH_SPINNER_EDITOR_SELECTION_POSITION);
                if (selectedEditorPosition != -1) {
                    Spinner spnEditor = (Spinner) findViewById(R.id.search_spn_editor);
                    spnEditor.setSelection(selectedEditorPosition);
                }
            } catch (NullPointerException ex) {
                // rien à faire
            }

            try {
                int selectedKindOfGoodiesPosition = (int) getParameterIn(ParametersCodes.SEARCH_SPINNER_KIND_OF_GOODIES_SELECTION_POSITION);
                if (selectedKindOfGoodiesPosition != -1) {
                    Spinner spnKindOfGoodies = (Spinner) findViewById(R.id.search_spn_kind_of_goodies);
                    spnKindOfGoodies.setSelection(selectedKindOfGoodiesPosition);
                }
            } catch (NullPointerException ex) {
                // rien à faire
            }

            ((CheckButton) findViewById(R.id.search_btn_in_possession)).setChecked(searchParameters.getInPossession());
            ((CheckButton) findViewById(R.id.search_btn_wanted)).setChecked(searchParameters.getWanted());
            ((CheckButton) findViewById(R.id.search_btn_not_wanted)).setChecked(searchParameters.getNotWanted());

            ((CheckButton) findViewById(R.id.search_btn_comings)).setChecked(searchParameters.getComings());
            ((CheckButton) findViewById(R.id.search_btn_news)).setChecked(searchParameters.getNews());
            ((CheckButton) findViewById(R.id.search_btn_others)).setChecked(searchParameters.getOthers());

        }

    }

    private void initFragment() {

        initButtons();
        initEditorsSpinner();
        initKindOfGoodiesSpinner();
        checkAllDate(true);
        checkAllPossession(true);

    }

    private void initButtons() {

        ImageViewButton btnStartSearch = (ImageViewButton) findViewById(R.id.search_img_search);
        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });

        ImageViewButton btnCheckAll = (ImageViewButton) findViewById(R.id.search_img_check);
        btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllPossession(true);
            }
        });

        ImageViewButton btnUnheckAll = (ImageViewButton) findViewById(R.id.search_img_uncheck);
        btnUnheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllPossession(false);
            }
        });

        ImageViewButton btnCheckAll2 = (ImageViewButton) findViewById(R.id.search_img_check2);
        btnCheckAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllDate(true);
            }
        });

        ImageViewButton btnUnheckAll2 = (ImageViewButton) findViewById(R.id.search_img_uncheck2);
        btnUnheckAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllDate(false);
            }
        });

        ImageViewButton btnBean = (ImageViewButton) findViewById(R.id.search_img_bean);
        btnBean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();

            }
        });
    }

    private void checkAllPossession(final boolean checkState) {

        ((CheckButton) findViewById(R.id.search_btn_in_possession)).setChecked(checkState);
        ((CheckButton) findViewById(R.id.search_btn_wanted)).setChecked(checkState);
        ((CheckButton) findViewById(R.id.search_btn_not_wanted)).setChecked(checkState);

    }

    private void checkAllDate(final boolean checkState) {

        ((CheckButton) findViewById(R.id.search_btn_comings)).setChecked(checkState);
        ((CheckButton) findViewById(R.id.search_btn_news)).setChecked(checkState);
        ((CheckButton) findViewById(R.id.search_btn_others)).setChecked(checkState);

    }

    private void clearAll() {
        ((EditText) findViewById(R.id.search_txt_titleorname)).setText(null);
        ((Spinner) findViewById(R.id.search_spn_editor)).setSelection(0);
        ((Spinner) findViewById(R.id.search_spn_kind_of_goodies)).setSelection(0);
    }

    private void showResult() {

        final SearchParameters searchParameters = new SearchParameters();

        final String name = ((EditText) findViewById(R.id.search_txt_titleorname)).getText().toString();
        if (name.length() > 0) {
            searchParameters.setRealName(name);
            searchParameters.setName(StringUtils.ToSearchString(name));
        }

        Spinner editorsSpinner = (Spinner) findViewById(R.id.search_spn_editor);
        Editor editor = (Editor) editorsSpinner.getSelectedItem();
        if (editor != null) {
            Long idEditor = editor.getId();
            if (idEditor != null) {
                searchParameters.setIdEditor(idEditor);
            }
        }

        Spinner kindsOfGoodiesSpinner = (Spinner) findViewById(R.id.search_spn_kind_of_goodies);
        String kindOfGoodies = (String) kindsOfGoodiesSpinner.getSelectedItem();
        if (! "".equals(kindOfGoodies)) {
            searchParameters.setKindOfGoody(kindOfGoodies);
        }

        searchParameters.setInPossession(((CheckButton) findViewById(R.id.search_btn_in_possession)).isChecked());
        searchParameters.setWanted(((CheckButton) findViewById(R.id.search_btn_wanted)).isChecked());
        searchParameters.setNotWanted(((CheckButton) findViewById(R.id.search_btn_not_wanted)).isChecked());

        searchParameters.setComings(((CheckButton) findViewById(R.id.search_btn_comings)).isChecked());
        searchParameters.setNews(((CheckButton) findViewById(R.id.search_btn_news)).isChecked());
        searchParameters.setOthers(((CheckButton) findViewById(R.id.search_btn_others)).isChecked());

        searchParameters.setOrder(SearchOrder.SerieDesc);

        addParameterOut(ParametersCodes.SEARCH_PARAMETERS, searchParameters);
        getParentActivity().showFragment(FragmentSearchResult.class.getName());
    }

}