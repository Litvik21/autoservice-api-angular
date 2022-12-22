<div id="header" align="center">
  <img src="src/main/resources/images-for-readme/autoservice-logo-png-transparent.png" width="500"/>
</div>

## 📖 Description
This app look like a simple visualisation of AutoService. 
Where you can add car to service, add some jobs and products for fixing a car.
Also, after all work master get salary and client gets total price with sale.
NOTE: if owner of a car has only one task for mechanic, and it is diagnostic - owner will pay 500 s.o.
But if the owner agrees to repair the car, diagnostic is free.
 
## 📋 Project structure
**The project has an 3-Tier Architecture**
- Controller - This level allows the user to work with this application.
- Service - This level of architecture is responsible for processing the data received from the DAO level.
- Repository - This level of architecture is responsible for communicating with the database.

## 🎯 Features
- Save to DB all of models
- Update of all models
- Update status of some models
- Get salary for master

## 🖥️ Technologies
- Java 17
- Maven
- MySQL
- Tomcat
- Swagger
- Spring Web/Boot/MVC
- DOCKER

## ⚡️Quickstart
## For lau
1. Fork this repository
2. Copy link of project
3. Create new project from Version Control
4. Edit resources/application.properties - set the necessary parameters
``` java
    spring.datasource.driver-class-name=YOUR_DRIVER
    spring.datasource.url=YOUR_URL
    spring.datasource.username=YOUR_USERNAME
    spring.datasource.password=YOUR_PASSWORD
```
5. Do not forget set this param on "create" for first project run. Like this: 
``` java
    spring.jpa.hibernate.ddl-auto=create
```
6. Create the necessary name of DB
7. Run project
8. this command "docker pull litvik/autoservice:latest" for using docker

## 👀 Example of parameters for db.properties
``` java
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/NameOfDataBase?useUnicode=true&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=123456
```