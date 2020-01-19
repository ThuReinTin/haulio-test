package tin.thurein.data.usecases

import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import io.reactivex.MaybeOnSubscribe
import tin.thurein.domain.models.Job
import tin.thurein.domain.repositories.JobRepository
import tin.thurein.domain.usecases.SaveJobsUseCase
import java.lang.Exception
import javax.inject.Inject

class SaveJobsUseCaseImpl: SaveJobsUseCase {

    private val jobRepository: JobRepository

    @Inject
    constructor(jobRepository: JobRepository) {
        this.jobRepository = jobRepository

    }

    override fun saveJobs(jobs: List<Job>): Maybe<List<Long>> {
        return Maybe.create { emitter ->
            try {
                emitter.onSuccess(jobRepository.saveLocalJobs(jobs))
            } catch (ex: Exception) {
                emitter.onError(ex.cause?: Throwable("Uncaught exception"))
            } finally {
                emitter.onComplete()
            }
        }
    }
}