import steps.autoru.CarsInfoParser

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

fun main(args: Array<String>) {
    CarsInfoParser().createFiles(brands)
    CarsInfoParser().writeAutoInfoToCsv(brands = brands, start = 19, end = 100, threadsAmount = 10)
}
