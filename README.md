# 🚗 Electric Car Rental

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.5-green) ![Java](https://img.shields.io/badge/Java-21-blue) ![Microservices](https://img.shields.io/badge/Architecture-Microservices-orange)

Electric Car Rental – это REST API backend-приложение для аренды электромобилей, построенное на микросервисной
архитектуре с использованием Spring Boot.

## 🏗 Архитектура
[Графическая схема проекта](https://excalidraw.com/#json=QGoNZYwAvznGbzwGVHCMm,ZRk220TFOQrcR5D_XUW9dQ)

Проект построен на микросервисной архитектуре и включает следующие сервисы:

1. **User Management** – управление пользователями (регистрация, удаление и т. д.).
2. **Car Booking** – бронирование автомобилей (проверка доступности, расчёт стоимости).
3. **Car Management** – управление автопарком (добавление, редактирование, удаление автомобилей).
4. **Car Charger Management** – управление зарядными станциями.
5. **Notifications** – отправка уведомлений пользователям.

Все микросервисы регистрируются в **Eureka Discovery Service** и взаимодействуют через **API Gateway**.

## 📌 Стек технологий

- **Java 21**
- **Spring Boot** (Web, Cloud, Security, Data JPA)
- **Spring Cloud Eureka** (Service Discovery)
- **Spring Cloud Gateway** (API Gateway)
- **PostgreSQL** (БД для каждого микросервиса)
- **Docker, Docker Compose** (контейнеризация)
- **Swagger/OpenAPI** (документация API)

## 🚀 Запуск проекта

### 0. Перед запуском

Необходимо внести ключ и почту для отправки уведомлений, для этого:

- перейти в сервисе notifications/src/mian/resources
- https://myaccount.google.com/apppasswords - получить пароль.
- отредактировать файл api-keys.yaml.sample, указав свой gmail и пароль для приложений
- переименовать файл в api-keys.yaml

### 1. Клонирование репозитория

```bash
git clone https://github.com/aston-group/electric-car-rental.git
cd electric-car-rental
```

п

### 2. Запуск через Docker Compose

```bash
mvn clean install
docker-compose up -d
```

### 3. Запуск вручную (локально)

#### 🔹 Запуск сервисов

Запускаем **Eureka Server**:

```bash
cd discovery-server
mvn spring-boot:run
```

Запускаем **API Gateway**:

```bash
cd api-gateway
mvn spring-boot:run
```

Запускаем остальные микросервисы (аналогично):

```bash
cd user-management
mvn spring-boot:run
```

### 4. Доступные эндпоинты

#### 📌 API Gateway (localhost:8080)

| URL                                                      | Описание                       |
|----------------------------------------------------------|--------------------------------|
| [Описание user-management](user-management/README.md)    | Управление пользователями      |
| [Описание car-management](car-management/README.md)      | Управление электромобилями     |
| [Описание car-booking](car-booking/README.md)            | Создание бронирования          |
| [Описание car-charger](car-charger-management/README.md) | Управление зарядными станциями |
| [Описание notifications](notifications/README.md)        | Получение уведомлений          |

## 📜 Структура проекта

```
📦 electric-car-rental
 ┣ 📂 api-gateway
 ┣ 📂 discovery-server
 ┣ 📂 user-management
 ┣ 📂 car-booking
 ┣ 📂 car-management
 ┣ 📂 car-charger-management
 ┣ 📂 notifications
 ┣ 📜 docker-compose.yml
 ┣ 📜 README.md
```

## 🛠 Разработка и улучшение

1. Добавление сервиса для оплаты.
2. Расширение сервиса уведомлений на другие каналы.
3. Фронтэнд.

