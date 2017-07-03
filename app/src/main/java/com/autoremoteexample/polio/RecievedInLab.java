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
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.autoremoteexample.polio.common.Constants;
import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;

public class RecievedInLab extends AppCompatActivity {

    private  static PolioScannerData polioData;
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

    static final RadioGroup.OnCheckedChangeListener ToggleListener2 = new RadioGroup.OnCheckedChangeListener() {
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
        Log.v("navidi", "REceievedinLab::OnCreate()::");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved_in_lab);
        //assign listener to the RadioGroup
        ((RadioGroup) findViewById(R.id.tempYesNoRadioGroup)).setOnCheckedChangeListener(ToggleListener);
        ((RadioGroup) findViewById(R.id.SampleColdYesNoRadioGroup)).setOnCheckedChangeListener(ToggleListener2);

        //first init -- Hide them all.
        TableRow tempretureQuestsionTableRow = (TableRow) findViewById(R.id.tempretureQuestsionTableRow);
        TableRow tempretureAnswerTableRow = (TableRow) findViewById(R.id.tempretureAnswerTableRow);
        tempretureQuestsionTableRow.setVisibility(View.GONE);
        tempretureAnswerTableRow.setVisibility(View.GONE);


        TableRow refrigQuestionTableRow = (TableRow) findViewById(R.id.refrigQuestionTableRow);
        TableRow refrigAnswerTableRow = (TableRow) findViewById(R.id.refrigAnswerTableRow);
        refrigQuestionTableRow.setVisibility(View.GONE);
        refrigAnswerTableRow.setVisibility(View.GONE);


        ((ToggleButton) findViewById(R.id.toggleButton1)).setSelected(false);
        ((ToggleButton) findViewById(R.id.toggleButton2)).setSelected(false);
        ((ToggleButton) findViewById(R.id.toggleButton3)).setSelected(false);
        ((ToggleButton) findViewById(R.id.toggleButton4)).setSelected(false);

        try {
//            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
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
            Toast.makeText(getApplicationContext(), "There is a problem in Retriveing Data from Previous page! REASON:"+e.getMessage()+",SOURCE:"+this.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::error in reading poliodata scanner in receivedLAB");
        }

        //init the cold or NOT
        polioData.setSampleCold(Constants.NOT_SELECTED_STATE);
        // init the tempreture field in revieced in lab
        polioData.setLabReceivedTemp(Float.NaN);

        // init the temp logger included
        polioData.setTempLoggerIncluded(Constants.NOT_SELECTED_STATE);
        polioData.setTempLoggerNotIncluded(Constants.NOT_SELECTED_STATE);

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
//        switch (item.getItemId()){
//            case R.id.myToolbarItem:
//                // handle submitting the items
//                saveRecieptInLab();
//                return true;
//
//            default:
//                break;
//        }
//        return(super.onOptionsItemSelected(item));
//    }

    public void saveRecieptInLab() {

        try {
            Log.d("navidi", this.getClass().getCanonicalName() + "::saveRecieptInLab()::start saving data .");

            if (((EditText) findViewById(R.id.labRecievedBy)).getText() == null || ((EditText) findViewById(R.id.labRecievedBy)).getText().toString().equalsIgnoreCase("")) {
                throw new NullPointerException("Doer name is null");
            }

            polioData.setLabRecievedBy(((EditText) findViewById(R.id.labRecievedBy)).getText().toString());
            Log.d("navidi", this.getClass().getCanonicalName() + "::saveRecieptInLab()::QR::" + polioData.getQrCodeContent());


            DatePicker dp1 = ((DatePicker) findViewById(R.id.labRecievedOn));
            polioData.setLabRecievedOn(dp1.getDayOfMonth() + "-" + dp1.getMonth() + "-" + dp1.getYear());

            if (polioData.getTempLoggerIncluded() != Constants.NOT_SELECTED_STATE && polioData.getTempLoggerNotIncluded() == Constants.NOT_SELECTED_STATE) {
// included so read the temp field
                if (((EditText) findViewById(R.id.labReceivedTemp)).getText() == null || ((EditText) findViewById(R.id.labReceivedTemp)).getText().toString().equalsIgnoreCase("")) {
                    throw new NullPointerException("Tempreture field is empty.");
                }
                polioData.setLabReceivedTemp(Float.parseFloat(((EditText) findViewById(R.id.labReceivedTemp)).getText().toString()));
                Log.d("navidi", this.getClass().getCanonicalName() + "::save()::temp:" + polioData.getLabReceivedTemp());
                polioData.setSampleCold(Constants.NOT_SELECTED_STATE);
                polioData.setSampleNotCold(Constants.NOT_SELECTED_STATE);

            } else if (polioData.getTempLoggerIncluded() == Constants.NOT_SELECTED_STATE && polioData.getTempLoggerNotIncluded() != Constants.NOT_SELECTED_STATE) {
                // check the other section and make sure they have proper values for
                if (polioData.getSampleCold() == Constants.NOT_SELECTED_STATE && polioData.getSampleNotCold() == Constants.NOT_SELECTED_STATE) {
                    throw new NullPointerException("sample is cold or not? nothing was selected.");
                }
                polioData.setTempLoggerNotIncluded(Constants.NOT_SELECTED_STATE);
                polioData.setTempLoggerIncluded(Constants.NOT_SELECTED_STATE);
                polioData.setLabReceivedTemp(Float.NaN);

            } else {
                throw new NullPointerException("temp logger included or not? nothing was selected.");
            }

            Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
            nextScreen.putExtra("polioData", (Parcelable) polioData);
            startActivity(nextScreen);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please fill out the required fields!", Toast.LENGTH_SHORT).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::saveRecieptInLab():: ." + e.getMessage());
            e.printStackTrace();
        }
        Log.d("navidi", this.getClass().getCanonicalName() + "::saveRecieptInLab()::End of 6.RecievedInLab .");


    }


    public void onToggleTempYesNo(View view) {


        ((RadioGroup) view.getParent()).check(0); // force the toggle buttons to work exactly like radiobutton, don't toggle when doule tab on one toggle btn
        // then set to the correct value.
        ((RadioGroup) view.getParent()).check(view.getId());

        //handling what needs to be done after toggling.
        RadioGroup togglegroup = ((RadioGroup) view.getParent());
        ToggleButton btn = (ToggleButton) findViewById(togglegroup.getCheckedRadioButtonId());

        if (btn.getText().toString().equalsIgnoreCase("Yes")) {
            //skimmed milk btn, so handle that one.
//            Toast.makeText(this, "yes", Toast.LENGTH_LONG).show();
            polioData.setTempLoggerIncluded(Constants.TEMP_LOGGER_INCLUDED);
            polioData.setTempLoggerNotIncluded(Constants.NOT_SELECTED_STATE);

            TableRow tempretureQuestsionTableRow = (TableRow) findViewById(R.id.tempretureQuestsionTableRow);
            TableRow tempretureAnswerTableRow = (TableRow) findViewById(R.id.tempretureAnswerTableRow);
            tempretureQuestsionTableRow.setVisibility(View.VISIBLE);
            tempretureAnswerTableRow.setVisibility(View.VISIBLE);


            TableRow refrigQuestionTableRow = (TableRow) findViewById(R.id.refrigQuestionTableRow);
            TableRow refrigAnswerTableRow = (TableRow) findViewById(R.id.refrigAnswerTableRow);
            refrigQuestionTableRow.setVisibility(View.GONE);
            refrigAnswerTableRow.setVisibility(View.GONE);


        } else if (btn.getTextOn().toString().equalsIgnoreCase("No")) {
////            Toast.makeText(this, "no", Toast.LENGTH_LONG).show();
            polioData.setTempLoggerNotIncluded(Constants.TEMP_LOGGER_NOT_INCLUDED);
            polioData.setTempLoggerIncluded(Constants.NOT_SELECTED_STATE);
//
            TableRow tempretureQuestsionTableRow = (TableRow) findViewById(R.id.tempretureQuestsionTableRow);
            TableRow tempretureAnswerTableRow = (TableRow) findViewById(R.id.tempretureAnswerTableRow);
            tempretureQuestsionTableRow.setVisibility(View.GONE);
            tempretureAnswerTableRow.setVisibility(View.GONE);


            TableRow refrigQuestionTableRow = (TableRow) findViewById(R.id.refrigQuestionTableRow);
            TableRow refrigAnswerTableRow = (TableRow) findViewById(R.id.refrigAnswerTableRow);
            refrigQuestionTableRow.setVisibility(View.VISIBLE);
            refrigAnswerTableRow.setVisibility(View.VISIBLE);

        }
    }

    public void onToggleSampleCold(View view) {


        ((RadioGroup) view.getParent()).check(0); // force thetoggle buttons to work exactly like radiobutton, don't toggle when doule tab on one toggle btn
        // then set to the correct value.
        ((RadioGroup) view.getParent()).check(view.getId());

        //handling what needs to be done after toggling.
        RadioGroup togglegroup = ((RadioGroup) view.getParent());
        ToggleButton btn = (ToggleButton) findViewById(togglegroup.getCheckedRadioButtonId());

        if (btn.getText().toString().equalsIgnoreCase("Yes")) {

//            Toast.makeText(this, "yes", Toast.LENGTH_LONG).show();

            polioData.setSampleCold(Constants.SAMPLE_COLD);
            polioData.setSampleNotCold(Constants.NOT_SELECTED_STATE);


        } else if (btn.getTextOn().toString().equalsIgnoreCase("No")) {
//           Toast.makeText(this, "no", Toast.LENGTH_LONG).show();

            polioData.setSampleNotCold(Constants.SAMPLE_NOT_COLD);
            polioData.setSampleCold(Constants.NOT_SELECTED_STATE);

        }
    }

    public void dummyButton6(View view) {
        saveRecieptInLab();
//        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//        nextScreen.putExtra("polioData", (Parcelable) polioData);
//        startActivity(nextScreen);
    }
}
