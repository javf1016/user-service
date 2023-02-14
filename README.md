# user-service
User-service eureka

EndPoints

Lista Paginada
* http://localhost:9001/user/list?page=1&size=10

Registrar un Usuario
* http://localhost:9001/user/register
* Body: {
        "firstName": "Jorge",
        "lastName": "Vera",
        "email": "javf1016@hotmail.com",
        "password": "UmgREZZJ"
}

Buscar por email
* http://localhost:9001/user/findemail/javf1016@hotmail.com
