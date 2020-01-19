package tin.thurein.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Job(
    var id: Long?,

    @Expose
    @SerializedName("job-id")
    var jobId: Long?,
    var priority: Int?,
    var company: String?,
    var address: String?,
    var geolocation: Geolocation?,
    var isAccepted: Boolean = false
)
