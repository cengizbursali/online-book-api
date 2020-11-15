# online-book-api


## Tech stack & Open-source libraries

### Microservices & Database

* 	[online-book-api](https://github.com/cengizbursali/online-book-api) - to connect database for providing rest service.
* 	[postgresql-db](https://github.com/cengizbursali/online-book-api/tree/master/postgresql-db) - to store client information.

### Server - Backend

* 	[JDK-11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) - Java™ Platform, Standard Edition Development Kit.
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications.
* 	[Maven](https://maven.apache.org/) - Dependency Management.

### Data

* 	[PostgreSql](https://www.postgresql.org/) - Open-Source Relational Database Management System.

###  Libraries and Plugins

* 	[Lombok](https://projectlombok.org/) - Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.
* 	[Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.

### Others 

* 	[Git](https://git-scm.com/) - Free and Open-Source distributed version control system.
*   [Docker](https://www.docker.com/) - A set of platform as a service products that use OS-level virtualization to deliver software in packages called containers.


## Running the application locally

*	use "**docker-compose up -d**" to run the dockerized spring boot projects.

## Documentation

* 	[Swagger](http://localhost:8888/swagger-ui.html) - `http://localhost:8888/swagger-ui.html`- Documentation & Testing

### URLs

|                   URL                           | Method |              Remarks                 |
|-------------------------------------------------|--------|--------------------------------------|
|`https://localhost:8088/auth`                    | POST   | Authentication user                  |
|`https://localhost:8088/books`                   | POST   | Create a book with stock option      |
|`https://localhost:8088/book-stocks`             | GET    | Get all books with stock option      |
|`https://localhost:8088/customers`               | POST   | Create a customer                    |
|`https://localhost:8088/orders?customerId=1`     | GET    | Get orders of the customer           |
|`https://localhost:8088/orders`                  | POST   | Create a order                       |
|`https://localhost:8088/order-details?orderId=1` | GET    | Get order details                    |


## EER Diagram

<img src="images\book.png"/>
