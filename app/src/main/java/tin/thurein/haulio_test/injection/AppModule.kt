package tin.thurein.haulio_test.injection

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import tin.thurein.data.local.AppDatabase
import tin.thurein.data.remote.ApiBuilder
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app").build()
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideApiBuilder(): ApiBuilder {
        return ApiBuilder
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }
}