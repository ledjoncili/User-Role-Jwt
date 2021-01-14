#Requirements
For building and running the application you need: 

Java 8 SDk
Maven 3.6.3

Running the application locally
mvn spring-boot:run

#Project summary
This projects consists on two users. One is a normal user and the other one is admin.
Normal user only can be logged in or create a new account.

Admin can be logged in, list all users, create account for a normal user or admin, update an user,
delete an user account.

Also when an user is authenticated a token is generated. 
Use this token as authorization header (Bearer Token) in order to perform above crud actions.

