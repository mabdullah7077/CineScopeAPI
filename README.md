# CineScopeAPI

A RESTful API built with **Java and Spring Boot**, serving film data from the [Sakila](https://dev.mysql.com/doc/sakila/en/) sample database. It's the backend for [CineScope](https://github.com/mabdullah7077/CineScope), a React film-browsing front end.

## Tech stack

- Java + Spring Boot
- Spring Data JPA
- MySQL (Sakila sample schema)
- Maven

## Getting started

**Prerequisites:** JDK 17+, Maven (or use the bundled `./mvnw`), and a running MySQL instance with the Sakila database loaded.

1. Load the Sakila schema and data into MySQL — see the [Sakila installation guide](https://dev.mysql.com/doc/sakila/en/sakila-installation.html).

2. Set these environment variables (the app reads all DB config from them — nothing is hardcoded):

   ```
   MYSQL_HOSTNAME=localhost
   MYSQL_PORT=3306
   MYSQL_DB_NAME=sakila
   MYSQL_USERNAME=your_user
   MYSQL_PASSWORD=your_password
   ```

3. Run it:

   ```bash
   ./mvnw spring-boot:run
   ```

   The API starts on http://localhost:8080.

## Endpoints

<!-- List your actual controllers/routes here. Examples: -->
- `GET /films` — list all films
- `GET /films/{id}` — get a film by id
- `GET /actors` — list actors
- `GET /categories` — list film categories

## Configuration

All database settings are supplied via environment variables (see above) and read in `src/main/resources/application.properties`. No credentials are committed to the repo.

## Related

- Frontend: [CineScope](https://github.com/mabdullah7077/CineScope)
