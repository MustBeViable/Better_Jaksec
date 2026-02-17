#!/bin/bash

BASE_URL="http://localhost:8081/api"

teacher1_resp=$(curl -s -X POST "$BASE_URL/teacher" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Alice",
        "lastName": "Smith",
        "email": "alice.smith@example.com",
        "password": "password123",
        "isAdmin": true
      }')
teacher1_id=$(echo "$teacher1_resp" | jq -r '.teacherID')

teacher2_resp=$(curl -s -X POST "$BASE_URL/teacher" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Mark",
        "lastName": "Taylor",
        "email": "mark.taylor@example.com",
        "password": "password123",
        "isAdmin": false
      }')
teacher2_id=$(echo "$teacher2_resp" | jq -r '.teacherID')

teacher3_resp=$(curl -s -X POST "$BASE_URL/teacher" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Laura",
        "lastName": "Wilson",
        "email": "laura.wilson@example.com",
        "password": "password123",
        "isAdmin": false
      }')
teacher3_id=$(echo "$teacher3_resp" | jq -r '.teacherID')
student1_resp=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Bob",
        "lastName": "Johnson",
        "email": "bob.johnson@example.com",
        "password": "password123"
      }')
student1_id=$(echo "$student1_resp" | jq -r '.studentID')

student2_resp=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Emma",
        "lastName": "Brown",
        "email": "emma.brown@example.com",
        "password": "password123"
      }')
student2_id=$(echo "$student2_resp" | jq -r '.studentID')

student3_resp=$(curl -s -X POST "$BASE_URL/student" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Daniel",
        "lastName": "Miller",
        "email": "daniel.miller@example.com",
        "password": "password123"
      }')
student3_id=$(echo "$student3_resp" | jq -r '.studentID')
