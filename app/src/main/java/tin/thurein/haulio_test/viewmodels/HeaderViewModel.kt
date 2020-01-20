package tin.thurein.haulio_test.viewmodels

import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import tin.thurein.domain.models.ModelWrapper
import tin.thurein.domain.models.Progress
import tin.thurein.domain.usecases.DeleteAllUseCase
import tin.thurein.haulio_test.App
import javax.inject.Inject

open class HeaderViewModel: BaseViewModel() {
    @JvmField
    @Inject
    var googleSignInClient: GoogleSignInClient? = null

    @JvmField
    @Inject
    var deleteAllUseCase: DeleteAllUseCase? = null

    private var ivLeft: Drawable? = null

    var mutableIvLeft: MutableLiveData<Int> = MutableLiveData()

    var mutableSignOut: MutableLiveData<ModelWrapper<Int>> = MutableLiveData()

    fun setIvLeftSrc(ivLeft: Drawable?) {
        this.ivLeft = ivLeft
        notifyPropertyChanged(BR.ivLeftSrc)
    }

    @Bindable
    fun getIvLeftSrc(): Drawable? {
        return ivLeft
    }

    fun ivLeftOnClick() {
        mutableIvLeft.postValue(1)
    }

    fun ivSignOutOnClick() {
        deleteAllUseCase?.let {
            it.deleteAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : SingleObserver<Int>{
                    override fun onSuccess(t: Int) {
                        googleSignInClient?.let {signInClient ->
                            signInClient.signOut()
                            App.isLogin = false
                            App.givenName = ""
                            App.profilePhoto = null
                            mutableSignOut.postValue(ModelWrapper(Progress.SUCCESS, "Success logout"))
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        mutableSignOut.postValue(ModelWrapper(Progress.SUCCESS, "Logging out..."))
                    }

                    override fun onError(e: Throwable) {
                        mutableSignOut.postValue(ModelWrapper(Progress.SUCCESS, "Fail logout. ${e.message}"))
                    }
                })
        }
    }
}