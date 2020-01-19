package tin.thurein.haulio_test.common

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    fun checkLocationPermission(context: Activity): Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION)
            return false
        } else {
            return true
        }
    }
}