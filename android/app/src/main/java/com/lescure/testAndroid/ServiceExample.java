package com.lescure.testAndroid;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceExample extends Service implements LocationListener {

    private Timer timer;
    private LocationManager locationMgr;


    public ServiceExample() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.w("TAG_TIMER", "timer task");
            }
        }, 0, 60000);
        Log.w("TAG_SERVICE", "service created");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            stopSelf();
            return;
        }

        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationMgr.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
            locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        }
        if (locationMgr.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
            locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        Log.w("TAG_SERVICE", "service started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        locationMgr.removeUpdates(this);
        Log.w("TAG_SERVICE", "service destroyed");
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        Toast.makeText(this,
                "la latitude est : " + latitude + " et la longitude est : " + longitude + ".", Toast.LENGTH_SHORT).show();
        MyApplication.getBus().post(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
