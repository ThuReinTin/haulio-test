package tin.thurein.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import tin.thurein.data.local.DatabaseConstants
import tin.thurein.domain.models.Job

@Entity(tableName = DatabaseConstants.JOB_TABLE_NAME)
data class JobEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DatabaseConstants.ID)
    var id : Long?,

    @ColumnInfo(name = DatabaseConstants.JOB_ID)
    var jobId : Long?,

    @ColumnInfo(name = DatabaseConstants.PRIORITY)
    var priority : Int?,

    @ColumnInfo(name = DatabaseConstants.COMPANY)
    var company : String?,

    @ColumnInfo(name = DatabaseConstants.ADDRESS)
    var address : String?,

    @Embedded
    var geolocationEntity: GeolocationEntity?,

    @ColumnInfo(name = DatabaseConstants.IS_ACCEPTED)
    var isAccepted: Boolean?
) {
    fun toJob(): Job {
        return Job(id, jobId, priority, company, address, geolocationEntity?.toGeolocation(), isAccepted?: false)
    }

    companion object {
        fun fromJob(job: Job): JobEntity {
            return JobEntity(job.id, job.jobId, job.priority, job.company, job.address, GeolocationEntity.fromGeolocation(job.geolocation!!), job.isAccepted)
        }
    }
}