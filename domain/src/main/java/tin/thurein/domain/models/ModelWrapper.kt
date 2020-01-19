package tin.thurein.domain.models

class ModelWrapper<T> {
    var model : T? = null
    var progress : Progress = Progress.LOADING
    var message : String? = null

    constructor(progress: Progress, message: String) {
        this.progress = progress
        this.message = message
    }

    constructor(model: T?, progress: Progress, message: String) {
        this.model = model
        this.progress = progress
        this.message = message
    }
}