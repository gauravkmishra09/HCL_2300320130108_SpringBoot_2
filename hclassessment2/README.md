# HCL Online Shopping REST API

Spring Boot REST API practical assessment using Java 17, Spring Data JPA and MySQL.

## Requirements
- Java 17+
- Maven 3.8+
- MySQL 8+

## Database
Create the database:

```sql
CREATE DATABASE hcldb;
```

Set environment variables `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` for your local MySQL connection. The default URL is `jdbc:mysql://localhost:3306/hcldb?useSSL=false&serverTimezone=UTC`.

## Run
From the `hclassessment2` directory:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

## Endpoints

### Customers
- POST `/api/customers/register`
- GET `/api/customers`
- GET `/api/customers/{id}`
- PUT `/api/customers/{id}`
- DELETE `/api/customers/{id}`

### Products
- POST `/api/products`
- GET `/api/products`
- GET `/api/products/{id}`
- PUT `/api/products/{id}`
- DELETE `/api/products/{id}`
- GET `/api/products/category/{category}`
- GET `/api/products/search?name=phone`

### Cart
- POST `/api/cart/add/{customerId}/{productId}?quantity=1`
- GET `/api/cart/{customerId}`
- PUT `/api/cart/update/{cartItemId}?quantity=2`
- DELETE `/api/cart/remove/{cartItemId}`
- DELETE `/api/cart/clear/{customerId}`

## Notes
- No JWT, Spring Security or DTO layer is used.
- Hibernate creates/updates tables using `ddl-auto=update`.
- Cart item price comes from the product record.
- Cart quantity is checked against current product stock.
