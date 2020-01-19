package tin.thurein.haulio_test.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import tin.thurein.haulio_test.App
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    FragmentModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    UseCaseModule::class
])
interface ApplicationModule {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationModule
    }

    fun inject(application: App)
}