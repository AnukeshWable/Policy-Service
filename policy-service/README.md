# PolicySphere – Policy Service

Spring Boot 3 / Java 21 / MySQL (`policy_db`). Port 8082.
Calls Customer Service (default http://localhost:8081) to validate customers.

Override via env vars: `DB_USERNAME`, `DB_PASSWORD`, `CUSTOMER_SERVICE_BASE_URL`.

## Endpoints (`/api/v1/policies`)
| Method | Path | Purpose |
|---|---|---|
| POST | / | Create |
| GET | /{id} | Get one |
| GET | / | Get all |
| GET | /customer/{customerId} | Customer's policies |
| PATCH | /{id}/cancel | Cancel |
