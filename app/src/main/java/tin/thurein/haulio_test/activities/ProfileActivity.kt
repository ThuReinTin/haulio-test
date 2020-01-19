package tin.thurein.haulio_test.activities

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationSettingsRequest
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_profile.*
import tin.thurein.domain.models.Job
import tin.thurein.domain.models.ModelWrapper
import tin.thurein.domain.models.Progress
import tin.thurein.haulio_test.R
import tin.thurein.haulio_test.adapters.JobAdapter
import tin.thurein.haulio_test.databinding.ActivityProfileBinding
import tin.thurein.haulio_test.injection.ViewModelFactory
import tin.thurein.haulio_test.viewmodels.ProfileViewModel
import java.util.ArrayList
import javax.inject.Inject

class ProfileActivity : BaseActivity(), LocationListener {

    private var jobs: MutableList<Job> = ArrayList()

    private lateinit var binding: ActivityProfileBinding

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var jobAdapter: JobAdapter

    val builder = LocationSettingsRequest.Builder()

    @JvmField
    @Inject
    var viewModelFactory: ViewModelFactory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        AndroidInjection.inject(this)
        initUI()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    private fun initUI() {
        jobAdapter = JobAdapter(jobs, null, true)
        rvJobs.adapter = jobAdapter
    }

    private fun initData() {
        profileViewModel = ViewModelProvider(this, viewModelFactory as ViewModelProvider.Factory).get(ProfileViewModel::class.java)
        profileViewModel.setIvLeftSrc(ActivityCompat.getDrawable(this, R.drawable.back))
        profileViewModel.getJobs()
        profileViewModel.mutableJobs.observe(this, Observer { initJobs(it) })
        profileViewModel.mutableIvLeft.observe(this, Observer { initBack(it) })
        profileViewModel.mutableSignOut.observe(this, Observer { signOut(it) })

        binding.profileViewModel = profileViewModel
    }

    private fun signOut(modelWrapper: ModelWrapper<Int>?) {
        if (modelWrapper?.progress == Progress.SUCCESS) {
            gotoActivity(LoginActivity::class.java)
            finish()
        }
        Toast.makeText(this, modelWrapper?.message, Toast.LENGTH_SHORT).show()
    }

    private fun initBack(result: Int?) {
        onBackPressed()
    }

    private fun initJobs(modelWrapper: ModelWrapper<List<Job>>) {
        if (modelWrapper.progress == Progress.SUCCESS) {
            jobs.clear()
            jobs.addAll(modelWrapper.model!!)
            jobAdapter.notifyDataSetChanged()
        }
        Toast.makeText(this, modelWrapper.message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        gotoActivity(MainActivity::class.java)
        finish()
    }

    override fun onLocationChanged(p0: Location?) {

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
