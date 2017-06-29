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
import android.widget.Toast;

import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;

public class StartingElutionLab extends AppCompatActivity {

//    private DatePicker elutedDate;
//    private EditText elutedBy;
    private PolioScannerData polioData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_elution_lab);
        Log.v("navidi", this.getClass().getCanonicalName() + "::oncreate()");

        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");
        }catch(Exception e){
            Log.e("navidi","error in reading poliodata scanner in StartingElutionLab");
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
//                saveStartingEluted();
//                return true;
////                break;
//            default:
//                break;
//        }
//        return(super.onOptionsItemSelected(item));
//    }

    public void saveStartingEluted(){

        Log.v("navidi", "saveStartingEluted()::Start");
        try {
            Log.v("navidi", "saveStartingEluted()::Inside TRY");
//            elutedBy = (EditText) findViewById(R.id.elutedByEditText);
//            elutedDate = (DatePicker) findViewById(R.id.elutedDatePicker);

//            polioData.setElutedByPerson(elutedBy.getText().toString().trim());

            polioData.setElutedByPerson(((EditText) findViewById(R.id.elutedByEditText)).getText().toString());

            DatePicker dp3 = ((DatePicker) findViewById(R.id.elutedDatePicker));
            polioData.setElutedBydate(dp3.getDayOfMonth() + "-" + dp3.getMonth() + "-" + dp3.getYear());

            Log.v("navidi", "saveStartingEluted()::Middle");

            // probably use SimpleDateformat instead! :)
//            polioData.setElutedBydate(elutedDate.getDayOfMonth() + "-" + elutedDate.getMonth() + "-" + elutedDate.getYear());
            Log.v("navidi", "saveStartingEluted()::Before Next Screen");

            Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
            nextScreen.putExtra("polioData", (Parcelable)polioData);
            startActivity(nextScreen);

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Please fill out the required fields!" , Toast.LENGTH_LONG).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::oncreate():: ."+e.getMessage());
            e.printStackTrace();

        }
    }
    public void dummyButton9(View view)
    {
//        Toast.makeText(getApplicationContext(),
//                "" + polioData.getElutedBydate() + ", "+ polioData.getElutedByPerson(), Toast.LENGTH_LONG).show();
        saveStartingEluted();
//        Intent nextScreen = new Intent(getApplicationContext(), FinalActivityLab.class);
//        nextScreen.putExtra("polioData", (Parcelable) polioData);
//        startActivity(nextScreen);
    }
}
