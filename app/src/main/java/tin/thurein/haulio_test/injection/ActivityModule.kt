package tin.thurein.haulio_test.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tin.thurein.haulio_test.activities.LoginActivity
import tin.thurein.haulio_test.activities.MainActivity
import tin.thurein.haulio_test.activities.MapsActivity
import tin.thurein.haulio_test.activities.ProfileActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindMapsActivity(): MapsActivity

    @ContributesAndroidInjector
    abstract fun bindProfileActivity(): ProfileActivity
}