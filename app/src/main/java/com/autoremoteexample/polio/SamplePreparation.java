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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.autoremoteexample.polio.common.Constants;
import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;


public class SamplePreparation extends AppCompatActivity {

    private PolioScannerData polioData;

    static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };

    static final RadioGroup.OnCheckedChangeListener ToggleListenerToday = new RadioGroup.OnCheckedChangeListener() {
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
        setContentView(R.layout.activity_sample_preparation);
        ((RadioGroup) findViewById(R.id.rainYesNoGroup)).setOnCheckedChangeListener(ToggleListener);
        ((RadioGroup) findViewById(R.id.rainYesNoTodayGroup)).setOnCheckedChangeListener(ToggleListenerToday);

        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
        }catch(Exception e){
            Log.e("navidi",this.getClass().getCanonicalName() + "::error in reading poliodata scanner in receivedLAB");
        }

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkListener receiver = new NetworkListener();
        registerReceiver(receiver, filter);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        return super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.actionbar, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        return super.onOptionsItemSelected(item);
//
//        switch (item.getItemId()) {
//            case R.id.myToolbarItem:
//                // handle submitting the items
//                saveSamplePreparation();
//                return true;
//
//            default:
//                break;
//        }
//        return (super.onOptionsItemSelected(item));
//    }

    private void saveSamplePreparation() {

        try {
            polioData.setFieldtemp(Float.parseFloat(((EditText) findViewById(R.id.fieldTempEditText)).getText().toString()));
            polioData.setFieldShippedBy(((EditText) findViewById(R.id.fieldShippedByTextEdit)).getText().toString());
            DatePicker dp = ((DatePicker) findViewById(R.id.fieldDatePicker));
            polioData.setFieldShippedOn(dp.getDayOfMonth() + "-" + dp.getMonth() + "-" + dp.getYear());

            polioData.setFieldWaterPh(Float.parseFloat(((EditText) findViewById(R.id.fieldWaterPHEditText)).getText().toString()));

//            Toast.makeText(this, ""+ polioData.getFieldShippedBy()+"," + polioData.getFieldWaterPh(), Toast.LENGTH_LONG).show();
            Intent nextScreen = new Intent(getApplicationContext(), FinalActivityField.class);
            nextScreen.putExtra("polioData",(Parcelable)polioData);
            startActivity(nextScreen);


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please fill out the required fields!", Toast.LENGTH_SHORT).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::oncreate():: ."+e.getMessage());
            e.printStackTrace();
        }
    }


    public void onToggledRain(View view) {

        polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");

        ((RadioGroup) view.getParent()).check(0); // force the toggle buttons to work exactly like radio button, don't toggle when double tab on one toggle btn
        // then set to the correct value.
        ((RadioGroup) view.getParent()).check(view.getId());

        //handling what needs to be done after toggling.
        RadioGroup togglegroup = ((RadioGroup) view.getParent());
        ToggleButton btn = (ToggleButton) findViewById(togglegroup.getCheckedRadioButtonId());

        if (btn.getText().toString().equalsIgnoreCase("Yes")) {
            //skimmed milk btn, so handle that one.
//            Toast.makeText(this, "yes", Toast.LENGTH_LONG).show();
            polioData.setRainYesterday(Constants.FIELD_RAINY_YESTERDAY);


        } else if (btn.getTextOn().toString().equalsIgnoreCase("No")) {
//            Toast.makeText(this, "no", Toast.LENGTH_LONG).show();
            polioData.setRainYesterday(Constants.FIELD_NOT_RAINY_YESTERDAY);

        }
    }

    public void onToggledRainToday(View view) {

        polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");

        ((RadioGroup) view.getParent()).check(0); // force the toggle buttons to work exactly like radio button, don't toggle when double tab on one toggle btn
        // then set to the correct value.
        ((RadioGroup) view.getParent()).check(view.getId());

        //handling what needs to be done after toggling.
        RadioGroup togglegroup = ((RadioGroup) view.getParent());
        ToggleButton btn = (ToggleButton) findViewById(togglegroup.getCheckedRadioButtonId());

        if (btn.getText().toString().equalsIgnoreCase("Yes")) {
            //skimmed milk btn, so handle that one.
//            Toast.makeText(this, "yes", Toast.LENGTH_LONG).show();
            polioData.setRainToday(Constants.FIELD_RAINY_TODAY);


        } else if (btn.getTextOn().toString().equalsIgnoreCase("No")) {
//            Toast.makeText(this, "no", Toast.LENGTH_LONG).show();
            polioData.setRainToday(Constants.FIELD_NOT_RAINY_TODAY);


        }
    }
    public void dummyButton1(View view)
    {
        saveSamplePreparation();
//        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//        nextScreen.putExtra("polioData", (Parcelable) polioData);
//        startActivity(nextScreen);
    }
}
