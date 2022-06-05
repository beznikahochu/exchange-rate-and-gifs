# Exchange-Rate-And-Gifs

Создать сервис, который обращается к сервису курсов валют, и отображает gif:
* если курс по отношению к USD за сегодня стал выше вчерашнего, то отдаем рандомную [отсюда](https://giphy.com/search/rich)
* если ниже - [отсюда](https://giphy.com/search/broke)

Ссылки
* [REST API курсов валют](https://docs.openexchangerates.org/)
* [REST API гифок](https://developers.giphy.com/docs/api#quick-start-guide)

Must Have
* Сервис на Spring Boot 2 + Java / Kotlin
* Запросы приходят на HTTP endpoint (должен быть написан в соответствии с rest conventions), туда передается код валюты по отношению с которой сравнивается USD
* Для взаимодействия с внешними сервисами используется Feign
* Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
* На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
* Для сборки должен использоваться Gradle
* Результатом выполнения должен быть репо на GitHub с инструкцией по запуску

Nice to Have
* Сборка и запуск Docker контейнера с этим сервисом


***

# Инструкция по запуску
1) Клонировать репоситорий из GitHub, выполнив команду\
`git clone https://github.com/beznikahochu/exchange-rate-and-gifs`
2) Открыть папку с проектом в командной строке
3) Скомпилировать проект, выполнив команду:\
Windows: `gradlew build`\
Linux: `sh gradlew build`
4) Создать docker-image:\
`docker image build -t exchange-rate-and-gifs .`
5) Создать и запустить контейнер:\
`docker run --rm -p 8080:8080 exchange-rate-and-gifs` 

### Переменные среды
| Название переменной | Описание                                                                                                                                                                                                          | 
|------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| 
| GIF_APP_PORT           | Порт на котором будет запущено приложение                                                                                                                                                                         | 
| BASE_CURRENCY | Валюта по отнощению к которой будет сравнение<br/>[Допустимые значения](https://openexchangerates.org/api/currencies.json)<br/>В бесплатной версии [api](https://docs.openexchangerates.org/) доступно только USD | 
| EXCHANGE_RATE_APP_ID | Идентификатор приложения [OpenExchangeRates](https://docs.openexchangerates.org/)                                                                                                                                 |
|GIF_API_KEY| API ключ [giphy.com](https://developers.giphy.com/docs/api#quick-start-guide)                                                                                                                                     |

### Пример создания и запуска контейнера с переменными
`docker run --rm -e GIF_APP_PORT=7000 -p 8080:7000 exchange-rate-and-gifs` 
***
## Endpoint
#### /api/v1/gif?currency=[Код валюты](https://openexchangerates.org/api/currencies.json)
Пример:
`http://localhost:8080/api/v1/gif?currency=RUB`
