# Journal Application
Back-end application built with Java, Spring Boot, and MongoDB, designed to securely manage user journal entries through a set of RESTful APIs exposed via Swagger. The application supports various functionalities including user registration, login, and management, all secured via JWT and OAuth 2.0 for authentication. Users can create, update, delete, and retrieve journal entries, with data being efficiently managed in a MongoDB database. Admins have access to additional features, such as creating admin users and viewing all users in the system. The app integrates with external services like Weatherstack to greet the user with weather information of his city, caching responses in Redis to reduce latency. It also utilizes Kafka for message-driven communication, particularly for processing previous week's journal entries' sentiment and sending analysis reports to users via email. Comprehensive testing is done using JUnit and Mockito, and SonarQube is used to ensure code quality throughout the development cycle. This application is built for scalability and efficiency, providing a seamless experience for users and admins alike, with features aimed at improving user engagement and performance.

### Tech Stack

- Java 17
- Spring Boot 3.4
- MongoDB
- Hibernate
- SLF4J logging
- Maven
- Project Lombok
- Spring Security
- JWT authentication & authorization
- JUnit & Mockito testing
- SonarQube Cloud
- Weatherstack external API integration
- Java Mail Sender
- Cron Jobs
- Redis
- Kafka
- Swagger
- OAuth 2.0

### Screenshots:
#### Public APIs -
1. GET http://localhost:8081/journal-backend/public/health-check

![](src/main/resources/static/images/healthcheck.png)

2. POST http://localhost:8081/journal-backend/public/signup

![](src/main/resources/static/images/createuser.png)

3. POST http://localhost:8081/journal-backend/public/login

![](src/main/resources/static/images/login.png)

#### User APIs -
1. GET http://localhost:8081/journal-backend/user

![](src/main/resources/static/images/greetuser.png)

2. PUT http://localhost:8081/journal-backend/user

![](src/main/resources/static/images/updateuser.png)

3. DELETE http://localhost:8081/journal-backend/user

![](src/main/resources/static/images/deleteuser.png)

#### Admin APIs -
1. POST http://localhost:8081/journal-backend/admin/create-admin

![](src/main/resources/static/images/createadmin.png)

2. GET http://localhost:8081/journal-backend/admin/all-users

![](src/main/resources/static/images/allusers.png)

#### Journal APIs -
1. POST http://localhost:8081/journal-backend/journal

![](src/main/resources/static/images/createentry.png)

2. GET http://localhost:8081/journal-backend/journal

![](src/main/resources/static/images/getallentries.png)

3. GET http://localhost:8081/journal-backend/journal/id/{id}

![](src/main/resources/static/images/journalentrybyid.png)

4. PUT http://localhost:8081/journal-backend/journal/id/{id}

![](src/main/resources/static/images/updatejournal.png)

5. DELETE http://localhost:8081/journal-backend/journal/id/{id}

![](src/main/resources/static/images/deletejournal.png)

#### OAuth 2.0 APIs -
1. GET http://localhost:8081/journal-backend/auth/google/callback

![](src/main/resources/static/images/oauth2.png)
<br>

### Confluent Cloud : Messages consumed from the 'weekly-sentiments' topic -
<br>

![](src/main/resources/static/images/kafka.png)
<br>

### Sentiment analysis of last week's journal entries sent via email to opted users -
<br>

![](src/main/resources/static/images/sentimentmail.png)

### MongoDB Atlas : 'journal_db' Database and its Collections -
<br>

![](src/main/resources/static/images/mongoatlas.png)
<br>

### Redis Cloud : Reducing latency by caching city weather-response in Redis and avoiding repeated Weatherstack API calls" -

![](src/main/resources/static/images/redis.png)
