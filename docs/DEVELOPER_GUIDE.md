# Developer Guide

Job Hunting OS is built on a clean n-tier architecture.

## Architecture

`mermaid
graph TD
    UI[JavaFX UI Layer] --> Services[Service Layer]
    UI -.-> EventBus[Event Bus Observer]
    Services -.-> EventBus
    Services --> Repos[JDBC Repository Layer]
    Services --> Validators[Validation Module]
    Repos --> DB[(SQLite Database)]
`

## Running Locally
Requirements: JDK 21+ and Maven.
mvn clean compile javafx:run

## Packaging
Run .\build_installer.ps1 to invoke jpackage and generate native Windows binaries.
