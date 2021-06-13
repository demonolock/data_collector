package beans.autoru

import java.time.LocalDateTime

data class AutoInfo(
    var brand: String? = null,
    var productionDate: String? = null,
    var bodyType: String? = null, // седан, хэтчбек...
    var color: String? = null,
    var engineDisplacement: String? = null,
    var enginePower: String? = null,
    var fuelType: String? = null,
    var vehicleTransmission: String? = null,
    var drive: String? = null, // Привод
    var wheel: String? = null, // Руль
    var own: String? = null, // Владение
    var state: String? = null, // Состояние
    var customs: String? = null, // Таможня
    var price: Long? = null,
    var mileage: Long? = null, // Пробег
    var owners: String? = null,
    var car_url: String? = null,
    var image_url: String? = null,
    var description: String? = null,
    var numberOfDoors: String? = null,
    var parsing_unixtime: LocalDateTime = LocalDateTime.now(),
    var model: String? = null,
    var pts: String? = null // ПТС
)
