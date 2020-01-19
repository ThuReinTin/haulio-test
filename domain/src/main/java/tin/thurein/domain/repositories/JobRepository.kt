package tin.thurein.domain.repositories

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import tin.thurein.domain.models.Job

interface JobRepository {
    fun getRemoteJobs(): Observable<List<Job>>

    fun getLocalJobs(): Flowable<List<Job>>

    fun getLocalJobsByIsAccepted(isAccepted: Boolean): Flowable<List<Job>>

    fun saveLocalJobs(jobs: List<Job>): List<Long>

    fun deleteAll(): Int

    fun updateJob(job: Job): Single<Int>
}