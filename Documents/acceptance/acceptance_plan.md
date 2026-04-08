# Acceptance Test Plan

## Project Information

- **Project name:** BetterJakSec  
- **Team members:** Sakari Honkavaara, Leevi Laune, Elias Rinne  
- **Sprint / version:** Sprint 6 v1.0  
- **Document date:** 8.4.2026  
- **Prepared by:** Sakari Honkavaara, Leevi Laune, Elias Rinne  

---

## 1. Purpose

The purpose of this document is to define the acceptance criteria and planned acceptance tests for the BetterJakSec project.  
This document only includes the **planning and design** of the tests.

---

## 2. Acceptance Criteria

Acceptance criteria are based on the original project requirements and sprint requirements.

### Functional Criteria

- The system must allow a **teacher** to log in successfully.
- The system must allow a **student** to log in successfully.
- The system must allow an **admin** to log in successfully.
- The system must allow a teacher to create and manage courses.
- The system must allow a teacher to view students assigned to a course.
- The system must allow attendance to be marked as present or absent.
- The system must allow previously saved attendance to be updated.
- The system must allow a student to scan a QR code for attendance.
- The system must allow a student to view their attendance statistics.
- The system must allow an admin to manage users (create, update, delete).
- Invalid input or missing required information must show a clear error message.
- The system must allow a teacher to view attendance statistics.

### Usability Criteria

- The main views and navigation must be easy to understand for all roles.
- A teacher must be able to mark attendance without extra guidance.
- A student must be able to scan a QR code easily.
- Attendance statistics must be clearly presented to students.
- Admin user management must be intuitive and clear.
- Buttons, labels, and status information must be clear.
- Error messages must help the user understand how to fix the problem.

### Performance and Reliability Criteria

- The system should respond within an acceptable time during normal use.
- QR code scanning must work quickly and reliably.
- Saving attendance should not cause crashes or freezing.
- Data should remain correct after saving and refreshing the page.
- Attendance statistics must display correct data consistently.
- Repeating workflows (login, attendance marking, QR scanning) must not break the system.

---

## 3. Requirements / User Stories

- **R1:** As a teacher, I want to log in so that I can use the system securely.  
- **R2:** As a teacher, I want to create and manage courses so that I can organize teaching.  
- **R3:** As a teacher, I want to see students in a course so that I can manage attendance.  
- **R4:** As a teacher, I want to mark students as present or absent so that attendance is recorded correctly.  
- **R5:** As a teacher, I want to update already marked attendance so that mistakes can be corrected.  

- **R6:** As a student, I want to log in so that I can access my attendance data.  
- **R7:** As a student, I want to scan a QR code so that my attendance is recorded quickly.  
- **R8:** As a student, I want to view my attendance statistics so that I can track my participation.  

- **R9:** As an admin, I want to log in so that I can manage the system.  
- **R10:** As an admin, I want to manage users so that I can control access and roles.  
- **R11:** As a teacher, I want to check attendance statistics so that I can monitor student participation.

---

## 4. Coverage Matrix

| Requirement | Test Case ID | Test Type |
|------------|-------------|----------|
| R1 | AT-01 | Functional |
| R2 | AT-02 | Functional |
| R3 | AT-04 | Usability |
| R4 | AT-03 | Functional |
| R5 | AT-05 | Performance / Reliability |
| R6 | AT-06 | Functional |
| R7 | AT-07 | Functional |
| R8 | AT-08 | Usability |
| R9 | AT-09 | Functional |
| R10 | AT-10 | Functional |
| R11 | AT-11 | Functional |


---

## 5. Acceptance Test Cases

### AT-01 – Teacher Login
- **Type:** Functional  
- **Requirement:** R1  
- **Description:** Verify that a teacher can log in with valid credentials.  
- **Steps:**
  1. Open login page
  2. Enter valid credentials
  3. Click login  
- **Expected Result:** Teacher is logged in and main view opens.

---

### AT-02 – Course Management
- **Type:** Functional  
- **Requirement:** R2  
- **Description:** Verify that a teacher can create a course.  
- **Steps:**
  1. Log in as teacher
  2. Navigate to course management
  3. Create course  
- **Expected Result:** Course is saved and displayed.

---

### AT-03 – Attendance Marking
- **Type:** Functional  
- **Requirement:** R4  
- **Description:** Verify attendance marking.  
- **Steps:**
  1. Open course
  2. Mark students present/absent
  3. Save  
- **Expected Result:** Attendance is saved correctly.

---

### AT-04 – Course View Usability
- **Type:** Usability  
- **Requirement:** R3  
- **Description:** Verify ease of use of course view.  
- **Steps:**
  1. Log in
  2. Open course  
- **Expected Result:** User understands view without guidance.

---

### AT-05 – Attendance Update Reliability
- **Type:** Performance / Reliability  
- **Requirement:** R5  
- **Description:** Verify updating attendance works repeatedly.  
- **Steps:**
  1. Open saved attendance
  2. Modify data
  3. Save and refresh  
- **Expected Result:** Changes persist and system remains stable.



---

### AT-06 – Student Login
- **Type:** Functional  
- **Requirement:** R6  
- **Description:** Verify student login.  
- **Steps:**
  1. Open login page
  2. Enter student credentials  
- **Expected Result:** Student dashboard opens.

---

### AT-07 – QR Code Attendance
- **Type:** Functional  
- **Requirement:** R7  
- **Description:** Verify QR scanning for attendance.  
- **Steps:**
  1. Log in as student
  2. Open scanner
  3. Scan QR code  
- **Expected Result:** Attendance recorded successfully.

---

### AT-08 – Attendance Statistics
- **Type:** Usability  
- **Requirement:** R8  
- **Description:** Verify statistics visibility.  
- **Steps:**
  1. Log in as student
  2. Open statistics view  
- **Expected Result:** Data is clear and understandable.

---

### AT-09 – Admin Login
- **Type:** Functional  
- **Requirement:** R9  
- **Description:** Verify admin login.
- **Steps:**
  1. Open login page
  2. Enter valid admin credentials      
- **Expected Result:** Admin dashboard opens.

---

### AT-10 – User Management
- **Type:** Functional  
- **Requirement:** R10  
- **Description:** Verify admin can manage users.  
- **Steps:**
  1. Log in as admin
  2. Create, edit, delete user  
- **Expected Result:** Changes are saved and visible.

—

### AT-11 – Attendance Statistics
- **Type:** Functional  
- **Requirement:** R11 
- **Description:** Verify that a teacher can view attendance statistics.  
- **Steps:**
  1. Log in as a teacher  
  2. Open attendance tracking view  
  3. View statistics 
- **Expected Result:** Attendance statistics (e.g., percentages per student or course) are displayed correctly.

—


## 6. Summary

This acceptance test plan defines acceptance criteria and test coverage for **teacher, student, and admin roles** in BetterJakSec.  
It ensures validation of **functional, usability, and reliability requirements** across all key workflows.

