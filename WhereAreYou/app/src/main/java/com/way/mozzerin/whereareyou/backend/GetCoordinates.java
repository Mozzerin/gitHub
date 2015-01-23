package com.way.mozzerin.whereareyou.backend;

import android.content.Context;
import android.location.*;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.way.mozzerin.whereareyou.frontend.MainActivity;

/**
 * Created by Mozzerin on 26.11.2014.
 */
public class GetCoordinates extends MainActivity implements LocationListener {


    boolean isGPSEnabled = false;      // flag for GPS status
    boolean isNetworkEnabled = false;  // flag for network status
    boolean canGetLocation = false; // flag for GPS status
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 100 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 10000 * 60 * 1; // 10 minute
    // Declaring a Location Manager
    protected LocationManager locationManager;


    public void onLocationChanged(Location loc) {
        Log.d("activity", "LOC: onLocationChanged");
        location=loc;
        getCoordinates(location.getLatitude(), location.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("activity", "LOC: onStatusChanged");
        }

        public void onProviderEnabled(String provider) {
            LocationManager locManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            Log.d("activity", "LOC: onProviderEnabled");
            Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude=0;
            double longitude=0;
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d("activity", "LOC: onProviderEnabled "+latitude+" "+longitude);
        }

        public void onProviderDisabled(String provider) {
            Log.d("activity", "LOC: onProviderDisabled");
        }

        public Location getLocation(){
            try{
                locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                }else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("activity", "LOC Network Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                Log.d("activity", "LOC by Network");
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("activity", "LOC: GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    Log.d("activity", "LOC: loc by GPS");

                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("activity", "LOC: Location xx "+latitude+" "+longitude);

            return location;
        }

        public void getCoordinates(double latitude, double longitude){
             this.latitude = location.getLatitude();
             this.longitude = location.getLongitude();
         }
        public double getCurrentLatitude(){
            return latitude;
        }
        public double getCurrentLongitude(){
             return longitude;
         }


    }


    // Register the listener with the Location Manager to receive location updates


