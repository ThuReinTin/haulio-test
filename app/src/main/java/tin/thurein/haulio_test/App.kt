package tin.thurein.haulio_test

import android.app.Activity
import android.app.Application
import android.net.Uri
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import tin.thurein.haulio_test.injection.DaggerApplicationModule
import javax.inject.Inject


class App: Application(), HasActivityInjector, HasSupportFragmentInjector {
    @JvmField
    @Inject
    var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    @JvmField
    @Inject
    var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationModule.builder()
            .application(this)
            .build()
            .inject(this)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
            App.givenName = it.givenName
            App.profilePhoto = it.photoUrl
            App.isLogin = true
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector!!
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector!!
    }

    companion object {
        var isLogin = false
        var givenName: String? = null
        var profilePhoto: Uri? = null
    }
}