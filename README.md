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
---
### Для загрузки в Artifact Registry

```$xslt
# Логинимся в gcloud
>gcloud auth configure-docker LOCATION-docker.pkg.dev

# Помечаем и пушим image
>docker tag SOURCE-IMAGE LOCATION-docker.pkg.dev/PROJECT-ID/REPOSITORY/IMAGE:TAG
>docker push LOCATION-docker.pkg.dev/PROJECT-ID/REPOSITORY/IMAGE

# Для примера 
>gcloud auth configure-docker europe-west6-docker.pkg.dev
>docker tag app europe-west6-docker.pkg.dev/sound-fastness-194720/autoru/app:latest
>docker push europe-west6-docker.pkg.dev/sound-fastness-194720/autoru/app:latest
```
