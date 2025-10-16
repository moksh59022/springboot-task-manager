# 🚀 Task Management System

A professional Task Management System built with **Spring Boot** and **JPA** that provides REST APIs for managing users and tasks with role-based access control.

## 🌐 Live Demo

**📚 Swagger UI - Start Here!** | **📋 API Docs**

> **Note**: After deploying to Render, update these links with your Render app URL:
> - Swagger UI: `https://your-app-name.onrender.com/api/swagger-ui.html`
> - API Docs: `https://your-app-name.onrender.com/api/api-docs`

**Quick Test Links:**
- View All Tasks: `https://your-app-name.onrender.com/api/tasks`
- View All Users: `https://your-app-name.onrender.com/api/users`

## ✨ Features

- **User Management** with role-based access control (ADMIN/USER)
- **Task CRUD Operations** with status tracking (PENDING, IN_PROGRESS, COMPLETED)
- **Task Filtering** by status and assigned user
- **Interactive API Documentation** with Swagger UI
- **Sample Data** pre-loaded for testing
- **CORS Support** for web applications

## 🛠 Tech Stack

- **Java 21** | **Spring Boot 3.2.0** | **Spring Data JPA**
- **H2 Database** (in-memory with sample data)
- **Swagger/OpenAPI 3** | **Maven** | **Railway** (deployment)

## 📊 Sample Data

**Users:**
1. **Admin User** (admin@taskmanager.com) - ADMIN role
2. **John Doe** (john.doe@example.com) - USER role
3. **Jane Smith** (jane.smith@example.com) - USER role

**Tasks:**
1. Setup Development Environment - ✅ COMPLETED
2. Design Database Schema - 🔄 IN_PROGRESS
3. Implement User Authentication - ⏳ PENDING
4. Create API Documentation - ⏳ PENDING
5. Code Review and Testing - ⏳ PENDING

## 📖 API Endpoints

### User Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |
| `POST` | `/api/users` | Create a new user |

### Task Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/tasks` | Get all tasks (with optional filtering) |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `POST` | `/api/tasks` | Create a new task |
| `PUT` | `/api/tasks/{id}` | Update task |
| `PATCH` | `/api/tasks/{id}/status` | Update task status |
| `DELETE` | `/api/tasks/{id}` | Delete task |

**Query Parameters:**
- `?status=PENDING` - Filter by status
- `?userId=1` - Filter by assigned user
- `?status=IN_PROGRESS&userId=2` - Combined filters

## 🧪 How to Test

### **Option 1: Swagger UI (Recommended)**
1. Visit [Swagger UI](https://springboot-task-manager-production.up.railway.app/api/swagger-ui.html)
2. Click any endpoint → "Try it out" → "Execute"

### **Option 2: Direct Browser**
- [All Tasks](https://springboot-task-manager-production.up.railway.app/api/tasks)
- [All Users](https://springboot-task-manager-production.up.railway.app/api/users)
- [Pending Tasks](https://springboot-task-manager-production.up.railway.app/api/tasks?status=PENDING)

### **Option 3: cURL Examples**
```bash
# Get all tasks
curl "https://springboot-task-manager-production.up.railway.app/api/tasks"

# Create new task
curl -X POST "https://springboot-task-manager-production.up.railway.app/api/tasks" \
  -H "Content-Type: application/json" \
  -d '{"title": "New Task", "description": "Test task", "priority": "HIGH", "assignedTo": 1}'

# Update task status
curl -X PATCH "https://springboot-task-manager-production.up.railway.app/api/tasks/1/status" \
  -H "Content-Type: application/json" \
  -d '{"status": "IN_PROGRESS"}'
```

## 👨‍💻 Author

**Moksh Sharma** - [@moksh59022](https://github.com/moksh59022) - [Repository](https://github.com/moksh59022/springboot-task-manager)

---

**🎯 Start Testing:** [Swagger UI](https://springboot-task-manager-production.up.railway.app/api/swagger-ui.html) 🚀
