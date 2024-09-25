
The service accepts two types of transactions:

1. Loads: Add money to a user (credit)

2. Authorizations: Conditionally remove money from a user (debit)


To run this server locally, follow these steps:

1. Ensure you have Java and Maven installed on your machine.
2. Clone this repository.
3. Navigate to the project directory.
4. Run the following Maven command to build the application:

- `mvn clean install`

or if you want to skip test and build the application:

- `mvn install -DskipTests`

5. Once the build is successful, you can start the application by running:

- `mvn spring-boot:run`

6. The application will start and be accessible at `http://localhost:8080`.
7. Additionally, you can view the H2 database console by visiting `http://localhost:8080/h2-console`.

- Set Saved Settings to `Generic H2 (Embedded)`
- Use Setting Name: `Generic H2 (Embedded)`
- Set this as your Driver Class `org.h2.Driver`
- Use the JDBC URL `jdbc:h2:file:./TxnDataBase` to connect.
- Login credentials for database are `username = root`.

8. To run tests, execute the following command in your terminal:

- `mvn test`.

Note: Before running the above test command, ensure that the Spring Boot application is not running. This ensures that the tests execute in an isolated environment without interference from the running application.

9. I have attached a postman collection file (`TxnService.postman_collection.json`) that contains example requests to interact with the application.

