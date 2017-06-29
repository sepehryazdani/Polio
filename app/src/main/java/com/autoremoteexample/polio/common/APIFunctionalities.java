package com.autoremoteexample.polio.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.autoremoteexample.polio.model.PolioScannerData;

/**
 * Created by Unknown_ on 6/6/2017.
 */

public class APIFunctionalities {

    public static boolean syncData(Context ctx){

        try {
            ConnectivityManager cm =
                    (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            //getting the type of connectivity
//            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            return isConnected;

        }catch(Exception e){
//            return false; // can't make a connection out
//            e.printStackTrace();
            return false;
        }
    }


    public static String convert2CommaSeperatedString(PolioScannerData polioData){

        StringBuilder sb = new StringBuilder();
        sb.append(polioData.getLat() +  ",");
        sb.append(polioData.getLng() +  ",");
        sb.append(polioData.getSample_id());
        sb.append(polioData.getTempreture());
//adding the rest of fields in here

        return sb.toString();

    }


}
