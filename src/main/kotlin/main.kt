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
    System.setProperty("javax.net.ssl.trustStore", "/path/to/web2.uconn.edu.jks");
    CarsInfoParser().createFiles(brands)
    CarsInfoParser().writeAutoInfoToCsv(city="moskva", brands = brands, start = 1, end = 30, threadsAmount = 10)
    CarsInfoParser().writeAutoInfoToCsv(city="sankt-peterburg", brands = brands, start = 1, end = 30, threadsAmount = 10)
}
