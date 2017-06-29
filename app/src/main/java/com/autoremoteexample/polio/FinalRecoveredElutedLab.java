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

public class FinalRecoveredElutedLab extends AppCompatActivity {

    private PolioScannerData polioData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_recovered_eluted_lab);
        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
        } catch (Exception e) {
            e.printStackTrace();
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
//                saveEndRecoveredEluted();
//                return true;
////                break;
//            default:
//                break;
//        }
//        return (super.onOptionsItemSelected(item));
//    }

    public void saveEndRecoveredEluted() {

        try {
            //save data from this page also
            float vol = Float.parseFloat(((EditText) findViewById(R.id.recoveredElutedmL)).getText().toString());

            polioData.setRecoveredElutedmL(vol);

//            Toast.makeText(getApplicationContext(),
//                    "" + polioData.getRecoveredElutedmL(), Toast.LENGTH_LONG).show();

            Log.v("navidi", this.getClass().getCanonicalName() + "::ml from polio data::" + polioData.getRecoveredElutedmL());

            Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
            nextScreen.putExtra("polioData", (Parcelable) polioData);
            startActivity(nextScreen);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please fill out the required fields!", Toast.LENGTH_LONG).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::oncreate():: ."+e.getMessage());
            e.printStackTrace();

        }
    }
    public void dummyButton10(View view)
    {
        saveEndRecoveredEluted();
//        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//        nextScreen.putExtra("polioData", (Parcelable) polioData);
//        startActivity(nextScreen);
    }
}
