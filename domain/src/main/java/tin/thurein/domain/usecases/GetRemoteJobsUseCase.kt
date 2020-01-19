package tin.thurein.domain.usecases

import io.reactivex.Observable
import tin.thurein.domain.models.Job

interface GetRemoteJobsUseCase {
    fun getJobs(): Observable<List<Job>>
}