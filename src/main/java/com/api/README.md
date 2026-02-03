## Layer Responsibilities

- **Controller**  
  Handles HTTP requests and responses.

- **Service**  
  Contains business rules and coordinates repositories. Includes "transacitions" to the database.
  Assosiates with mapper and repository.

- **Repository**  
  Handles database access using Spring Data JPA.
  Extended class got save and delete, but If you need extra checking implement here.

- **Entity**  
  Represents database tables using JPA annotations. Same as in OOP course.

- **DTOs**  
  Define the API contract and prevent exposing entities directly.
  Needs only create and update. Delete and get does already exist.

- **Mapper**  
  Converts between entities and DTOs to keep layers decoupled.
  Needs only toEntity, toDTO and updateEntity (toStudentEntity, toStudentDto, updateStudentEntity (void)) 
