package tin.thurein.haulio_test.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import tin.thurein.domain.models.Job
import tin.thurein.domain.models.Progress
import tin.thurein.haulio_test.R
import tin.thurein.haulio_test.adapters.JobAdapter
import tin.thurein.haulio_test.databinding.FragmentHomeBinding
import tin.thurein.haulio_test.injection.ViewModelFactory
import tin.thurein.haulio_test.listeners.HomeFragmentListener
import tin.thurein.haulio_test.listeners.JobAdapterListener
import tin.thurein.haulio_test.viewmodels.HomeFragmentViewModel
import java.util.ArrayList
import javax.inject.Inject


class HomeFragment: DaggerFragment(), JobAdapterListener {

    @JvmField
    @Inject
    var viewModelFactory: ViewModelFactory? = null

    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    private var jobs: MutableList<Job> = ArrayList()

    private lateinit var jobAdapter: JobAdapter

    private var listener: HomeFragmentListener? = null

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jobAdapter = JobAdapter(jobs, this, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initUI()
        initData()
        binding.homeFragmentViewModel = homeFragmentViewModel
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initUI() {
        if (rvJobs.adapter == null) {
            rvJobs.adapter = jobAdapter
        }

        srlJobs.setOnRefreshListener{
            listener?.let { it.onRefresh() }
            srlJobs.isRefreshing = false
        }
    }

    private fun initData() {
        homeFragmentViewModel =  ViewModelProvider(this, viewModelFactory as ViewModelProvider.Factory).get(HomeFragmentViewModel::class.java)

        homeFragmentViewModel.getJobs()

        homeFragmentViewModel.mutableJobs.observe(this, Observer { modelWrapper ->
            Toast.makeText(activity, modelWrapper.message, Toast.LENGTH_SHORT).show()
            when(modelWrapper.progress) {
                Progress.SUCCESS -> {
                    jobs.clear()
                    modelWrapper.model?.let { jobList -> jobs.addAll(jobList) }
                    jobAdapter.notifyDataSetChanged()
                    srlJobs.isRefreshing = false
                }

                Progress.ERROR -> {
                    srlJobs.isRefreshing = false
                }

                Progress.LOADING -> {
                    srlJobs.isRefreshing = true
                }
            }
        })

        homeFragmentViewModel.mutableUpdateJob.observe(this, Observer { modelWrapper ->
            Toast.makeText(activity, modelWrapper.message, Toast.LENGTH_SHORT).show()
            jobAdapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(position: Int) {
        jobs[position].isAccepted = true
        homeFragmentViewModel.updateJob(jobs[position])
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
