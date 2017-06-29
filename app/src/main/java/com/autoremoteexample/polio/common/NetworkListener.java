package com.autoremoteexample.polio.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.autoremoteexample.polio.model.PolioScannerData;

import org.json.JSONException;
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
        if (APIFunctionalities.syncData(context)) {
//             Log.d("Connected");

            File file = new File(context.getFilesDir(), Constants.FILE_NAME);
            ArrayList<PolioScannerData> data = FileOpsUtil.readFile(file);
            if (data != null) {
                Log.v("navidi", "OnWifi_Reading File, Size:" + data.size());

                for (PolioScannerData p : data) {
                    Log.v("navidi", this.getClass().getCanonicalName() +":: Data is being Sync'ed:" + p.getQrCodeContent());
                    // CALLING API FROM HERE

                    JSONObject post_dict = new JSONObject();

                    try {

                        post_dict.put("lat" ,"33333" ); // p.getLat());
                        post_dict.put("lng" , "66666"); //p.getLng());
                        post_dict.put("note" , APIFunctionalities.convert2CommaSeperatedString(p) ); // creating comma seperated String and sending them to server side via API call.
                        Log.v("navidi","" + this.getClass().getCanonicalName() + ":: after init the JSON");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.v("navidi","" + this.getClass().getCanonicalName() + ":: before IF");
                    if (post_dict.length() > 0) {
                        Log.v("navidi","" + this.getClass().getCanonicalName() + ":: after if before calling sendDatToSErver");
                        new SendDataToServer().execute(String.valueOf(post_dict),p.getSample_id());
                        Log.v("navidi","" + this.getClass().getCanonicalName() + ":: after IF and after calling sendDataToSErver");

//                        if(!polioJsonResponse.contains("200")){
//                            Toast.makeText(null, "ERROR: Calling API--" + polioJsonResponse, Toast.LENGTH_LONG).show();
//                        }

                    }
                    // end of calling API
                }


                try {
                    // DO NOT delete the file, since in process of saving the current data inside the app, the application may wanted to connect to wifi and sync data! Anyway, JUST DO NOT DELETE the file.
                    new FileOutputStream(file).close(); // just opening the same file and closing it, will clear that file out! :)
                    Log.d("navidi", this.getClass().getCanonicalName() +"::Finish syncing, just clearing the file out." );
                } catch (IOException e) {
                    Log.d("navidi", this.getClass().getCanonicalName() +"::Issue in clearing the file:" + e.toString());
                }

            } else {

                Log.d("navidi", this.getClass().getCanonicalName() +":: No data to Sync - Collection is NULL");
                Toast.makeText(context, "Finish Syncing process. No Data to Sync.", Toast.LENGTH_LONG).show();
            }


        }

    }
}
