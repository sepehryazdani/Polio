package com.autoremoteexample.polio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LabOptionMenu extends AppCompatActivity implements View.OnClickListener {

    private Button receiptInLabratoryButton;
    private Button storageinLabButton;
    private Button preservativesButton;
    private Button startOfElutionButton;
    private Button endOfElutionButton;
    private Button secondaryConcenterationButton;
    private static PolioScannerData polioData;
    private View whichButtonWasClicked; // use this as navigation id to find out which button was clicked.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_option_menu);

        receiptInLabratoryButton = (Button) findViewById(R.id.receiptInLabratoryButton);
        receiptInLabratoryButton.setOnClickListener(this);

        storageinLabButton = (Button) findViewById(R.id.storageinLabButton);
        storageinLabButton.setOnClickListener(this);

        preservativesButton = (Button) findViewById(R.id.preservativesButton);
        preservativesButton.setOnClickListener(this);

        startOfElutionButton = (Button) findViewById(R.id.startOfElutionButton);
        startOfElutionButton.setOnClickListener(this);

        endOfElutionButton = (Button) findViewById(R.id.EndOfElutionButton);
        endOfElutionButton.setOnClickListener(this);

        secondaryConcenterationButton = (Button) findViewById(R.id.SecondaryConcenterationButton);
        secondaryConcenterationButton.setOnClickListener(this);


        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
        } catch (Exception e) {
            Log.e("navidi", "error in reading poliodata scanner in LAB MENUE");
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkListener receiver = new NetworkListener();
        registerReceiver(receiver, filter);


    }

    // scanning the QR code will be happened in here
    public void scanNow(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan QR barcode");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
        Log.v("navidi", "ScanNow()::");
    }

    // handling the scanned stuff will happen here. so the scnaNow will trigger this method.
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result

        try {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            Log.v("navidi", this.getClass().getCanonicalName() + "OnActivityResult()::");

            if (scanningResult != null && scanningResult.getContents() != null && scanningResult.getFormatName() != null) {
                Log.v("navidi", "OnActivityResult():: scanning result != null");
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                polioData.setQrCodeContent(scanContent);
                polioData.setQrCodeFormat(scanFormat);
                Log.v("navidi", "OnActivityResult()::adding to poliodata");
                Intent nextScreen = null;
//            //initiating the qr code scan
//
//            // geeting the id and find out which button and then
//            // navigte the proper intent from there call the proper function, maybe some changes.
                Log.v("navidi", "OnActivityResult()::before switch");


                switch (whichButtonWasClicked.getId()) {
//

                    case R.id.receiptInLabratoryButton:
//
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to RecievedInLab");
//                    handleRecieptInLabratoryButton(whichButtonWasClicked);
                        nextScreen = new Intent(getApplicationContext(), RecievedInLab.class);
                        Log.v("navidi", "receiovedLab after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", "receiovedLab after putting Extra");
                        startActivity(nextScreen);
//
                        break;
//
                    case R.id.storageinLabButton:
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to storage lab");
//                    handleStorageInLabratoryButton(whichButtonWasClicked);
                        nextScreen = new Intent(getApplicationContext(), RecordTempLab.class);
                        Log.v("navidi", "receiovedLab after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", "receiovedLab after putting Extra");
                        startActivity(nextScreen);
                        break;
//
                    case R.id.preservativesButton:
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to preservatives");

//                    handlePresevativesInLabratoryButton(whichButtonWasClicked);
                        nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
                        Log.v("navidi", "preservatives after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", "preservatives after putting Extra");
                        startActivity(nextScreen);
                        break;
//
                    case R.id.startOfElutionButton:
//                    handleStartOfElutionInLabratoryButton(whichButtonWasClicked);
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to start elution");
                        nextScreen = new Intent(getApplicationContext(), StartingElutionLab.class);
                        Log.v("navidi", " start elution after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", " start elution after putting Extra");
                        startActivity(nextScreen);
                        break;
//
                    case R.id.EndOfElutionButton:
//                    handleEndOfElutionInLabratoryButton(whichButtonWasClicked);
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to end elution");
                        nextScreen = new Intent(getApplicationContext(), FinalRecoveredElutedLab.class);
                        Log.v("navidi", "end elution after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", "end elution after putting Extra");
                        startActivity(nextScreen);
                        break;

                    case R.id.SecondaryConcenterationButton:
//                    handleEndOfElutionInLabratoryButton(whichButtonWasClicked);
                        Log.v("navidi", this.getClass().getCanonicalName() + "::in Scanning QR was done and now we are navigating to secondary concenteration page");
                        nextScreen = new Intent(getApplicationContext(), RecordingSecondryConsentration.class);
                        Log.v("navidi", "secondary concenteration  after getting Context");
                        nextScreen.putExtra("polioData", (Parcelable) polioData);
                        Log.v("navidi", this.getClass().getCanonicalName() + "::secondary concenteration  after putting Extra");
                        startActivity(nextScreen);
                        break;


                    default:
                        break;
                }


            } else {
                Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT).show();
                Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                nextScreen.putExtra("polioData", (Parcelable) polioData);
                startActivity(nextScreen);
            }
        }catch(Exception e){
            Log.e("navidi" ,this.getClass().getCanonicalName() + ":: Unknown Error.");
        }
    }

    @Override
    public void onClick(View view) {

        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
            whichButtonWasClicked = view;
//            scanNow(view);
            //initiating the qr code scan
            switch (view.getId()) {

                case R.id.receiptInLabratoryButton:

                    handleRecieptInLabratoryButton(view);
//                        scanNow(view);
                    break;
//
                case R.id.storageinLabButton:
                    handleStorageInLabratoryButton(view);
//                    scanNow(view);
                    break;
//
                case R.id.preservativesButton:
                    handlePresevativesInLabratoryButton(view);
//                    scanNow(view);
                    break;
//
                case R.id.startOfElutionButton:
//                    Log.d("navidi", this.getClass().getCanonicalName() + "::in switch");
//                    scanNow(view);
                    handleStartOfElutionInLabratoryButton(view);
//                    scanNow(view);
                    break;
//
                case R.id.EndOfElutionButton:
                    handleEndOfElutionInLabratoryButton(view);
//                    scanNow(view);
                    break;
//
                case R.id.SecondaryConcenterationButton:
                    handleSecondaryConLabratoryButton(view);
//                    scanNow(view);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Exception in LAB menue processing", Toast.LENGTH_LONG).show();

        }
    }



    public void handleRecieptInLabratoryButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(LabOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage("Has the sample been recieved and is currently processing in the laboratory?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        scanNow(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), RecievedInLab.class);
//                        nextScreen.putExtra("polioData", (Parcelable) polioData);
//                        startActivity(nextScreen);
                    }
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
//        alert.show();
//        alert.getWindow().setAttributes(lp);
    }


    /**
     * handle StorageLab button click request
     *
     * @param view
     */
    public void handleStorageInLabratoryButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(LabOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage("Is the sample stored prior to preservative addition or elution?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        scanNow(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), RecordTempLab.class);
//                        nextScreen.putExtra("polioData", (Parcelable) polioData);
//                        startActivity(nextScreen);
                    }
                }
        );

        mDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
//        AlertDialog alert = mDialog.setView(new View(this)).create();
        // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(alert.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        AlertDialog alert = mDialog.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
//        alert.show();
//        alert.getWindow().setAttributes(lp);
    }


    public void handlePresevativesInLabratoryButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(LabOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage("Has preservatives been added?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        scanNow(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//                        nextScreen.putExtra("polioData", (Parcelable) polioData);
//                        startActivity(nextScreen);

                    }
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


    public void handleStartOfElutionInLabratoryButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(LabOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage("Has sample elution just begun?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Log.v("navidi", this.getClass().getCanonicalName() + "::start elution in lab before scan");

                        scanNow(view);
                        Log.v("navidi", this.getClass().getCanonicalName() + "::after scan");
//                        Intent nextScreen = new Intent(getApplicationContext(), StartingElutionLab.class);
//
//                        nextScreen.putExtra("polioData", (Parcelable) polioData);
//                        startActivity(nextScreen);
                    }
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

    public void handleEndOfElutionInLabratoryButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(LabOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage("Has sample elution just ended?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(),
//                                "11a", Toast.LENGTH_LONG).show();
                        scanNow(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), FinalRecoveredElutedLab.class);
//                        nextScreen.putExtra("polioData", (Parcelable) polioData);
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


    public void handleSecondaryConLabratoryButton(final View view) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(LabOptionMenu.this);

        mDialog.setCancelable(false);

        mDialog.setMessage("Are you performing secondary concentration?");
        mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        scanNow(view);
//                        Intent nextScreen = new Intent(getApplicationContext(), RecievedInLab.class);
//                        nextScreen.putExtra("polioData", (Parcelable) polioData);
//                        startActivity(nextScreen);
                    }
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
//        alert.show();
//        alert.getWindow().setAttributes(lp);
    }



}
