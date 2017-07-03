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
        try {
            if (APIFunctionalities.syncData(context)) {
//             Log.d("Connected");

                File file = new File(context.getFilesDir(), Constants.FILE_NAME);
                ArrayList<PolioScannerData> data = FileOpsUtil.readFile(file);
                if (data != null) {
                    Log.v("navidi", this.getClass().getCanonicalName() + "::onReceive()::OnWifi_Reading File, Size:" + data.size());

                    for (PolioScannerData p : data) {
                        Log.v("navidi", this.getClass().getCanonicalName() + ":: Data is being Sync'ed:" + p.getQrCodeContent());
                        // CALLING API FROM HERE

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
                            Log.v("navidi", "" + this.getClass().getCanonicalName() + ":: onReceive():: double Checking SampleID:" + p.getSample_id());
//                        PolioDataJSON.put("note" , APIFunctionalities.convert2CommaSeperatedString(p) ); // creating comma seperated String and sending them to server side via API call.
//                            polioJSON.put("QrContent", p.getQrCodeContent());
//                        polioJSON.put("ConcentratedBy",p.getConcentratedBy());
                            //Log.v("navidi", this.getClass().getCanonicalName() + ":: after init the JSON");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                            new SendDataToServer().execute(packedDataReady2Send);
                            Log.v("navidi", "" + this.getClass().getCanonicalName() + ":: After calling sendDataToSErver");


//                        if(!polioJsonResponse.contains("200")){
//                            Toast.makeText(null, "ERROR: Calling API--" + polioJsonResponse, Toast.LENGTH_LONG).show();
//                        }

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
