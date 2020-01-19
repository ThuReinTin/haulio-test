package tin.thurein.data.usecases

import io.reactivex.Single
import tin.thurein.domain.repositories.JobRepository
import tin.thurein.domain.usecases.DeleteAllUseCase
import java.lang.Exception
import javax.inject.Inject

class DeleteAllUseCaseImpl: DeleteAllUseCase {
    private val jobRepository: JobRepository

    @Inject
    constructor(jobRepository: JobRepository) {
        this.jobRepository = jobRepository
    }

    override fun deleteAll(): Single<Int> {
        return Single.create{emitter ->
            try {
                emitter.onSuccess(jobRepository.deleteAll())
            } catch (ex: Exception) {
                emitter.onError(ex.cause?: Throwable("Uncaught exception"))
            }
        }
    }
}