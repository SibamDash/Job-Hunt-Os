# Job Hunting OS

A professional desktop application built with Java 21, JavaFX, and Maven to manage your job hunting process.

## Prerequisites

- Java Development Kit (JDK) 21 or later
- Maven 3.8+

## Setup & Run

### Running locally
You can run the application directly using the Maven JavaFX plugin:

```bash
mvn clean javafx:run
```

Alternatively, you can compile and run using the `Launcher` class:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.jobhuntos.Launcher"
```

## Features (Phase 1)
- Clean, IntelliJ IDEA-like UI layout
- Left Sidebar navigation
- Light and Dark modes
- Maven modularized structure ready for SQLite, Apache POI, and iText PDF integration.

## Architecture
- **MVC Pattern**: Clear separation of Views (FXML), Controllers (Java), and Models (TBD).
- **Styling**: Vanilla CSS (`light.css`, `dark.css`) managed by `ThemeManager`.
- **Navigation**: Views are cached and switched via `NavigationManager`.
