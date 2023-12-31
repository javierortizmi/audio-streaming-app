package com.example.music_player

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class GPSSpeed (private val context: Context){

    private var classSpeed: Float = 0F
    private var floatList: MutableList<Float> = mutableListOf()
//the fusedLocationClient is the thing that actually gets the speed. relies on
//    google services, extremely important
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

//is essentially a config file for location services
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 1000
        fastestInterval = 500
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                val speed = location.speed
                classSpeed = speed
                // Do something with the speed, e.g., update UI or log
//                Log.w("MainActivity", "Current speed: $speed meters/second")
            }
        }
    }

    fun getSpeed(): Float {
        floatList.add(0, classSpeed)
        if(floatList.size > 10){
            floatList.removeAt(floatList.size - 1)
        }

        return floatList.average().toFloat()
    }

    fun startTrackingSpeed() {
        if (checkLocationPermission()) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    fun stopTrackingSpeed() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun checkLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }


}