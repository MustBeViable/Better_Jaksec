# Attendance API

## Course

-   **POST** `/course`\
    auth: admin + teacher

-   **GET** `/course/all`\
    auth: admin + self

-   **GET** `/course/:id`\
    auth: admin + self

-   **PUT** `/course/:id`\
    auth: admin + teacher

-   **DELETE** `/course/:id`\
    auth: admin + teacher

## StudentCourse / Grade

-   **POST** `/grade`\
    auth: admin + teacher

-   **GET** `/grade/:id`\
    auth: any

-   **PUT** `/grade/:id`\
    auth: admin + teacher

-   **DELETE** `/grade/:id`\
    auth: admin\
    
## Lesson

-   **POST** `/lesson`\
    auth: admin + teacher

-   **GET** `/lesson/all`  **TODO**\
    auth: any

-   **GET** `/lesson/:id`\
    auth: any

-   **PUT** `/lesson/:id`\
    auth: admin + teacher

-   **DELETE** `/lesson/:id`\
    auth: admin + teacher

## StudentLesson / Attendance

-   **POST** `/attendance`\
    auth: any

-   **GET** `/attendance/:id`\
    auth: any

-   **GET** `/attendance/all` **TODO**\
    auth: any

-   **GET** `/attendance/course/:id` **TODO**\
    auth: admin + teacher

-   **PUT** `/attendance/:id`\
    auth: admin + teacher

-   **DELETE** `/attendance/:id`\
    auth: admin + teacher

## Student

-   **POST** `/student`\
    auth: admin

-   **GET** `/student/all`\
    auth: admin

-   **GET** `/student/:id`\
    auth: any

-   **PUT** `/student/:id`\
    auth: admin + self

-   **DELETE** `/student/:id`\
    auth: admin

## Teacher

-   **POST** `/teacher`\
    auth: admin

-   **GET** `/teacher/:id`\
    auth: any

-   **GET** `/teacher/all`\
    auth: any

-   **PUT** `/teacher/:id`\
    auth: admin + self

-   **DELETE** `/teacher`\
    auth: admin (not self)

## Login

-   **GET** `/emailAvailable/:email`\
    auth: none

-   **POST** `/login`\
    auth: none

-   **GET** `/me`\
    auth: self
