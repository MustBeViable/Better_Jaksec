#!/bin/bash

BASE_URL="http://localhost:8081/api"

echo "===== START API TEST ====="

# -------------------------
# CREATE TEACHER
# -------------------------
teacher_resp=$(curl -s -X POST "$BASE_URL/teacher" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Alice",
        "lastName": "Smith",
        "email": "alice.smith@example.com",
        "password": "password123",
        "isAdmin": true
      }')

teacherID=$(echo "$teacher_resp" | jq '.teacherID')
echo "Teacher created: $teacherID"

# -------------------------
# CREATE STUDENT
# -------------------------
student_resp=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Bob",
        "lastName": "Johnson",
        "email": "bob.johnson@example.com",
        "password": "password123"
      }')

studentID=$(echo "$student_resp" | jq '.studentID')
echo "Student created: $studentID"

# -------------------------
# CREATE COURSE
# -------------------------
course_resp=$(curl -s -X POST "$BASE_URL/course" \
  -H "Content-Type: application/json" \
  -d "{
        \"courseName\": \"Backend Programming Advanced\",
        \"lessonIds\": [],
        \"assignmentIds\": [],
        \"teacherIds\": [$teacherID]
      }")

courseID=$(echo "$course_resp" | jq '.id')   # ✅ Correct field
echo "Course created: $courseID"

# -------------------------
# CREATE LESSON
# -------------------------
lesson_resp=$(curl -s -X POST "$BASE_URL/lesson" \
  -H "Content-Type: application/json" \
  -d "{
        \"lessonName\": \"JPA Basics\",
        \"courseId\": $courseID,
        \"date\": \"2026-02-08T12:00:00Z\"
      }")

lessonID=$(echo "$lesson_resp" | jq '.lessonID')
echo "Lesson created: $lessonID"

# -------------------------
# CREATE ASSIGNMENT
# -------------------------
assignment_resp=$(curl -s -X POST "$BASE_URL/assignment" \
  -H "Content-Type: application/json" \
  -d "{
        \"assignmentName\": \"Homework 1\",
        \"assignmentDescription\": \"Read chapters 1-3 and complete exercises.\",
        \"courseId\": $courseID
      }")

assignmentID=$(echo "$assignment_resp" | jq '.id')
echo "Assignment created: $assignmentID"

# -------------------------
# MARK STUDENT ATTENDANCE
# -------------------------
attendance_resp=$(curl -s -X POST "$BASE_URL/student/$studentID/attendance" \
  -H "Content-Type: application/json" \
  -d "{
        \"lessonId\": $lessonID,
        \"present\": false,
        \"reason\": \"\"
      }")

attendanceID=$(echo "$attendance_resp" | jq '.id')
echo "Attendance marked: $attendanceID"

# -------------------------
# CREATE STUDENT COURSE GRADE
# -------------------------
student_course_resp=$(curl -s -X POST "$BASE_URL/student/$studentID/grade" \
  -H "Content-Type: application/json" \
  -d "{
        \"studentId\": $studentID,
        \"courseId\": $courseID,
        \"grade\": 5
      }")

echo "StudentCourse creation response:"
echo "$student_course_resp" | jq

# Extract grade ID from response (if your DTO has no ID field, use courseId)
gradeID=$(echo "$student_course_resp" | jq '.courseId')
echo "Grade created for course ID: $gradeID"

# -------------------------
# FETCH STUDENT COURSE GRADE
# -------------------------
echo "Fetching StudentCourse Grade..."
curl -s "$BASE_URL/student/$studentID/grade/$gradeID" | jq

# -------------------------
# UPDATE STUDENT COURSE GRADE
# -------------------------
curl -s -X PUT "$BASE_URL/student/$studentID/grade/$gradeID" \
  -H "Content-Type: application/json" \
  -d '{
        "grade": 4
      }' | jq

# -------------------------
# FINAL FETCH
# -------------------------
echo "===== FINAL ENTITY STATE ====="
curl -s "$BASE_URL/student/$studentID" | jq
curl -s "$BASE_URL/course/$courseID" | jq

echo "===== API TEST COMPLETE ====="