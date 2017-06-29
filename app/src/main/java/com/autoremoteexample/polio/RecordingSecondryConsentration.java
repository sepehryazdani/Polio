package com.autoremoteexample.polio;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.autoremoteexample.polio.common.Constants;
import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;

public class RecordingSecondryConsentration extends AppCompatActivity {


    private PolioScannerData polioData;
    private ToggleButton btn;
//    private EditText author;


    static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {


            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_secondry_consentration);
        ((RadioGroup) findViewById(R.id.secondaryConRadioGroup)).setOnCheckedChangeListener(ToggleListener);

        ((TextView) findViewById(R.id.textView5)).setVisibility(View.GONE);
        ((EditText) findViewById(R.id.methodConcentrationEditText)).setVisibility(View.GONE);
        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("navidi", this.getClass().getCanonicalName() + "::savemethod()");
        }

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkListener receiver = new NetworkListener();
        registerReceiver(receiver, filter);
    }
    //up top right hand side button

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.actionbar, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.myToolbarItem:
//                // handle submitting the items
//                checkData();
//                saveData();
//                return true;
//            default:
//                break;
//        }
//        return (super.onOptionsItemSelected(item));
//    }

    public void saveSecondaryConcentration() {

        Log.d("navidi", this.getClass().getCanonicalName() + "::saveSecondaryConcentration...");

        try {

//            Log.d("navidi", this.getClass().getCanonicalName() + "::saveSecondaryConcentration():: before date::" + polioData.getConcentratedOn());
            DatePicker dp4 = ((DatePicker) findViewById(R.id.concentrationDatePicker));

            polioData.setConcentratedOn(dp4.getDayOfMonth() + "-" + dp4.getMonth() + "-" + dp4.getYear());
            Log.d("navidi", this.getClass().getCanonicalName() + "::saveSecondaryConcentration():: getdate::" + polioData.getConcentratedOn());


            if (((EditText) findViewById(R.id.authorEditText)).getText() == null || ((EditText) findViewById(R.id.authorEditText)).getText().toString().equalsIgnoreCase("")) {
                throw new NullPointerException("Secondary Concentration is done By field Needs to be filled.1");
            }
            polioData.setConcentratedBy(((EditText) findViewById(R.id.authorEditText)).getText().toString());
            Log.d("navidi", this.getClass().getCanonicalName() + "::saveSecondaryConcentration():: audithor ::" + polioData.getConcentratedBy());

            //-----------------------------

            Log.d("navidi", this.getClass().getCanonicalName() + "::saveSecondaryConcentration():: before method::" + polioData.getSecConMethod());
            if (btn == null) {
                throw new NullPointerException("Secondary Concentration method field Needs to be filled.2");
            } else {

                if (btn.getText().toString().equalsIgnoreCase("Skimmed Milk Flocculation")) {

                    polioData.setSecConMethod(Constants.LAB_CONCENTRATION_METHOD_SKIMMED_MILK);

                } else if (btn.getTextOn().toString().equalsIgnoreCase("PEG/NaCI")) {

                    polioData.setSecConMethod(Constants.LAB_CONCENTRATION_METHOD_PEG);

                } else if (btn.getTextOn().toString().equalsIgnoreCase("other")) {

                    polioData.setSecConMethod(((EditText) findViewById(R.id.methodConcentrationEditText)).getText().toString());

                }


            }

            //checking if the Concentration method is null or not.
            if (polioData.getSecConMethod() == null || polioData.getSecConMethod().equalsIgnoreCase("")) {
                throw new NullPointerException("Secondary Concentration method field Needs to be filled.3");
            }

            Log.d("navidi", this.getClass().getCanonicalName() + "::saveSecondaryConcentration():: after method::" + polioData.getSecConMethod());

            Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
            nextScreen.putExtra("polioData", (Parcelable) polioData);
            startActivity(nextScreen);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please fill out the required fields!", Toast.LENGTH_LONG).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::saveSecondaryConcentration()::3" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * call this method to check all the data in this form
     * and make sure erverything is filled so far. don't save unless everyt field in this screen was filled.
     */

    public void onToggle(View view) {

        Log.d("navidi", this.getClass().getCanonicalName() + "::ontoggle()");

        ((RadioGroup) view.getParent()).check(0); // force the toggle buttons to work exactly like radiobutton, don't toggle when doule tab on one toggle btn
        // then set to the correct value.
        ((RadioGroup) view.getParent()).check(view.getId());

        //handling what needs to be done after toggling.
        RadioGroup togglegroup = ((RadioGroup) view.getParent());
        btn = (ToggleButton) findViewById(togglegroup.getCheckedRadioButtonId());


        if (btn.getText().toString().equalsIgnoreCase("Skimmed Milk Flocculation")) {
            //skimmed milk btn, so handle that one.
//            Toast.makeText(this, "Skimmed", Toast.LENGTH_LONG).show();
            ((TextView) findViewById(R.id.textView5)).setVisibility(View.GONE);
            ((EditText) findViewById(R.id.methodConcentrationEditText)).setVisibility(View.GONE);

            // polioData.setSecConMethod(Constants.LAB_CONCENTRATION_METHOD_SKIMMED_MILK);

        } else if (btn.getTextOn().toString().equalsIgnoreCase("PEG/NaCI")) {
//            Toast.makeText(this, "PEG", Toast.LENGTH_LONG).show();
            ((TextView) findViewById(R.id.textView5)).setVisibility(View.GONE);
            ((EditText) findViewById(R.id.methodConcentrationEditText)).setVisibility(View.GONE);

            //polioData.setSecConMethod(Constants.LAB_CONCENTRATION_METHOD_PEG);

        } else if (btn.getTextOn().toString().equalsIgnoreCase("other")) {
//            Toast.makeText(this, "other", Toast.LENGTH_LONG).show();

            ((TextView) findViewById(R.id.textView5)).setVisibility(View.VISIBLE);
            ((EditText) findViewById(R.id.methodConcentrationEditText)).setVisibility(View.VISIBLE);

//            polioData.setSecConMethod(Constants.LAB_CONCENTRATION_METHOD_OTHER);
            //polioData.setSecConMethod(((EditText) findViewById(R.id.methodConcentrationEditText)).getText().toString());
//            polioData.setSecConOtherMethod(((EditText) findViewById(R.id.methodConcentrationEditText)).getText().toString());

        }
    }

    public void dummyButton11(View view) {

        saveSecondaryConcentration();


//
//        Log.d("navidi", this.getClass().getCanonicalName() + "::hitting dummy button");
//        Log.d("navidi", "SecMethod: " + polioData.getSecConMethod()  + "| Date: " + polioData.getConcentratedOn() + ":By: " + polioData.getConcentratedBy());
//
////        try{
//        if (polioData.getConcentratedOn() != null &&
//                polioData.getConcentratedBy() != null  && polioData.getSecConMethod() != null) {
////            if(polioData.getSecConMethod()==  || polioData.getSecConMethod()==1 || polioData.getSecConMethod()== 2) {
////            polioData.getConcentratedBy();
//            Toast.makeText(getApplicationContext(), "SecMethod: " + polioData.getSecConMethod()  + "| Date: " + polioData.getConcentratedOn() + "By: " + polioData.getConcentratedBy(), Toast.LENGTH_LONG).show();
//            Log.d("navidi", "SecMethod: " + polioData.getSecConMethod()  + "| Date: " + polioData.getConcentratedOn() + "By: " + polioData.getConcentratedBy());
//            saveSecondaryConcentration();
//
////            else
////            {
////                Toast.makeText(getApplicationContext(), "Please fill out the required fields!", Toast.LENGTH_LONG).show();
////            }
//
//
//        } else {
//            Toast.makeText(getApplicationContext(), "1Please fill out the required fields!", Toast.LENGTH_LONG).show();
//        }
//        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//        nextScreen.putExtra("polioData", (Parcelable) polioData);
//        startActivity(nextScreen);
//        } catch (Exception e) {
//        Toast.makeText(getApplicationContext(), "Please fill out the required fields!", Toast.LENGTH_LONG).show();
//        Log.e("navidi", this.getClass().getCanonicalName() + "::oncreate():: ."+e.getMessage());
//        e.printStackTrace();
//    }
    }


}
