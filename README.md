#  🐦Murmuration
## 🌱Collaborative Community API

A **Java + Spring Boot REST API** designed to help local communities organize the exchange of goods and services using a **virtual currency**.  

The application aims to **enhance cooperation**, **promote the circular economy**, **reduce unnecessary consumption**, promote a **healthier food**, promote **talent** and strengthen communities by facilitating the exchange of handmade products, services (e.g., yoga classes or pet care), and reusable goods, through donation or loans.

---

## 🚀 Key Features (MVP)

- **User Management**
    - Registration with an initial local currency balance.
    - Authentication with **JWT**.
    - Roles: `USER` and `ADMIN`.

- **Product and Service Offers**
    - Publish offers (type, title, description, value).
    - List and browse available offers.
    - Edit or delete own offers.
    - Admin moderation of offers.

- **Transactions**
    - Accept an offer and generate an automatic transaction.
    - Validate sufficient balance.
    - Update balances of both users.
    - Personal and global transaction history.

- **Categories**
    - Manage categories to classify products and services.

- **Security**
    - Endpoints protected with **Spring Security + JWT**.
    - Identity and role-based validations.
    - Clear error handling.

---

## 📚 Main Endpoints

### Authentication
| Method | Endpoint               | Description                  | Access  |
|--------|------------------------|------------------------------|---------|
| POST   | `/api/auth/register`   | Register a new user          | Public  |
| POST   | `/api/auth/login`      | Login and obtain JWT         | Public  |

### Users
| Method | Endpoint               | Description                  | Access  |
|--------|------------------------|------------------------------|---------|
| GET    | `/api/users/{id}`      | View user profile            | User    |
| PUT    | `/api/users/{id}`      | Edit own profile             | User    |
| GET    | `/api/users`           | List all users               | Admin   |
| DELETE | `/api/users/{id}`      | Delete a user                | Admin   |

### Offers
| Method | Endpoint                  | Description                      | Access  |
|--------|---------------------------|----------------------------------|---------|
| GET    | `/api/offers`             | List all offers                  | Public  |
| GET    | `/api/offers/{id}`        | View offer details               | Public  |
| GET    | `/api/offers/user/{id}`   | View offers by a specific user   | Public  |
| POST   | `/api/offers`             | Create a new offer               | User    |
| PUT    | `/api/offers/{id}`        | Edit own offer                   | User    |
| DELETE | `/api/offers/{id}`        | Delete own offer                 | User    |

### Transactions
| Method | Endpoint                           | Description                          | Access |
|--------|------------------------------------|--------------------------------------|--------|
| GET    | `/api/transactions`                | View complete history                | Admin  |
| GET    | `/api/transactions/user`           | View personal transaction history    | User   |
| POST    | `/api/transactions/add/{offerId}}` | Accept offer and start transaction   | User   |

### Categories
| Method | Endpoint                  | Description               | Access |
|--------|---------------------------|---------------------------|--------|
| GET    | `/api/categories`         | List categories           | Admin  |
| POST   | `/api/categories`         | Add a new category        | Admin  |
| PUT    | `/api/categories/{id}`    | Update a category         | Admin  |
| DELETE | `/api/categories/{id}`    | Delete a category         | Admin  |

---

## 🛠️ Tech Stack

**Backend**
- Java + Spring Boot
- Spring Security + JWT
- MySQL

**Development Tools**
- IntelliJ IDEA
- Git + GitHub
- Postman / Swagger
- Docker

---

## 📦 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/thaisrqueiroz/murmuration.git
   cd murmuration
   ```

2. **Configure environment variables** in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/murmuration
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   jwt.secret=yourjwtsecret
   ```

3. **Build the project**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
5. **Swagger UI will be available at:**

`http://localhost:8080/swagger-ui.html`

---

## 🐳 Run with Docker

To run Murmuration in a Dockerized environment:

Build and start containers
```bash
  docker-compose up --build
```

Once the application is running, the API will be available at:
`http://localhost:8080/`

---

## 🔄 CI/CD with GitHub Actions & Docker

This project implements a robust CI/CD pipeline using GitHub Actions to automate testing, Docker image building, and release management.

### 📋 Workflow

#### 🧪 Test Pipeline (test.yml)
Trigger: Automatically runs on every Pull Request targeting main

Purpose: Quality assurance before code merging

Activities:
- Runs comprehensive test suite in Docker containers
- Executes security scans and code analysis
- Generates test coverage reports
- Uses docker-compose-test.yml for test environment
- Automatic cleanup after execution
  
#### 🏗️ Build Pipeline (build.yml)
Trigger: Automatically executes on every push to main branch

Purpose: Build and publish development images

Activities:
- Docker image building and optimization
- Pushing images to Docker Hub registry
- Tagging images with commit SHAs
- Generating build artifacts and reports

#### 🚀 Release Pipeline (release.yml)
Trigger: Automatically activates when version tags are pushed (format: v*, e.g., v1.0.0)

Purpose: Production-ready deployments

Activities:

- Builds versioned Docker images
- Pushes to registry with semantic version tags
- Runs full test suite pre-release
- Tags images as latest for deployment
- Optional GitHub release creation

---

## ✅ Best Practices Implemented

- Layered architecture (Controller, Service, Repository).
- DTOs for data transfer.
- Centralized exception handling.
- Automated tests with 74% coverage.

---

## 🌍 Future Improvements

- Multi-community support (independent groups).
- Develop a frontend
- Edit offer value at the time of approval
- Admin-managed offer approval process
- Add status (available/unavailable)
- Add subcategories
- Email notifications for key actions.

