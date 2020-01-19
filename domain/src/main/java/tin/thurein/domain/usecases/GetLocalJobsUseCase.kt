package tin.thurein.domain.usecases

import io.reactivex.Flowable
import tin.thurein.domain.models.Job

interface GetLocalJobsUseCase {
    fun getLocalJobs(): Flowable<List<Job>>
}