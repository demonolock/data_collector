package endpoints

enum class AutoRuEndpoints(var url: String) {

    BASE_URL("http://auto.ru"),
    CARS_INFO_ALL("/cars/%s/used/?sort=cr_date-desc&has_image=false&customs_state_group=DOESNT_MATTER&page=%s"), // brand, page number
    CAR_INFO_URL("/%s/cars/%s/used/?page=%s"); // city, brand, page number

    override fun toString(): String {
        return this.url
    }
}
