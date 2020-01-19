package tin.thurein.data.local.daos

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import tin.thurein.data.local.DatabaseConstants
import tin.thurein.data.local.entities.JobEntity

@Dao
abstract class JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(jobEntities: List<JobEntity>): List<Long>

    @Update
    abstract fun update(jobEntity: JobEntity): Single<Int>

    @Query("SELECT * FROM ${DatabaseConstants.JOB_TABLE_NAME};")
    abstract fun getJobs(): Flowable<List<JobEntity>>

    @Query("SELECT * FROM ${DatabaseConstants.JOB_TABLE_NAME} WHERE ${DatabaseConstants.IS_ACCEPTED} = :isAccepted;")
    abstract fun getJobsByIsAcceptable(isAccepted: Boolean): Flowable<List<JobEntity>>

    @Query("DELETE FROM ${DatabaseConstants.JOB_TABLE_NAME};")
    abstract fun deleteAllJobs(): Int

    @Transaction
    open fun saveAllJobs(jobEntities: List<JobEntity>): List<Long> {
        deleteAllJobs()
        return insertAll(jobEntities)
    }
}