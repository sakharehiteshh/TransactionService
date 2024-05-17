## Details

The service accepts two types of transactions:

1. Loads: Add money to a user (credit)

2. Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.

You may use any technologies to support the service. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Bootstrap instructions

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

## Design considerations

- I decided to build a backend service using Java Spring Boot with Maven for its robustness and ease of development. Spring Boot provides a comprehensive framework for creating web applications, simplifying configuration and setup tasks.

- I implemented request validation to ensure data integrity and prevent invalid transactions. This validation includes checks for various constraints such as transaction amount format, currency, debit or credit type, and decimal precision. Invalid requests are rejected with error responses, enhancing the reliability and security of the service.

- Additionally, I chose to utilize H2 database for event persistence due to its lightweight nature and fast performance. H2 is an in-memory database that is well-suited for development and testing purposes. By using H2, we can efficiently keep track of event data while maintaining a simple setup, facilitating rapid iteration and debugging.

## Bonus: Deployment considerations

- If I were to deploy this application, I would consider hosting it on a cloud platform such as AWS or Heroku. These platforms offer scalable infrastructure and easy deployment options for Spring Boot applications.

- For database persistence in a production environment, I would recommend using a more robust database solution such as PostgreSQL or MySQL instead of H2. These databases provide better durability and performance for handling large volumes of data.

- To ensure high availability and fault tolerance, I would deploy the application across multiple availability zones or regions and implement auto-scaling policies to handle varying traffic loads.

- Additionally, I would containerize the application using Docker and orchestrate the containers using a tool like Kubernetes for easier management and scalability.
