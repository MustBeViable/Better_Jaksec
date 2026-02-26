#!/bin/bash

BASE_URL="http://localhost:8081/api"
UNIQUE_SUFFIX="$(date +%s)_$RANDOM"

echo "===== START API TEST ====="

# =========================
# LOGIN ADMIN
# =========================
echo "Logging In Admin..."
login_resp=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
        "email": "admin@example.com",
        "password": "adminpassword"
      }')

echo "$login_resp" | jq
admin_token=$(echo "$login_resp" | jq -r '.token')

# =========================
# CREATE TEACHER
# =========================
teacherEmail="alice.smith_${UNIQUE_SUFFIX}@example.com"
teacherPass="password123"

echo "Creating Teacher..."
teacher_resp=$(curl -s -X POST "$BASE_URL/teacher" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $admin_token" \
  -d "{
        \"firstName\": \"Alice\",
        \"lastName\": \"Smith\",
        \"email\": \"$teacherEmail\",
        \"password\": \"$teacherPass\",
        \"isAdmin\": true
      }")

echo "$teacher_resp" | jq
teacherID=$(echo "$teacher_resp" | jq -r '.teacherID')

echo "Logging In Teacher..."
login_resp=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
        \"email\": \"$teacherEmail\",
        \"password\": \"$teacherPass\"
      }")

teacher_token=$(echo "$login_resp" | jq -r '.token')

# =========================
# CREATE STUDENT 1
# =========================
studentEmail="bob.johnson_${UNIQUE_SUFFIX}@example.com"
studentPass="password123"

echo "Creating Student..."
student_resp=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $admin_token" \
  -d "{
        \"firstName\": \"Bob\",
        \"lastName\": \"Johnson\",
        \"email\": \"$studentEmail\",
        \"password\": \"$studentPass\"
      }")

echo "$student_resp" | jq
studentID=$(echo "$student_resp" | jq -r '.studentID')

echo "Logging In Student..."
login_resp=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
        \"email\": \"$studentEmail\",
        \"password\": \"$studentPass\"
      }")

student_token=$(echo "$login_resp" | jq -r '.token')

# =========================
# CREATE STUDENT 2
# =========================
student2Email="test.student_${UNIQUE_SUFFIX}@example.com"
student2Pass="password123"

echo "Creating Student2..."
student_resp2=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $admin_token" \
  -d "{
        \"firstName\": \"Test\",
        \"lastName\": \"Student\",
        \"email\": \"$student2Email\",
        \"password\": \"$student2Pass\"
      }")

echo "$student_resp2" | jq
student2ID=$(echo "$student_resp2" | jq -r '.studentID')

echo "Logging In Student2..."
login_resp=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
        \"email\": \"$student2Email\",
        \"password\": \"$student2Pass\"
      }")

student2_token=$(echo "$login_resp" | jq -r '.token')

# =========================
# DUPLICATE EMAIL TEST
# =========================
echo "Attempting duplicate student creation (should fail)..."
duplicate_resp=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $admin_token" \
  -d "{
        \"firstName\": \"Duplicate\",
        \"lastName\": \"Student\",
        \"email\": \"$studentEmail\",
        \"password\": \"password123\"
      }")

echo "$duplicate_resp" | jq

# =========================
# CREATE COURSE (Teacher)
# =========================
echo "Teacher Creating Course..."
course_resp=$(curl -s -X POST "$BASE_URL/course" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $teacher_token" \
  -d "{
        \"courseName\": \"Backend Programming Advanced ${UNIQUE_SUFFIX}\",
        \"lessonIds\": [],
        \"assignmentIds\": [],
        \"teacherIds\": [$teacherID]
      }")

echo "$course_resp" | jq
courseID=$(echo "$course_resp" | jq -r '.id')

# =========================
# CREATE LESSON
# =========================
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

# =========================
# CREATE ASSIGNMENT
# =========================
echo "Creating Assignment..."
assignment_resp=$(curl -s -X POST "$BASE_URL/assignment" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $teacher_token" \
  -d "{
        \"assignmentName\": \"Homework 1\",
        \"assignmentDescription\": \"Read chapters 1-3.\",
        \"courseId\": $courseID
      }")

echo "$assignment_resp" | jq

# =========================
# MARK ATTENDANCE
# =========================
echo "Marking Own Attendance..."
curl -s -X POST "$BASE_URL/student/$studentID/attendance" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $student_token" \
  -d "{
        \"lessonId\": $lessonID,
        \"present\": true,
        \"reason\": \"\"
      }" | jq

echo "Attempting to Mark Other Student Attendance (should fail)..."
curl -s -X POST "$BASE_URL/student/$studentID/attendance" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $student2_token" \
  -d "{
        \"lessonId\": $lessonID,
        \"present\": false,
        \"reason\": \"\"
      }" | jq

# =========================
# CREATE GRADE
# =========================
echo "Creating Grade..."
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

# =========================
# FINAL FETCHES
# =========================
echo "Fetching Student..."
curl -s "$BASE_URL/student/$studentID" \
  -H "Authorization: Bearer $student_token" | jq

echo "Fetching Course as Teacher..."
curl -s "$BASE_URL/course/$courseID" \
  -H "Authorization: Bearer $teacher_token" | jq

echo "===== API TEST COMPLETE ====="