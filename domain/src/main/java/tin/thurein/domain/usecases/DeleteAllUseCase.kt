package tin.thurein.domain.usecases

import io.reactivex.Single

interface DeleteAllUseCase {
    fun deleteAll(): Single<Int>
}