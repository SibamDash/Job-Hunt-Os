<h1 align="center">Job Hunting OS</h1>

<p align="center">
  <strong>A production-ready, local-first Windows desktop application to manage your career search.</strong>
</p>

## Overview
Job Hunting OS is a comprehensive tracking application built with JavaFX and SQLite. It is designed to act as your personal CRM during the job search process, replacing messy spreadsheets with a beautiful, performant, and 100% offline desktop application.

## Features
- **Application Tracking:** Log job applications, track their statuses (Applied, Screening, Interviewing, Offer Received, Rejected), and store salaries.
- **Entity Management:** Manage Companies, Recruiters, Resumes, Documents, and Tasks.
- **Live Analytics Dashboard:** Real-time metrics and dynamic JavaFX charts visualising your job search progress (Offers vs. Rejections, Application Trends).
- **Offline & Private:** Powered by a local SQLite database (`jobhuntingos.db`). No cloud tracking, no accounts required.
- **Portability:** Export your applications to CSV or generate a complete ZIP backup of your database with a single click.
- **Dynamic Theming:** Native Light and Dark mode UI, built using customized CSS.

## Architecture
Job Hunting OS strictly adheres to a modular N-Tier architecture:
1. **Presentation Layer (JavaFX):** Clean `.fxml` Views styled entirely with external `.css` files.
2. **Controller Layer:** Mediates between UI events and Business logic.
3. **Service Layer:** Singletons handling all complex logic, input validation, and asynchronous Task generation.
4. **Data Access (JDBC):** Secure, parameterized `PreparedStatement` Repository layer.

## How to Run Locally

### Requirements
- Java Development Kit (JDK) 21+
- Maven 3.9+

### Execution
From your terminal, navigate to the project root and execute the Maven JavaFX plugin:
```bash
mvn clean compile javafx:run
```

Alternatively, open the project in your favorite IDE (IntelliJ IDEA, Eclipse, VS Code) and run the `src/main/java/com/jobhuntos/Main.java` file.

## Packaging
Job Hunting OS is configured with `maven-shade-plugin` and `jpackage`. You can generate a native Windows Installer (`.msi`) by running the provided PowerShell script:
```powershell
.\build_installer.ps1
```

## Developer Documentation
For detailed insights into the database schema and system flow, please view the [Developer Guide](docs/DEVELOPER_GUIDE.md) and [Database Schema](docs/DATABASE_SCHEMA.md) in the `/docs` directory.
