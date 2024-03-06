Mortage calculator application.

This application is an exercise in Java using Spring Boot, Thymeleaf and H2 database.
The compiled .jar file in the "build" directory is run with "java -jar src/build/mortageapplication-1.1.jar" 
then open a webbrowser and go to "http://localhost:8080" or "http://127.0.0.1:8080".
To compile the application yourself run "./mvnw clean install" and then you will find the compiled .jar file in the "target" directory.

If no csv file is specified it defaults to "src/prospects.txt".
The docker image is here: 851725570547.dkr.ecr.eu-north-1.amazonaws.com/mortageapplication:1.1.1 
And is run with this command "docker run -it -p8080:8080 -v (path to prospect.txt):/src  mortageapplication:1.1.1 
