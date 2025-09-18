# 🚀 Task Management System

A professional Task Management System built with **Spring Boot** and **JPA**. This application provides comprehensive REST APIs for managing users and tasks with role-based access control.

## ✨ Features

- **User Management**: Create and manage users with different roles (ADMIN, USER)
- **Task Management**: Full CRUD operations for tasks
- **Role-Based Access Control**: Different permissions for ADMIN and USER roles
- **Status Tracking**: Track task status (PENDING, IN_PROGRESS, COMPLETED)
- **Filtering**: Filter tasks by status and assigned user
- **Input Validation**: Comprehensive validation with meaningful error messages
- **API Documentation**: Interactive Swagger UI for testing APIs
- **Exception Handling**: Global exception handling with detailed error responses

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Data JPA** (Hibernate)
- **H2 Database** (In-memory for demo)
- **Lombok** (reduces boilerplate code)
- **Swagger/OpenAPI** (API documentation)
- **Maven** (dependency management)

## 📁 Project Structure

```
src/main/java/com/taskmanager/
├── controller/          # REST Controllers
│   ├── UserController.java
│   └── TaskController.java
├── service/            # Business Logic Layer
│   ├── UserService.java
│   └── TaskService.java
├── repository/         # Data Access Layer
│   ├── UserRepository.java
│   └── TaskRepository.java
├── model/              # JPA Entities
│   ├── User.java
│   ├── Task.java
│   ├── Role.java
│   └── TaskStatus.java
├── dto/                # Data Transfer Objects
│   ├── UserRequestDto.java
│   ├── UserResponseDto.java
│   ├── TaskRequestDto.java
│   ├── TaskResponseDto.java
│   ├── TaskUpdateDto.java
│   └── TaskStatusUpdateDto.java
├── exception/          # Exception Handling
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── BadRequestException.java
│   ├── ForbiddenException.java
│   └── ErrorResponse.java
├── config/             # Configuration Classes
│   └── OpenApiConfig.java
└── TaskManagementSystemApplication.java
```

## 🗄 Database Schema

### Users Table
- `id` (Primary Key)
- `name` (String, required)
- `email` (String, unique, required)
- `role` (Enum: ADMIN, USER)

### Tasks Table
- `id` (Primary Key)
- `title` (String, required)
- `description` (Text, optional)
- `status` (Enum: PENDING, IN_PROGRESS, COMPLETED)
- `created_at` (Timestamp, auto-generated)
- `updated_at` (Timestamp, auto-updated)
- `assigned_to` (Foreign Key to Users)

## 🚦 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (or PostgreSQL/H2 for alternatives)

### Database Setup

1. **MySQL Setup** (Default):
   ```sql
   CREATE DATABASE taskmanager_db;
   CREATE USER 'taskuser'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON taskmanager_db.* TO 'taskuser'@'localhost';
   ```

2. **Alternative: Use H2 In-Memory Database** (for quick testing):
   - Uncomment H2 configuration in `application.properties`
   - Comment out MySQL configuration

### Running the Application

1. **Clone and navigate to the project**:
   ```bash
   cd task-management-system
   ```

2. **Update database configuration** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager_db?createDatabaseIfNotExist=true
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**:
   - **API Base URL**: http://localhost:8080/api
   - **Swagger UI**: http://localhost:8080/api/swagger-ui.html
   - **API Docs**: http://localhost:8080/api/api-docs

## 📖 API Endpoints

### User APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create a new user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |

### Task APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks` | Get all tasks (with optional filters) |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update task details |
| PATCH | `/api/tasks/{id}/status` | Update task status only |
| DELETE | `/api/tasks/{id}` | Delete task (ADMIN only) |

### Query Parameters for GET /api/tasks
- `status`: Filter by task status (PENDING, IN_PROGRESS, COMPLETED)
- `userId`: Filter by assigned user ID

## 🔒 Business Rules & Security

1. **Task Assignment**: Every task must be assigned to a valid user
2. **Role-Based Access**:
   - **ADMIN**: Can perform all operations including deleting tasks
   - **USER**: Can only update tasks assigned to them
3. **Validation Rules**:
   - User email must be valid and unique
   - Task title is required
   - Proper error handling with meaningful messages

## 📝 Sample API Usage

### 1. Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "role": "USER"
  }'
```

### 2. Create a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Complete project documentation",
    "description": "Write comprehensive documentation for the project",
    "status": "PENDING",
    "assignedToId": 1
  }'
```

### 3. Update Task Status
```bash
curl -X PATCH http://localhost:8080/api/tasks/1/status \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -H "X-User-Role: USER" \
  -d '{
    "status": "IN_PROGRESS"
  }'
```

### 4. Get Tasks with Filters
```bash
# Get all tasks
curl http://localhost:8080/api/tasks

# Get tasks by status
curl http://localhost:8080/api/tasks?status=PENDING

# Get tasks by user
curl http://localhost:8080/api/tasks?userId=1

# Get tasks by status and user
curl http://localhost:8080/api/tasks?status=IN_PROGRESS&userId=1
```

## 🧪 Testing

The application includes comprehensive error handling and validation. Use the Swagger UI at http://localhost:8080/api/swagger-ui.html to test all endpoints interactively.

## 🔧 Configuration Options

### Database Configuration
Switch between different databases by modifying `application.properties`:

- **MySQL** (default): Already configured
- **PostgreSQL**: Uncomment PostgreSQL section
- **H2 In-Memory**: Uncomment H2 section for quick testing

### Logging
Adjust logging levels in `application.properties`:
```properties
logging.level.com.taskmanager=DEBUG
logging.level.org.springframework.web=DEBUG
```

## 🚀 Deployment

For production deployment:

1. Update `application.properties` with production database credentials
2. Build the application: `mvn clean package`
3. Run the JAR: `java -jar target/task-management-system-0.0.1-SNAPSHOT.jar`

## 📄 License

This project is licensed under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

---

**Ready to run with `mvn spring-boot:run`!** 🎉
