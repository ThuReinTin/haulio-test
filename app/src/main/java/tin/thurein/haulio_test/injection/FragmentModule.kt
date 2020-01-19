package tin.thurein.haulio_test.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tin.thurein.haulio_test.fragments.HomeFragment

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHomFragment(): HomeFragment
}