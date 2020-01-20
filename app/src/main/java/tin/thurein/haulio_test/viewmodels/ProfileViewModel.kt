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
import tin.thurein.domain.usecases.GetLocalJobsByIsAccepted
import tin.thurein.haulio_test.App
import javax.inject.Inject

class ProfileViewModel: HeaderViewModel {
    @Inject
    constructor()

    private var title = ""

    @JvmField
    @Inject
    var getLocalJobsByIsAccepted: GetLocalJobsByIsAccepted? = null

    val mutableJobs: MutableLiveData<ModelWrapper<List<Job>>> = MutableLiveData()

    @Bindable
    fun getGivenName(): String {
        return "${App.givenName}"
    }

    @Bindable
    fun getProfilePhoto(): String? {
        return App.profilePhoto.toString()
    }

    @Bindable
    fun getJobTitle(): String {
        return title
    }

    fun setJobTitle(jobTitle: String) {
        title = jobTitle
        notifyPropertyChanged(BR.jobTitle)
    }

    fun getJobs() {
        getLocalJobsByIsAccepted?.let {
            it.getJobsByIsAccepted(true)
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
                        if (jobs == null || jobs.isEmpty()) {
                            setJobTitle("No accepted job")
                        } else {
                            setJobTitle("Accepted jobs")
                        }
                        mutableJobs.postValue(ModelWrapper(jobs, Progress.SUCCESS, "Success"))
                    }

                    override fun onError(t: Throwable?) {
                        setJobTitle("No accepted job")
                        mutableJobs.postValue(ModelWrapper(Progress.ERROR, t?.message?:"System error"))
                    }

                })
        }
    }
}