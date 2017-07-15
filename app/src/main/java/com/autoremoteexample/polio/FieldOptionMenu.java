package com.autoremoteexample.polio;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class FieldOptionMenu extends AppCompatActivity implements View.OnClickListener {

    private Button samplePreparationButton;
    private Button startSettlingButton;
    private Button startFiltrationButton;
    private Button endFiltrationButton;
    private Button shipButton;

    //    private TextView t;
    private static PolioScannerData polioData;
    private View whichButtonWasClickedField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_option_menu);

//        t = (TextView) findViewById(R.id.fieldText);

//        Intent thisIntent = getIntent();
        // now we are getting what we set in the first activity
//        t.setText(thisIntent.getStringExtra("format") + " " +  thisIntent.getStringExtra("content") );

        samplePreparationButton = (Button) findViewById(R.id.samplePreparationButton);
        samplePreparationButton.setOnClickListener(this);

        startSettlingButton = (Button) findViewById(R.id.startSettlingButton);
        startSettlingButton.setOnClickListener(this);

        startFiltrationButton = (Button) findViewById(R.id.startFiltrationButton);
        startFiltrationButton.setOnClickListener(this);

        endFiltrationButton = (Button) findViewById(R.id.endFiltrationButton);
        endFiltrationButton.setOnClickListener(this);

        shipButton = (Button) findViewById(R.id.shipButton);
        shipButton.setOnClickListener(this);
       

        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
        }
        catch (Exception e) {
            Log.e("navidi", "error in reading poliodata scanner in FIELD MENUE");
        }


        try {
            //new waY OF READING PARCELABLE
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                throw new NullPointerException(this.getClass().getCanonicalName() + "=Can't Retrived the Passed data in this activity!");

            }

            polioData = (PolioScannerData) bundle.getParcelable("polioData");
            Log.d("navidi", this.getClass().getCanonicalName() + "::OnCreate()::Retrive Data from previous page.");

            Log.d("navidi", this.getClass().getCanonicalName() + "::onCreate()\n\t::SampleId from polioDatascanner before save into file:" + polioData.getSample_id()
                    + "\n\t::LAT from polioDatascanner before save into file:" + polioData.getLat()
                    + "\n\t::LNG from polioDatascanner before save into file:" + polioData.getLng()
                    + "\n\t::QR_COMTENT from polioDatascanner before save into file:" + polioData.getQrCodeContent());
// End of reading parcelable in new way

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "There is a problem in Retriveing Data from Previous page! REASON:" + e.getMessage() + ",SOURCE:" + this.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::error in reading poliodata scanner in " + this.getClass().getCanonicalName());
        }

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkListener receiver = new NetworkListener();
        registerReceiver(receiver, filter);


    }

    public void scanNowField(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan QR barcode");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureActivityPortait.class);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
        Log.v("navidi", "ScanNowField()::");

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        try {
            //retrieve scan result
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            Log.v("navidi", "OnActivityResult()::");

            if (scanningResult != null) {
                Log.v("navidi", "OnActivityResult():: scanning result != null");
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();

                Log.d("navidi", "OnActivityResult()::SCANNED content:  " + scanContent);
                Log.d("navidi", "OnActivityResult()::SCANNED format:  " + scanFormat);

                if(scanContent==null){
                    throw new NullPointerException("can't finish scanning properly.");
                }

                polioData.setQrCodeContent(scanContent);
                polioData.setQrCodeFormat(scanFormat);

                Log.v("navidi", this.getClass().getCanonicalName() + "OnActivityResult()::"+polioData.getQrCodeContent());
                //breaking up the content from QR code:
                String[] commaSeperatedPairTokens = scanContent.replace("/","|").split(",");
                for(String pairToken : commaSeperatedPairTokens ){
                    String[] tokens = pairToken.split(":");
                    Log.v("navidi", this.getClass().getCanonicalName() + "OnActivityResult(r)::Before Processing:"+pairToken + ",("+tokens[0] + ")=" + tokens[1] );
                    // token[0] --> KEY
                    // toekn[1] --> value
                    if(tokens[0].equalsIgnoreCase("Sample Id")){
                        polioData.setSample_id(tokens[1].trim());
                        Log.v("navidi", this.getClass().getCanonicalName() + "::OnActivityResult()::Sample ID=" + polioData.getSample_id());

                    }else if(tokens[0].equalsIgnoreCase("Lat")){
                        polioData.setLat(tokens[1].trim());
                        Log.v("navidi", this.getClass().getCanonicalName() + "::OnActivityResult()::Lat=" + polioData.getLat());
                    }else if(tokens[0].equalsIgnoreCase("Lng")){
                        polioData.setLng(tokens[1].trim());
                        Log.v("navidi", this.getClass().getCanonicalName() + "::OnActivityResult()::Lng=" + polioData.getLng());

                    }else{
                        Log.v("navidi", this.getClass().getCanonicalName() + "::OnActivityResult()::["+tokens[0]+"]=" + tokens[1]);
                    }

                }



                Log.v("navidi", this.getClass().getCanonicalName() + "::OnActivityResult()::adding to poliodata");


                Log.v("navidi", "OnActivityResult()::adding to poliodata");
                Intent nextScreen = null;
//            //initiating the qr code scan
//
//            // geeting the id and find out which button and then
//            // navigte the proper intent from there call the proper function, maybe some changes.
                Log.v("navidi", "OnActivityResult()::before switch");
                switch (whichButtonWasClickedField.getId()) {
//
                    case R.id.samplePreparationButton:
//
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to SamplePreparation");
//                    handleRecieptInLabratoryButton(whichButtonWasClicked);
                        nextScreen = new Intent(getApplicationContext(), SamplePreparation.class);
                        Log.v("navidi", "SamplePreparation after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", "SamplePreparation after putting Extra");
                        startActivity(nextScreen);
//
                        break;
//
                    case R.id.startSettlingButton:
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to StartingSettelingField");
//                    handleStorageInLabratoryButton(whichButtonWasClicked);
                        nextScreen = new Intent(getApplicationContext(), StartingSettelingField.class);
                        Log.v("navidi", "StartingSettelingField after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", "StartingSettelingField after putting Extra");
                        startActivity(nextScreen);
                        break;
//
                    case R.id.startFiltrationButton:
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to StartingFiltrationField");

//                    handlePresevativesInLabratoryButton(whichButtonWasClicked);
                        nextScreen = new Intent(getApplicationContext(), StartingFiltrationField.class);
                        Log.v("navidi", "StartingFiltrationField after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", "StartingFiltrationField after putting Extra");
                        startActivity(nextScreen);
                        break;
//
                    case R.id.endFiltrationButton:
//                    handleStartOfElutionInLabratoryButton(whichButtonWasClicked);
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to EndingFiltrationField");
                        nextScreen = new Intent(getApplicationContext(), EndingFiltrationField.class);
                        Log.v("navidi", " EndingFiltrationField after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", " EndingFiltrationField after putting Extra");
                        startActivity(nextScreen);
                        break;
//
                    case R.id.shipButton:
//                    handleEndOfElutionInLabratoryButton(whichButtonWasClicked);
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to ShippingField");
                        nextScreen = new Intent(getApplicationContext(), ShippingField.class);
                        Log.v("navidi", "ShippingField after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", "ShippingField after putting Extra");
                        startActivity(nextScreen);
                        break;


                    default:
                        break;
                }


            } else {
                Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Oops! An unexpected event happened, so we are taking back to Home page!", Toast.LENGTH_SHORT).show();

            Log.e("navidi", e.toString());
            Intent nextScreen = new Intent(getApplicationContext(), Home.class);
            startActivity(nextScreen);
        }
    }

    @Override
    public void onClick(View view) {
        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
            whichButtonWasClickedField = view;
            switch (view.getId()) {

                case R.id.samplePreparationButton:
                    handleSamplePreparationButton(view);
                    break;

                case R.id.startSettlingButton:
                    handleStartSettlingButton(view);
                    break;

                case R.id.startFiltrationButton:
                    handleStartFiltrationButton(view);
                    break;

                case R.id.endFiltrationButton:
                    handleEndFiltrationButton(view);
                    break;

                case R.id.shipButton:
                    handleShipButton(view);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "NULL", Toast.LENGTH_LONG).show();
        }
    }

    private void handleSamplePreparationButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(FieldOptionMenu.this);

        mDialog.setCancelable(true);

        mDialog.setMessage("Are you begining to prepare to take the BMFS sample?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        scanNowField(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), SamplePreparation.class);
//                        nextScreen.putExtra("polioData",(Parcelable)polioData);
//                        startActivity(nextScreen);
                    }
//                dialog.cancel();
                }
        );

        mDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = mDialog.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
    }

    private void handleShipButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(FieldOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage(Html.fromHtml("Is the sample ready to be packed for shipment to the processing laboratory?"));
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        scanNowField(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), ShippingField.class);
//                        nextScreen.putExtra("polioData",(Parcelable) polioData);
//                        startActivity(nextScreen);
                    }
//                dialog.cancel();
                }
        );

        mDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = mDialog.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
    }

    private void handleEndFiltrationButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(FieldOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage("Have you just completed the sample filtration?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        scanNowField(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), EndingFiltrationField.class);
//                        nextScreen.putExtra("polioData",(Parcelable)polioData);
//                        startActivity(nextScreen);
                    }
//                dialog.cancel();
                }
        );

        mDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = mDialog.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
    }

    private void handleStartFiltrationButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(FieldOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage(Html.fromHtml("Has the bag already been hung on the tripod <b>and</b> the settled volume bled off?"));
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        scanNowField(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), StartingFiltrationField.class);
//                        nextScreen.putExtra("polioData",(Parcelable)polioData);
//                        startActivity(nextScreen);
                    }
//                dialog.cancel();
                }
        );

        mDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = mDialog.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
    }

    private void handleStartSettlingButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(FieldOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage("Have you just hung the bag on the tripod?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        scanNowField(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), StartingSettelingField.class);
//                        nextScreen.putExtra("polioData",(Parcelable)polioData);
//                        startActivity(nextScreen);
                    }
//                dialog.cancel();
                }
        );

        mDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = mDialog.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
    }

}
