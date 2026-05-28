# Mobilisation de Créance — BTE Desktop Application

> A Java Swing desktop application developed during a summer internship at **Banque de Tunisie et des Emirats (BTE)** to manage and process debt claim mobilization operations.

---

## Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Internship Context](#internship-context)

---

## About the Project

This application was built to digitize and streamline the **mobilisation de créance** process at BTE. It allows authorized bank staff to log in securely, manage credit type references, process claim records, and export data to Excel for reporting purposes.

---

## Features

-  **Secure Login System** — username/password authentication with session logging
-  **Main Dashboard** — branded interface with BTE visual identity
-  **Référentiel Module** — manage and browse credit/claim types (Type de Créance)
-  **Data Table View** — display and filter mobilization records
-  **Excel Export** — export processed data to `.xlsx` using Apache POI
-  **Session Logging** — tracks login activity with timestamps
-  **Branded UI** — BTE logo and visual assets integrated into the interface

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core application language |
| Java Swing | Desktop GUI framework |
| Maven | Dependency and build management |
| Apache POI 5.4.0 | Excel file reading and writing |
| IntelliJ IDEA | Development environment |

---

## Project Structure

```
mobilisation-swing-app/
├── src/
│   └── main/
│       └── java/
│           └── view/
│               ├── Main.java               # Application entry point
│               ├── LoginWindow.java        # Login screen with auth logic
│               ├── MainWindow.java         # Main dashboard with menu
│               └── TypeCreanceWindow.java  # Credit type management module
├── src/
│   └── resources/
│       └── *.jpg                           # BTE branding assets
├── database/ (Original bank data has been removed for confidentiality reasons)
│   ├── users.txt                           # User credentials (sample only)
│   ├── TYPES_CREDITS_MCR.xlsx              # Credit type reference data
│   ├── invoices.xlsx                       # Invoice records
│   └── login_log.xlsx                      # Session audit log
├── pom.xml                                 # Maven configuration
└── README.md
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- IntelliJ IDEA (recommended) or any Java IDE

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/MohamedAmineKlila/bte-mobilisation-creance-java.git
   cd bte-mobilisation-creance-java
   ```

2. Install dependencies:
   ```bash
   mvn install
   ```

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="view.Main"
   ```

   Or simply open the project in IntelliJ IDEA and run `Main.java`.

---

## Usage

1. Launch the app — the **BTE login screen** appears
2. Enter credentials to access the main dashboard
3. Navigate via the **Référentiel** menu to manage credit types
4. Use the data table to view and filter mobilization records
5. Export results to Excel using the export function

---

## Internship Context

This project was developed during a **summer internship at BTE (Banque de Tunisie et des Emirats)** as part of a Business Information Systems curriculum. The goal was to build a functional internal tool that replaces manual spreadsheet-based processes with a structured desktop application.

**Role:** Intern Developer
**Duration:** Summer 2025
**Organization:** Banque de Tunisie et des Emirats (BTE)

---

## License

This project is licensed under the Apache License 2.0 — see the [LICENSE](LICENSE) file for details.

> **Note:** This repository contains only sample/dummy data. All real bank data used during development has been removed to comply with data confidentiality requirements.
