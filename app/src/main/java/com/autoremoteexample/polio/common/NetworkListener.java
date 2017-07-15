package com.autoremoteexample.polio.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.autoremoteexample.polio.model.PolioScannerData;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Unknown_ on 6/6/2017.
 */

public class NetworkListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.d("getting network connectivity");

        try {
            if (APIFunctionalities.syncData(context)) {
//             Log.d("Connected");

                File file = new File(context.getFilesDir(), Constants.FILE_NAME);

                ArrayList<PolioScannerData> data = FileOpsUtil.readFile(file);
                if (data != null) {
                    Log.v("navidi", this.getClass().getCanonicalName() + "::onReceive()::OnWifi_Reading File, Size:" + data.size());

                    for (PolioScannerData p : data) {
                        Log.v("navidi", this.getClass().getCanonicalName() + ":: Data is being Sync'ed:" + p.getQrCodeContent());

                        // prepareing JSON object from PolioDataScanner obj
                        JSONObject polioJSON = APIFunctionalities.prepareJSON(p);

                        Log.v("navidi", "" + this.getClass().getCanonicalName() + ":: onReceive()::before checking the JSON content...");
                        if (polioJSON.length() > 0) {
                            Log.v("navidi", "" + this.getClass().getCanonicalName() + ":: onReceive():: Checking content ... before calling sendDatToSErver");
                            Log.d("navidi", "" + this.getClass().getCanonicalName() + ":: onReceive():: JSON content =\n" + polioJSON.toString());
//                        new SendDataToServer().execute(String.valueOf(polioJSON),p.getSample_id());
                            // make a list for Async thread
//                            ArrayList<String> packedDataReady2Send = new ArrayList<String>();
                            String[] packedDataReady2Send = {"", ""};
                            packedDataReady2Send[0] = p.getSample_id();
                            packedDataReady2Send[1] = String.valueOf(polioJSON);
                            Log.d("navidi", "" + this.getClass().getCanonicalName() + ":: onReceive():: Prepared Arraylist");
                            //Calling API from here
                            new SendDataToServer().execute(packedDataReady2Send);
                            Log.v("navidi", "" + this.getClass().getCanonicalName() + ":: After calling sendDataToSErver");

                        }
                        // end of calling API
                    }


                    try {

                        // DO NOT delete the file, since in process of saving the current data inside the app, the application may wanted to connect to wifi and sync data! Anyway, JUST DO NOT DELETE the file.
                        new FileOutputStream(file).close(); // just opening the same file and closing it, will clear that file out! :)
                        Log.d("navidi", this.getClass().getCanonicalName() + "::Finish syncing, just clearing the file out.");
                    } catch (IOException e) {
                        Log.d("navidi", this.getClass().getCanonicalName() + "::Issue in clearing the file:" + e.toString());
                    }

                } else {

                    Log.d("navidi", this.getClass().getCanonicalName() + ":: No data to Sync - Collection is NULL");
                    Toast.makeText(context, "Finish Syncing process. No Data to Sync.", Toast.LENGTH_LONG).show();
                }


            }
        } catch (Exception e) {
            Toast.makeText(null, "Oops! Something bad happened in Calling the Server API. MSG:" + e.getMessage() + "| REASON:" + e.getCause(), Toast.LENGTH_LONG).show();
            Log.e("navidi", this.getClass().getCanonicalName() + "::onRecieved()::" + e.getMessage() + "::" + e.getCause());
            //e.printStackTrace();
        }


    }
}
