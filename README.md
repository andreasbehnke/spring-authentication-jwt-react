# Spring Authentication with JWT, REST API and React Frontend
Building block for a web application authentication using 
JWT cookie, spring security, stateless REST backend and react in the frontend. 
Fork or copy this code for your own web application.

The goal of this repository is to

* use the latest and up to date libraries for JWT token generation
* write as little code as possible to accomplish the task
* use 100% of the security API provided by spring instead of writing own code

**Warning**:

* For now the code is incomplete, it is work in progress.
* The code is demonstrating the correct usage of user based authentication with spring and react only, it does not include all other hardening required to run a secure application in production.
* Don't use the flyway SQL scripts directly, make sure your app db user is different from the flyway user with reduced permissions!

## Usage of this building block

You can use this code for free in your code regarding the requirements
of the apache licence 2.0 .

## Backend Technology Stack

* Spring Security
* Spring Web with REST API for user authentication
* Spring Data JPA for User persistence
* SQL migration scripts for postgres but can be migrated to any SQL DB
* Flyway Migration

### Start Backend

Start database container:

* ```cd backend```
* ```docker-compose start```

Access postgres admin running at http://localhost:8090/ with password "admin":

* click "Add New Server"
* Tab "Connection"
* host: postgres
* port: 5432
* password: changeme

Start backend app:

* ```./mvnw spring-boot:run```

## Web Frontend Techonoly Stack

* React
* Typescript
* material-ui

### Start Web Frontend

* ```cd frontend```
* ```npm start```

### Build Web Frontend

* ```npm run build```
  
Builds the app for production to the build folder.
It correctly bundles React in production mode and optimizes the build for the best performance.
