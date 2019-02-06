# Overview
This project is a fairly simple web-based project management tool. 
It's a sample project to demonstrate a full-stack of technologies.

There are two folders:
* project-management-tool: contains the spring boot project
* project-management-tool-react-client: contains the React.js client source code

# Show Me The Things!
1. Build and start the Spring Boot app
2. Navigate to localhost://8080
3. Register and new user and login
4. Start creating projects and tasks

## Tech Stack
* ReactJS and Redux front-end
* Spring Boot Java back-end application
* Spring RESTful APIs on the back-end
* Secured by Spring Security and JWT
* Spring Data and JPA for persistence
* H2 database
* Unit Testing with JUnit, Mockito and MockMVC

## project-management-tool folder
This folder contains the Spring Boot project.
It's a standard Spring Boot project, built with Maven.
I'm going to assume that you are familiar enough with Spring Boot 
and Maven to know how to build/run such an application from the command line 
or from your IDE.
The project does use Lombok, so you'll require the Lombok plugins if running from an IDE.

## project-management-tool-react-client folder
This folder contains the source code for the React.js web client application.
The compiled ("npm run build") React app files have already been copied over to the Spring Boot project (src/main/resources/static), so it is not necessary to run the react project separately.
But if you wish to make changes and/or run in separately, fire it up with "npm start" and navigate to localhost:3000.

## Known Limitations/Bugs
This is a sample project, so I haven't gone-to-town on delivering production quality code. I note the limitations/bugs here so I know what to improve in future, or to acknowledge what is out of scope.
* Use of the H2 database: quick and easy for development of sample projects, but it's obviously going to wipe clean eever time the server restarts.
* React/Redux: This is my very first React project and I made it as part of a learning process. It's definitely not meant to be a showcase of best practices.
* Bugs exist related to invalid JWT tokens stored on the front-end: Need to add functionality to clear JWT token and "logout" when server responds with invalid token response.
* Bug/design error with unique project code: Project code should be unique per user, not unique across the app.
* Bugs exist realted to authorisation: Need to add functionality to better handle authorisation exceptions (distinguish between expired token, invalid tokens, no tokens, invalid login, etc)
* Test Coverage: Very limited unit test coverage. That's me just being lazy for a simple sample app that probably only I will ever use.
* API documentation: Didn't do any.
* Lack of concurrent database updates: Not handling potential concurrent updates to the database.

## Use Cases
* Register a new user
* Login with user
* Create/Read/Update/Delete Project
* Create/Read/Update/Delete Project Task
