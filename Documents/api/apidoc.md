# Student Management API Documentation

Base URL:

http://localhost:8081/api


All requests and responses use **JSON**.

---

## Domain Overview

This API manages:

- Students
- Teachers
- Courses
- Lessons
- Assignments
- Attendance (Student ↔ Lesson)
- Grades (Student ↔ Course)

DTOs are used to **transfer data only** (no entities exposed).

---

1. Student

StudentDto
```json
{
  "studentID": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "attendance": [],
  "grades": []
}
```
2. Teacher
TeacherDto
```json

{
  "teacherID": 10,
  "firstName": "Alice",
  "lastName": "Smith",
  "email": "alice.smith@email.com",
  "admin": true
}
```
3. Course
CourseDto
```json

{
  "id": 100,
  "name": "Mathematics",
  "lessonIds": [1000, 1001],
  "assignmentIds": [2000],
  "teacherNames": ["Alice Smith"]
}
```
4. Lesson
LessonDto
```json

{
  "lessonID": 1000,
  "lessonName": "Algebra Basics",
  "date": "2026-01-10T09:00:00Z",
  "courseId": 100
}
```
5. Assignment
AssignmentDto
```json

{
  "id": 2000,
  "assignmentName": "Homework 1",
  "assignmentDescription": "Linear equations",
  "courseId": 100
}
```
6. Attendance (Student ↔ Lesson)
StudentLessonDto
```json

{
  "present": true,
  "lessonId": 1000,
  "studentId": 1,
  "lessonDate": "2026-01-10T09:00:00Z"
}
```
7. Grades (Student ↔ Course)
StudentCourseDto
```json

{
  "studentId": 1,
  "courseId": 100,
  "grade": 95
}
```

Assignment API
Create Assignment

POST

/api/assignment

Request Body
```json
{
  "assignmentName": "Homework 1",
  "assignmentDescription": "Linear equations",
  "courseId": 100
}
```
Response 200 OK
```json
{
  "id": 2000,
  "assignmentName": "Homework 1",
  "assignmentDescription": "Linear equations",
  "courseId": 100
}
```
Get Assignment

GET

/api/assignment/{assignmentID}

Example

/api/assignment/2000

Response 200 OK

```json
{
  "id": 2000,
  "assignmentName": "Homework 1",
  "assignmentDescription": "Linear equations",
  "courseId": 100
}
```
Update Assignment

PUT

/api/assignment/{assignmentID}

Request Body
```json
{
  "assignmentName": "Homework 1 (Updated)",
  "assignmentDescription": "Linear + quadratic equations"
}
```
Response 200 OK
```json
{
  "id": 2000,
  "assignmentName": "Homework 1 (Updated)",
  "assignmentDescription": "Linear + quadratic equations",
  "courseId": 100
}
```
End-to-End Example Flow
1. Create a Course
```json
{
  "name": "Mathematics"
}
```
→ returns courseId = 100
2. Create a Lesson for the Course
```son
{
  "lessonName": "Algebra Basics",
  "date": "2026-01-10T09:00:00Z",
  "courseId": 100
}
```
→ returns lessonId = 1000

3. Create an Assignment
```json

{
  "assignmentName": "Homework 1",
  "assignmentDescription": "Linear equations",
  "courseId": 100
}
```

→ returns assignmentId = 2000
4. Enroll Student & Record Attendance
```json
{
  "present": true,
  "lessonId": 1000,
  "studentId": 1,
  "lessonDate": "2026-01-10T09:00:00Z"
}
```
5. Assign Grade
```json
{
  "studentId": 1,
  "courseId": 100,
  "grade": 95
}
```
6. Fetch Student Overview
Response
```json
{
  "studentID": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "attendance": [
    {
      "present": true,
      "lessonId": 1000,
      "studentId": 1,
      "lessonDate": "2026-01-10T09:00:00Z"
    }
  ],
  "grades": [
    {
      "studentId": 1,
      "courseId": 100,
      "grade": 95
    }
  ]
}
```

