package tin.thurein.haulio_test.injection;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tin.thurein.haulio_test.viewmodels.HomeFragmentViewModel;
import tin.thurein.haulio_test.viewmodels.MainViewModel;
import tin.thurein.haulio_test.viewmodels.MapsViewModel;
import tin.thurein.haulio_test.viewmodels.ProfileViewModel;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel.class)
    public abstract ViewModel bindHomeFragmentViewModel(HomeFragmentViewModel homeFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel.class)
    public abstract ViewModel bindMapsViewModel(MapsViewModel mapsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }
}
