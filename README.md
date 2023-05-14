# ULTRAS - Ticket Management Application

ULTRAS is an application for customers to purchase tickets about football matches and more.

## Description

ULTRAS is an application, which shows matches that will be played soon and provides the possibility for football fans to buy tickets and enjoy watching the match live. Moreover, users can create account so that they can keep track of their purchased tickets and able to modify their profile information. For that the application uses JWT technology for Authentication & Authorization to provide security. The software solution has matches, football teams, stadiums and users. More information about the entities can be seen in the [Design Document](https://github.com/Lachezar14/ULTRAS/tree/main/docs). Administrators can perform CRUD operation on these entities.The application has role authorization and serves 2 types of roles:
   * User
   * Administrator
   
More information can be found in the [docs folder](https://github.com/Lachezar14/ULTRAS/tree/main/docs) where there is information about all design decisions connected with the project.

## Getting Started

### Dependencies

Application uses:

* SpringBoot Framework version  '2.7.3'
* Java 17
* React App
* MYSLQ Database

### Build instructions

1. Ensure you have [Docker](https://www.docker.com/) installed
2. Clone the project and navigate to its directory
```
git clone https://github.com/Lachezar14/Football-Ticket-Application.git
cd ./football-tickets-app/
```
3. Open a terminal and execute:
```
docker compose up
```
4. Visit http://localhost:3000 in your browser

### Credentials
* Admin account
    * Email: `admin@gmail.com`
    * Password: `admin`

## Authors

Contributors names and contact info

* Lachezar Mitov
    * Email: lachomitov2609@gmail.com
    * Linkedin: [Lachezar Mitov](https://www.linkedin.com/in/lachezar-mitov-922b49230/)
