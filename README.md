# Library Management System

The Library Management System is a Java-based web application that provides a REST API for managing library books and loans. It uses the Spring framework, Hibernate for database access, PostgreSQL for the database, Maven for build management, Lombok for boilerplate code reduction and JWT token for authentication.

## Table of Contents

- [Usage](#usage)
- [Endpoints](#endpoints)

## Usage

Once the application is up and running, you can access the API endpoints using a tool such as Postman. To use the API, you will need to authenticate using a JWT token, which you can obtain by sending a POST request to the `/api/v1/auth/authenticate` endpoint with your credentials. 

Here are some example requests you can make:

- Creating a new user:

```
POST /api/v1/auth/register
Content-Type: application/json

{
"firstname": "john",
"lastname: "doe"
"password": "password",
"email": "john.doe@example.com"
}

```
- Getting a token, Authenticating:

```
POST /api/v1/auth/authenticate
Content-Type: application/json

{
"email": "john.doe@example.com",
"password": "password"
}

```

- Adding a new book:
```
POST /books
Content-Type: application/json
Authorization: Bearer <jwt-token>

{
"title": "The Great Gatsby",
"author": "F. Scott Fitzgerald",
"available": 1925
}

```

- Adding a loan:
```
Loan added to a user that sent request
User is extracted from JWT

POST /loans
Content-Type: application/json
Authorization: Bearer <jwt-token>

{
"id": BookIdToBoroow
}
```

- Getting information about user's loans:
```
GET /loans
Content-Type: application/json
Authorization: Bearer <jwt-token>

[
    {
        "id": 1,
        "dateBorrowed": "2023-03-14",
        "dateDue": "2023-03-30",
        "returned": true,
        "user": {
            "id": 1,
            "firstname": "John",
            "lastname": "Doe",
            "enabled": true
        },

]
```

- Getting information about a user:
```
GET /user
Content-Type: application/json
Authorization: Bearer <jwt-token>

{
    "firstname": "John",
    "lastname": "Doe",
    "enabled": true,
    "booksBorrowed": 3,
    "role": "ROLE_ADMIN"
}
```

## Endpoints

Here's a list of the available endpoints:

- `POST /api/v1/auth/authenticate` - Authenticates a user and returns a JWT token.
- `POST /api/v1/auth/register` - Creates a new user.
- `POST /books` - Adds a new book to the library. (Requires Admin Role)
- `POST /loans` - Adds a new loan to the user that sent request.
- `GET /loans` - Gets information about user loans.
- `GET /user` - Gets information about user that sent request.
- `GET /books` - Gets a list of books
