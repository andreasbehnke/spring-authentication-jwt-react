# Spring Authentication with JWT and React Frontend
Building block for a web application authentication using 
JWT cookie, spring security and react in the frontend. 
Fork or copy this code for your own web application.

The goal of this repository is to

* use the latest and up to date libraries for JWT token generation
* write as little code as possible to accomplish the task
* use 100% of the security API provided by spring instead of writing own code

## Backend Technology Stack

* Spring Security
* Spring Web with REST API for user authentication
* Spring Data JPA for User persistence
* SQL migration scripts for postgres but can be migrated to any SQL DB
* Flyway Migration

### Start Backend

Start database container:

* ```cd backend```
* ```docker-compose .```

Access postgres admin running at http://localhost:8090/ with password "admin":

* click "Add New Server"
* Tab "Connection"
* host: postgres
* port: 5432
* password: changeme

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
