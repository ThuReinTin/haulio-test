package tin.thurein.data.repositories

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import tin.thurein.data.local.AppDatabase
import tin.thurein.data.local.daos.JobDao
import tin.thurein.data.local.entities.JobEntity
import tin.thurein.data.remote.ApiBuilder
import tin.thurein.data.remote.services.JobService
import tin.thurein.domain.models.Job
import tin.thurein.domain.repositories.JobRepository
import javax.inject.Inject

class JobRepositoryImpl: JobRepository {
    private val appDatabase: AppDatabase
    private val apiBuilder: ApiBuilder
    private val jobDao: JobDao

    @Inject
    constructor(appDatabase: AppDatabase, apiBuilder: ApiBuilder) {
        this.appDatabase = appDatabase
        this.apiBuilder = apiBuilder
        jobDao = appDatabase.jobDao()
    }

    override fun getRemoteJobs(): Observable<List<Job>> {
        return apiBuilder.getService(JobService::class.java).getJobs()
    }

    override fun getLocalJobs(): Flowable<List<Job>> {
        return jobDao.getJobs().map {jobEntities -> jobEntities.map {jobEntity -> jobEntity.toJob() } }
    }

    override fun getLocalJobsByIsAccepted(isAccepted: Boolean): Flowable<List<Job>> {
        return jobDao.getJobsByIsAcceptable(isAccepted).map { jobEntities -> jobEntities.map { jobEntity -> jobEntity.toJob() } }
    }

    override fun saveLocalJobs(jobs: List<Job>): List<Long> {
        return jobDao.saveAllJobs(jobs.map { job -> JobEntity.fromJob(job) })
    }

    override fun deleteAll(): Int {
        return jobDao.deleteAllJobs()
    }

    override fun updateJob(job: Job): Single<Int> {
        return jobDao.update(JobEntity.fromJob(job))
    }
}