package com.autoremoteexample.polio;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

/**
 * Created by Rex Zhang on 7/12/2017.
 */

public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    public void onCreate(){
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                i.putExtra("coordinates", location.getLatitude() + " " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        // noinspection MissingPermission
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null);


    }

    public void onDestroy(){
        super.onDestroy();;
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);

        }
    }

}

