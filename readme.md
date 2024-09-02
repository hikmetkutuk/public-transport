<p align="center">
    <img src="assets/springboot.png" alt="spring boot" width="220">  
</p>

![GitHub repo size](https://img.shields.io/github/repo-size/hikmetkutuk/public-transport?color=inactive&logo=github&style=for-the-badge)
![Java](https://img.shields.io/static/v1?&logo=openjdk&label=java&message=17&color=f29111&style=for-the-badge)
![SpringBoot](https://img.shields.io/static/v1?&logo=springboot&label=spring%20boot&message=3.2.4&color=6db33f&style=for-the-badge)
![Gradle](https://img.shields.io/static/v1?&logo=gradle&label=gradle&message=8.8&color=32abc2&style=for-the-badge)
![Postgres](https://img.shields.io/static/v1?&logo=postgresql&label=postgre%20sql&message=15.5&color=336791&style=for-the-badge)
![Docker](https://img.shields.io/static/v1?&logo=docker&label=docker&message=25.0.2&color=086dd7&style=for-the-badge)
![Swagger](https://img.shields.io/static/v1?&logo=swagger&label=swagger&message=3.0.1&color=44cc11&style=for-the-badge)
---

- [x] Flow Architecture
- [x] Multi Project Architecture
- [x] User data and test data added when project runs
- [x] Spotless format gradle task
- [x] Vehicle Service
- [x] Station and Route Operations
- [x] Route assignment for vehicles
- [x] Authentication and Authorization
- [x] Exception Handling
- [x] Docker Compose
- [x] Json Mapper
- [x] Database Version Control with Liquibase
- [ ] Log Management
- [x] Swagger Doc
- [x] Unit and Integration Tests

---

### Notlar
```
./gradlew buildCycle   // Format, build ve jar kopyalama işlemlerini yapar.
```

```
./gradlew runJar   // Kopyalanan jar dosyasını çalıştırır.
```

```
./gradlew fullCycle   // Format, build, jar kopyalama ve çalıştırma işlemlerini yapar.
```

#### swagger doc
http://localhost:8090/swagger-ui/index.html

#### kullanıcılar
email: "user@mail.com"
password: "123456"

email: "admin@mail.com"
password: "123456"

email: "superadmin@mail.com"
password: "123456"