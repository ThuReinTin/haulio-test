package tin.thurein.domain.usecases

import io.reactivex.Single
import tin.thurein.domain.models.Job

interface UpdateJobUseCase {
    fun updateJob(job: Job): Single<Int>
}