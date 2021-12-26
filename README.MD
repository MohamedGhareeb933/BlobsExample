# Blob PROJECT 

Java and Spring project 

## build - install 

assuming you have java and maven installed globally in your computer and also MySQL 

open `db-scripts` file and run the following scripts 

 **create-employee-file.sql**  

## run 

`Note: you may need Lombok Extension or Plugin`


open terminal and `cd` to sensor-backend file then run -> 

```bash
`mvn spring-boot:run`
```

or from your IDE navigate to : 

`src\main\java\example\blob\blobApplication.java` and `run`

## development server 

after you build and run the project

head to http://localhost:8080/


## SQL Scripts Has : 

 - create-employee-file.sql
    - the script create a schema for the spring datasource and table for the entities 
    - the script creates two tables 
    - `employee` table has the demo information aboout employee like firstName, lastName, Email, Age
    - `employee_file` table has contain information about the file like file name , extension, data  



## Java 

`EmployeeFile.java` 

tranisent field for `url`

Many-to-One Realtion with Employee Entity 

takes MultipartFile as A constructor Argument to set the Entity properties 

`EmployeeType.java`

Enum Class that Define the Type Memeber Variable of EmployeeFile class 

## EMPLOYEES REST 

OPEN THE JSON FILE AND TEST IN YOU'R CLIENT YOU PREFERE 

## EMPLOYEE FILE REST 

NOT THAT EMPLOYEE MUST BE POSTED FIRST BEFORE THE FILE 

FILE HAS RELATION WITH EMPLOYEE AND SHOULD BE ADDED TO EMPOLOYEE 

# POST EMPLOYEE FILE 

`url` : `http://localhost:8080/files?id=1&type=RESUME&description=Mohamed Ahmed Java Developer CV `

THE URL HAS MULTIPLE PARAMATERS 
FIRST PARAMTER IS THE EMPLOYEE ENTITY ID 
SECOND IS THE TYPE OF THE FILE WHICH IS DEFINED IN THE FILETYPE.java Enum class 
THIRD IS THE DESCRIPTION OF THE FILE 