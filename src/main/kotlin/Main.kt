import steps.autoru.CarsInfoParser

fun main() {
    val brands = listOf(
        "lexus",
        "bmw",
        "audi",
        "honda",
        "infiniti",
        "mercedes",
        "mitsubishi",
        "nissan",
        "skoda",
        "toyota",
        "volkswagen",
        "volvo"
    )
    CarsInfoParser()
        .createFiles(brands)
        .writeAutoInfoToCsv(city = "moskva", brands = brands, start = 1, end = 2, threadsAmount = 1)
        .writeAutoInfoToCsv(city = "sankt-peterburg", brands = brands, start = 1, end = 2, threadsAmount = 1)
}