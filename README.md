# project-management-tool
This project is a fairly simple web-based project management tool. 
It's a sample project to demonstrate a full-stack of technologies.

There are two folders. 
One contains the Spring Boot back-end.
The other contains the React front-end client.

## Tech Stack
* ReactJS and Redux front-end
* Spring Boot Java back-end application
* Spring RESTful APIs on the back-end
* Secured by Spring Security and JWT
* Spring Data and JPA for persistence
* H2 database (might make that MySQL later, but H2 is good for demo purposes)
* Unit Testing with JUnit, Mockito and MockMVC

## Back End
The back-end is a standard Spring Boot project, built with Maven. 
I'm going to assume that you are familiar enough with Spring Boot 
and Maven to know how to build/run such an application from the command line 
or from your IDE.
The project does use Lombok, so you'll require the Lombok plugins if running from an IDE.

## Front End
The front-end is a React/Redux web application.
Make sure the back-end is running, then fire it up with "npm start". 

## Other Notes
This is a sample project, so I haven't gone-to-town on delivering production quality code.
The obvious example in the Spring Boot back-end is the use of H2 database.
Other notable missing factors are: lack of handling for concurrent updates to the database, lack of API documentation and lack of test coverage.

The React.js front-end is my first React.js project. 
I strongly suspect that the design and structure is less than best-practice.
But it's been a good learning experience and I hope to have another, better, project in the near future.

## TODO
* Back-end security
* Front-end security

## Use Cases
* Register a new user
* Login with user
* Create/Read/Update/Delete Project
* Create/Read/Update/Delete Project Task
