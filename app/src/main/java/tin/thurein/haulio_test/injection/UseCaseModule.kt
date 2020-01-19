package tin.thurein.haulio_test.injection

import dagger.Binds
import dagger.Module
import tin.thurein.data.usecases.*
import tin.thurein.domain.usecases.*

@Module
interface UseCaseModule {
    @Binds
    fun bindGetRemoteJobsUseCase(getRemoteJobsUseCaseImpl: GetRemoteJobsUseCaseImpl): GetRemoteJobsUseCase

    @Binds
    fun bindSaveJobsUseCase(saveJobsUseCaseImpl: SaveJobsUseCaseImpl): SaveJobsUseCase

    @Binds
    fun bindGetLocalJobsUseCase(getLocalJobsUseCaseImpl: GetLocalJobsUseCaseImpl): GetLocalJobsUseCase

    @Binds
    fun bindGetLocalJobsUseCaseByIsAccepted(getLocalJobsUseCaseByIsAcceptedImpl: GetLocalJobsByIsAcceptedImpl): GetLocalJobsByIsAccepted

    @Binds
    fun bindUpdateJobUseCase(updateJobUseCaseImpl: UpdateJobUseCaseImpl): UpdateJobUseCase

    @Binds
    fun bindDeleteAllUseCase(deleteAllUseCaseImpl: DeleteAllUseCaseImpl): DeleteAllUseCase
}