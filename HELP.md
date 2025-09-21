# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.6/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.6/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.6/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.6/reference/data/sql.html#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Testing API (Use Postman)

Upload an Image (POST)
Method: POST
URL: http://localhost:8080/api/images
Body → select form-data
Add a key: file
Change type (dropdown on left) from Text → File
Select an image & Send request.


Download/View Image (GET)
Method: GET
URL: http://localhost:8080/api/images/1
Send request.

Error Testing
Try a wrong ID:
GET http://localhost:8080/api/images/999
Response: 404 Not Found.

Try uploading with wrong key (not file):
Response: 415 Unsupported Media Type or 400 Bad Request or 405 Method Not Allowed

### Postgre Changes
CREATE DATABASE image_db;

CREATE TABLE images (
id BIGSERIAL PRIMARY KEY,
filename TEXT NOT NULL,
content_type TEXT,
data BYTEA NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

Update datatype for data as bigint instead of BYTEA or delete and create manually 
### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

