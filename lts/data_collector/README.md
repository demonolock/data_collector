# Data collector
____

Инструмент сбора данных с сайтов

Kotlin, Jsoup
____
### Структура проекта
Для парсинга html страниц используется библиотека `Jsoup` (аналогична `BeautifulSoup` в Python)

Описание дата классов для парсинга находятся по пути `beans.{название_сайта}` (для авто.ру - `beans.autoru.AutoInfo`).
Сам парсинг происходит в классе `CarsInfoParser`.

----
### Для запуска

```
fun main(args: Array<String>) {
    CarsInfoParser().createFiles(brands)
    CarsInfoParser().writeAutoInfoToCsv(brands = brands, start = 1, end = 200, threadsAmount = 10)
}
```
Метод `createFiles` создает csv файлы для дальнейшего сбора данных. Если файл уже есть, то создаваться не будет.
Метод `writeAutoInfoToCsv` извлекает данные с сайта для страниц вида `/cars/${brand}/used/?page=${index}` и записывает в созданный методом `createFiles` csv-файл.

`start` - индекс страницы с которой начинаем парсинг

`end` - индекс страницы на которой парсинг будет остановлен

`threadsAmount` - количество потоков для обработки (по умолчанию 10)

