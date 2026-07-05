 # Blog API

A Spring Boot based blog platform providing a simple REST API for managing posts, categories and tags with JWT-based authentication. Designed for easy local development and extension, it uses Spring Data JPA for persistence and MapStruct for DTO mapping.

## Key Features

- RESTful API for Posts, Categories and Tags (CRUD operations)
- JWT authentication and stateless security filter
- Spring Data JPA repositories and entity mappings
- MapStruct mappers + DTOs for separation of API and persistence models
- Validation (Jakarta Validation) and centralized error response DTOs
- MySQL for production; H2 is used for tests (configurable via properties)

## Tech Stack

- Java 17
- Spring Boot 3.4.x (Web, Data JPA, Security, Validation)
- Maven build system
- MapStruct (mapping DTOs <-> entities)
- Lombok (boilerplate reduction)
- JJWT (JSON Web Token handling)
- MySQL (production), H2 (tests)

## Prerequisites

- Java 17 (JDK)
- Maven 3.6+
- MySQL (or use the default H2 for tests)
- Git

## Getting Started / Installation

1. Clone the repository

   git clone https://github.com/Harshith-005/Blogapi.git
   cd Blog

2. Configure environment / application properties

   The project reads configuration from `src/main/resources/application.properties`. Recommended to override sensitive values with environment variables or an external properties file. Replace placeholders below:

   - jwt.secret: [JWT_SECRET]
   - spring.datasource.url: [DB_URL] (e.g. jdbc:mysql://localhost:3306/blog)
   - spring.datasource.username: [DB_USER]
   - spring.datasource.password: [DB_PASS]

   Example (Linux / macOS):

   export JWT_SECRET="[YOUR_JWT_SECRET]"
   export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/blog"
   export SPRING_DATASOURCE_USERNAME="root"
   export SPRING_DATASOURCE_PASSWORD="root"

   Or edit `src/main/resources/application.properties` with values for local development.

3. Build the project

   mvn clean package -DskipTests

4. Run the application

   mvn spring-boot:run

   or

   java -jar target/*.jar

## Usage

1. Authenticate (obtain JWT)

   POST /api/v1/auth/login
   Content-Type: application/json

   {
     "email": "user@example.com",
     "password": "password"
   }

   Response: { "token": "<jwt>", "expiresIn": 86400 }

2. Use the token for authenticated requests

   GET /api/v1/posts
   Authorization: Bearer <jwt>

3. Example cURL: create a post (authenticated)

   curl -X POST http://localhost:8080/api/v1/posts \
     -H "Authorization: Bearer <jwt>" \
     -H "Content-Type: application/json" \
     -d '{"title":"Hello","content":"World","categoryId":"<uuid>","tagId":[],"status":"DRAFT"}'

4. Run tests

   mvn test

## API Endpoints (high level)

- POST /api/v1/auth/login — authenticate and receive JWT
- GET /api/v1/posts — list public posts (filters: categoryId, tagId)
- GET /api/v1/posts/{id} — get single post
- GET /api/v1/posts/drafts — authenticated: list drafts for logged-in user
- POST /api/v1/posts — create post (authenticated)
- PUT /api/v1/posts/{id} — update post
- DELETE /api/v1/posts/{id} — delete post
- GET/POST/DELETE /api/v1/categories — manage categories
- (Tags) POST /api/vq/tag — note: controller path contains `/api/vq/tag` (check if intended)

## Project Structure

src/
├─ main/
│  ├─ java/com/harshith/blog/
│  │  ├─ BlogApplication.java         # Spring Boot entrypoint
│  │  ├─ domain/
│  │  │  ├─ controller/               # REST controllers (Auth, Post, Category, Tag)
│  │  │  ├─ dto/                      # API DTOs (AuthResponse, PostDto, LoginRequest, ...)
│  │  │  ├─ entity/                   # JPA entities (Post, Category, Tag, User)
│  │  │  ├─ Repository/               # Spring Data JPA repositories
│  │  │  ├─ service/                  # Service interfaces and impls (Authentication, Post, ...)
│  │  │  └─ security/                 # JWT filter and user-details integration
│  │  └─ mapper/                      # MapStruct mappers
│  └─ resources/
│     ├─ application.properties       # DB and jwt.secret (default values present)
│     ├─ static/
│     └─ templates/
└─ test/

## Notes & Recommendations

- The repository includes `jwt.secret` in `application.properties` — treat this as a placeholder and move secrets to environment variables or a secrets store for production.
- Controller `TagController` is mapped to `/api/vq/tag`. Verify intended path (possible typo) and correct to `/api/v1/tags` if expected.
- Use an externalized configuration or Spring Cloud Config for production deployments.

## Contributing

Contributions welcome. Open an issue or a PR with a clear description and tests where applicable.

## License

Add project license here.

