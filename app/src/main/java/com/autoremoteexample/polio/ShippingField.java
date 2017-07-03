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

public class ShippingField extends AppCompatActivity {

    private static PolioScannerData polioData;
//    private String receivedDate;
//    private String receivedBy;
//    private String tempreture;
//    private boolean receivedColdResult = false;

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
        setContentView(R.layout.activity_shipping_field);
        ((RadioGroup) findViewById(R.id.SampleColdShippedYesNoRadioGroup)).setOnCheckedChangeListener(ToggleListener);

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
//                saveShippingField();
//                return true;
//
//            default:
//                break;
//        }
//        return (super.onOptionsItemSelected(item));
//    }

    private void saveShippingField() {

        try {
            polioData.setFieldShippedtemp(Float.parseFloat(((EditText) findViewById(R.id.fieldShippedTempEditText)).getText().toString()));

//            Log.d("navidi",this.getClass().getCanonicalName() + "::saveshippingfield():: casted:" + Float.parseFloat(((EditText) findViewById(R.id.fieldShippedTempEditText)).getText().toString()));
            Log.d("navidi",this.getClass().getCanonicalName() + "::saveShippingField():: shipping field temp:" + polioData.getFieldShippedtemp());

            if(((EditText) findViewById(R.id.fieldShippedBy2TextEdit)).getText() == null || ((EditText) findViewById(R.id.fieldShippedBy2TextEdit)).getText().toString().equals("")){
                throw  new NullPointerException("Shipped By field is empty, please enter something!");
            }
            Log.d("navidi",this.getClass().getCanonicalName() + "::saveShippingField():: shipping field by before:" + ((EditText) findViewById(R.id.fieldShippedBy2TextEdit)).getText().toString());

            polioData.setFieldShippedBy2(((EditText) findViewById(R.id.fieldShippedBy2TextEdit)).getText().toString());
            Log.d("navidi",this.getClass().getCanonicalName() + "::saveShippingField():: shipping field by:" + polioData.getFieldShippedBy2());

            DatePicker dp = ((DatePicker) findViewById(R.id.fieldDatePicker2));
            polioData.setFieldShippedOn2(dp.getDayOfMonth() + "-" + dp.getMonth() + "-" + dp.getYear());
            Log.d("navidi",this.getClass().getCanonicalName() + "::saveShippingField():: shipping field time:" + polioData.getFieldShippedOn2());
            Log.d("navidi",this.getClass().getCanonicalName() + "::saveShippingField():: shipping field was cold?:" + polioData.getShippedCold());

            if(polioData.getShippedCold() == -1 ){
                throw new NullPointerException("How was the field, cold or not?");
            }

//            polioData.setFieldWaterPh(Float.parseFloat(((EditText) findViewById(R.id.fieldWaterPHEditText)).getText().toString()));

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

    public void onToggledShippedColdPacks(View view) {

//        polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");

        ((RadioGroup) view.getParent()).check(0); // force the toggle buttons to work exactly like radio button, don't toggle when double tab on one toggle btn
        // then set to the correct value.
        ((RadioGroup) view.getParent()).check(view.getId());

        //handling what needs to be done after toggling.
        RadioGroup togglegroup = ((RadioGroup) view.getParent());
        ToggleButton btn = (ToggleButton) findViewById(togglegroup.getCheckedRadioButtonId());

        if (btn.getText().toString().equalsIgnoreCase("Yes")) {
            //skimmed milk btn, so handle that one.
//            Toast.makeText(this, "yes", Toast.LENGTH_LONG).show();
            polioData.setShippedCold(Constants.FIELD_SHIPPED_COLD);


        } else if (btn.getTextOn().toString().equalsIgnoreCase("No")) {
//            Toast.makeText(this, "no", Toast.LENGTH_LONG).show();
            polioData.setShippedCold(Constants.FIELD_SHIPPED_NOT_COLD);

        }
    }
    public void dummyButton5(View view)
    {
        saveShippingField();
//        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//        nextScreen.putExtra("polioData", (Parcelable) polioData);
//        startActivity(nextScreen);
    }



}
