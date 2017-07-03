package com.autoremoteexample.polio;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.autoremoteexample.polio.common.Constants;
import com.autoremoteexample.polio.common.FileOpsUtil;
import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FinalActivityField extends AppCompatActivity implements View.OnClickListener  {

    private static PolioScannerData polioData;
    private Button navField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_field);

        navField = (Button) findViewById(R.id.doneButton);
        navField.setOnClickListener(this);

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

        try {
//            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");


//        TextView t = (TextView) findViewById(R.id.textView);
//        t.setText(polioData.getTempreture() + ", " + polioData.getQrCodeContent() + ",date " + polioData.getElutedBydate() + ",ml: " + polioData.getRecoveredElutedmL());


            File file = new File(getBaseContext().getFilesDir(), Constants.FILE_NAME);
            FileOutputStream fo = null;

            fo = openFileOutput(Constants.FILE_NAME, getBaseContext().MODE_APPEND);
            FileOpsUtil.write2File(fo, file, polioData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "There is a problem in Opening File! REASON:" + e.getMessage() + ",Location=" + this.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::error in Opening file in " + this.getClass().getCanonicalName());
        }

        //////////////////


//////////////
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkListener receiver = new NetworkListener();
        registerReceiver(receiver, filter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doneButton:

                Intent nextScreen = new Intent(getApplicationContext(), Home.class);
//                nextScreen.putExtra("polioData",(Parcelable)null);
                startActivity(nextScreen);

                break;
        }
    }
}
