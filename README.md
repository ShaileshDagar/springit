# Springit [A Reddit Clone]

A project to learn Spring Boot by creating a simple clone of Reddit.

## Description
This is a simplified Reddit Clone where users can explore links that other users have posted, and can also post their own links.
Users can also interact with a link by commenting or up-voting/down-voting a link. 
To post a link or to interact with a link, a user must be registered on the application.
Presently, a user can up-vote or down-vote on a link as many times as they like.

## Built With
* [Spring Framework 6](https://spring.io/projects/spring-framework)
* [Spring Boot 3](https://spring.io/projects/spring-boot)

## Tools
* [Hibernate](https://hibernate.org/orm/documentation/6.1/)
* [Project Lombok](https://projectlombok.org/)
* [PrettyTime](https://www.ocpsoft.org/prettytime/)

## Requirements
For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [MySQL](https://www.mysql.com/)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. 
One way is to execute the `main` method in the `com.springboot.springit.SpringitApplication` class from your IDE.

## API Endpoints
### Home Page
```
GET /
```
### Login
```
GET /login
```
### Profile
```
GET /profile
```
### Register
```
GET /register
```
### View Link
```
GET /link/{id}
```
### Link Submission Page
```
GET /link/submit
```
### Post a comment
```
POST /link/comments
```
### Post a Vote
```
POST /vote/link/{linkID}/direction/{direction}/votecount/{voteCount}
```
## Authors

* Shailesh Dagar - [Github](https://github.com/ShaileshDagar)

## Acknowledgments

Inspiration, code snippets, etc.
* [The Real Dan Vega](http://www.therealdanvega.com)
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
