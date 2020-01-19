package tin.thurein.haulio_test.common

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log


object NetworkUtils {
    fun isNetworkAvailabe(context: Context): Boolean {
        if (context == null) {
            return false
        }
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // if no network is available networkInfo will be null, otherwise check if we are connected
        try {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        } catch (e: Exception) {
            Log.e("isNetworkAvailable()", e.message)
        }
        return false
    }
}