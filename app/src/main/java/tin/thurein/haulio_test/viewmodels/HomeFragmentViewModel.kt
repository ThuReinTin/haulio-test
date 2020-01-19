package tin.thurein.haulio_test.viewmodels

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import io.reactivex.FlowableSubscriber
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import tin.thurein.domain.models.Job
import tin.thurein.domain.models.ModelWrapper
import tin.thurein.domain.models.Progress
import tin.thurein.domain.usecases.GetLocalJobsUseCase
import tin.thurein.domain.usecases.UpdateJobUseCase
import javax.inject.Inject

class HomeFragmentViewModel: BaseViewModel {

    var mutableJobs: MutableLiveData<ModelWrapper<List<Job>>> = MutableLiveData()

    var mutableUpdateJob: MutableLiveData<ModelWrapper<Int>> = MutableLiveData()

    private var isRefreshing = false

    private var title = ""

    @Bindable
    fun getRefresh(): Boolean {
        return isRefreshing
    }

    fun setRefresh(fresh: Boolean) {
        isRefreshing = fresh
        notifyPropertyChanged(BR.refresh)
    }

    @Bindable
    fun getJobTitle(): String {
        return title
    }

    fun setJobTitle(jobTitle: String) {
        title = jobTitle
        notifyPropertyChanged(BR.jobTitle)
    }

    @Inject
    constructor()

    @JvmField
    @Inject
    var getLocalJobsUseCase: GetLocalJobsUseCase? = null

    @JvmField
    @Inject
    var updateJobUseCase: UpdateJobUseCase? = null

    fun getJobs() {
        getLocalJobsUseCase?.let {
            it.getLocalJobs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : FlowableSubscriber<List<Job>> {
                    override fun onComplete() {
                        setRefresh(false)
                    }

                    override fun onSubscribe(s: Subscription) {
                        s.request(Long.MAX_VALUE)
                        setRefresh(true)
                        mutableJobs.postValue(ModelWrapper(Progress.LOADING, "Retrieving data..."))
                    }

                    override fun onNext(jobs: List<Job>?) {
                        if (jobs == null || jobs.isEmpty()) {
                            setJobTitle("No available job")
                        } else {
                            setJobTitle("Jobs Available")
                        }
                        mutableJobs.postValue(ModelWrapper(jobs, Progress.SUCCESS, "Success"))
                    }

                    override fun onError(t: Throwable?) {
                        setJobTitle("No available jobs")
                        mutableJobs.postValue(ModelWrapper(Progress.ERROR, t?.message?:"System error"))
                    }

                })
        }
    }

    fun updateJob(job: Job) {
        updateJobUseCase?.let {
            it.updateJob(job)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : SingleObserver<Int> {
                    override fun onSubscribe(d: Disposable) {
                        mutableUpdateJob.postValue(ModelWrapper(Progress.LOADING, "Updating data..."))
                    }

                    override fun onError(e: Throwable) {
                        mutableUpdateJob.postValue(ModelWrapper(Progress.ERROR, e.message?:"System error"))
                    }

                    override fun onSuccess(count: Int) {
                        if (count != 1) {
                            mutableUpdateJob.postValue(ModelWrapper(Progress.ERROR, "Failed to update."))
                        } else {
                            mutableUpdateJob.postValue(
                                ModelWrapper(
                                    count,
                                    Progress.SUCCESS,
                                    "Updated"
                                )
                            )
                        }
                    }

                })
        }
    }
}