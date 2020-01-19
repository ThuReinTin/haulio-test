package tin.thurein.haulio_test.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import tin.thurein.haulio_test.App
import tin.thurein.haulio_test.R
import javax.inject.Inject


class LoginActivity : BaseActivity() {

    @JvmField
    @Inject
    var googleSignInClient : GoogleSignInClient? = null

    private val RC_SIGN_IN = 100

    private fun alreadyLogin(account: GoogleSignInAccount?) {
        App.isLogin = account != null
        updateUI(account)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        account?.apply {
            App.givenName = givenName
            App.profilePhoto = photoUrl
            gotoMainActivity()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AndroidInjection.inject(this)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        alreadyLogin(account)

        btnGoogleSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        googleSignInClient?.apply {
            val signInIntent = signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } else {
                Log.e("Result", "Not ok")
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("handleSignIn", "signInResult:failed code=${e.statusCode}")
            updateUI(null)
        }
    }

    private fun gotoMainActivity() {
        gotoActivity(MainActivity::class.java)
        finish()
    }
}
