# Mobilisation de Créance - Desktop Application

## Overview

This desktop application was developed during a BTE internship.  
It manages the mobilisation of receivables (créances) through a Java Swing interface.

The project focuses on basic financial workflow handling and user interaction through a desktop UI.

---

## Features

- User login screen
- Main dashboard interface
- Management of “types de créance”
- Simple navigation between windows
- Image-based UI components

---

## Tech Stack

- Java (Swing)
- Maven
- File-based resources 

---

## Project Structure

mobilisation-swing-app/
│
├── src/
│   ├── main/java/view/
│   │   ├── LoginWindow.java
│   │   ├── Main.java
│   │   ├── MainWindow.java
│   │   └── TypeCreanceWindow.java
│   │
│   └── resources/
│       ├── BTE000_026d9ecc-d817-4258-b39a-2cf60408797e_b.jpg
│       └── typecreance.jpg
│
├── pom.xml
└── .gitignore

---

## How to Run

### Requirements
- Java JDK 8 or higher
- Maven installed
- IDE (IntelliJ IDEA or Eclipse)

### Run Steps

git clone https://github.com/MohamedAmineKlila/bte-mobilisation-creance-java.git
cd bte-mobilisation-creance-java

mvn clean install

Run main class:
src/main/java/view/Main.java

---

## Internship Report

The full internship report is available upon request.

---

## Future Improvements

- Add database integration (MySQL / PostgreSQL)
- Improve UI design (modern Swing or JavaFX migration)
- Implement MVC architecture
- Add export features (PDF / Excel)
- Improve separation between business logic and UI

---

## Author

Mohamed Amine Klila  
BTE Internship Project