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
    CarsInfoParser().writeAutoInfoToCsv(brands = brands, start = 1, end = 30, threadsAmount = 10)
}
