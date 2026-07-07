# Database Schema

## Tables
- **Application**: Stores job applications.
  - id (PK), companyId (FK), ole, status, salary, pplyDate
- **Company**: Stores company profiles.
  - id (PK), 
ame (Unique), website, 	echStack
- **ActivityLog**: Automated audit trail.
  - id (PK), entityName, entityId, ction, 	imestamp
- **Settings**: Key-Value store for preferences.
