package tin.thurein.haulio_test.common

import android.app.Activity
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log

object LocationUtils {
    fun getLocation(activity: Activity, listener: LocationListener): Location? {
        val mLocationManager =
            activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var location: Location? = null
        if (PermissionUtils.checkLocationPermission(activity)) {
            try {
                if (mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                    Log.e("Passive Provider", "OK")
                    mLocationManager.requestLocationUpdates(
                        LocationManager.PASSIVE_PROVIDER,
                        1000,
                        10f,
                        listener
                    )
                    location = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                }

                if (location == null && mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("GPS Provider", "OK")
                    mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000,
                        10f,
                        listener
                    )
                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }

                if (location == null && mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Log.e("Network Provider", "OK")
                    mLocationManager.getBestProvider(createFineCriteria(), true)
                    mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000,
                        10f,
                        listener
                    )
                    location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }

            } catch (exp: SecurityException) {
                Log.e("EXP", exp.toString())
            }
        }
        return location
    }

    private fun createFineCriteria(): Criteria {
        val c = Criteria()
        c.accuracy = Criteria.ACCURACY_FINE
        c.isAltitudeRequired = false
        c.isBearingRequired = false
        c.isSpeedRequired = false
        c.isCostAllowed = true
        c.powerRequirement = Criteria.POWER_HIGH
        return c
    }
}