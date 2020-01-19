package tin.thurein.data.local.entities

import tin.thurein.domain.models.Geolocation

data class GeolocationEntity(var longitude: Double?, var latitude: Double?) {
    fun toGeolocation(): Geolocation {
        return Geolocation(latitude, longitude)
    }

    companion object {
        fun fromGeolocation(geolocation: Geolocation): GeolocationEntity {
            return GeolocationEntity(geolocation.longitude, geolocation.latitude)
        }
    }
}