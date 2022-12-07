# ULTRAS - Ticket Management Application

ULTRAS is an application for customers to purchase tickets about football matches and more.

## Description

ULTRAS is an application, which shows matches that will be played soon and provides the possibility for football fans to buy tickets and enjoy watching the match live. Moreover, users can create account so that they can keep track of their purchased tickets and able to modify their profile information. For that the application uses JWT technology for Authentication & Authorization to provide security. The software solution has matches, football teams, stadiums and users. More information about the entities can be seen in the Design Document. Administrators can perform CRUD operation on these entities.The application has role authorization and serves 2 types of roles:
   * User
   * Administrator
   
More information can be found in the docs folder where there is information about all design decisions connected with the project.

## Getting Started

### Dependencies

Application uses:

* SpringBoot Framework version  '2.7.3'
* Java 17
* React App
* MYSLQ Database

### Executing program

* Backend (SpringBoot Application)
   * Host a MYSQL database with the specified settings from the application.properties file
   * Open the application and run FootbalTicketsAppApplication.java

* Frontend (React App)
   * Open the React application
   * For installing all node modules type in the terminal:
```
npm install
```
   * To run the app type:
   ```
npm start
```

## Authors

Contributors names and contact info

* Lachezar Mitov
    * Email: lachomitov2609@gmail.com
    * Linkedin: [Lachezar Mitov](https://www.linkedin.com/in/lachezar-mitov-922b49230/)
