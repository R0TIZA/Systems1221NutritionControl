<h2>Restfull CRUD API "Nutrition Control"<br>Тестовое задание для 1221 Systems</h2>
<h3>Проект состоит из 4 контроллеров, каждый из которых занимается валидацией входных данных, обработкой исключений и возвращением ответа:</h3>
<ul>
    <li>
        <h3>User controller</h3>
        <h4>
            Методы:
            <br>- handleGetAllUsers - метод: Get, путь: /api/v1/user, возвращает: список всех пользователей.
            <br>- handleGetUser - метод: Get, путь: /api/v1/user/{userId}, возвращает: запрашиваемого пользователя.
            <br>- handleAddNewUser - метод: Post, путь: /api/v1/user, возвращает: созданного пользователя.
            <br>- handleUpdateUser - метод: Update, путь: /api/v1/user/{userId}, возвращает: обновленного пользователя.
            <br>- handleDeleteUser - метод: Delete, путь: /api/v1/user/{userId}, возвращает: Http status code 200, если пользователь был удален и Http status code 404, если пользователя нет в базе.
        </h4>
    </li>
    <li>
        <h3>Dish controller</h3>
        <h4>
            Методы:
            <br>- handleGetDish - метод: Get, путь: /api/v1/dish/{dishId}, возвращает: запрашиваемое блюдо.
            <br>- handleAddNewDish - метод: Post, путь: /api/v1/dish, возвращает: созданное блюдо.
            <br>- handleUpdateDish - метод: Update, путь: /api/v1/dish/{dishId}, возвращает: обновленное блюдо.
            <br>- handleDeleteDish - метод: Delete, путь: /api/v1/dish/{dishId}, возвращает: Http status code 200, если блюдо было удалено, в противном случае - Http status code 404.
        </h4>
    </li>
    <li>
        <h3>Meal controller</h3>
        <h4>
            Методы:
            <br>- handleGetMeal - метод: Get, путь: /api/v1/meal/{mealId}, возвращает: запрашиваемый прием пищи.
            <br>- handleAddNewMeal - метод: Post, путь: /api/v1/meal, возвращает: созданный прием пищи.
            <br>- handleUpdateMeal - метод: Update, путь: /api/v1/meal/{mealId}, возвращает: обновленный прием пищи.
            <br>- handleDeleteMeal - метод: Delete, путь: /api/v1/meal/{mealId}, возвращает: Http status code 200, если прием пищи был удален, в противном случае - Http status code 404.
        </h4>
    </li>
    <li>
        <h3>Report controller</h3>
        <h4>
            Методы:
            <br>- handleGetDailyReport - метод: Get, путь: /api/v1/report/daily/{userId}, возвращает: список приемов пищи за текущий день и общее количество калорий.
            <br>- handleCheckBmr - метод: Get, путь: /api/v1/report/bmr/{userId}?date={yyyy-mm-dd}, возвращает: true если пользователь уложился в норму в указанный день, в противном случае false.
            <br>- handleGetMealHistoryReport - метод: Get, путь: /api/v1/report/history/{userId}?date={yyyy-mm-dd}, возвращает: список приемов пищи в указанный день и общее количество калорий.
        </h4>
    </li>
</ul>
<h3>Контроллеры не занимаются задачами работы с базой данных, агрегации объектов и конвертации объектов DTO в объекты-представления. Для этих целей были реализованны классы-сервисы, которым эти задачи делегируются.</h3>
<h3>Помимо всего прочего были реализованны классы-представлений: User, Dish, Meal. И классы DTO: NewUserRequestDTO, UpdateUserRequestDTO, NewDishRequestDTO, UpdateDishRequestDTO, NewMealRequestDTO, UpdateMealRequestDTO, MealHistoryReportDTO.</h3>
<h3>
    Для запуска проекта необходимы следующие шаги:
    <ol>
        <li>Клонировать проект</li>
        <li>Создать пустую базу данных в Postges</li>
        <li>Создать переменные окружения DB_URL, DB_USERNAME, DB_PASSWORD</li>
        <li>При помощи команды "mvn package" собрать билд проекта</li>
        <li>Запустить билд проекта при помощи команды "java -jar {билд_проекта}"</li>
    </ol>
    !Для тестирования API загрузил postman коллекцию, находится в корне проекта, в папке postman!
</h3>