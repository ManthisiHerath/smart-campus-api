# smart-campus-api

A Java EE 8–based RESTful API using JAX-RS (Jersey) for managing university campus rooms and sensors.

## API Overview

The Smart Campus API provides endpoints to:
- Manage campus *Rooms*
- Register and monitor *Sensors*
- Record and retrieve *Sensor Readings*
- Handle errors gracefully with custom exception mappers

Base URL: http://localhost:8080/SmartCampusAPI/api/v1

## Technology Stack

- Java EE 8
- JAX-RS (Jersey 2.41)
- Jackson (JSON)
- Apache Tomcat 9
- Maven
- NetBeans 24

## How to Build and Run

### Prerequisites
- Java JDK 17 or higher
- Apache Tomcat 9
- NetBeans 24 (or any Maven-supported IDE)

### Steps

1. Clone the repository: https://github.com/ManthisiHerath/smart-campus-api
2. Open the project in NetBeans:
   - File → Open Project
   - Navigate to the cloned folder
   - Click Open
3. Build the project:
   - Right-click project → Clean and Build
4. Run the project:
   - Right-click project → Run
   - Server: Apache Tomcat or TomEE
5. API is now running at: http://localhost:8080/SmartCampusAPI/api/v1

## Sample curl Commands

### 1. Discovery Endpoint
bash
curl -X GET http://localhost:8080/SmartCampusAPI/api/v1

### 2. Create a Room
bash
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/rooms \
-H "Content-Type: application/json" \
-d "{\"id\":\"LIB-301\",\"name\":\"Library Quiet Study\",\"capacity\":50}"

### 3. Get All Rooms
bash
curl -X GET http://localhost:8080/SmartCampusAPI/api/v1/rooms

### 4. Create a Sensor
bash
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors \
-H "Content-Type: application/json" \
-d "{\"id\":\"TEMP-001\",\"type\":\"Temperature\",\"status\":\"ACTIVE\",\"currentValue\":0.0,\"roomId\":\"LIB-301\"}"

### 5. Get Sensors filtered by type
bash
curl -X GET http://localhost:8080/SmartCampusAPI/api/v1/sensors?type=Temperature

### 6. Add a Sensor Reading
bash
curl -X POST http://localhost:8080/SmartCampusAPI/api/v1/sensors/TEMP-001/readings \
-H "Content-Type: application/json" \
-d "{\"value\":23.5}"

### 7. Delete a Room
bash
curl -X DELETE http://localhost:8080/SmartCampusAPI/api/v1/rooms/LIB-301

## Report 

### Part 1.1
Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a
new instance instantiated for every incoming request, or does the runtime treat it as a
singleton? Elaborate on how this architectural decision impacts the way you manage and
synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.

-By default, JAX-RS instantiates a new object of a resource class for each incoming HTTP request, which is known as a request-scoped lifecycle. As a result, every request operates on its own separate instance, helping to prevent thread-safety problems between concurrent requests. However, because a fresh instance is created each time, instance variables within the resource class cannot be used to store shared data. To enable data sharing across multiple requests, a static DataStore class containing static HashMap structures is used. Since these static collections are accessible by all instances, they need to be handled with care to prevent race conditions in a multi-threaded environment.

### Part 1.2
Question: Why is the provision of ”Hypermedia” (links and navigation within responses)
considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach
benefit client developers compared to static documentation?

-HATEOAS (Hypermedia as the Engine of Application State) refers to the concept where API responses provide links to related resources and possible actions. It is regarded as a key feature of advanced RESTful design because it allows the API to describe itself. Instead of memorizing or hardcoding URLs, client developers can navigate and discover available operations directly from the responses. This approach lowers the dependency between the client and server, making it easier to update or extend the API without affecting existing clients.

### Part 2.1
Question: When returning a list of rooms, what are the implications of returning only
IDs versus returning the full room objects? Consider network bandwidth and client side
processing.

-Providing only IDs in a list helps minimize response size and network usage, which is useful when the client only needs to identify available resources. However, this approach forces the client to send additional requests to retrieve detailed information for each item, increasing the number of round trips. On the other hand, returning complete objects results in larger payloads but reduces the need for multiple requests. In most situations, sending full objects is preferred because it lowers client-side complexity and improves overall response time.

### Part 2.2 
Question: Is the DELETE operation idempotent in your implementation? Provide a detailed
justification by describing what happens if a client mistakenly sends the exact same DELETE
request for a room multiple times.

-Yes, in this implementation, the DELETE operation is idempotent. The initial DELETE request successfully removes the room and returns a 200 OK response. If the same request is sent again for that room ID, the server responds with 404 Not Found since the room has already been deleted. Importantly, the state of the server does not change after the first deletion, the room remains removed no matter how many times the request is repeated. This behavior aligns with the principle of idempotency, where repeated identical requests result in the same server state.

### Part 3.1 
Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on
the POST method. Explain the technical consequences if a client attempts to send data in
a different format, such as text/plain or application/xml. How does JAX-RS handle this
mismatch?

-The @Consumes(MediaType.APPLICATION_JSON) annotation indicates that the endpoint is designed to handle only requests with the Content-Type set to application/json. If a client submits data using a different content type, such as text/plain or application/xml, JAX-RS will automatically reject the request and respond with an HTTP 415 Unsupported Media Type status. In such cases, the request does not reach the resource method at all. This mechanism ensures that the API only processes data formats it is capable of handling, providing an extra layer of protection.

### Part 3.2 
Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why
is the query parameter approach generally considered superior for filtering and searching
collections?

-Using @QueryParam for filtering (for example, /sensors?type=CO2) is more appropriate than using a path parameter like /sensors/type/CO2 because query parameters are inherently optional. When no filter is applied, the base endpoint /sensors can still return the complete list of sensors. In contrast, path parameters are typically used to represent a specific resource, making them less suitable for filtering purposes. Query parameters follow standard REST practices for handling filtering, searching, and sorting of collections.

### Part 4.1
Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How
does delegating logic to separate classes help manage complexity in large APIs compared
to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?

-The Sub-Resource Locator pattern works by directing request handling to a different class depending on the URL path. Rather than placing all nested endpoints within a single large controller, each sub-resource is managed by its own dedicated class. This approach enhances code structure, readability, and ease of maintenance. In larger APIs with multiple nested resources, it helps prevent resource classes from becoming overly complex. Additionally, it enables sub-resources to be developed and tested independently.

### Part 5.2
Question: Why is HTTP 422 often considered more semantically accurate than a standard
404 when the issue is a missing reference inside a valid JSON payload?

-HTTP 404 indicates that the requested resource could not be found at the given URL. In contrast, HTTP 422 signifies that the request was successfully understood and the endpoint is valid, but the data provided in the request body is semantically invalid. For example, when a client sends a POST request to create a sensor with a roomId that does not exist, the /sensors endpoint itself is valid and accessible. However, the issue lies within the JSON payload, where the referenced room is not present. Therefore, using 422 is more appropriate, as the error is related to the request content rather than the URL.

### Part 5.4
Question: From a cybersecurity standpoint, explain the risks associated with exposing
internal Java stack traces to external API consumers. What specific information could an
attacker gather from such a trace?

-Revealing Java stack traces to external API users poses a significant security threat. These stack traces can expose internal package structures, class names, library details and versions (which may be checked against known vulnerabilities), server file paths, and the application’s execution flow. Such information can be exploited by attackers to identify outdated dependencies, understand the system’s internal design, and develop targeted attacks. A global ExceptionMapper helps mitigate this risk by returning only a generic error response instead of detailed internal information.

### Part 5.5
Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like
logging, rather than manually inserting Logger.info() statements inside every single resource method?

-Using JAX-RS filters to handle cross-cutting concerns such as logging is more effective than manually adding Logger.info() statements inside each resource method. Filters are automatically executed for all incoming requests and outgoing responses without interfering with the core business logic. This approach follows the principle of separation of concerns. If logging behavior needs to be updated or turned off, only the filter implementation must be changed. In contrast, manually placing logging code in every method can result in duplicated code, inconsistent logging practices, and increased maintenance effort.

## Project Structure

```
SmartCampusAPI/
├── Web Pages/
│   ├── META-INF/
│   ├── WEB-INF/
│   │   ├── beans.xml
│   │   └── web.xml
│   └── index.html
│
├── RESTful Web Services/
│   ├── DiscoveryResource [/]
│   ├── JavaEE8Resource [/javaee8]
│   ├── RoomResource [/rooms]
│   │   ├── HTTP Methods
│   │   └── Subresource Locators
│   ├── SensorReadingResource
│   └── SensorResource [/sensors]
│
├── Source Packages/
│   ├── com.smartcampus/
│   │   ├── JAXRSConfiguration.java
│   │   └── SmartCampusApplication.java
│   │
│   ├── com.smartcampus.exception/
│   │   ├── GlobalExceptionMapper.java
│   │   ├── LinkedResourceNotFoundException.java
│   │   ├── LinkedResourceNotFoundExceptionMapper.java
│   │   ├── RoomNotEmptyException.java
│   │   ├── RoomNotEmptyExceptionMapper.java
│   │   ├── SensorUnavailableException.java
│   │   └── SensorUnavailableExceptionMapper.java
│   │
│   ├── com.smartcampus.filter/
│   │   └── ApiLoggingFilter.java
│   │
│   ├── com.smartcampus.model/
│   │   ├── Room.java
│   │   ├── Sensor.java
│   │   └── SensorReading.java
│   │
│   ├── com.smartcampus.resources/
│   │   ├── DiscoveryResource.java
│   │   ├── JavaEE8Resource.java
│   │   ├── RoomResource.java
│   │   ├── SensorReadingResource.java
│   │   └── SensorResource.java
│   │
│   └── com.smartcampus.store/
│       └── DataStore.java
│
├── Test Packages/
├── Other Sources/
├── Dependencies/
├── Java Dependencies/
│
└── Project Files/
    ├── pom.xml
    └── nb-configuration.xml
```
5COSC022W Client-Server Architectures Coursework 2025/26
University of Westminster
