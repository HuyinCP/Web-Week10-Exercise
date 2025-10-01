# Spring Security 6 Demo

Một dự án demo toàn diện về Spring Security 6 với Spring Boot, Spring Data JPA, H2 Database và giao diện Thymeleaf + Bootstrap.

## 🚀 Tính năng chính

### Spring Security 6
- **Authentication**: Form-based authentication với custom UserDetailsService
- **JWT Authentication**: JSON Web Token support cho REST API
- **Authorization**: Role-based access control (RBAC) với các role USER, MODERATOR, ADMIN
- **Password Encoding**: Sử dụng BCrypt để mã hóa mật khẩu
- **Session Management**: Quản lý session và logout
- **Access Control**: Bảo vệ các endpoint dựa trên role
- **Dual Authentication**: Hỗ trợ cả form-based và JWT authentication

### Spring Data JPA
- **Entity Management**: User và Role entities với quan hệ Many-to-Many
- **Repository Pattern**: UserRepository và RoleRepository
- **Database Operations**: CRUD operations với JPA

### H2 Database
- **In-Memory Database**: H2 database chạy trong memory
- **Console Access**: Truy cập H2 Console tại `/h2-console`
- **Sample Data**: Dữ liệu mẫu được tự động tạo khi khởi động

### Giao diện Thymeleaf + Bootstrap
- **Responsive Design**: Giao diện responsive với Bootstrap 5
- **Security Integration**: Tích hợp với Spring Security cho hiển thị thông tin user
- **Role-based UI**: Giao diện khác nhau dựa trên role của user
- **Modern UI**: Giao diện hiện đại với Bootstrap Icons

## 🛠️ Công nghệ sử dụng

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security 6**
- **Spring Data JPA**
- **H2 Database**
- **Thymeleaf**
- **Bootstrap 5**
- **JWT (JSON Web Token)**
- **Maven**

## 📋 Yêu cầu hệ thống

- Java 17 hoặc cao hơn
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Cách chạy dự án

### 1. Clone repository
```bash
git clone <repository-url>
cd spring-security-demo
```

### 2. Chạy ứng dụng
```bash
mvn spring-boot:run
```

Hoặc sử dụng IDE để chạy class `SpringSecurityDemoApplication`

### 3. Truy cập ứng dụng
- **Trang chủ**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`
- **API Documentation**: Xem file [TESTING.md](TESTING.md) để biết cách test JWT API

## 👥 Tài khoản demo

Dự án đã được cấu hình sẵn 3 tài khoản demo:

| Username  | Password  | Role       | Quyền truy cập                    |
|-----------|-----------|------------|-----------------------------------|
| admin     | password  | ADMIN      | Toàn quyền, quản lý users, settings |
| moderator | password  | MODERATOR  | Quản lý content, moderate users   |
| user      | password  | USER       | Truy cập cơ bản, xem profile      |

## 🏗️ Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/example/springsecuritydemo/
│   │   ├── config/
│   │   │   └── SecurityConfig.java          # Cấu hình Spring Security
│   │   ├── controller/
│   │   │   ├── AdminController.java         # Controller cho admin
│   │   │   ├── AuthController.java          # Controller cho authentication
│   │   │   ├── DashboardController.java     # Controller cho dashboard
│   │   │   ├── HomeController.java          # Controller cho trang chủ
│   │   │   ├── ModeratorController.java     # Controller cho moderator
│   │   │   └── UserController.java          # Controller cho user
│   │   ├── entity/
│   │   │   ├── Role.java                    # Entity Role
│   │   │   └── User.java                    # Entity User
│   │   ├── repository/
│   │   │   ├── RoleRepository.java          # Repository cho Role
│   │   │   └── UserRepository.java          # Repository cho User
│   │   ├── security/
│   │   │   ├── CustomUserDetails.java       # Custom UserDetails implementation
│   │   │   └── CustomUserDetailsService.java # Custom UserDetailsService
│   │   └── SpringSecurityDemoApplication.java # Main application class
│   └── resources/
│       ├── data.sql                         # Dữ liệu mẫu
│       ├── application.yml                  # Cấu hình ứng dụng
│       └── templates/                       # Thymeleaf templates
│           ├── admin/                       # Templates cho admin
│           ├── moderator/                   # Templates cho moderator
│           ├── user/                        # Templates cho user
│           ├── access-denied.html           # Trang access denied
│           ├── dashboard.html               # Trang dashboard
│           ├── home.html                    # Trang chủ
│           └── login.html                   # Trang đăng nhập
└── pom.xml                                  # Maven configuration
```

## 🔐 Các tính năng bảo mật

### 1. Authentication
- Form-based login với custom login page
- JWT authentication cho REST API
- Custom UserDetailsService để load user từ database
- Password encoding với BCrypt
- Session management
- Stateless authentication với JWT

### 2. Authorization
- Role-based access control
- Method-level security với `@PreAuthorize`
- URL-based security configuration
- Access denied handling

### 3. Security Configuration
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    // Cấu hình security filter chain
    // Cấu hình authentication provider
    // Cấu hình password encoder
}
```

## 🎨 Giao diện

### Trang chủ (`/home`)
- Giới thiệu về dự án
- Thông tin tài khoản demo
- Navigation bar với role-based menu

### Trang đăng nhập (`/login`)
- Form đăng nhập đẹp mắt
- Hiển thị thông tin tài khoản demo
- Error handling

### Dashboard (`/dashboard`)
- Thông tin user đã đăng nhập
- Role-based quick actions
- User information display

### Admin Panel (`/admin/`)
- Quản lý users (`/admin/users`)
- System settings (`/admin/settings`)
- Admin home (`/admin/`)

### Moderator Panel (`/moderator/`)
- Content moderation (`/moderator/content`)
- Moderator home (`/moderator/`)

### User Area (`/user/`)
- User profile (`/user/profile`)
- User home (`/user/`)

## 🔌 JWT API Endpoints

### Authentication Endpoints
- `POST /api/auth/login` - Đăng nhập để lấy JWT token
- `POST /api/auth/validate` - Validate JWT token
- `GET /api/auth/info` - Lấy thông tin từ JWT token

### Protected API Endpoints
- `GET /api/public` - Public endpoint (không cần authentication)
- `GET /api/user` - User endpoint (cần USER, MODERATOR, hoặc ADMIN role)
- `GET /api/moderator` - Moderator endpoint (cần MODERATOR hoặc ADMIN role)
- `GET /api/admin` - Admin endpoint (chỉ ADMIN role)
- `GET /api/profile` - Lấy thông tin profile của user hiện tại
- `GET /api/test` - Test endpoint để kiểm tra authentication

### Example JWT Login Request
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password"
  }'
```

### Example JWT Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "admin",
  "email": "admin@example.com",
  "fullName": "Admin User",
  "roles": ["ROLE_ADMIN"],
  "expirationTime": 86400000
}
```

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE
);
```

### Roles Table
```sql
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);
```

### User Roles Table
```sql
CREATE TABLE user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
```

## 🔧 Cấu hình

### Application Properties
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: password
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

### Security Configuration
- CSRF protection enabled
- Frame options for H2 console
- Custom login/logout URLs
- Role-based URL access control
- JWT authentication support
- Stateless session management for API

### JWT Configuration
```yaml
jwt:
  secret: mySecretKey123456789012345678901234567890
  expiration: 86400000 # 24 hours in milliseconds
```

## 🧪 Testing

### Web UI Testing
1. Truy cập http://localhost:8080
2. Đăng nhập với các tài khoản demo
3. Kiểm tra quyền truy cập các trang khác nhau
4. Test logout functionality

### JWT API Testing
Xem file [TESTING.md](TESTING.md) để có hướng dẫn chi tiết về:
- JWT authentication flow
- REST API endpoints testing
- Postman collection
- cURL commands
- JavaScript/Fetch examples

### Test Cases
- ✅ Authentication với valid credentials
- ✅ Authentication với invalid credentials
- ✅ Authorization cho các role khác nhau
- ✅ Access denied cho unauthorized users
- ✅ Session management
- ✅ Logout functionality
- ✅ JWT token generation và validation
- ✅ JWT API endpoints
- ✅ Role-based API access control

## 📚 Tài liệu tham khảo

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Bootstrap Documentation](https://getbootstrap.com/docs/5.3/)

## 🤝 Đóng góp

1. Fork dự án
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Mở Pull Request

## 📄 License

Dự án này được phân phối dưới MIT License. Xem file `LICENSE` để biết thêm thông tin.

## 👨‍💻 Tác giả

**Spring Security Demo Team**
- Name: Nghiêm Quang Huy
- ID: 23110222
- Email: huy211020055@gmail.com
- GitHub: [@spring-security-demo](https://github.com/spring-security-demo)

---

**Lưu ý**: Đây là dự án demo để học tập và nghiên cứu Spring Security 6. Không sử dụng trong môi trường production mà không có các biện pháp bảo mật bổ sung.
