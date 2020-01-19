package tin.thurein.data.usecases

import io.reactivex.Flowable
import tin.thurein.domain.models.Job
import tin.thurein.domain.repositories.JobRepository
import tin.thurein.domain.usecases.GetLocalJobsByIsAccepted
import javax.inject.Inject

class GetLocalJobsByIsAcceptedImpl: GetLocalJobsByIsAccepted {
    private val jobRepository: JobRepository

    @Inject
    constructor(jobRepository: JobRepository) {
        this.jobRepository = jobRepository
    }

    override fun getJobsByIsAccepted(isAccepted: Boolean): Flowable<List<Job>> {
        return jobRepository.getLocalJobsByIsAccepted(isAccepted)
    }
}