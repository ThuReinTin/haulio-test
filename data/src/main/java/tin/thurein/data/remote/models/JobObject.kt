package tin.thurein.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import tin.thurein.domain.models.Job

class JobObject (
    @Expose
    @SerializedName("geolocation")
    var geolocation: GeolocationObject?,

    @Expose
    @SerializedName("address")
    var address: String? = null,

    @Expose
    @SerializedName("company")
    var company: String? = null,

    @Expose
    @SerializedName("priority")
    var priority: Int? = null,

    @Expose
    @SerializedName("job-id")
    var jobId: Long? = null,

    @Expose
    @SerializedName("id")
    var id: Long? = null
) {
    fun toJob(): Job {
        return Job(id, jobId, priority, company, address, geolocation?.toGeolocation())
    }
}