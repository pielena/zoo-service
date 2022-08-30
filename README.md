# Zoo-service

This project consists of three services:

- auth-service, where user can register and log in, includes the limit of the fail attempts
- animal-service is for CRUD operations with user's animals
- gateway service is a way of hiding the other services behind a single facade.

### Features:

- Java 11
- Spring Boot 2.7.1
- Spring Security, JWT
- PostgreSQL, Spring JPA
- Maven

### Getting started:

- You need to have Maven and JDK11+. The best way to run the project is with IDE like IntelliJ IDEA.
- Clone project from GitHub
- You need specify the correct database settings in application.properties in animal-service and auth-service modules
- Run sql script in animal-service module
- Run all services
- Open Postman for sending requests

### Usage:

1. Registration:

   http://localhost/registration
    
    Send a request with JSON body "username", "userSecret" like this:

   {

   "username":"user12",

   "userSecret":"secret12"

   }

   You will receive a token in return.

    Note that the username must be at least 4 characters and the userSecret must be at least 8 characters, or you'll get an error message.

    The username must be unique, so you can get an error message if this username has been already registered.
    

2. Log in: 

    http://localhost/login
   
    Send a request with JSON body "username", "userSecret". 
    
    You will receive a token in return, if you are already registered. 
    If userSecret is wrong, you'll get an error message. You have 10 fail attempts in a row to log in per hour (you can change max-tries and ban-time in application.properties in auth-service module).


3. Check if username is already in use (without registration)

    http://localhost/check 
   
    POST request with username as JSON in its body like this: 

   {

   "username":"us"

   }

    (On this request you'll get an error message because the username is invalid)


4. For CRUD operations you need to add to your requests the Authorization header with Type Bearer and the token you got earlier.



- POST http://localhost/animals - add your animal. JSON body is like this:
  {

        "birthday": "2022-06-20",
        "animalType": "MONKEY",
        "animalSex": "FEMALE",
        "name": "Tra-la-lu"
  }
 
   Note that the name must be unique, so you can get an error message if it already exists in database.

- PUT http://localhost/animals/{id} - update your animal
- DELETE http://localhost/animals/{id} - delete your animal
- GET http://localhost/animals - get list of your animals
- GET http://localhost/animals/{id} - you can get any animal by id, not only yours. But, of course, you can't change or delete them.

### Contacts: 

- Author: Pilyugina Elena
- Gmail: elena.pilyugina.job@gmail.com
- https://github.com/pielena/security-crud-gateway





