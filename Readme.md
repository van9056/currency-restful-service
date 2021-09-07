## Currency RESTful Service

---

### Инструкция по запуску

Для запуска сервиса вам потребуется предуставленный Docker.
- Команда для запуска сервиса с настройками по умолчанию:
  
   `docker run --rm -p 8080:8080 --name gif-app -d van9056/gif`
  

- Команда для запуска сервиса с пользовательскими настройками:

   `docker run --rm -p 8080:8080 --name gif-app 
  -e BASE='USD'
  -e API_OPENEXCHANGERATES_KEY='your_key'
  -e API_GIPHY_KEY='your_key'
  -d van9056/gif`
, где BASE - базовая валюта, 
  API_OPENEXCHANGERATES_KEY - API ключ сервиса [OpenExchangeRates](https://openexchangerates.org/),
  API_GIPHY_KEY - API ключ сервиса [Giphy](https://giphy.com/).
  
Примечение: Для изменения базового курса валюты требуется аккаунт с платным планом
в сервисе [OpenExchangeRates](https://openexchangerates.org/). В ином случае можно использовать 'USD' по умолчанию.
  
### Http endpoints
Базовый API путь: `http://localhost:8080/`

**API endpoints**:

`GET http://localhost:8080/gif`

Параметры запроса:
`symbol` : string (_Required_) - Код валюты в трехбуквенном формате (Примеры: 'USD', 'RUB', 'EUR' и т.д.)


Примеры:

- `http://localhost:8080/gif?symbol=RUB`
  
- `http://localhost:8080/gif?symbol=EUR`

### Описание
Данный сервис принимает запросы с помощью REST API, обращается к сервису курсов валют и 
предоставляет в качестве результата Gif-картинку. Если курс по отношению к рублю (к примеру) за 
сегодня стал выше вчерашнего, то сервис возвращает Gif-картинку по тэгу 'rich', в противном случае по тэгу 'broke'.