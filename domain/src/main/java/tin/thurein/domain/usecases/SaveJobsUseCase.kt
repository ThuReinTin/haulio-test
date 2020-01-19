package tin.thurein.domain.usecases

import io.reactivex.Maybe
import tin.thurein.domain.models.Job

interface SaveJobsUseCase {
    fun saveJobs(jobs: List<Job>): Maybe<List<Long>>
}