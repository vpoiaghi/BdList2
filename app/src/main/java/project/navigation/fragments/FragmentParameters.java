package project.navigation.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import bdlist.bdlist.R;
import framework.dialogboxes.DialogBox;
import framework.fragments.AbstractFragment;
import project.donnees.extendedBo.Parameters;
import project.donnees.bo.botypes.SqlDate;
import project.navigation.components.ImageViewButton;
import project.services.ServiceParameters;
import framework.tools.DateUtils;
import project.services.factory.ServicesFactory;

/**
 *
 * Created by VINCENT on 27/02/2016.
 */
public class FragmentParameters extends AbstractFragment {

    private static final ServiceParameters svcParameters = ServicesFactory.get(ServiceParameters.class);
    private Parameters parameters;

    public FragmentParameters() {
        super(R.layout.frg_parameters);
    }

    @Override
    protected void loadFragment() {

        parameters = svcParameters.getParameters();

    }

    @Override
    protected void refreshFragment() {

        loadValues();

        Button chkUseDefaultFirstComingDate = (Button)findViewById(R.id.parameters_chk_default_coming_date);
        chkUseDefaultFirstComingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtComingFirstDate = (EditText)findViewById(R.id.parameters_edt_coming_first_date);
                edtComingFirstDate.setText(DateUtils.dateToString(DateUtils.getTomorrow()));
            }
        });

        ImageViewButton btnSave = (ImageViewButton)findViewById(R.id.tools_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValues();
            }
        });

    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_tools;
    }

    @Override
    public String getFragmentTitle() {
        return "Outils / Paramètres";
    }

    private void loadValues() {

        TextView txtAppVersion = (TextView)findViewById(R.id.parameters_txt_app_version);
        String txt = "Version de l'application : " + parameters.getAppVersion();
        txtAppVersion.setText(txt);

        TextView txtBddVersion = (TextView)findViewById(R.id.parameters_txt_base_version);
        txt = "Version de la base de données : " + svcParameters.getDatabaseVersion();
        txtBddVersion.setText(txt);

        TextView txtDataUpdatedDate = (TextView)findViewById(R.id.parameters_txt_data_updated_date);
        txt = "Dernière mise à jour des données : " + parameters.getDataUpdateDate().toString(true);
        txtDataUpdatedDate.setText(txt);

        EditText edtNewsDaysCount = (EditText)findViewById(R.id.parameters_edt_news_days_count);
        edtNewsDaysCount.setText(parameters.getNewsDaysCount().toString());

        EditText edtComingFirstDate = (EditText)findViewById(R.id.parameters_edt_coming_first_date);
        edtComingFirstDate.setText(parameters.getFirstComingDate().toString());

        CheckBox chkUseDefaultComingFirstDate = (CheckBox)findViewById(R.id.parameters_chk_default_coming_date);
        chkUseDefaultComingFirstDate.setChecked(parameters.getUseDefaultComingDate());

        EditText edtEventsDaysBeforeCount = (EditText)findViewById(R.id.parameters_edt_events_days_before_count);
        edtEventsDaysBeforeCount.setText(parameters.getEventsPastDaysCount().toString());

    }

    private void saveValues() {

        Parameters parameters = svcParameters.getParameters();

        EditText edtNewsDaysCount = (EditText)findViewById(R.id.parameters_edt_news_days_count);
        try {
            String newsDaysCount = edtNewsDaysCount.getText().toString();
            parameters.setNewsDaysCount(Integer.parseInt(newsDaysCount));
        } catch (Exception e) {
            parameters.setNewsDaysCount(null);
        }

        EditText edtComingFirstDate = (EditText)findViewById(R.id.parameters_edt_coming_first_date);
        try {
            String comingFirstDate = edtComingFirstDate.getText().toString();
            parameters.setFirstComingDate(new SqlDate(DateUtils.stringToDate(comingFirstDate)));
        } catch (Exception e) {
            parameters.setFirstComingDate(null);
        }

        CheckBox chkUseDefaultComingFirstDate = (CheckBox)findViewById(R.id.parameters_chk_default_coming_date);
        try {
            parameters.setUseDefaultComingDate(chkUseDefaultComingFirstDate.isChecked());
        } catch (Exception e) {
            parameters.setUseDefaultComingDate(true);
        }

        EditText edtEventsDaysBeforeCount = (EditText)findViewById(R.id.parameters_edt_events_days_before_count);
        try {
            String eventsDaysBeforeCount = edtEventsDaysBeforeCount.getText().toString();
            parameters.setEventsPastDaysCount(Integer.parseInt(eventsDaysBeforeCount));
        } catch (Exception e) {
            parameters.setEventsPastDaysCount(null);
        }

        svcParameters.saveParameters(parameters);

        DialogBox dlg = new DialogBox(getParentActivity());
        dlg.showInformation("Validation...", "Paramètres enregistrés.");
    }

}