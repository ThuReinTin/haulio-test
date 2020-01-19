package tin.thurein.data.remote.services

import io.reactivex.Observable
import retrofit2.http.GET
import tin.thurein.domain.models.Job

interface JobService {
    @GET("8d195.json")
    fun getJobs(): Observable<List<Job>>
}