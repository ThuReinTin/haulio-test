package tin.thurein.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import tin.thurein.domain.models.Geolocation

data class GeolocationObject(
    @Expose
    @SerializedName("longitude")
    private var longitude: Double?,

    @Expose
    @SerializedName("latitude")
    private var latitude: Double?
) {
    fun toGeolocation(): Geolocation {
        return Geolocation(latitude, longitude)
    }
}