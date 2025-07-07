# 📋 ToDoList API

A RESTful API developed in Java using Spring Boot for task management, featuring JWT authentication and secure access.

📦 Tech Stack

⚙️ Java 24

🌱 Spring Boot 3

🔐 Spring Security + JWT

📂 MySQL + JPA (Hibernate)

🧪 JUnit (for future unit testing)

🧠 Monolithic Structure

---

Layered architecture:

📁 src/main/java/com/Ariel_Rom/toDoList
├── models → JPA entities
├── dtos → Data Transfer Objects
├── controllers → REST endpoints
├── services → business logic
├── repositories → Data Access Layer (DAO)
├── security → security configuration and JWT

---

🚀 Run the App

✅ Requirements

Have a running MySQL database

Configure the application.yml file with your credentials:

spring:
datasource:
url: jdbc:mysql://localhost:3306/yourDatabase
username: your_user
password: your_password

▶️ Run command: mvn spring-boot:run

---

📬 Main Endpoints

| Method | Route                      | Description                | 🔐 Auth |
| ------ | -------------------------- | -------------------------- | ------- |
| POST   | /app/v1/task/auth/register | User registration          | No      |
| POST   | /app/v1/task/auth/login    | User login                 | No      |
| POST   | /app/v1/task/create        | Create a task              | Yes     |
| GET    | /app/v1/task/              | List all tasks             | Yes     |
| GET    | /app/v1/task/{id}          | Get task by ID             | Yes     |
| PUT    | /app/v1/task/{id}          | Update a task              | Yes     |
| DELETE | /app/v1/task/{id}          | Delete a task              | Yes     |

🔒 Protected endpoints require a JWT token in the header:

Authorization: Bearer <token>

---

🧪 Postman Example (Authentication & CRUD)

🔐 Registration

POST /app/v1/task/auth/register

Request Body:

{
"username": "exampleUser",
"email": "example@email.com",
"password": "1234"
}

Response:

{
"token": "eyJhbGcifasdlfkjJKfSDafIkpXVCJ9..."
}

🔑 Login

POST /app/v1/task/auth/login

Request Body:

{
"username": "exampleUser",
"password": "1234"
}

Response:

{
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6Ikp432F..."
}

📌 Create a Task

POST /app/v1/task/createHeaders: Authorization: Bearer <your_token>

Request:

{
"title": "Study Spring Boot",
"description": "Review JWT security and practice DTOs"
}

Response:

{
"title": "Study Spring Boot",
"description": "Review JWT security and practice DTOs"
}

📄 Get Tasks

GET /app/v1/task?page=1&limit=5

Response:

{
"data": [
{
"title": "Study Spring Boot",
"description": "Review JWT security and practice DTOs"
},
...
],
"page": 1,
"limit": 5,
"total": 10
}

🔍 Get Task by ID

GET /app/v1/task/1

Response:

{
"title": "Study Spring Boot",
"description": "Review JWT security and practice DTOs"
}

✏️ Update Task

PUT /app/v1/task/1

Request:

{
"title": "Study Unit Tests",
"description": "Review JUnit for Java unit testing"
}

Response:

{
"title": "Study Unit Tests",
"description": "Review JUnit for Java unit testing"
}

🗑️ Delete Task

DELETE /app/v1/task/1

Response: HTTP 204 No Content

---

👨‍💻 Author

Developed by Ariel Romero. 🧐💻

🔗 Linkedin: https://www.linkedin.com/in/arielrom/

📜 License

This project is licensed under the MIT License.

---

https://roadmap.sh/projects/todo-list-api
