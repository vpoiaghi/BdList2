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
import project.services.ServiceBlueAngel82Books;
import project.services.abs.IAsyncServiceListener;

/**
 * Created by VINCENT on 27/02/2016.
 *
 * https://www.spaceotechnologies.com/qr-code-android-using-zxing-library/
 *
 */
public class FragmentBarCode extends AbstractFragment implements IAsyncServiceListener {

    private static String scannedCodeValue;
    private static String scannedCodeType;
    private static int scannedResult;  // 0=scan not started; 1=scan ok; 2 = scan ko;

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

        final Button btnScan = (Button) findViewById(R.id.barcode_btn_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });

        final Button btnGetInfo = (Button) findViewById(R.id.barcode_btn_getinfos);
        btnGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfos();
            }
        });

        showScanResult();

        // Le temps de tester l'api google
        final LinearLayout llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llSearch.setVisibility(View.VISIBLE);

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

    private void getInfos() {
        //https://code.tutsplus.com/tutorials/android-sdk-create-a-book-scanning-app-interface-book-search--mobile-17790

        //String bookSearchString = "https://www.googleapis.com/books/v1/volumes?q=9782756040295&key=AIzaSyB3Z8f544fCYBe7eElVJUoh4N-RIZWAi3k";
        //new ServiceGoogleBooks().execute(bookSearchString);

        // test

        if(scannedCodeValue != null){
            //book search
            ServiceBlueAngel82Books svcBooks = new ServiceBlueAngel82Books(this);
            svcBooks.searchByIsbn(scannedCodeValue);
        } else{
            Toast toast = Toast.makeText(getParentActivity(), "Code barre non reconnu !", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void asyncServiceCallBack(int status, ServiceBlueAngel82Books.ResultEdition result) {

        final TextView txtSerie = (TextView) findViewById(R.id.barcode_serie);
        final TextView txtNumber = (TextView) findViewById(R.id.barcode_num);
        final TextView txtTitle = (TextView) findViewById(R.id.barcode_title);
        final TextView txtIsbn = (TextView) findViewById(R.id.barcode_isbn);
        final TextView txtEditor = (TextView) findViewById(R.id.barcode_editor);

        if (status == 200) {
            txtSerie.setText(result.getSerie());
            txtNumber.setText(result.getNumber());
            txtTitle.setText(result.getTitle());
            txtIsbn.setText(result.getIsbn());
            txtEditor.setText(result.getEditor());
        } else {
            txtSerie.setText("");
            txtNumber.setText("");
            txtTitle.setText("");
            txtIsbn.setText("");
            txtEditor.setText("");
            Toast toast = Toast.makeText(getParentActivity(), "Erreur " + status, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}