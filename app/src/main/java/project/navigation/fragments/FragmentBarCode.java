package project.navigation.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;

/**
 * Created by VINCENT on 27/02/2016.
 *
 * https://www.spaceotechnologies.com/qr-code-android-using-zxing-library/
 *
 */
public class FragmentBarCode extends AbstractFragment {

    private static String scannedCodeValue;
    private static String scannedCodeType;
    private static int scannedResult;  // 0=scan not started; 1=scan ok; 2 = scan ko;

    private Button btn;

    public FragmentBarCode() {
        super(R.layout.frg_barcode);
    }

    @Override
    protected void loadFragment() {
        scannedCodeValue = null;
        scannedCodeType = null;
    }

    @Override
    protected void refreshFragment() {

        btn = (Button) findViewById(R.id.barcode_btn_read);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });

        showScanResult();
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_tools;
    }

    @Override
    public String getFragmentTitle() {
        return "Lecture code barres";
    }

    private void startScan() {

        scannedResult = 0; // Default : scan not started

        IntentIntegrator integrator = new IntentIntegrator(getParentActivity());

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if ((result != null) && (result.getContents() != null)) {
            scannedCodeValue = result.getContents();
            scannedCodeType = result.getFormatName();
            scannedResult = 1; // scan ok
        } else {
            scannedCodeValue = null;
            scannedCodeType = null;
            scannedResult = 2; // scan ko
        }
    }

    private void showScanResult() {

        final LinearLayout llSearch = (LinearLayout) findViewById(R.id.llSearch);

        if (scannedResult == 0) {
            // scan not started
            llSearch.setVisibility(View.GONE);

        } else if (scannedResult == 2) {
            // scan ko
            llSearch.setVisibility(View.GONE);
            Toast.makeText(getParentActivity(), "Erreur", Toast.LENGTH_LONG).show();

        } else {
            // scan ok
            llSearch.setVisibility(View.VISIBLE);

            final TextView tvScanContent = (TextView) findViewById(R.id.tvScanContent);
            tvScanContent.setText(scannedCodeValue);

            final TextView tvScanFormat = (TextView) findViewById(R.id.tvScanFormat);
            tvScanFormat.setText(scannedCodeType);

            Toast.makeText(getParentActivity(), "Ok", Toast.LENGTH_LONG).show();
        }

        scannedResult = 0;
    }

}