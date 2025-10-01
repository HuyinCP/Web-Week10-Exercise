# 🧪 Testing Guide - Spring Security 6 Demo with JWT

Hướng dẫn chi tiết để test các tính năng của dự án Spring Security 6 Demo, bao gồm cả JWT authentication.

## 📋 Mục lục

1. [Chuẩn bị môi trường](#chuẩn-bị-môi-trường)
2. [Test Web UI (Form-based Authentication)](#test-web-ui)
3. [Test JWT API Endpoints](#test-jwt-api)
4. [Test với Postman](#test-với-postman)
5. [Test với cURL](#test-với-curl)
6. [Test với JavaScript/Fetch](#test-với-javascript)
7. [Test Cases](#test-cases)
8. [Troubleshooting](#troubleshooting)

## 🚀 Chuẩn bị môi trường

### 1. Khởi động ứng dụng
```bash
mvn spring-boot:run
```

### 2. Xác nhận ứng dụng đang chạy
- **Web UI**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
- **API Base URL**: http://localhost:8080/api

### 3. Tài khoản demo
| Username  | Password  | Role       | Mô tả                    |
|-----------|-----------|------------|---------------------------|
| admin     | password  | ADMIN      | Toàn quyền hệ thống       |
| moderator | password  | MODERATOR  | Quản lý content          |
| user      | password  | USER       | Quyền cơ bản             |

## 🌐 Test Web UI (Form-based Authentication)

### 1. Test trang chủ
```bash
# Truy cập trang chủ
curl -X GET http://localhost:8080/home
```

### 2. Test đăng nhập
1. Truy cập: http://localhost:8080/login
2. Đăng nhập với các tài khoản demo
3. Kiểm tra redirect đến dashboard

### 3. Test role-based access
- **User**: Truy cập `/user/`, `/dashboard`
- **Moderator**: Truy cập `/moderator/`, `/user/`, `/dashboard`
- **Admin**: Truy cập tất cả các trang

### 4. Test logout
1. Đăng nhập thành công
2. Click logout
3. Kiểm tra redirect về trang chủ

## 🔐 Test JWT API Endpoints

### 1. Authentication Endpoints

#### Login để lấy JWT token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password"
  }'
```

**Response mẫu:**
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

#### Validate JWT token
```bash
curl -X POST http://localhost:8080/api/auth/validate \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Get token info
```bash
curl -X GET http://localhost:8080/api/auth/info \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 2. Protected API Endpoints

#### Public endpoint (không cần authentication)
```bash
curl -X GET http://localhost:8080/api/public
```

#### User endpoint (cần USER, MODERATOR, hoặc ADMIN role)
```bash
curl -X GET http://localhost:8080/api/user \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Moderator endpoint (cần MODERATOR hoặc ADMIN role)
```bash
curl -X GET http://localhost:8080/api/moderator \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Admin endpoint (chỉ ADMIN role)
```bash
curl -X GET http://localhost:8080/api/admin \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Get user profile
```bash
curl -X GET http://localhost:8080/api/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Test endpoint
```bash
curl -X GET http://localhost:8080/api/test \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📮 Test với Postman

### 1. Tạo Collection mới
1. Mở Postman
2. Tạo Collection: "Spring Security Demo API"
3. Tạo Environment với variables:
   - `base_url`: `http://localhost:8080`
   - `jwt_token`: (sẽ được set sau khi login)

### 2. Test Authentication Flow

#### Step 1: Login
- **Method**: POST
- **URL**: `{{base_url}}/api/auth/login`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "username": "admin",
  "password": "password"
}
```

#### Step 2: Set JWT Token
- Copy token từ response
- Set vào environment variable `jwt_token`

#### Step 3: Test Protected Endpoints
- **Method**: GET
- **URL**: `{{base_url}}/api/admin`
- **Headers**: `Authorization: Bearer {{jwt_token}}`

### 3. Test với các role khác nhau
Lặp lại quy trình với:
- `moderator` / `password`
- `user` / `password`

## 💻 Test với cURL

### Script test hoàn chỉnh

```bash
#!/bin/bash

BASE_URL="http://localhost:8080"
API_BASE="$BASE_URL/api"

echo "=== Testing Spring Security Demo API ==="

# Test 1: Public endpoint
echo "1. Testing public endpoint..."
curl -s -X GET "$API_BASE/public" | jq .

# Test 2: Login as admin
echo "2. Login as admin..."
ADMIN_RESPONSE=$(curl -s -X POST "$API_BASE/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}')

echo "Admin login response:"
echo $ADMIN_RESPONSE | jq .

# Extract token
ADMIN_TOKEN=$(echo $ADMIN_RESPONSE | jq -r '.token')
echo "Admin token: $ADMIN_TOKEN"

# Test 3: Test admin endpoint
echo "3. Testing admin endpoint..."
curl -s -X GET "$API_BASE/admin" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq .

# Test 4: Login as user
echo "4. Login as user..."
USER_RESPONSE=$(curl -s -X POST "$API_BASE/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username": "user", "password": "password"}')

USER_TOKEN=$(echo $USER_RESPONSE | jq -r '.token')
echo "User token: $USER_TOKEN"

# Test 5: Test user endpoint
echo "5. Testing user endpoint..."
curl -s -X GET "$API_BASE/user" \
  -H "Authorization: Bearer $USER_TOKEN" | jq .

# Test 6: Test access denied (user trying to access admin)
echo "6. Testing access denied..."
curl -s -X GET "$API_BASE/admin" \
  -H "Authorization: Bearer $USER_TOKEN" | jq .

# Test 7: Validate token
echo "7. Validating token..."
curl -s -X POST "$API_BASE/auth/validate" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq .

echo "=== Testing completed ==="
```

## 🌐 Test với JavaScript/Fetch

### HTML Test Page

```html
<!DOCTYPE html>
<html>
<head>
    <title>JWT API Test</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .test-section { margin: 20px 0; padding: 15px; border: 1px solid #ccc; }
        button { margin: 5px; padding: 10px; }
        .result { background: #f5f5f5; padding: 10px; margin: 10px 0; }
    </style>
</head>
<body>
    <h1>Spring Security Demo - JWT API Test</h1>
    
    <div class="test-section">
        <h3>1. Login</h3>
        <input type="text" id="username" placeholder="Username" value="admin">
        <input type="password" id="password" placeholder="Password" value="password">
        <button onclick="login()">Login</button>
        <div id="loginResult" class="result"></div>
    </div>
    
    <div class="test-section">
        <h3>2. Test Endpoints</h3>
        <button onclick="testPublic()">Test Public</button>
        <button onclick="testUser()">Test User</button>
        <button onclick="testModerator()">Test Moderator</button>
        <button onclick="testAdmin()">Test Admin</button>
        <button onclick="testProfile()">Test Profile</button>
        <div id="testResult" class="result"></div>
    </div>
    
    <div class="test-section">
        <h3>3. Token Operations</h3>
        <button onclick="validateToken()">Validate Token</button>
        <button onclick="getTokenInfo()">Get Token Info</button>
        <div id="tokenResult" class="result"></div>
    </div>

    <script>
        const API_BASE = 'http://localhost:8080/api';
        let jwtToken = '';

        async function login() {
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            
            try {
                const response = await fetch(`${API_BASE}/auth/login`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ username, password })
                });
                
                const data = await response.json();
                
                if (response.ok) {
                    jwtToken = data.token;
                    document.getElementById('loginResult').innerHTML = 
                        `<strong>Login successful!</strong><br>
                         Token: ${jwtToken.substring(0, 50)}...<br>
                         User: ${data.username}<br>
                         Roles: ${data.roles.join(', ')}`;
                } else {
                    document.getElementById('loginResult').innerHTML = 
                        `<strong>Login failed:</strong> ${data.error}`;
                }
            } catch (error) {
                document.getElementById('loginResult').innerHTML = 
                    `<strong>Error:</strong> ${error.message}`;
            }
        }

        async function testPublic() {
            await testEndpoint('GET', `${API_BASE}/public`, 'testResult');
        }

        async function testUser() {
            await testEndpoint('GET', `${API_BASE}/user`, 'testResult');
        }

        async function testModerator() {
            await testEndpoint('GET', `${API_BASE}/moderator`, 'testResult');
        }

        async function testAdmin() {
            await testEndpoint('GET', `${API_BASE}/admin`, 'testResult');
        }

        async function testProfile() {
            await testEndpoint('GET', `${API_BASE}/profile`, 'testResult');
        }

        async function validateToken() {
            await testEndpoint('POST', `${API_BASE}/auth/validate`, 'tokenResult');
        }

        async function getTokenInfo() {
            await testEndpoint('GET', `${API_BASE}/auth/info`, 'tokenResult');
        }

        async function testEndpoint(method, url, resultElementId) {
            if (!jwtToken && url !== `${API_BASE}/public`) {
                document.getElementById(resultElementId).innerHTML = 
                    '<strong>Error:</strong> Please login first!';
                return;
            }

            try {
                const headers = {
                    'Content-Type': 'application/json',
                };
                
                if (jwtToken) {
                    headers['Authorization'] = `Bearer ${jwtToken}`;
                }

                const response = await fetch(url, {
                    method: method,
                    headers: headers
                });
                
                const data = await response.json();
                
                document.getElementById(resultElementId).innerHTML = 
                    `<strong>${method} ${url}</strong><br>
                     Status: ${response.status}<br>
                     Response: <pre>${JSON.stringify(data, null, 2)}</pre>`;
            } catch (error) {
                document.getElementById(resultElementId).innerHTML = 
                    `<strong>Error:</strong> ${error.message}`;
            }
        }
    </script>
</body>
</html>
```

## ✅ Test Cases

### 1. Authentication Tests
- [ ] Login với valid credentials
- [ ] Login với invalid credentials
- [ ] Login với disabled user
- [ ] Token generation và validation
- [ ] Token expiration handling

### 2. Authorization Tests
- [ ] USER role access to user endpoints
- [ ] MODERATOR role access to moderator endpoints
- [ ] ADMIN role access to admin endpoints
- [ ] Access denied for insufficient privileges
- [ ] Cross-role access attempts

### 3. API Endpoint Tests
- [ ] Public endpoints (no auth required)
- [ ] Protected endpoints (auth required)
- [ ] Role-based endpoints
- [ ] Error handling
- [ ] Response format validation

### 4. Security Tests
- [ ] JWT token tampering
- [ ] Expired token handling
- [ ] Invalid token format
- [ ] Missing Authorization header
- [ ] CSRF protection (disabled for API)

### 5. Integration Tests
- [ ] Web UI + JWT API compatibility
- [ ] Session vs JWT authentication
- [ ] Database consistency
- [ ] H2 Console access

## 🔧 Troubleshooting

### Common Issues

#### 1. "Invalid credentials" error
```bash
# Check if user exists in database
curl -X GET http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:testdb
# Username: sa
# Password: password
```

#### 2. "Access denied" error
- Kiểm tra role của user
- Kiểm tra endpoint permissions
- Kiểm tra JWT token validity

#### 3. "Token validation failed"
- Kiểm tra token format
- Kiểm tra token expiration
- Kiểm tra JWT secret configuration

#### 4. CORS issues
- API endpoints có CORS enabled
- Kiểm tra browser console
- Sử dụng Postman thay vì browser

### Debug Commands

```bash
# Check application logs
tail -f logs/spring-security-demo.log

# Test database connection
curl -X GET http://localhost:8080/h2-console

# Check JWT token structure
echo "YOUR_JWT_TOKEN" | base64 -d

# Test with verbose curl
curl -v -X GET http://localhost:8080/api/admin \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Log Configuration

Để enable debug logging, thêm vào `application.yml`:
```yaml
logging:
  level:
    com.example.springsecuritydemo: DEBUG
    org.springframework.security: DEBUG
    org.springframework.web: DEBUG
```

## 📊 Performance Testing

### Load Testing với Apache Bench

```bash
# Test public endpoint
ab -n 1000 -c 10 http://localhost:8080/api/public

# Test authenticated endpoint (cần token)
ab -n 1000 -c 10 -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  http://localhost:8080/api/user
```

### Memory Usage Monitoring

```bash
# Monitor JVM memory
jstat -gc $(pgrep java) 1s

# Monitor application metrics
curl -X GET http://localhost:8080/actuator/health
```

## 🎯 Expected Results

### Successful Test Results

1. **Login Response**:
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

2. **Protected Endpoint Response**:
```json
{
  "message": "This is an admin endpoint - requires ADMIN role",
  "user": "admin",
  "email": "admin@example.com",
  "fullName": "Admin User",
  "roles": ["ROLE_ADMIN"],
  "timestamp": 1703123456789
}
```

3. **Access Denied Response**:
```json
{
  "error": "Access Denied",
  "message": "Access is denied"
}
```

---

**Lưu ý**: Đây là dự án demo, không sử dụng trong production mà không có các biện pháp bảo mật bổ sung như rate limiting, token refresh, và monitoring.
