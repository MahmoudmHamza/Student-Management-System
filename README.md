# Student Management System

## Motivation

Student management system service is built to help students view and manage courses. 

**Provided functionalities:**

- Login using username and password.
- Get course details by id.
- Get course schedule by id.
- View all courses.
- View user registred courses.
- Register new course.
- Cancel course registration.

### Prerequisites

- **Your favorite Java IDE** (e.g., IntelliJ IDEA, Eclipse) with Spring Boot support.
- **JDK 17:** Make sure you have Java Development Kit 17 installed on your system.
- **Git:** You will need Git for version control and project cloning. 
- **Oracle Database:** This project relies on Oracle as its database. Install and configure Oracle on your system, 
  and ensure that the database connection properties in your project's configuration are correctly set.
- **Redis:** Student management system uses redis for caching to improve performance.

### Clone the Repository

Use the following command to clone the project repository:

```shell
git clone https://github.com/MahmoudmHamza/Student-Management-System.git
```

### Build and Run

1. Make sure your database is up and running as per your configurations.
2. Make sure that Redis is up and running as per your configurations.
3. Open the project in your Java IDE.
4. Build the project.
5. Run the main application class `StudentManagementSystemApplication.java`.

### Access Swagger Documentation

Swagger documentation is available for easy API exploration.

- Open your web browser and navigate to `http://localhost:8000/swagger-ui.html`.
- Here, you can interact with and test the available API endpoints.

### Project Features

- **Aspect Oriented Programming:** The project utilizes Aspect Oriented Programming for separating cross-cutting concerns from business logic such as exception handling and logging.
For the exception handling aspect you will find GlobalExceptionHandler that handles exceptions thrown by controllers in a centralized manner.
You will also find LogAspect that is used to intercept method calls and log relevant information.

- **Flyway Database Migration:** Database migration is managed using Flyway, which ensures database schema changes are applied 
consistently across different environments.

- **JWT Authentication and Refresh Tokens:** JWT (JSON Web Token) authentication is implemented to ensure secure and stateless user authentication. To enhance the user experience,
we use refresh tokens to extend user sessions without requiring them to re-authenticate frequently. When the access token expires, the refresh token can be used to obtain a new access token,
ensuring continuous access while maintaining security.

- **PDF Generation Using OpenPDF:** OpenPDF is used for generating PDFs due to its rich features and ease of use. OpenPDF allows us to create and customize complex documents quickly and efficiently.
Its developer-friendly API simplifies the process, and as an open-source solution.

- **Redis Caching:** We use Redis for caching to enhance performance and scalability. Redis provides fast in-memory data storage, reducing database load and speeding up data retrieval.
Redis helps improve user experience by reducing latency and ensuring quick access to frequently accessed data.

### API Testing Using Postman Collection

- You will find the postman collection of the exposed endpoints test cases a long with its environment variables located in `postman directory` in the repository.



