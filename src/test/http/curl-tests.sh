#!/bin/bash

BASE_URL="http://localhost:8081/api"

echo "===== START API TEST ====="

# -------------------------
# CREATE TEACHER
# -------------------------
echo "Creating Teacher..."
teacher_resp=$(curl -s -X POST "$BASE_URL/teacher" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Alice",
        "lastName": "Smith",
        "email": "alice.smith@example.com",
        "password": "password123",
        "isAdmin": true
      }')

echo "$teacher_resp" | jq
teacherID=$(echo "$teacher_resp" | jq -r '.teacherID')
teacherPass="password123"
teacherEmail="alice.smith@example.com"

echo "Teacher created with ID: $teacherID"
echo "-----------------------------------"

echo "Logging In Teacher..."
login_resp=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
        \"email\": \"$teacherEmail\",
        \"password\": \"$teacherPass\"
      }")

echo "$login_resp" | jq

teacher_token=$(echo "$login_resp" | jq -r '.token')
echo "Teacher Token: $teacher_token"

# -------------------------
# CREATE STUDENT
# -------------------------
echo "Creating Student..."
student_resp=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Bob",
        "lastName": "Johnson",
        "email": "bob.johnson@example.com",
        "password": "password123"
      }')

studentPass="password123"
studentEmail="bob.johnson@example.com"
echo "Logging In Student..."
login_resp=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
        \"email\": \"$studentEmail\",
        \"password\": \"$studentPass\"
      }")

student_token=$(echo "$login_resp" | jq -r '.token')
echo "Student Token: $student_token"
studentID=$(echo "$student_resp" | jq -r '.studentID')

echo "Creating Student2..."
student_resp=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "test",
        "lastName": "student",
        "email": "test.student@example.com",
        "password": "password123"
      }')

student2Pass="password123"
student2Email="test.student@example.com"
echo "Logging In Student..."
login_resp=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
        \"email\": \"$student2Email\",
        \"password\": \"$student2Pass\"
      }")

student2_token=$(echo "$login_resp" | jq -r '.token')

echo "$student_resp" | jq
student2ID=$(echo "$student_resp" | jq -r '.studentID')
echo "Student created with ID: $studentID"
echo "-----------------------------------"

echo "Creating Student 2..."
student_resp2=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Bobby",
        "lastName": "Johnson",
        "email": "bob.johnson@example.com",
        "password": "password123"
      }')

echo "$student_resp2" | jq

# -------------------------
# CREATE COURSE
# -------------------------
echo "Student Creating Course..."
course_resp=$(curl -s -X POST "$BASE_URL/course" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $student_token" \
  -d "{
        \"courseName\": \"Backend Programming Advanced\",
        \"lessonIds\": [],
        \"assignmentIds\": [],
        \"teacherIds\": [$teacherID]
      }")

echo "$course_resp" | jq
courseID=$(echo "$course_resp" | jq -r '.id')
echo "Course created with ID: $courseID"
echo "-----------------------------------"

echo "Teacher Creating Course..."
course_resp=$(curl -s -X POST "$BASE_URL/course" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $teacher_token" \
  -d "{
        \"courseName\": \"Backend Programming Advanced\",
        \"lessonIds\": [],
        \"assignmentIds\": [],
        \"teacherIds\": [$teacherID]
      }")

echo "$course_resp" | jq
courseID=$(echo "$course_resp" | jq -r '.id')
echo "Course created with ID: $courseID"
echo "-----------------------------------"
# -------------------------
# CREATE LESSON
# -------------------------
echo "Creating Lesson..."
lesson_resp=$(curl -s -X POST "$BASE_URL/lesson" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $teacher_token" \
  -d "{
        \"lessonName\": \"JPA Basics\",
        \"courseId\": $courseID,
        \"date\": \"2026-02-08T12:00:00Z\"
      }")

echo "$lesson_resp" | jq
lessonID=$(echo "$lesson_resp" | jq -r '.lessonID')
echo "Lesson created with ID: $lessonID"
echo "-----------------------------------"


# -------------------------
# CREATE ASSIGNMENT
# -------------------------
echo "Creating Assignment..."
assignment_resp=$(curl -s -X POST "$BASE_URL/assignment" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $teacher_token" \
  -d "{
        \"assignmentName\": \"Homework 1\",
        \"assignmentDescription\": \"Read chapters 1-3 and complete exercises.\",
        \"courseId\": $courseID
      }")

echo "$assignment_resp" | jq
assignmentID=$(echo "$assignment_resp" | jq -r '.id')
echo "Assignment created with ID: $assignmentID"
echo "-----------------------------------"


# -------------------------
# MARK STUDENT ATTENDANCE
# -------------------------
echo "Marking Attendance..."
attendance_resp=$(curl -s -X POST "$BASE_URL/student/$studentID/attendance" \
  -H "Content-Type: application/json" \
  -d "{
        \"lessonId\": $lessonID,
        \"present\": false,
        \"reason\": \"\"
      }")

echo "$attendance_resp" | jq
attendanceID=$(echo "$attendance_resp" | jq -r '.id')
echo "Attendance created with ID: $attendanceID"
echo "-----------------------------------"


# -------------------------
# CREATE STUDENT COURSE GRADE
# -------------------------
echo "Creating Student Course Grade..."
student_course_resp=$(curl -s -X POST "$BASE_URL/student/grade" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $teacher_token" \
  -d "{
        \"studentId\": $studentID,
        \"courseId\": $courseID,
        \"grade\": 5
      }")

echo "$student_course_resp" | jq
gradeID=$(echo "$student_course_resp" | jq -r '.courseId')
echo "Grade created for Course ID: $gradeID"
echo "-----------------------------------"


# -------------------------
# FETCH STUDENT COURSE GRADE
# -------------------------
echo "Fetching StudentCourse Grade..."
curl -s "$BASE_URL/student/grade/$gradeID" | jq
echo "-----------------------------------"


# -------------------------
# UPDATE STUDENT COURSE GRADE
# -------------------------
echo "Updating Grade..."
curl -s -X PUT "$BASE_URL/student/grade/$gradeID" \
  -H "Content-Type: application/json" \
  -d '{
        "grade": 4
      }' | jq
echo "-----------------------------------"

# -------------------------
# FETCH CURRENT USER (/auth/me)
# -------------------------
echo "Fetching Current Authenticated User..."
me_resp=$(curl -s -X GET "$BASE_URL/auth/me" \
  -H "Authorization: Bearer $student2_token" \
  -H "Content-Type: application/json")

echo "$me_resp" | jq
echo "-----------------------------------"

echo "$login_resp" | jq
# -------------------------
# FINAL FETCH
# -------------------------
echo "===== FINAL ENTITY STATE ====="
curl -s "$BASE_URL/student/$studentID" \
  -H "Authorization: Bearer $student_token" | jq

echo "Unauthorized student fetches course"
curl -s "$BASE_URL/course/$courseID" \
  -H "Authorization: Bearer $student2_token" | jq

echo "Authorized student fetches course"
curl -s "$BASE_URL/course/$courseID" \
  -H "Authorization: Bearer $student_token" | jq

echo "Admin teacher fetches course"
curl -s "$BASE_URL/course/$courseID" \
  -H "Authorization: Bearer $teacher_token" | jq

echo "Student with courses gets all courses"
curl -s "$BASE_URL/course/all" \
  -H "Authorization: Bearer $student_token" | jq

echo "Student without courses gets all courses"
curl -s "$BASE_URL/course/all" \
  -H "Authorization: Bearer $student2_token" | jq

echo "Admin teachers gets all courses"
curl -s "$BASE_URL/course/all" \
  -H "Authorization: Bearer $teacher_token" | jq

echo "===== API TEST COMPLETE ====="