package tin.thurein.haulio_test.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_maps.*
import tin.thurein.domain.models.Job
import tin.thurein.domain.models.Progress
import tin.thurein.haulio_test.R
import tin.thurein.haulio_test.adapters.JobSearchAdapter
import tin.thurein.haulio_test.common.LocationUtils
import tin.thurein.haulio_test.common.PermissionUtils
import tin.thurein.haulio_test.databinding.ActivityMapsBinding
import tin.thurein.haulio_test.injection.ViewModelFactory
import tin.thurein.haulio_test.listeners.JobSearchAdapterListener
import tin.thurein.haulio_test.viewmodels.MapsViewModel
import javax.inject.Inject


class MapsActivity : BaseActivity(), OnMapReadyCallback, LocationListener, JobSearchAdapterListener {

    private lateinit var mMap: GoogleMap

    private val REQUEST_CODE_CHECK_SETTINGS = 121

    private val LOCATION_UPDATE_INTERVAL: Long = 8000

    private val LOCATION_UPDATE_FASTEST_INTERVAL: Long = 8000

    private var searchResultList: MutableList<String> = ArrayList()

    private var resultJobs: MutableList<Job> = ArrayList()

    private lateinit var jobSearchAdapter: JobSearchAdapter

    @JvmField
    @Inject
    var viewModelFactory: ViewModelFactory? = null

    private lateinit var binding: ActivityMapsBinding

    private lateinit var mapsViewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)
        AndroidInjection.inject(this)
        initData()

        binding.mapsViewModel = mapsViewModel

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        etSearch.setOnItemClickListener { _, _, position, _ ->
            mapsViewModel.updateJob(resultJobs[position])
            resultJobs.clear()
            resultJobs.add(mapsViewModel.job!!)
            updateJobMarkers()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener {
            if ((it.tag) is Job) {
                mapsViewModel.updateJob(it.tag as Job)
            }
            false
        }

        enableLocationSettings()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        gotoActivity(MainActivity::class.java)
        finish()
    }

    private fun initData() {
        mapsViewModel = ViewModelProvider(this, viewModelFactory as ViewModelProvider.Factory).get(MapsViewModel::class.java)
        mapsViewModel.setIvLeftSrc(ActivityCompat.getDrawable(this, R.drawable.back))
        mapsViewModel.getJobs()

        mapsViewModel.mutableIvLeft.observe(this, Observer { onBackPressed() })
        mapsViewModel.mutableSignOut.observe(this, Observer { modelWrapper ->
            Toast.makeText(this, modelWrapper.message, Toast.LENGTH_SHORT).show()
            if (modelWrapper.progress == (Progress.SUCCESS)) {
                gotoActivity(LoginActivity::class.java)
                finish()
            }
        })

        mapsViewModel.mutableJobs.observe(this, Observer { modelWrapper ->
            Toast.makeText(this, modelWrapper.message, Toast.LENGTH_SHORT).show()
            when(modelWrapper.progress) {
                Progress.SUCCESS -> {
                    modelWrapper.model?.let {
                        resultJobs.clear()
                        resultJobs.addAll(it)
                        updateJobMarkers()
                        jobSearchAdapter = JobSearchAdapter(this, R.layout.job_filter_layout, searchResultList, it, resultJobs, this)
                        etSearch.setAdapter(jobSearchAdapter)
                    }

                }
            }

            Toast.makeText(this, modelWrapper.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun addMarker(job: Job?, location: Location?) {
        job?.let {
            val position = LatLng(it.geolocation?.latitude!!, it.geolocation?.longitude!!)
            val marker = addMarker(position, BitmapDescriptorFactory.defaultMarker(if (job.isAccepted) BitmapDescriptorFactory.HUE_GREEN else BitmapDescriptorFactory.HUE_AZURE))
            marker.tag = it
        }

        location?.let {
            val position = LatLng(it.latitude, it.longitude)
            addMarker(position, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 13.0f))
        }
    }

    private fun addMarker(position: LatLng, bitmapDescriptorFactory: BitmapDescriptor?): Marker {
        return mMap
            .addMarker(MarkerOptions()
                .position(position)
                .icon(bitmapDescriptorFactory))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            addMarker(null, it)
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addCurrentLocation()
            }
        }
    }

    private fun enableLocationSettings() {
        val locationRequest: LocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        LocationServices
            .getSettingsClient(this)
            .checkLocationSettings(builder.build())
            .addOnSuccessListener(this) { response ->
                Log.e("OnSuccess", "OK")
                if (PermissionUtils.checkLocationPermission(this)) {
                    addCurrentLocation()
                    Log.e("Location","Added")
                }
            }
            .addOnCanceledListener {
                gotoActivity(MainActivity::class.java)
                finish()
            }
            .addOnFailureListener(this) { ex ->
                if (ex is ResolvableApiException) {
                    try {
                        Log.e("OnFailure", "OK")
                        // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                        val resolvable: ResolvableApiException = ex
                        resolvable.startResolutionForResult(this, REQUEST_CODE_CHECK_SETTINGS)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Log.e("Ex", sendEx.message)
                    }
                }
            }
    }

    private fun addCurrentLocation() {
        addMarker(null, LocationUtils.getLocation(this, this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (REQUEST_CODE_CHECK_SETTINGS == requestCode) {
            if(Activity.RESULT_OK == resultCode) {
                if (PermissionUtils.checkLocationPermission(this)) {
                    addCurrentLocation()
                }
            } else {
                gotoActivity(MainActivity::class.java)
                finish()
            }
        }
    }

    private fun updateJobMarkers() {
        mMap.clear()
        resultJobs.forEach { addMarker(it, null) }
        addCurrentLocation()
    }

    override fun onRefresh() {
        updateJobMarkers()
    }
}
