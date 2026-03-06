# Better_Jaksec

Link to the sprint Report folder
***[Front end here](https://github.com/MustBeViable?tab=repositories)***

***Project Description***

Better jaksec provides a attendance tracking system for schools. It provides key features such:
  - Courses/lessons:
    - Creatin new
    - Updating already existing one
    - Deleting
  - Attendance marking:
    - For now only teacher is able to mark students attendace
      - In the future student is able to mark it via QR-code
  - Attendance tracking and reports:
    - teacher can create report:
      - Individual course overall attendance percentage
      - Individual students attendance percentag per course/ per lesson
      - Individual students reason for absence
    - studen can:
      - Students own courses overal percentage
      - Check students own lessons reason for absence and mark it
 
**Idea:**


-----------------------------------

***Project techstack***

**Frontend**

1. React
2. Vanilla CSS

Decision reasons:
  - Web application first approach reaches more users and less limitations
  - React enables us to create reactive web application
  - Vanilla css limits our dependecies.

-----------------------------------

**Backend**

1. Spring Boot for Rest API
2. Spring Data JPA

Decision reasons:
  - Spring Boot enables to use only one language at the backend and creating RESTful API authentication support, and seamless integration with the database.
  - Spring Data JPA enables to use modular API to database connection

-----------------------------------

**Database**

1. MariaDB/HeidiSQL

Decision reasons:
  - SQL based relational database to store information
  - MariaDB provides enough types

-----------------------------------

**Additional APIs, Framework etc**

1. [QR code API](https://goqr.me/api/)
2. [Java JWT: JSON Web Token for Java and Android](https://github.com/jwtk/jjwt)

Decision reasons:
  - QR code API for QR code generation for attendance sessions
  - JJWT for authentication and authorization (JWT-based authentication)

## Running docker image locally
### Via docker-compose

Benefits of this way is that you dont need to have database locally setup, everything neded to run this application is created

Cons of this way is that you need to have 2 files copied for it

requirements
- docker compose is installed on the system
- docker-compose.yml and init.sql are copied and in the same folder

to bring up the services run
`docker-compose up -d`

- Some systems might use `docker compose` instead of `docker-compose`

to bring down the services run
`docker-compose down -v`

Default admin login is <br>
email=admin@example.com <br>
pass=adminpassword <br>

### Docker run

Benefits of this way is that its a single command and no file copying needed

Cons of this way is that you need to have a database setup locally (or remotely) yourself

requirements
- empty database w/ user with full access
- docker (+docker desktop on non linux systems) installed
- ability to make http requests, browser or curl easiest

to bring up the service run
```
docker run -d \
  --name better-jaksec-api \
  -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:dbport/dbname \
  -e SPRING_DATASOURCE_USERNAME=dbuser \
  -e SPRING_DATASOURCE_PASSWORD=dbpass \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  -p external_port:8080 \
  leevivl/better-jaksec-api:latest
```

`dbport` mariadb default is 3306 <br>
`dbname` is the name of empty db <br>
`dbuser` name of the db user <br>
`dbpass` password of the db user <be>
`external_port` is what port you want to run the application, 8080 is a common one but might have some other service running there

after the service is running, send a request to <br>
`http://localhost:external_port/api/auth/admin/init`

Default admin login is <br>
email=admin@betterjaksec.com <br>
pass=adminpassword <br>

***Arichteture Design (ER and Use Case diagram) very short elaboration of the image***

Entity relation diagram:

![Use case]()
  - Illustrates users and heir use cases for the program. Student councelor notification is not implemented.

![Entity relation diagram](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/diagrams/better-jaksec-er.png)
  - Entity relation diagram made with [ERDplus](ERDplus.com). Picture illustrates entity relation diagram. Users have been divided to students and teachers. Teachers contain admins.

Relational schema:

![Relational schema](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/diagrams/better-jaksec-relational.png)
  - Relational schema for our database. Database is created using object relational mapping when initiating API. 

[Link to sprint report folder](https://github.com/MustBeViable/Better_Jaksec/tree/main/Documents/Sprint_reports)
