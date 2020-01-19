package tin.thurein.haulio_test.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.MaybeObserver
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import tin.thurein.domain.models.Job
import tin.thurein.domain.models.ModelWrapper
import tin.thurein.domain.models.Progress
import tin.thurein.domain.usecases.GetRemoteJobsUseCase
import tin.thurein.domain.usecases.SaveJobsUseCase
import tin.thurein.haulio_test.common.NetworkUtils
import java.lang.Exception
import javax.inject.Inject

class MainViewModel: HeaderViewModel {

    @Inject
    constructor() {
        init()
    }

    @Inject
    @JvmField
    var getRemoteJobsUseCase: GetRemoteJobsUseCase? = null

    @Inject
    @JvmField
    var saveJobsUseCase: SaveJobsUseCase? = null

    lateinit var mutableRemoteJobs: MutableLiveData<ModelWrapper<List<Job>>>

    lateinit var mutableSaveJobs: MutableLiveData<ModelWrapper<List<Long>>>

    fun getRemoteJobs(context: Context) {
        if (!NetworkUtils.isNetworkAvailabe(context)) {
            mutableRemoteJobs.postValue(ModelWrapper(Progress.ERROR, "No internet connection."))
            return
        }
        getRemoteJobsUseCase?.let {
            it.getJobs()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(object : Observer<List<Job>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        mutableRemoteJobs.postValue(ModelWrapper(Progress.LOADING, "Loading online jobs..."))
                    }

                    override fun onNext(jobs: List<Job>) {
                        mutableRemoteJobs.postValue(
                            ModelWrapper(
                                jobs,
                                Progress.SUCCESS,
                                "Success"
                            )
                        )
                        saveJobs(jobs)
                    }

                    override fun onError(e: Throwable) {
                        mutableRemoteJobs.postValue(
                            ModelWrapper(
                                Progress.ERROR,
                                e.message?: "System Error"
                            )
                        )
                    }
                })
        }
    }

    private fun saveJobs(jobs: List<Job>) {
        saveJobsUseCase?.let {
            it.saveJobs(jobs)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : MaybeObserver<List<Long>>{
                    override fun onSuccess(ids: List<Long>) {
                        mutableSaveJobs.postValue(ModelWrapper(ids, Progress.SUCCESS, "Success"))
                    }

                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                        mutableSaveJobs.postValue(ModelWrapper(Progress.LOADING, "Saving jobs..."))
                    }

                    override fun onError(e: Throwable) {
                        mutableSaveJobs.postValue(ModelWrapper(Progress.ERROR, e.message?:"System error"))
                    }
                })
        }
    }

    private fun init() {
        mutableRemoteJobs = MutableLiveData()
        mutableSaveJobs = MutableLiveData()
    }
}