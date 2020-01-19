package tin.thurein.domain.models.enums

enum class Meta(val code: String, val message: String) {
    REQUEST_SUCCESS("000", "Success"),
    INVALID_PARAMETER("100", "Invalid parameter"),
    SYSTEM_ERROR("999", "System error")
}