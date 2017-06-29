package com.autoremoteexample.polio;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.autoremoteexample.polio.common.Constants;
import com.autoremoteexample.polio.common.FileOpsUtil;
import com.autoremoteexample.polio.common.NetworkListener;
import com.autoremoteexample.polio.model.LabInfo;
import com.autoremoteexample.polio.model.PolioScannerData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FinalActivityLab extends AppCompatActivity implements View.OnClickListener {

    private PolioScannerData polioData;
    private Button nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_lab);

        nav = (Button) findViewById(R.id.Nav2Home);
        nav.setOnClickListener(this);
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


//        FileOpsUtil.write2File(Constants.FILE_NAME,polioData);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Nav2Home:

                Intent nextScreen = new Intent(getApplicationContext(), Home.class);
//                nextScreen.putExtra("polioData",(Parcelable)null);
                startActivity(nextScreen);

                break;
        }
    }
}
