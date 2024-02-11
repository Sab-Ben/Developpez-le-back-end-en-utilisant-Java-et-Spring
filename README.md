# ChaTop App
ChaTop App is an application that allows users (future tenants) to contact 
landlords via a matchmaking portal, for seasonal rentals on the Basque coast 
initially and, later, throughout France.

## Technologies
- NodeJS v16+
- Angular CLI v14+
- Java 17+
- SpringBoot v3.0.4
- MySQL v8.0
- Maven (installed on your system or available with your IDE)
- Docker
- AWS S3

## Authors
Our code squad: Joffrey from OpenClassrooms about the Frontend part of the application, 
and Sabrina student from OpenClassrooms about the Backend part of the application.

## Contribute to the project
ChaTop App is available on my github via the following link "https://github.com/Sab-Ben/Developpez-le-back-end-en-utilisant-Java-et-Spring.git", 
it must be clone with command line "git clone https://github.com/Sab-Ben/Developpez-le-back-end-en-utilisant-Java-et-Spring.git".
When the project is cloned, it must be create a database with MySQL and use file script.sql available via chaTop-Backend/src/main/resources/script.sql

To install MySQL : 

If you have Docker, create an image with the following command line "docker pull mysql" to get the latest one.
The MySQL image requires that you supply a root password. You cannot set this in the Docker Desktop GUI and must do so through the command line (PowerShell or cmd):
docker run --name my-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mypassword -d mysql. A terminal window should appear. First run mysql -u root -p and enter your specified root password when prompted.
Once you reach the MySQL prompt, run the following commands 'CREATE DATABASE chatopDB;',
then retrieve the information from the script.sql file and copy and paste it into the terminal window.

Otherwise with MySQL Workbench : Import the sql script from the chaTop-Backend/src/main/resources/script.sql and execute it, this will create the tables
From MySQL terminal: Use the command SOURCE </absolute/path/to/script>

## Build
For BackEnd App :

With IntelliJ IDE, open the file pom.xml as a project, click at top right on the IDE on Maven -> Chatop Backend -> Lifecycle -> clean and after build -> install.
With Eclipse IDE, import your project with Maven and choose existing maven project to find the file pom.xml, 
then right-click on the project and choose update the project, right-click agin and choose maven clean and after build maven install.

For FrontEnd App :

Use command line inside the folder chaTop-Frontend `npm install` to install nodes_modules.

## Development server
For Backend App :
Run the project at Chatop class (where annotation @SpringBootApplication appears).

For FrontEnd App :
Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. 

## Documentation Swagger UI
Once Spring Boot is running, you can access to the documentation with Swagger UI documentation at 
the following address: http://localhost:3001/api/swagger-ui/index.html#/

## Licensing
This project was created by OpenClassrooms for the training course "Développeur Fullstack Java-Angular".
