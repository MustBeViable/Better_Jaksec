## Layer Responsibilities

- **Controller**  
  Handles HTTP requests and responses. No business logic.

- **Service**  
  Contains business rules and coordinates repositories.

- **Repository**  
  Handles database access using Spring Data JPA.

- **Entity**  
  Represents database tables using JPA annotations.

- **DTOs**  
  Define the API contract and prevent exposing entities directly.

- **Mapper**  
  Converts between entities and DTOs to keep layers decoupled.
