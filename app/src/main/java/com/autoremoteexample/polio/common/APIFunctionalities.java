package com.autoremoteexample.polio.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.autoremoteexample.polio.model.PolioScannerData;

import org.json.JSONException;
import org.json.JSONObject;

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

//
//    public static String convert2CommaSeperatedString(PolioScannerData polioData){
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(polioData.getLat() +  ",");
//        sb.append(polioData.getLng() +  ",");
//        sb.append(polioData.getSample_id());
//        sb.append(polioData.getTempreture());
//
////adding the rest of fields in here
//
//        return sb.toString();
//
//    }


    public static JSONObject prepareJSON(final PolioScannerData p){

        JSONObject polioJSON = new JSONObject();

        try {

            polioJSON.put("lat", p.getLat());
            polioJSON.put("lng", p.getLng());
            polioJSON.put("sampleId", p.getSample_id()); // we need that from QR code.
//                            polioJSON.put("username",p.getUsername());
            if (p.getTempreture() != Float.MIN_VALUE) {
                polioJSON.put("tempreture", p.getTempreture());
            }
            if (p.getElutedByPerson() != null) {
                polioJSON.put("elutedByPerson", p.getElutedByPerson());
            }
            if (p.getElutedBydate() != null) {
                polioJSON.put("elutedBydate", p.getElutedBydate());
            }
            if (p.getRecoveredElutedmL() != Float.MIN_VALUE) {
                polioJSON.put("recoveredElutedmL", p.getRecoveredElutedmL());
            }

            if (p.getRainYesterday() != Integer.MIN_VALUE) {
                polioJSON.put("rainYesterday", p.getRainYesterday());
            }
            if (p.getRainToday() != Integer.MIN_VALUE) {
                polioJSON.put("RainToday", p.getRainToday());
            }

            if (p.getFieldtemp() != Float.MIN_VALUE) {
                polioJSON.put("fieldtemp", p.getFieldtemp());
            }

            if (p.getFieldShippedBy() != null) {
                polioJSON.put("fieldShippedBy", p.getFieldShippedBy());
            }
            if( p.getFieldShippedOn() != null) {
                polioJSON.put("fieldShippedOn", p.getFieldShippedOn());
            }
            if(p.getFieldWaterPh() != Float.MIN_VALUE) {
                polioJSON.put("fieldWaterPh", p.getFieldWaterPh());
            }
            if(p.getFieldVolume() != Float.MIN_VALUE) {
                polioJSON.put("fieldVolume", p.getFieldVolume());
            }

            if(p.getFieldVolumeAfter() != Float.MIN_VALUE) {
                polioJSON.put("fieldVolumeAfter", p.getFieldVolumeAfter());
            }
            if(p.getFieldVolumeRemaining() != Float.MIN_VALUE) {
                polioJSON.put("fieldVolumeRemaining", p.getFieldVolumeRemaining());
            }
            if( p.getShippedCold() != Integer.MIN_VALUE) {
                polioJSON.put("shippedCold", p.getShippedCold());
            }
            if(p.getFieldShippedtemp() != Float.MIN_VALUE) {
                polioJSON.put("fieldShippedtemp", p.getFieldShippedtemp());
            }
            if(p.getFieldShippedBy2() != null) {
                polioJSON.put("fieldShippedBy2", p.getFieldShippedBy2());
            }
            if(p.getFieldShippedOn2() != null) {
                polioJSON.put("fieldShippedOn2", p.getFieldShippedOn2());
            }
//                            polioJSON.put("author",p.getAuthor());
            if(p.getTempLoggerIncluded() != Integer.MIN_VALUE) {
                polioJSON.put("tempLoggerIncluded", p.getTempLoggerIncluded());
            }
            if(p.getTempLoggerNotIncluded() != Integer.MIN_VALUE) {
                polioJSON.put("tempLoggerNotIncluded", p.getTempLoggerNotIncluded());
            }
            if(p.getLabRecievedBy() != null) {
                polioJSON.put("labRecievedBy", p.getLabRecievedBy());
            }
            if(p.getLabRecievedOn() != null) {
                polioJSON.put("labRecievedOn", p.getLabRecievedOn());
            }
            if(p.getLabReceivedTemp() != Float.MIN_VALUE) {
                polioJSON.put("labReceivedTemp", p.getLabReceivedTemp());
            }
            if(p.getSampleCold() != Integer.MIN_VALUE) {
                polioJSON.put("sampleCold", p.getSampleCold());
            }
            if(p.getSampleNotCold() != Integer.MIN_VALUE) {
                polioJSON.put("sampleNotCold", p.getSampleNotCold());
            }
            if(p.getConcentratedOn() != null) {
                polioJSON.put("concentratedOn", p.getConcentratedOn());
            }
            if(p.getConcentratedBy() != null) {
                polioJSON.put("concentratedBy", p.getConcentratedBy());
            }
            if(p.getSecConMethod() != null) {
                polioJSON.put("secConMethod", p.getSecConMethod());
            }
            Log.v("navidi", "" + APIFunctionalities.class.getCanonicalName() + ":: onReceive():: double Checking SampleID:" + p.getSample_id());
//                        PolioDataJSON.put("note" , APIFunctionalities.convert2CommaSeperatedString(p) ); // creating comma seperated String and sending them to server side via API call.
//                            polioJSON.put("QrContent", p.getQrCodeContent());
//                        polioJSON.put("ConcentratedBy",p.getConcentratedBy());
            //Log.v("navidi", this.getClass().getCanonicalName() + ":: after init the JSON");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return polioJSON;

    }


    public static byte[] prepareImageToPost(){

        byte[] byteArray = null;



        return byteArray;

    }

}
