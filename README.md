Mortage calculator application.

This application is an exercise in Java using Spring Boot, Thymeleaf and H2 database.
The compiled .jar file in the "src/build" directory is run with "java -jar src/build/mortageapplication-1.1.1.jar". 
Then open a browser and go to "http://localhost:8080" or "http://127.0.0.1:8080".
To compile the application yourself run "./mvnw clean install" and then you will find the compiled .jar 
file in the "target" directory.

If no csv file is specified it defaults to "src/prospects.txt".

A docker image is available and pulled with this command: 
docker pull public.ecr.aws/b8s3w5y3/mortageapplication:1.1.1

You should create a directory on the host machine and copy the "prospects.txt" to it.
The image is then run with this command: 
"docker run -it -p8080:8080 -v (full path to prospect.txt):/src  public.ecr.aws/b8s3w5y3/mortageapplication:1.1.1" 

Assume that the prospects.txt file is in the directory "Csv" in the user "john"s home directory of 
the host machine.
Then the command to run the docker image is:
Mac (Linux?): 
"docker run -it -p8080:8080 -v /Users/john/Csv:/src  public.ecr.aws/b8s3w5y3/mortageapplication:1.1.1"
Windows: 
"docker run -it -p8080:8080 -v C:\Users\john\Csv:/src  public.ecr.aws/b8s3w5y3/mortageapplication:1.1.1"
