# Better Jaksec – Attendance Tracking System

## 1. Project Title & Overview
Better Jaksec is a web-based attendance tracking system designed for educational institutions to simplify course management, attendance marking, and reporting. The system allows teachers to create and manage courses, mark attendance manually or via QR codes, and generate attendance reports. Students can view their attendance history and absence reasons per course.

The target users are teachers, students, and administrators in schools. The system is built using React (frontend), Spring Boot (backend REST API), and MariaDB (database).

The project was developed over 8 sprints, each lasting 2 weeks, following an Agile/Scrum methodology.

---

## 2. Product Vision
The vision of Better Jaksec is to digitize and simplify attendance tracking in educational environments by providing an accurate, transparent, and easy-to-use system.

### Goals
- Replace manual attendance tracking with a digital system
- Provide real-time and reliable attendance data
- Reduce administrative workload for teachers
- Improve transparency for students regarding attendance

### Key Features
- Course and lesson management
- Student enrollment in courses
- Manual and QR-based attendance marking
- Attendance tracking and reporting
- Multi-language support (i18n)
- Role-based access control (student, teacher, admin)

### Definition of Success
The project is successful if:
- Teachers can efficiently manage courses and attendance
- Students can reliably mark and track attendance
- Core features work without critical issues
- System is deployable using Docker with clear documentation

---

## 3. Project Plan & Sprint Structure
The project followed Agile Scrum methodology with 2-week sprints.

### Sprint Overview
1. Sprint 1: Project Planning & Vision
2. Sprint 2: Requirements & Database Design
3. Sprint 3: UI Implementation & CI Pipeline
4. Sprint 4: Docker Containerization
5. Sprint 5: UI Localization
6. Sprint 6: Database Localization
7. Sprint 7: Quality Assurance
8. Sprint 8: Documentation & Finalization

[Sprint reports can be found here](https://github.com/MustBeViable/Better_Jaksec/tree/main/Documents/Sprint_reports)

---

## 4. Sprint 1 – Project Planning & Vision
- Initial project planning and scope definition
- Product vision creation and validation
- Backlog creation and prioritization
- Risk analysis and scope definition

---

## 5. Sprint 2 – Requirements & Database
- Functional requirements defined
- Use case agram created
- Entity-Relationship (ER) diagram designed
- MariaDB selected as relational database
- Spring Data JPA used for ORM

### Testing
- Unit testing strategy defined using JUnit
- Backend service layer tests implemented

[Diagrams can be found here](https://github.com/MustBeViable/Better_Jaksec/tree/main/Documents/diagrams)

---

## 6. Sprint 3 – UI Implementation & CI
- React frontend implementation started
- Core UI screens developed (login, courses, attendance)
- Navigation and basic state management implemented

### CI Pipeline (Jenkins)
- Build stage: compile frontend and backend
- Test stage: run unit tests
- Coverage stage: generate test reports

[JaCoCo test report can be found here](https://users.metropolia.fi/~leevivl/better-jaksec/jacoco/)

---

## 7. Sprint 4 – Docker Containerization
- Backend and database containerized using Docker
- Docker Compose used for full environment setup
- Simplified local development and deployment

### Usage
- Single command startup using docker-compose
- Alternative docker run setup for manual configuration

[Docker image can be found here](https://hub.docker.com/r/leevivl/better-jaksec-api)

---

## 8. Sprint 5 – UI Localization
### Localization
- Supported languages: fi-FI, de-DE, en-US, ja-JP, zh-CN, fa-IR
- Implemented using react-i18next
- Language resources stored in frontend i18n structure

---

## 9. Sprint 6 – Database Localization
- Locale-based data model implemented
- Localized tables include locale column
- Queries filtered based on selected language
- Duplicate handling managed via locale separation

![Entity Relation diagram of DB localization](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/diagrams/better-jaksec-er-wl10n.png)

Entity relation diagram of how localized data is mapped

---

## 10. Sprint 7 – Quality Assurance
- Code quality monitored using SonarQube
- Static analysis for maintainability and bugs
- Functional testing performed
- Non-functional testing performed

[Testing reports can be found here](https://github.com/MustBeViable/Better_Jaksec/tree/main/Documents/tests_and_quality)

---

## 11. Sprint 8 – Documentation & Finalization
- Technical documentation completed
- User documentation prepared
- API structure documented
- Final system architecture diagrams updated

### System Overview
- React frontend
- Spring Boot REST API backend
- MariaDB database
- Docker-based deployment

---

## 12. How to Run the Project

### Prerequisites
- Docker and Docker Compose installed

### Run with Docker Compose
```bash
docker-compose up -d
docker-compose down -v
```

### Default Admin Credentials
- Email: admin@example.com
- Password: adminpassword

### Alternative Docker Run
```bash
docker run -d \
  --name better-jaksec-api \
  -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/dbname \
  -e SPRING_DATASOURCE_USERNAME=dbuser \
  -e SPRING_DATASOURCE_PASSWORD=dbpass \
  -p 8080:8080 \
  leevivl/better-jaksec-api:latest
```
![Example production environment the authors have in use](https://github.com/MustBeViable/Better_Jaksec/blob/main/Documents/diagrams/deployment_diagram/deployment_diagram.png)

Deployment diagram of an example production environment

---

## 13. Testing Instructions

### Unit Tests
```bash
mvn test
```

### Coverage
Coverage reports are generated during CI pipeline execution.

---

## 14. Repository Structure
- [Frontend in separate repo](https://github.com/MustBeViable/BetterJaksec_frontend)
- /src/main – Spring Boot REST API
- [/Documents/diagrams](https://github.com/MustBeViable/Better_Jaksec/tree/main/Documents/diagrams) – diagrams
- /src/test – unit test files

---

## 15. Authors

### Team Members
- Development Team: Sakari Honkavaara, Leevi Laune, Elias Rinne

### Course Information
- Course: Software Engineering Project 1&2
- Semester: Spring 2026
