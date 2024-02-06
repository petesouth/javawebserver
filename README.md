
# JavaWebServer
### Example of a simple multithreaded router server in Java.  

Home Grown Router type server.  Lets you map routes into HttpHandlers.  
Configuration of thread pool.
Leverages Mongo running in a docker as the storage.
All server params are configurable via javawebserver.properties described bellow.

## Setup

This project requires Maven, Java 17, and Docker for MongoDB. Follow the steps below to set up your environment and get the project running.

### Prerequisites

- Docker and Docker Compose
- Java 17
- Maven

### Installation and Running

1. **Maven Setup**:
    - `getsetupmaven.sh` grabs the latest version of Maven and puts it into `~/maven`. It also sets up the environment.
    
2. **Java Setup**:
    - `getjava.sh` grabs version 17 of Java and installs it to `~/java`.
    - `setupjava.sh` sets up the environment to run Java from the above directory.
    
3. **Run Maven**:
    - `setupmaven.sh` sets up the environment to run Maven from the above directories.

4. **MongoDB Setup**:
    - Ensure Docker and Docker Compose are set up and working on your system ([Get Started with Docker](https://docs.docker.com/get-started/)).
    - Configure `docker-compose.yaml` correctly for your system.
    - Run `./runmongodocker.sh` to start MongoDB using Docker Compose.
    - On the first run of `docker-compose up`, the `mongodb/mongo-init/init-mongo.js` script will prepopulate your database. Note: You must delete the database to run this script again.

5. **Build and Run the Server**:
    - First, start MongoDB with `docker-compose up`.
    - Then, you are free to run Maven commands:
        - `mvn clean package`
        - `mvn clean compile jar:jar`
    - To run the server:
        - `./runserver.sh`
        - or `mvn -pl javawebserver exec:java -Dexec.mainClass=com.shyhumangames.app.App`

### Server URLs

The server supports the following endpoints:

- `/players/${id}` Example: `http://0.0.0.0:8080/players/2`
- `/phones/${id}` Example: `http://0.0.0.0:8080/phones/4`
- `/tickets/${id}` Example: `http://0.0.0.0:8080/tickets/4`

### Configuration

- The main server class is `com.shyhumangames.app.App`.
- Server settings are controlled by `./javawebserver/resources/javawebserver.properties`.

## Contribution

Guidelines for how to contribute to the project.

#### TODO:  Add middleware - things like jwt token support for APIS, method logging, etc.
