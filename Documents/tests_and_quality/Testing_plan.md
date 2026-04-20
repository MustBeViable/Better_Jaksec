# Test Plan

## Project Information

- **Project name:** BetterJakSec
- **Project type:** School attendance tracking system
- **Sprint / version:** Sprint 7
- **Related project documents:** Acceptance Test Plan, Sprint 7 Plan, Jenkinsfile, Docker configuration

---

## 1. Objective

The objective of this test plan is to define how BetterJakSec will be tested during Sprint 7 so that the delivered product is functional, usable, and aligned with the project requirements.

This test plan combines the work already defined in the existing project documents and organizes it under one clear structure required by the sprint assignment.

The main objectives of testing are:

- to verify that the implemented core features work correctly
- to confirm that earlier implemented functionality still works after code cleanup and fixes
- to identify, track, and fix bugs found during testing
- to validate the product through both functional and non-functional testing
- to support the final sprint deliverables, including Jenkins build verification, Docker deployment verification, SonarQube reporting, heuristic evaluation, and user acceptance testing

The most important features to verify are based on the current acceptance criteria and sprint goals:

- login for teacher, student, and admin
- course creation and management
- viewing students assigned to a course
- marking attendance as present or absent
- updating previously saved attendance
- generating and reviewing attendance-related results and core user flows

---

## 2. Resources

### 2.1 Team Resources

The project currently assumes:

- **3 developers**
- **10 effective sprint days**
- **approximately 20 hours per developer**

Team members:

- Sakari Honkavaara
- Leevi Laune
- Elias Rinne

### 2.2 Human Resources for Testing

Testing work is divided across the project team. Depending on the activity, the same team members may act in different roles:

- **Developers** perform unit testing and fix defects
- **Project team** performs integration checks, regression checks, and environment verification
- **Evaluators** perform heuristic evaluation of the UI
- **Users / acceptance testers** perform user acceptance testing against defined criteria

### 2.3 Tools and Technical Resources

The current project already includes the following testing and quality-related resources:

- **JUnit** for backend unit testing
- **Jenkins** for automated build, test execution, and reporting
- **JaCoCo** for code coverage reporting
- **SonarQube** for static code analysis
- **Docker** and **docker-compose** for deployment and environment verification
- **MariaDB** as the database used in the test environment
- **Spring Boot / Maven** for build and backend execution
- **Frontend build tooling** through Node.js and npm in Jenkins
- **GitHub repository documentation** for acceptance criteria, sprint goals, and traceability

### 2.4 Test Deliverables

The following outputs are expected from testing:

- completed test plan
- executed unit and integration test results
- bug / issue tracking table
- Jenkins build evidence
- SonarQube report screenshots
- heuristic evaluation report
- user acceptance testing results
- documentation of technical changes based on testing outcomes

---

## 3. Test Environment

### 3.1 Application Environment

BetterJakSec is a web-based attendance tracking system with:

- **Frontend:** React
- **Backend:** Spring Boot REST API
- **Database:** MariaDB

### 3.2 Build and Execution Environment

The current repository shows that testing and deployment are supported with:

- Maven-based backend build
- Java runtime for the backend application
- Jenkins pipeline stages for build, unit tests, coverage, frontend build, and Docker image handling
- Dockerfile for containerized backend packaging
- docker-compose setup for database and API services

### 3.3 Environment Configuration Used in Testing

Based on the current repository setup, the test environment includes:

- **Database service:** MariaDB 11
- **Database port mapping:** host port `3307` to container port `3306`
- **API container:** Spring API connected to MariaDB
- **Database initialization:** `init.sql`
- **Application packaging:** built JAR file executed in Docker runtime

### 3.4 Quality Verification Environment

The CI/CD-related environment used for quality verification includes:

- Jenkins pipeline executing:
    - `mvn clean compile`
    - `mvn test`
    - `mvn jacoco:report`
- JUnit result publishing from `target/surefire-reports/*.xml`
- SonarQube static analysis generation through Jenkins
- Docker image build and deployment verification

### 3.5 Test Data and Accounts

Testing requires valid example data and user roles such as:

- teacher account
- student account
- admin account
- courses, students, and attendance entries for testing the main flows

---

## 4. Test Tasks

### 4.1 Functional Test Tasks

#### 4.1.1 Unit Testing

Unit tests are used to verify that individual backend components and methods work correctly after implementation changes and code cleanup.

Focus areas:

- recently changed functionality
- core backend logic
- validation rules
- attendance update logic
- course and user related service logic

#### 4.1.2 Integration Testing

Integration testing is used to verify that the main parts of the application work correctly together.

Focus areas:

- frontend and backend interaction
- backend and database interaction
- login flow
- course management flow
- attendance marking and updating flow

#### 4.1.3 System / End-to-End Style Verification

System-level verification is used to ensure the whole application works as expected in its intended environment.

Focus areas:

- application starts successfully
- database connection works
- Docker deployment works correctly
- main user flows can be completed without critical failure

#### 4.1.4 Regression Testing

Regression testing is used after bug fixes and code cleanup to ensure previously working functionality has not broken.

Focus areas:

- authentication
- course CRUD operations
- attendance marking
- attendance updates
- existing reporting and role-based functionality

#### 4.1.5 User Acceptance Testing (UAT)

User acceptance testing validates whether the system meets the previously defined acceptance criteria.

Acceptance testing should at minimum verify:

- teacher login
- student login
- admin login
- course creation and management
- viewing assigned students in a course
- marking attendance
- updating previously saved attendance

#### 4.1.6 Bug Tracking and Fix Verification

A bug table must be maintained during testing.

For each bug, record at least:

- bug ID
- description
- severity / priority
- component or feature affected
- status
- responsible team member
- retest result

### 4.2 Non-Functional Test Tasks

#### 4.2.1 Heuristic Evaluation

Heuristic evaluation is carried out on the final UI according to lecture instructions.

Recommended execution:

- use **3 to 5 evaluators**
- evaluators inspect the UI independently
- findings are aggregated afterward
- each issue is assigned a severity rating
- results are discussed with the team and used to improve the UI

Suggested evaluation areas:

- visibility of system status
- consistency and standards
- error prevention
- error messages
- user control and freedom
- clarity of navigation and feedback
- language and terminology

#### 4.2.2 Usability / UI Review

Usability observations from heuristic evaluation and acceptance testing should be reviewed and converted into actionable fixes when possible during the sprint.

#### 4.2.3 Performance Testing

Performance testing is optional in the sprint instructions, but recommended if time allows.

Potential focus areas:

- API response times for key endpoints
- page loading responsiveness
- system behavior under higher request volume

#### 4.2.4 Security Testing

Security testing is optional in the sprint instructions, but recommended if time allows.

Potential focus areas:

- authentication handling
- authorization checks by role
- invalid input handling
- exposure of sensitive data in responses or logs

#### 4.2.5 Static Code Quality Verification

Static quality verification is performed through Jenkins and SonarQube.

Goals:

- successful Jenkins build
- successful automated test execution
- SonarQube report generated
- quality grades at target level required by the course

#### 4.2.6 Documentation Updates

All important test-related findings must be reflected in project documentation.

Update targets:

- GitHub documentation
- Sprint 7 report
- Trello user stories if functionality or scope changes
- technical documentation if testing results require architecture or specification updates

---

## 5. Main Test Scope

The current main scope of testing in Sprint 7 is:

### In scope

- authentication flows
- role-based access related core flows
- course management
- student-course visibility
- attendance marking and update functionality
- Jenkins build pipeline verification
- Docker deployment verification
- static code quality reporting
- acceptance testing
- heuristic evaluation

### Out of scope or lower priority

These can be tested if time remains, but they are not the primary sprint requirement:

- extensive load testing
- advanced penetration testing
- broad optimization outside sprint goals
- major redesign work not directly linked to identified issues

---

## 6. Entry and Exit Criteria

### Entry Criteria

Testing can begin when:

- the latest sprint implementation is available
- the application builds successfully
- required test accounts and data exist
- the test environment is available
- acceptance criteria and key user flows are identified

### Exit Criteria

Testing for the sprint is considered sufficiently completed when:

- planned functional tests have been executed
- major bugs have been logged and addressed or clearly documented
- Jenkins build passes
- Docker deployment has been verified
- SonarQube report has been generated
- heuristic evaluation has been completed
- UAT results have been documented
- required sprint documentation has been updated