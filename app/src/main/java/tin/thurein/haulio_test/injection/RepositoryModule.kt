package tin.thurein.haulio_test.injection

import dagger.Binds
import dagger.Module
import tin.thurein.data.repositories.JobRepositoryImpl
import tin.thurein.domain.repositories.JobRepository

@Module
interface RepositoryModule {
    @Binds
    fun bindJobRepository(jobRepositoryImpl: JobRepositoryImpl): JobRepository
}