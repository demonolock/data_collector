package steps.autoru

import beans.autoru.AutoInfo
import endpoints.AutoRuEndpoints
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.stream.IntStream

class CarsInfoParser {

    fun writeAutoInfoToCsv(brands: List<String>, start: Int, end: Int, threadsAmount: Int = 10) {
        val service: ExecutorService = Executors.newFixedThreadPool(threadsAmount)
        IntStream.range(start, end).forEach { i ->
            run {
                for (brand in brands) {
                    service.submit { writeCarsData(brand, getCarsData(brand, i)) }
                }
            }
        }
        service.awaitTermination(0L, TimeUnit.SECONDS)
        service.shutdown()
    }

    fun createFiles(brands: List<String>) {
        for (brand in brands) {
            val pathName = "${System.getProperty("user.dir")}/lts/data_collector/src/main/resources/all_$brand.csv"
            val path = Paths.get(File("${System.getProperty("user.dir")}/lts/data_collector/src/main/resources/all_$brand.csv").toString())
            if (!Files.exists(path)) {
                Files.createFile(path)
                val outputStream: OutputStream = FileOutputStream(pathName)
                val writer = OutputStreamWriter(outputStream)
                writer.write("brand; productionDate; bodyType; color; engineDisplacement;enginePower; fuelType; vehicleTransmission; drive; wheel; own; state; customs; price; mileage; owners; car_url; image_url; parsing_unixtime; model; pts; description")
                writer.flush()
                writer.close()
            }
        }
    }

    fun parseAutoruPage(brand: String, index: Int): List<AutoInfo> {
        val autoru: Document = Jsoup.connect(AutoRuEndpoints.BASE_URL.url + AutoRuEndpoints.CARS_INFO_ALL.url.format(brand, index)).get()
        val headlines: Elements = autoru.select(".ListingItem-module__main")
        var autosInfo = arrayListOf<AutoInfo>()
        for (headline in headlines) {
            val techSummary = headline.select(".ListingItemTechSummaryDesktop.ListingItem-module__techSummary")
            val carInfo = headline.select(".ListingItemTechSummaryDesktop__cell")
            val engineInfo = techSummary.select(".ListingItemTechSummaryDesktop__cell")[0].text()
            val autoInfo = AutoInfo(
                parsing_unixtime = LocalDateTime.now(),
                brand = brand,
                productionDate = headline.select(".ListingItem-module__year").text().trim(),
                model = headline.select("h3").text().trim(),
                engineDisplacement = engineInfo.substringBefore("/").substringBefore(" л").trim(),
                enginePower = engineInfo.substringAfter("/").substringBefore("/").substringBefore(" л.c.").trim(),
                fuelType = engineInfo.substringAfterLast("/").trim(),
                vehicleTransmission = carInfo[1].text().trim(),
                bodyType = carInfo[2].text().substringBefore(" ").trim(),
                numberOfDoors = carInfo[2].text().substringAfter(" ").substringBefore(" ").trim(),
                drive = carInfo[3].text().trim(),
                color = carInfo[4].text().trim(),
                price = headline.select(".ListingItemPrice-module__content").text().replace("₽", "").replace(" ", "")
                    .toLongOrNull(),
                car_url = headline.select("a.Link.ListingItemTitle-module__link").attr("href").trim(),
                image_url = headline.select("a.Link.OfferThumb").attr("href").trim(),
                mileage = headline.select(".ListingItem-module__columnCellKmAge").text().replace("км", "")
                    .replace(" ", "").toLongOrNull(),
            )
            val autoPage: Document = Jsoup.connect(autoInfo.car_url).get()
            autoInfo.wheel = autoPage.select(".CardInfoRow.CardInfoRow_wheel").text().substringAfter("Руль").trim()
            autoInfo.state = autoPage.select(".CardInfoRow.CardInfoRow_state").text().substringAfter("Состояние").trim()
            autoInfo.owners =
                autoPage.select(".CardInfoRow.CardInfoRow_ownersCount").text().substringAfter("Владельцы").trim()
            autoInfo.pts = autoPage.select(".CardInfoRow.CardInfoRow_pts").text().substringAfter("ПТС").trim()
            autoInfo.customs =
                autoPage.select(".CardInfoRow.CardInfoRow_customs").text().substringAfter("Таможня").trim()
            autoInfo.description = autoPage.select(".CardDescription__textInner").text().trim()
            autosInfo.add(autoInfo)
        }
        return autosInfo
    }

    fun getCarsData(brand: String, index: Int): List<AutoInfo> {
        val autosInfo = parseAutoruPage(brand, index)
        println("$brand - $index : ${autosInfo.size}")
        return autosInfo
    }

    fun writeCarsData(brand: String, autosInfo: List<AutoInfo>) {
        val pathName = "${System.getProperty("user.dir")}/lts/data_collector/src/main/resources/all_$brand.csv"
        OutputStreamWriter(FileOutputStream(pathName)).use {
            for (autoInfo in autosInfo) {
                it.appendLine(" ${autoInfo.brand}; ${autoInfo.productionDate}; ${autoInfo.bodyType}; ${autoInfo.color}; ${autoInfo.engineDisplacement}; ${autoInfo.enginePower}; ${autoInfo.fuelType}; ${autoInfo.vehicleTransmission}; ${autoInfo.drive}; ${autoInfo.wheel}; ${autoInfo.own}; ${autoInfo.state}; ${autoInfo.customs}; ${autoInfo.price}; ${autoInfo.mileage}; ${autoInfo.owners}; ${autoInfo.car_url}; ${autoInfo.image_url}; ${autoInfo.numberOfDoors}; ${autoInfo.parsing_unixtime}; ${autoInfo.model}; ${autoInfo.pts}; ${autoInfo.description}")
            }
        }
    }
}

