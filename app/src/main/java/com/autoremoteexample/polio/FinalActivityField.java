package com.autoremoteexample.polio;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.autoremoteexample.polio.common.Constants;
import com.autoremoteexample.polio.common.FileOpsUtil;
import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.PolioScannerData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FinalActivityField extends AppCompatActivity implements View.OnClickListener  {

    private PolioScannerData polioData;
    private Button navField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_field);

        navField = (Button) findViewById(R.id.doneButton);
        navField.setOnClickListener(this);
        try {
            polioData = (PolioScannerData) getIntent().getParcelableExtra("polioData");


//        TextView t = (TextView) findViewById(R.id.textView);
//        t.setText(polioData.getTempreture() + ", " + polioData.getQrCodeContent() + ",date " + polioData.getElutedBydate() + ",ml: " + polioData.getRecoveredElutedmL());


            File file = new File(getBaseContext().getFilesDir(), Constants.FILE_NAME);
            FileOutputStream fo = null;

            fo = openFileOutput(Constants.FILE_NAME, getBaseContext().MODE_APPEND);
            FileOpsUtil.write2File(fo, file, polioData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
