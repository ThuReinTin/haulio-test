package tin.thurein.data.usecases

import io.reactivex.Flowable
import tin.thurein.domain.models.Job
import tin.thurein.domain.repositories.JobRepository
import tin.thurein.domain.usecases.GetLocalJobsUseCase
import javax.inject.Inject

class GetLocalJobsUseCaseImpl: GetLocalJobsUseCase {
    private val jobRepository: JobRepository

    @Inject
    constructor(jobRepository: JobRepository) {
        this.jobRepository = jobRepository
    }

    override fun getLocalJobs(): Flowable<List<Job>> {
        return jobRepository.getLocalJobs()
    }
}