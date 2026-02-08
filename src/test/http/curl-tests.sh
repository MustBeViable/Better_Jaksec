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

teacherID=$(echo "$teacher_resp" | jq '.teacherID')
if [[ "$teacherID" == "null" ]]; then
    echo "Error creating Teacher: $teacher_resp"
    exit 1
fi
echo "Teacher created: $teacherID"

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

studentID=$(echo "$student_resp" | jq '.studentID')
if [[ "$studentID" == "null" ]]; then
    echo "Error creating Student: $student_resp"
    exit 1
fi
echo "Student created: $studentID"

# -------------------------
# CREATE COURSE
# -------------------------
echo "Creating Course..."
course_resp=$(curl -s -X POST "$BASE_URL/course" \
  -H "Content-Type: application/json" \
  -d "{
        \"courseName\": \"Backend Programming Advanced\",
        \"lessonIds\": [],
        \"assignmentIds\": [],
        \"teacherIds\": [$teacherID]
      }")

courseID=$(echo "$course_resp" | jq '.id')
if [[ "$courseID" == "null" ]]; then
    echo "Error creating Course: $course_resp"
    exit 1
fi
echo "Course created: $courseID"

# -------------------------
# CREATE LESSON
# -------------------------
echo "Creating Lesson..."
lesson_resp=$(curl -s -X POST "$BASE_URL/lesson" \
  -H "Content-Type: application/json" \
  -d "{
        \"lessonName\": \"JPA Basics\",
        \"courseId\": $courseID,
        \"date\": \"2026-02-08T12:00:00Z\"
      }")

lessonID=$(echo "$lesson_resp" | jq '.lessonID')
if [[ "$lessonID" == "null" ]]; then
    echo "Error creating Lesson: $lesson_resp"
    exit 1
fi
echo "Lesson created: $lessonID"

# -------------------------
# CREATE ASSIGNMENT
# -------------------------
echo "Creating Assignment..."
assignment_resp=$(curl -s -X POST "$BASE_URL/assignment" \
  -H "Content-Type: application/json" \
  -d "{
        \"assignmentName\": \"Homework 1\",
        \"assignmentDescription\": \"Read chapters 1-3 and complete exercises.\",
        \"courseId\": $courseID
      }")

assignmentID=$(echo "$assignment_resp" | jq '.id')
if [[ "$assignmentID" == "null" ]]; then
    echo "Error creating Assignment: $assignment_resp"
    exit 1
fi
echo "Assignment created: $assignmentID"

# -------------------------
# GET ALL ENTITIES
# -------------------------
echo "Fetching all entities..."
curl -s "$BASE_URL/teacher/$teacherID" | jq
curl -s "$BASE_URL/student/$studentID" | jq
curl -s "$BASE_URL/course/$courseID" | jq
curl -s "$BASE_URL/lesson/$lessonID" | jq
curl -s "$BASE_URL/assignment/$assignmentID" | jq

# -------------------------
# UPDATE TEACHER
# -------------------------
echo "Updating Teacher..."
curl -s -X PUT "$BASE_URL/teacher/$teacherID" \
  -H "Content-Type: application/json" \
  -d "{
        \"firstName\": \"AliceUpdated\",
        \"lastName\": \"SmithUpdated\",
        \"email\": \"alice.updated@example.com\",
        \"isAdmin\": false
      }" | jq

# -------------------------
# UPDATE COURSE
# -------------------------
echo "Updating Course..."
curl -s -X PUT "$BASE_URL/course/$courseID" \
  -H "Content-Type: application/json" \
  -d "{
        \"courseName\": \"Backend Advanced Updated\",
        \"lessonIds\": [$lessonID],
        \"assignmentIds\": [$assignmentID],
        \"teacherIds\": [$teacherID]
      }" | jq

echo "===== API TEST COMPLETE ====="