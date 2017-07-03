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

public class StartingSettelingField extends AppCompatActivity {

    private static PolioScannerData polioData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_setteling_field);

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
//        switch (item.getItemId()){
//            case R.id.myToolbarItem:
//                // handle submitting the items
//                saveVolume();
//                return true;
////                break;
//            default:
//                break;
//        }
//        return(super.onOptionsItemSelected(item));
//    }

    public void saveVolume(){

        try {
            EditText t = (EditText) findViewById(R.id.volumeTextField);
//            float tempreture = Float.parseFloat(t.getText());
//            Toast.makeText(getApplicationContext(),
//                               t.getText(), Toast.LENGTH_LONG).show();

            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");

            polioData.setFieldVolume(Float.parseFloat(t.getText().toString()));

//        int degreeTemp = temp.getValue();

//            Toast.makeText(this, " "+ polioData.getFieldVolume()+"  L", Toast.LENGTH_LONG).show();
            Intent nextScreen = new Intent(getApplicationContext(), FinalActivityField.class);
            nextScreen.putExtra("polioData",(Parcelable)polioData);
            startActivity(nextScreen);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please fill out the required fields!", Toast.LENGTH_SHORT).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::oncreate():: ."+e.getMessage());
            e.printStackTrace();
        }
    }
    public void dummyButton2(View view)
    {
        saveVolume();
//        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//        nextScreen.putExtra("polioData", (Parcelable) polioData);
//        startActivity(nextScreen);
    }

}
