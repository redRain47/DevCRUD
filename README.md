# DevCRUD [![Build Status](https://travis-ci.com/redRain47/devcrud-epam-pet-project.svg?branch=master)](https://travis-ci.com/redRain47/DevCRUD)

This is a RESTful application which allows performing main CRUD operations via HTTP requests. 

There are such entities there:
- Developer
- Skill
- Account

Available actions with entities are:
- create (save)
- read (get by id, get all)
- update (update by id)
- delete (delete by id)

Layers of this application:
> model - POJO classes

> repository - classes which interact with the storage

> service - simple business logic

> rest - servlets which handle HTTP requests

> storage - tables in a relational database

#### Model
- Developer (Long id, String name, Set skills, Account account)
- Skill (Long id, String name)
- Account (Long id, String name, AccountStatus accountStatus)

#### Repository
- JdbcDeveloperRepositoryImpl
- JdbcSkillRepositoryImpl
- JdbcAccountRepositoryImpl

#### Service
- DeveloperService
- SkillService
- AccountService

#### Rest
- DeveloperServlet
- SkillServlet
- AccountServlet

JawsDB MySQL is used as a storage.

To initialize and fill database Liquibase is used.

To perform HTTP requests by respective servlets following endpoints are used:
> /api/v1/developers

> /api/v1/skills

> /api/v1/accounts

JUnit and Mockito are used to cover all basic functionality with unit tests. To imitate interaction with database H2 in-memory database is used.

Technology stack: Java, MySQL, JDBC, Servlets, Liquibase, JUnit, Mockito, Tomcat, Logback, H2, JSP.

Required 8 version of Java.

To run this project you should compile the code and start the app with entry point WebAppRunner. Then Tomcat server will start locally.

This application has been deployed on Heroku cloud platform. 

Link to the project: https://dev-crud.herokuapp.com/

Link to the project documentation: https://app.swaggerhub.com/apis-docs/Hobbyist/DevCRUD/1.0.0
