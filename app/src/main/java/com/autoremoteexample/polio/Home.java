package com.autoremoteexample.polio;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.autoremoteexample.polio.common.APIFunctionalities;
import com.autoremoteexample.polio.common.Constants;
import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;
import com.autoremoteexample.polio.service.GPSTracker;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private ImageButton fieldButton, labButton;
    //qr code scanner object
//    private IntentIntegrator qrScan;
    private boolean isInFieldPath = true;
    private static PolioScannerData polioData = new PolioScannerData();

    private Button syncData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fieldButton = (ImageButton) findViewById(R.id.FieldButton);
        labButton = (ImageButton) findViewById(R.id.LabButton);
        //attaching onclick listener
        fieldButton.setOnClickListener(this);
        labButton.setOnClickListener(this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkListener receiver = new NetworkListener();
        registerReceiver(receiver, filter);

        //hidding buttom buttons - back, etc.
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


    }


    // COMMENTED BEFORE CHANGING THE FLOW - MOVED TO LAB AND FIELD
//    @Override
//    public void onClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.FieldButton:
//                isInFieldPath = true;
//                scanNow(view);
//                break;
//            case R.id.LabButton:
//                isInFieldPath = false;
//                handleLabButton(view);
//                break;
//            default:
//                break;
//        }
//    }

// AFTER CHANGING THE FLOW

    @Override
    public void onClick(View view) {

//        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
//        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
//
//            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
//                    GPSTracker.MY_PERMISSION_ACCESS_COURSE_LOCATION );
//        }
        GPSTracker gps = new GPSTracker(this.getBaseContext());
        Double lng = gps.getLongitude();
        Double lat = gps.getLatitude();

        if(lng != null && lat != null){
            Log.d("navidi",this.getClass().getCanonicalName() + "::lat:" + lat + " lng:"+lng);
        }else{

            Log.e("navidi",this.getClass().getCanonicalName() + "::ERROR getting location!");
        }



        Intent nextScreen = null;
        switch (view.getId()) {
            case R.id.FieldButton:
//                handleFieldButton(view);
                nextScreen = new Intent(getApplicationContext(), FieldOptionMenu.class);
                nextScreen.putExtra("polioData", (Parcelable) polioData);

                startActivity(nextScreen);
                break;
            case R.id.LabButton:
//                isInFieldPath = false;
//                handleLabButton(view);
                nextScreen = new Intent(getApplicationContext(), LabOptionMenu.class);
                nextScreen.putExtra("polioData", (Parcelable) polioData);

                startActivity(nextScreen);
                break;
            default:
                break;
        }
    }


    // COMMENTED BEFORE CHANGING THE FLOW - MOVED TO LAB AND FIELD
//    public void scanNow(View view) {
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//        integrator.setPrompt("Scan QR barcode");
//        integrator.setCameraId(0);
//        integrator.setBeepEnabled(true);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
//    }

    // COMMENTED BEFORE CHANGING THE FLOW - MOVED TO LAB AND FIELD
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        //retrieve scan result
//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//
//        if (scanningResult != null) {
//
//            String scanContent = scanningResult.getContents();
//            String scanFormat = scanningResult.getFormatName();
//
//            Intent nextScreen = null;
//            if (isInFieldPath) {
//                nextScreen = new Intent(getApplicationContext(), FieldOptionMenu.class);
//
//            } else {
//                nextScreen = new Intent(getApplicationContext(), LabOptionMenu.class);
//            }
//
//            polioData.setQrCodeContent(scanContent);
//            polioData.setQrCodeFormat(scanFormat);
//            nextScreen.putExtra("polioData", (Parcelable)polioData);
//
//            startActivity(nextScreen);
//
//
//        } else {
//            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }


    public void handleLabButton(final View view) {

        AlertDialog.Builder mDialog = new AlertDialog.Builder(Home.this);

//        mDialog.setCancelable(false); // let others to cancel their decision after tapping one each of items in this page.
        mDialog.setMessage("Are you performing secondary concentration?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

//                        Intent nextScreen = new Intent(getApplicationContext(), RecordingSecondryConsentration.class);
                        Intent nextScreen = new Intent(getApplicationContext(), LabOptionMenu.class);
                        nextScreen.putExtra("polioData", (Parcelable) polioData);

                        startActivity(nextScreen);
                    }
                }
        );

        mDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    // open camera for canning barcode
                    public void onClick(DialogInterface dialog,
                                        int which) {
//                        isInFieldPath = false;
//                        scanNow(view);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = mDialog.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
    }


    public void handleFieldButton(final View view) {

        AlertDialog.Builder mDialog = new AlertDialog.Builder(Home.this);

//        mDialog.setCancelable(false); // let others to cancel their decision after tapping one each of items in this page.
        mDialog.setMessage("Are you performing secondary concentration?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent nextScreen = new Intent(getApplicationContext(), FieldOptionMenu.class);
                        nextScreen.putExtra("polioData", (Parcelable) polioData);

                        startActivity(nextScreen);
                    }
                }
        );

        mDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    // open camera for canning barcode
                    public void onClick(DialogInterface dialog,
                                        int which) {
//                        isInFieldPath = false;
//                        scanNow(view);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = mDialog.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
    }


}
