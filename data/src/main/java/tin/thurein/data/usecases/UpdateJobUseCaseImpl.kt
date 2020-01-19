package tin.thurein.data.usecases

import io.reactivex.Single
import tin.thurein.domain.models.Job
import tin.thurein.domain.repositories.JobRepository
import tin.thurein.domain.usecases.UpdateJobUseCase
import javax.inject.Inject

class UpdateJobUseCaseImpl: UpdateJobUseCase {
    private val jobRepository: JobRepository

    @Inject
    constructor(jobRepository: JobRepository) {
        this.jobRepository = jobRepository
    }

    override fun updateJob(job: Job): Single<Int> {
        return jobRepository.updateJob(job)
    }
}