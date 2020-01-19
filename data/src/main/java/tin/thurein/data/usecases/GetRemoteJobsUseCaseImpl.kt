package tin.thurein.data.usecases

import io.reactivex.Observable
import tin.thurein.domain.models.Job
import tin.thurein.domain.repositories.JobRepository
import tin.thurein.domain.usecases.GetRemoteJobsUseCase
import javax.inject.Inject

class GetRemoteJobsUseCaseImpl: GetRemoteJobsUseCase {
    private val jobRepository: JobRepository

    @Inject
    constructor(jobRepository: JobRepository) {
        this.jobRepository = jobRepository
    }

    override fun getJobs(): Observable<List<Job>> {
        return jobRepository.getRemoteJobs()
    }
}