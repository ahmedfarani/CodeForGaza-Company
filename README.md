<div align="center">

# 💻 Code For Gaza - Company Management System

**A JavaFX desktop application for managing tech-training admissions, built on a layered MVC architecture with a MySQL backend.**

![Java](https://img.shields.io/badge/Java-23-007396?logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-Desktop-orange)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-PreparedStatement-blue)

</div>

---

## 📖 Overview

**Code For Gaza Company** is a desktop management system for a fictional tech-training company that empowers students through specialized programs. The application handles user authentication with role-based access, student admission records, and course management — built as a final academic project with a clean **MVC architecture** (Controller / Model / DataBase layers) on top of JavaFX and MySQL.

> Developed as a final project under the supervision of **Eng. Mahmoud Ashour**.

## ✨ Features

- **Role-based authentication** | login routes to either an **Admin** dashboard or a **User** (student) dashboard based on the role stored in the database
- **Secure credential handling** | passwords are MD5-hashed via `MessageDigest` before being stored or compared; all database queries use `PreparedStatement` to prevent SQL injection
- **Account management flow** | Sign Up, Sign In, "Remember Me" (persisted via Java `Preferences`), and a Forgot/Change Password screen
- **Admin admission dashboard**
  - Full CRUD on student records (add, edit, delete) backed by a `TableView`
  - Live table search/filter and a manual refresh action
  - Row selection auto-fills the edit form
- **User admission form** | students can submit their own admission request (name, email, phone, course, etc.) without table-management access
- **Course-driven enrollment** | the course dropdown is populated dynamically from a `courses` table rather than hardcoded
- **Company "Definition" screen** | an informational view accessible from both the admin and user dashboards

## 🛠️ Tech Stack

- **Java 23** | application language
- **JavaFX** (Controls, FXML, Graphics, Media, Web) | desktop UI framework
- **MySQL** | relational database, accessed via **MySQL Connector/J 9.3.0**
- **JDBC** | `JDBC.java` provides a centralized connection/`PreparedStatement` helper used across all DAOs
- **MVC architecture**
  - `Controller/` | JavaFX `@FXML` controllers (Signin, SignUp, ChangePassword, AdmissionAdmin, AdmissionUser, Definition)
  - `Model/` | `student`, `Users`, `MD5Encryptor`, `Utils`
  - `DataBase/` | `JDBC`, `StudentDAO`, `UserDAO` (Data Access Object pattern)
  - `View/` | FXML layout files

## 🚀 Getting Started

### Prerequisites

- JDK 23+
- A running MySQL server (default connection: `127.0.0.1:4306`)
- A schema named `code_company` with `users`, `student`, and `courses` tables matching the fields used in `UserDAO` and `StudentDAO`

### Run from source (NetBeans)

This project was built with **NetBeans** (Ant-based build, see `nbproject/`). Open the project folder in NetBeans and run `CFG_Company.CFG_Company` as the main class.

### Run the pre-built JAR

```bash
git clone https://github.com/ahmedfarani/CodeForGaza-Company.git
cd CodeForGaza-Company/dist
java -jar CFG_Company.jar
```

> The `dist/lib` folder bundles the required JavaFX and MySQL Connector JARs referenced by the manifest classpath.

## 📁 Project Structure

```
CodeForGaza-Company/
├── src/
│   ├── CFG_Company/         # Application entry point
│   ├── Controller/            # FXML controllers (Signin, SignUp, Admission Admin/User, etc.)
│   ├── Model/                   # student, Users, MD5Encryptor, Utils
│   ├── DataBase/                  # JDBC connection helper + StudentDAO + UserDAO
│   ├── View/                        # FXML screen layouts
│   └── image/                         # UI assets
├── dist/
│   ├── CFG_Company.jar       # Runnable build output
│   └── lib/                       # Bundled JavaFX + MySQL Connector dependencies
└── nbproject/                  # NetBeans Ant build configuration
```

## 📜 License

This project is open source and available for personal or educational use.

---

<div align="center">

Built by **Ahmed Farani**

</div>
