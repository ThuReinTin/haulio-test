package tin.thurein.haulio_test.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import tin.thurein.haulio_test.App

open class BaseActivity: AppCompatActivity() {
    //    @JvmField
//    @Inject
//    var
    protected fun <T> gotoActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
            App.givenName = App.givenName
            App.profilePhoto = App.profilePhoto
            App.isLogin = true
        }
    }
}