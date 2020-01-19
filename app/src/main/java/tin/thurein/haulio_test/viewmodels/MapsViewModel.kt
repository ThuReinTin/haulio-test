package tin.thurein.haulio_test.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import io.reactivex.FlowableSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import tin.thurein.domain.models.Job
import tin.thurein.domain.models.ModelWrapper
import tin.thurein.domain.models.Progress
import tin.thurein.domain.usecases.GetLocalJobsUseCase
import tin.thurein.haulio_test.App
import javax.inject.Inject

class MapsViewModel: HeaderViewModel {
    @Inject
    constructor()

    var job: Job? = null

    @Bindable
    fun getGivenName(): String {
        return "${App.givenName}"
    }

    @Bindable
    fun getProfilePhoto(): String? {
        return App.profilePhoto.toString()
    }

    @Bindable
    fun getJobNumber(): String? {
        return job?.let { return "Job number: ${it.jobId}" }
    }

    fun updateJob(job: Job) {
        this.job = job
        notifyPropertyChanged(BR.jobNumber)
    }

    @JvmField
    @Inject
    var getLocalJobsUseCase: GetLocalJobsUseCase? = null

    val mutableJobs : MutableLiveData<ModelWrapper<List<Job>>> = MutableLiveData()

    fun getJobs() {
        getLocalJobsUseCase?.let {
            it.getLocalJobs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : FlowableSubscriber<List<Job>> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(s: Subscription) {
                        s.request(Long.MAX_VALUE)
                        mutableJobs.postValue(ModelWrapper(Progress.LOADING, "Retrieving data..."))
                    }

                    override fun onNext(jobs: List<Job>?) {
                        mutableJobs.postValue(ModelWrapper(jobs, Progress.SUCCESS, "Success"))
                    }

                    override fun onError(t: Throwable?) {
                        mutableJobs.postValue(ModelWrapper(Progress.ERROR, t?.message?:"System error"))
                    }

                })
        }
    }
}