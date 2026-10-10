# Campus Complaint Management System

A Java web application for students to submit campus complaints and track their status, and for administrators to review and update complaints.

## Features

- Student and administrator login backed by PostgreSQL.
- Students can submit complaints for campus facilities and view their complaint status.
- Administrators can view all complaints and mark them as **Pending**, **In Progress**, or **Resolved**.
- Role checks protect the student and administrator pages.

## GitHub Pages

The repository publishes a static project showcase with GitHub Pages. Pages cannot run JSP, Java servlets, or PostgreSQL, so the showcase is informational only; use Tomcat to run the interactive application. After the Pages workflow succeeds, visit:

<https://sahil07-codes.github.io/Campus_Complaint_Management/>

## Technology

- Java 21
- Jakarta Servlet 6.0 and JSP
- Apache Tomcat 10.1
- PostgreSQL and PostgreSQL JDBC driver 42.7.13
- Eclipse Dynamic Web Project

## Prerequisites

- JDK 21
- Apache Tomcat 10.1
- PostgreSQL
- Eclipse IDE for Enterprise Java and Web Developers (or another Jakarta EE-compatible IDE)

The PostgreSQL JDBC driver is included in `src/main/webapp/WEB-INF/lib`.

## Database setup

The login servlet expects a PostgreSQL database named `campus_complaints` and a `users` table with `username`, `password`, and `role` columns. For example:

```sql
CREATE DATABASE campus_complaints;
```

Connect to that database, then create the table:

```sql
CREATE TABLE users (
    username VARCHAR(100) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('student', 'admin'))
);
```

Add student and administrator accounts to the table using your own credentials. The current login code compares the submitted password directly with the stored `password` value; use password hashing before deploying this application beyond a local/demo environment.

Copy `.env.example` to `.env` in the project root and set `DB_URL`, `DB_USER`, and `DB_PASSWORD` to match your local database. The `.env` file is excluded from Git. Java environment variables with the same names take precedence over values in `.env`.

The application reads `.env` from its current working directory by default. If Tomcat runs with a different working directory, set `CAMPUS_ENV_FILE` to the absolute path of your `.env` file, or set `-Dcampus.env.file=<absolute-path>` in the Tomcat JVM arguments. Never commit `.env` or put real credentials in `.env.example`.

> **Note:** Complaint records are currently stored in the web application's memory, not in PostgreSQL. They are lost when the application restarts.

## Run in Eclipse

1. Clone or download this repository.
2. In Eclipse, import the project as an **Existing Project into Workspace**.
3. Set the project JRE to Java 21 and configure an Apache Tomcat 10.1 server.
4. Ensure the PostgreSQL JDBC driver is on the project's build path. The driver JAR is in `src/main/webapp/WEB-INF/lib`.
5. Configure PostgreSQL and the database credentials as described above.
6. Add the project to Tomcat and start the server.
7. Open the application context's `index.jsp` in a browser and sign in with an account from the `users` table.

## Project structure

```text
src/
└── main/
    ├── java/com/example/servlet/   # Servlets and database connection
    └── webapp/                     # JSP pages, web.xml, and JDBC driver
```
