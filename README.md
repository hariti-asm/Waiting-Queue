# Waiting Room Management System 🏥

## 📋 Overview

The Waiting Room Management System is a robust Java-based application designed to efficiently manage visitor flow and waiting lists in facilities such as hospitals, clinics, or any service-based organization requiring queue management.

## ✨ Features

- 🔄 Multiple scheduling algorithms support (FIFO, Priority, Shortest Job First)
- 👥 Comprehensive visitor management
- ⏱️ Real-time waiting list updates
- 📊 Capacity management
- 🎯 Visit tracking and monitoring
- ⚙️ Configurable scheduling parameters
- 🔒 Multiple environment profiles (dev, prod)
- 📦 MySQL database integration

## 🏗️ Architecture

The project follows a clean architecture pattern with clear separation of concerns:

```
ma.hariti.asmaa.wrm/
├── config/           # Configuration classes
├── controller/       # REST API endpoints
├── dto/             # Data Transfer Objects
├── entity/          # Domain entities
├── mapper/          # DTO-Entity mappers
├── repository/      # Data access layer
├── service/         # Business logic
│   ├── algorithm/   # Scheduling algorithms
│   ├── visit/       # Visit management
│   └── visitor/     # Visitor management
└── util/            # Utility classes
```

## 🚀 Getting Started

### Prerequisites

- Java JDK 17 or higher
- Maven 3.6+
- MySQL 8.0 or higher
- Your preferred IDE (IntelliJ IDEA recommended)

### Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE waiting_room_db;
```

2. Create a MySQL user (or use existing one):
```sql
CREATE USER 'wrm_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON waiting_room_db.* TO 'wrm_user'@'localhost';
FLUSH PRIVILEGES;
```

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/waiting-room-management.git
```

2. Navigate to the project directory:
```bash
cd waiting-room-management
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application with specific profile:
```bash
# Development profile
mvn spring-boot:run -Dspring.profiles.active=dev

# Production profile
mvn spring-boot:run -Dspring.profiles.active=prod
```

## 🔧 Configuration

### Profile-Specific Configuration Files

1. **application.yml** (Common configuration)
```yaml
spring:
  application:
    name: Waiting Room Management System
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
```

2. **application-dev.yml** (Development profile)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/waiting_room_db
    username: wrm_user
    password: your_dev_password
  jpa:
    show-sql: true

logging:
  level:
    ma.hariti.asmaa.wrm: DEBUG
```

3. **application-prod.yml** (Production profile)
```yaml
spring:
  datasource:
    url: jdbc:mysql://production-host:3306/waiting_room_db
    username: wrm_user
    password: your_prod_password
  jpa:
    show-sql: false

logging:
  level:
    ma.hariti.asmaa.wrm: INFO
```

## 💡 Available Scheduling Strategies

1. **FIFO (First In, First Out)**
    - Traditional queue-based scheduling
    - Fair and predictable waiting times

2. **Priority Scheduling**
    - Supports emergency cases
    - Configurable priority levels

3. **Shortest Job First**
    - Optimizes for quick services
    - Reduces average waiting time

## 🔗 API Endpoints

The system provides RESTful APIs for:
- Visitor management
- Visit scheduling
- Waiting list operations
- Capacity configuration
- Schedule management

## 🛠️ Technical Stack

- **Framework:** Spring Boot
- **Database:** MySQL 8.0
- **ORM:** JPA/Hibernate
- **API Documentation:** SpringDoc/OpenAPI
- **Testing:** JUnit, Mockito
- **Build Tool:** Maven

## 🗄️ Database Schema

The system uses MySQL with the following key tables:
- `visitor` - Stores visitor information
- `visit` - Records visit details
- `waiting_list` - Manages queue information
- `scheduling_config` - Stores scheduling parameters

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- Asmaa Hariti - _Initial work_

## 📮 Contact

For any inquiries or support, please contact [haritiasmae@gmail.com]

## 🔍 Troubleshooting

### Common Issues

1. **Database Connection Issues**
   ```
   Solution: Check MySQL service status and credentials
   ```

2. **Profile Loading Issues**
   ```
   Solution: Verify profile name in command line argument or IDE configuration
   ```

3. **Port Conflicts**
   ```
   Solution: Configure different port in application-{profile}.yml
   ```