package tin.thurein.domain.usecases

import io.reactivex.Flowable
import tin.thurein.domain.models.Job

interface GetLocalJobsByIsAccepted {
    fun getJobsByIsAccepted(isAccepted: Boolean): Flowable<List<Job>>
}