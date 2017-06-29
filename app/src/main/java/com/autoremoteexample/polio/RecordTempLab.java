package com.autoremoteexample.polio;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;

public class RecordTempLab extends AppCompatActivity {

    //    private NumberPicker temp;
    private PolioScannerData polioData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()::before activity");
        setContentView(R.layout.activity_record_temp_lab);
        Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()::after activity");
//        temp = (NumberPicker) findViewById(R.id.numberPicker2);
//        temp.setMaxValue(100);
//        temp.setValue(5); // like the diagram
//        temp.setOnClickListener(this);
        try {
            Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()::before reading poliodata from prev activity.");
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
            Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()::after reading poliodata from prev activity.");
        } catch (Exception e) {
            Log.e("navidi", this.getClass().getCanonicalName() + "::error in reading poliodata scanner in receivedLAB");
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
//                saveTempreture();
//                return true;
////                break;
//            default:
//                break;
//        }
//        return (super.onOptionsItemSelected(item));
//    }

    public void saveTempreture() {

        try {
            Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()::start saving data .");
            EditText t = (EditText) findViewById(R.id.tempTextField);
//            float tempreture = Float.parseFloat(t.getText());
//            Toast.makeText(getApplicationContext(),
//                               t.getText(), Toast.LENGTH_LONG).show();


//            if(t.getText()!=null && t.getText().toString().equalsIgnoreCase("")) {
                polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
                polioData.setTempreture(Float.parseFloat(t.getText().toString()));
                Log.d("navidi",this.getClass().getCanonicalName()+"::saveTemp()::QR::" + polioData.getQrCodeContent());
                Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
                nextScreen.putExtra("polioData", (Parcelable) polioData);
                startActivity(nextScreen);
//            }
//            else
//            {
//                Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()::Else .");

//            }
//        int degreeTemp = temp.getValue();

            Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()::In try After Else .");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please fill out the required fields!", Toast.LENGTH_SHORT).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::oncreate():: ."+e.getMessage());
            e.printStackTrace();
        }
        Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()::End of 6.saveTempreture .");
    }

    public void dummyButton7(View view)
    {
        saveTempreture();
//        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//        nextScreen.putExtra("polioData", (Parcelable) polioData);
//        startActivity(nextScreen);
    }


}
