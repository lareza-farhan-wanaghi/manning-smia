# Manning - Spring Microservices in Action

## A. How to Run

1. **Build Docker Images:** Execute the `setup.sh` script to build Docker images for all Spring Boot projects.
    ```sh
    bash setup.sh
    ```

2. **Launch Containers:** Start all containers using the `docker-compose.yml` file.
    ```sh
    docker-compose up
    ```

3. **Regenerate Client Secret in Keycloak:**
   - Access the Keycloak web UI.
   - Navigate to the `spmia-realm` and regenerate the `ostock` client credentials secret.

4. **Create a User in Keycloak:**
   - In the Keycloak web UI, create a user in the `spmia-realm`.
   - Ensure the user has a password-type credential and is assigned either the `ostock-admin` or `ostock-user` realm role.

## B. Endpoints

### Gateway (`http://localhost:8072`)

1. **GET /license-service/v1/organization/{organizationId}/license/{licenseId}**
   - **Description:** Retrieve license data from the license database.
   - **Path Parameters:**
     - `organizationId`: The ID of the organization.
     - `licenseId`: The ID of the license.

2. **POST /license-service/v1/organization/{organizationId}/license**
   - **Description:** Create new license data in the license database.
   - **Path Parameters:**
     - `organizationId`: The ID of the organization.
   - **Request Body:**
     ```json
     {
         "description" : "sample desc",
         "organizationId" : "e6a625cc-718b-48c2-ac76-1dfdff9a531e",
         "productName" : "licensi",
         "licenseType" : "permanen",
         "comment" : "permanen"
     }
     ```

3. **PUT /license-service/v1/organization/{organizationId}/license**
   - **Description:** Update existing license data in the license database.
   - **Path Parameters:**
     - `organizationId`: The ID of the organization.
   - **Request Body:**
     ```json
     {
         "description" : "sample desc",
         "organizationId" : "e6a625cc-718b-48c2-ac76-1dfdff9a531e",
         "productName" : "licensi",
         "licenseType" : "permanen",
         "comment" : "permanen"
     }
     ```

4. **DELETE /license-service/v1/organization/{organizationId}/license/{licenseId}**
   - **Description:** Delete license data from the license database.
   - **Path Parameters:**
     - `organizationId`: The ID of the organization.
     - `licenseId`: The ID of the license.

5. **GET /license-service/v1/organization/{organizationId}/license**
   - **Description:** Retrieve (dummy) license data from the organization service via the license service.
   - **Path Parameters:**
     - `organizationId`: The ID of the organization.
   - **Authorization:** Bearer token required.
     - `Token`: User's authenticated JWT token.

6. **GET /organization-service/v1/test**
   - **Description:** Return dummy data while sending a message to Kafka.
   - **Authorization:** Bearer token required.
     - `Token`: User's authenticated JWT token.

### Keycloak (`http://localhost:8100`)

1. **POST /realms/spmia-realm/protocol/openid-connect/token**
   - **Description:** Retrieve a JWT token from the Keycloak server using the user's password credentials.
   - **Authorization:** Basic authentication required.
     - `Username`: Keycloak realm client name.
     - `Password`: Keycloak realm client secret.
   - **Request Body (form-data):**
     - `grant_type`: Authentication grant type (use `password`).
     - `username`: User's username.
     - `password`: User's password.

## C. Screenshots
| ![Image Description](screenshots/3.png) |
|:-:|
| *Gateway Get License by ID* |

| ![Image Description](screenshots/2.png) |
|:-:|
| *Gateway Create License* |

| ![Image Description](screenshots/4.png) |
|:-:|
| *Gateway Update License* |

| ![Image Description](screenshots/5.png) |
|:-:|
| *Gateway Delete License* |

| ![Image Description](screenshots/7.png) |
|:-:|
| *Gateway Get License by Organization* |

| ![Image Description](screenshots/8.png) |
|:-:|
| *Gateway Get Organization by ID* |

| ![Image Description](screenshots/6.png) |
|:-:|
| *Keycloak Get Token* |

| ![Image Description](screenshots/17.png) |
|:-:|
| *Spring Security* |

| ![Image Description](screenshots/10.png) |
|:-:|
| *Kafka Event Producer* |

| ![Image Description](screenshots/9.png) |
|:-:|
| *Kafka Event Consumer* |

| ![Image Description](screenshots/12.png) |
|:-:|
| *Trace Correlation ID Parent* |

| ![Image Description](screenshots/11.png) |
|:-:|
| *Trace Correlation ID Child* |

| ![Image Description](screenshots/13.png) |
|:-:|
| *Retrieve Data from Redis* |

| ![Image Description](screenshots/14.png) |
|:-:|
| *Keycloak UI* |

| ![Image Description](screenshots/15.png) |
|:-:|
| *Grafana Dashboard* |

| ![Image Description](screenshots/16.png) |
|:-:|
| *Database in DBeaver UI* |