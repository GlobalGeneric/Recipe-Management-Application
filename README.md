       # Recipe-Management-Application
       
1) **For database config:** run psql in terminal
   
      for dev database: 
    1. _CREATE DATABASE devdb;_
    2. _CREATE USER devuser WITH ENCRYPTED PASSWORD 'devpass';_
    3. _GRANT ALL PRIVILEGES ON DATABASE devdb TO devuser;_
       
     for prod database:
   
    1. _CREATE DATABASE proddb;_
    2. _CREATE USER produser WITH ENCRYPTED PASSWORD 'prodpass';_
    3. _GRANT ALL PRIVILEGES ON DATABASE proddb TO produser;_

3)  **running app in dev profile:** mvn spring-boot:run -Dspring-boot.run.profiles=dev
4)  **running app in prod profile:** mvn spring-boot:run -Dspring-boot.run.profiles=prod 

5)  **Running tests:** mvn test

       
