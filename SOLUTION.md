# MY SOLUTION

## Prerequisites

Ensure you have JDK 17 installed to build and run this project.

## Start locally

```shell
./gradlew clean build && docker compose up --build
```
Swagger is on <http://localhost:8080/swagger-ui.html>.

## Swagger Usage

Swagger provides a UI to interact with all available API endpoints. Accessing Swagger, you'll see a list of endpoints with descriptions and parameter details. Use Swagger to:

Find and interact with each endpoint, such as /rover/initialize, /rover/move, and /rover/status.
Test each endpoint by filling in request bodies or parameters directly in the interface and clicking "Execute" to view responses.

## Initialize the Rover
Before executing any move commands, you must initialize the rover. 
Use the '/rover/initialize' endpoint with a JSON body containing the starting position and direction.
Here’s an example of how to provide the initialization command:

```shell
{
  "x": 0,
  "y": 0,
  "direction": "N"
}

```
##  Features

1. **Command Processing:**
- The rover processes commands to move forward, move backward, rotate left, and rotate right. Each command is validated to ensure it belongs to the defined set (f, b, l, r).
- The service logs successful and unsuccessful command executions, leveraging metrics to track command outcomes.
2. **Movement and Wrapping Logic:**
- Movement is calculated in the move function. The rover uses wrapCoordinate to handle a spherical surface, wrapping around boundaries seamlessly with modulo 360.
- Both positive and negative overflow in coordinates are handled gracefully, simulating infinite surface behavior.
3. **Obstacle Detection:**
- Before each move, the new position is checked against predefined obstacles. If an obstacle is detected, the rover reverts to its previous position instead of halting or throwing an exception. This behavior prevents errors while preserving the rover's last valid state.
- Obstacles are managed via ObstacleConfig for modular configuration, allowing easier testing and expandability.
4. **Direction Control:**
- The Direction enum includes dx and dy values that denote directional increments
- If facing North (y increases), moving forward would increase the y-coordinate.
- If facing South (y decreases), moving forward would decrease the y-coordinate.
- If facing East (x increases), moving forward would increase the x-coordinate.
- If facing West (x decreases), moving forward would decrease the x-coordinate.


##  Implementation Choices

1. **Service Layer:**
- Service orchestrates command processing, interacts with Helper for movement calculations, and tracks metrics. This separation allows for more modular and testable code.
- Initialization and state retrieval are encapsulated in the service to ensure a clear interface for controller interactions.
2. **Helper Class:**
- The Helper class encapsulates movement, rotation, and wrapping logic, streamlining calculations and reducing duplication.
- Obstacle handling is centralized within Helper, maintaining single responsibility for movement operations.
3. **Testing Strategy:**
- Unit tests validate command processing, direction rotation, obstacle handling, and coordinate wrapping.
- Edge cases (like boundary wrapping) are explicitly tested to ensure robustness across typical rover scenarios.
4. **Error Handling:**
- Commands outside the valid set result in an InvalidCommandException. By logging these instances, the application maintains reliability and observability for invalid command scenarios.
- If an obstacle is encountered, the rover maintains its last valid position instead of throwing errors, allowing uninterrupted operation.

##  Choice Rationale for Persistence Layer
In this phase, a persistence layer was omitted to focus on meeting core functional requirements and delivering a clean, well-tested solution. 
Adding a database would introduce additional setup, management, and potential overhead for error handling and transaction consistency, which are not strictly necessary for demonstrating core functionality. 
For a production-level application, persistence would be essential, allowing for state maintenance across restarts and supporting data persistence for historical commands.

**Scalability and Future Extensions**
1. Multithreading for Concurrent Rovers: Introducing multiple rovers could benefit from Kotlin coroutines or asynchronous processing to manage concurrent movements and interactions in a scalable way.
2. Database Integration: Adding a database like PostgreSQL would enhance scalability, as rover states and obstacle data could be stored, allowing future retrieval and support for multi-rover scenarios.
3. Scalability for Obstacle Handling: Leveraging a concurrent hash map could provide thread-safe, efficient access to obstacle data, enabling multiple rovers to process their commands concurrently without blocking.

## Tooling and CI/CD Considerations
Adding tools like JIRA for issue tracking and a CI/CD pipeline would streamline project management and deployment, ensuring quality through continuous testing.
Such a setup would demonstrate best practices in collaboration, progress tracking, and testing for reliability. 
For this phase, priority was given to feature functionality, but these tools could be introduced in future iterations.

**Future Improvements**
1. GetOrSet Method: Implementing a GetOrSet approach could optimize rover state retrieval, reducing repetitive checks and providing a more robust method for state management.
3. Interface Abstraction: Defining interfaces for the rover’s client and data layer would improve flexibility, allowing for changes in data storage or client interaction without affecting the overall architecture.
4. ACID Compliance: As part of adding persistence, future improvements could also address transaction management and ACID compliance to ensure data consistency, especially in multi-rover setups.
