package endpoints

enum class AutoRuEndpoints(var url: String) {

    BASE_URL("https://auto.ru"),
    CARS_INFO_ALL("/cars/%s/used/?page=%s"), // brand, page number
    CAR_INFO_URL("/%s/cars/%s/used/?page=%s"); // city, brand, page number

    override fun toString(): String {
        return this.url
    }
}
