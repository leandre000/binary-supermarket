# 🛒 Binary Supermarket Online Shopping System

## 📝 Overview
An online shopping management system for Binary Supermarket, located in Kimironko sector in Gasabo district. The system enables customers to order and purchase products online, in addition to the existing physical store purchases.

## ✨ Features

### 🏷️ Product Management
- Register product details (code, name, product type, price, in-date, image)
- Track product quantities with detailed operations log (IN/OUT)

### 👥 Customer Management
- Register customers with name, email (as username), phone, and password
- Secure authentication system

### 🛍️ Shopping Cart Management
- Add products to a shopping cart
- Update quantities in the cart
- Remove items from the cart
- View cart contents

### 💰 Purchase System
- Buy products directly
- Checkout items from the shopping cart
- Automatic calculation of total purchase amount
- History of all customer purchases

## 🛠️ Technical Implementation

### Backend
- Java Spring Boot application
- RESTful API endpoints for all operations
- Spring Security for authentication
- Spring Data JPA for database operations
- PostgreSQL database with triggers for price calculation

### 📊 Database Design
The system includes the following main entities:
- Product (code, name, product type, price, in date, image)
- Quantity (id, product code, quantity, operation, date)
- Customer (id, firstname, phone, email, password)
- CartItem (id, customer id, product code, quantity)
- Purchase (id, product code, quantity, total, date)

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Maven

### Database Setup
1. Create a PostgreSQL database named `binary_supermarket`
2. Configure database credentials in `application.properties` if necessary

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run `mvn clean install` to build the project
4. Run `mvn spring-boot:run` to start the application
5. The application will be accessible at `http://localhost:8080`

## 📡 API Documentation

### Base URL
```
http://localhost:8080
```

### 🔐 Authentication
```
POST /api/auth/register - Register a new customer
POST /api/auth/login - Login with email and password
GET /api/customer/me - Get current logged in user profile
```

### 🏷️ Products
```
GET /api/products/all - Get all products
GET /api/products/{code} - Get a specific product
POST /api/products - Create a new product
PUT /api/products/{code} - Update a product
DELETE /api/products/{code} - Delete a product
```

### 📦 Quantities
```
GET /api/quantities/product/{productCode} - Get quantities for a product
POST /api/quantities/add - Add quantity to a product
POST /api/quantities/remove - Remove quantity from a product
GET /api/quantities/available/{productCode} - Get available quantity for a product
```

### 🛒 Cart
```
GET /api/cart/{customerId} - Get cart items for a customer
POST /api/cart/{customerId}/add - Add item to cart
PUT /api/cart/{customerId}/update/{cartItemId} - Update cart item quantity
DELETE /api/cart/{customerId}/remove/{cartItemId} - Remove item from cart
DELETE /api/cart/{customerId}/clear - Clear cart
```

### 💰 Purchases
```
GET /api/purchases/{customerId} - Get purchase history for a customer
POST /api/purchases/{customerId}/buy - Buy a product directly
POST /api/purchases/{customerId}/checkout - Checkout all items in cart
```

## 📋 API Endpoints Details

### 1. Product Management

#### Get All Products
```http
GET /api/products/all
```
Response:
```json
[
    {
        "code": "P001",
        "name": "Laptop",
        "productType": "ELECTRONICS",
        "price": 999.99,
        "inDate": "2024-03-20T10:00:00",
        "imageUrl": "laptop.jpg",
        "availableQuantity": 10
    }
]
```

#### Get Product by Code
```http
GET /api/products/{code}
```
Example: `GET /api/products/P001`
Response:
```json
{
    "code": "P001",
    "name": "Laptop",
    "productType": "ELECTRONICS",
    "price": 999.99,
    "inDate": "2024-03-20T10:00:00",
    "imageUrl": "laptop.jpg",
    "availableQuantity": 10
}
```

#### Create Product
```http
POST /api/products
```
Request Body:
```json
{
    "code": "P001",
    "name": "Laptop",
    "productType": "ELECTRONICS",
    "price": 999.99,
    "imageUrl": "laptop.jpg"
}
```

#### Update Product
```http
PUT /api/products/{code}
```
Example: `PUT /api/products/P001`
Request Body:
```json
{
    "name": "Updated Laptop",
    "productType": "ELECTRONICS",
    "price": 899.99,
    "imageUrl": "updated-laptop.jpg"
}
```

#### Delete Product
```http
DELETE /api/products/{code}
```
Example: `DELETE /api/products/P001`

### 2. Quantity Management

#### Get Quantities for Product
```http
GET /api/quantities/product/{productCode}
```
Example: `GET /api/quantities/product/P001`
Response:
```json
[
    {
        "id": 1,
        "productCode": "P001",
        "productName": "Laptop",
        "quantity": 10,
        "operation": "IN",
        "date": "2024-03-20T10:00:00"
    }
]
```

#### Add Quantity
```http
POST /api/quantities/add
```
Request Body:
```json
{
    "productCode": "P001",
    "quantity": 10,
    "operation": "IN"
}
```

#### Remove Quantity
```http
POST /api/quantities/remove
```
Request Body:
```json
{
    "productCode": "P001",
    "quantity": 5,
    "operation": "OUT"
}
```

#### Get Available Quantity
```http
GET /api/quantities/available/{productCode}
```
Example: `GET /api/quantities/available/P001`
Response: `5`

### 3. Cart Management

#### Get Cart Items
```http
GET /api/cart/{customerId}
```
Example: `GET /api/cart/1`
Response:
```json
[
    {
        "id": 1,
        "customerId": 1,
        "productCode": "P001",
        "productName": "Laptop",
        "productPrice": 999.99,
        "quantity": 2,
        "createdAt": "2024-03-20T10:00:00"
    }
]
```

#### Add Item to Cart
```http
POST /api/cart/{customerId}/add?productCode={code}&quantity={qty}
```
Example: `POST /api/cart/1/add?productCode=P001&quantity=2`

#### Update Cart Item
```http
PUT /api/cart/{customerId}/update/{cartItemId}?quantity={qty}
```
Example: `PUT /api/cart/1/update/1?quantity=3`

#### Remove Item from Cart
```http
DELETE /api/cart/{customerId}/remove/{cartItemId}
```
Example: `DELETE /api/cart/1/remove/1`

#### Clear Cart
```http
DELETE /api/cart/{customerId}/clear
```
Example: `DELETE /api/cart/1/clear`

### 4. Purchase Management

#### Buy Product Directly
```http
POST /api/purchases/{customerId}/buy?productCode={code}&quantity={qty}
```
Example: `POST /api/purchases/1/buy?productCode=P001&quantity=2`
Response:
```json
{
    "id": 1,
    "customerId": 1,
    "customerName": "John Doe",
    "productCode": "P001",
    "productName": "Laptop",
    "quantity": 2,
    "total": 1999.98,
    "purchaseDate": "2024-03-20T10:00:00"
}
```

#### Checkout Cart
```http
POST /api/purchases/{customerId}/checkout
```
Example: `POST /api/purchases/1/checkout`
Response:
```json
[
    {
        "id": 1,
        "customerId": 1,
        "customerName": "John Doe",
        "productCode": "P001",
        "productName": "Laptop",
        "quantity": 2,
        "total": 1999.98,
        "purchaseDate": "2024-03-20T10:00:00"
    }
]
```

#### Get Purchase History
```http
GET /api/purchases/{customerId}
```
Example: `GET /api/purchases/1`
Response:
```json
[
    {
        "id": 1,
        "customerId": 1,
        "customerName": "John Doe",
        "productCode": "P001",
        "productName": "Laptop",
        "quantity": 2,
        "total": 1999.98,
        "purchaseDate": "2024-03-20T10:00:00"
    }
]
```

## ⚠️ Error Responses

All error responses follow this format:
```json
{
    "timestamp": "2024-03-20T10:00:00",
    "message": "Error message here",
    "status": 400
}
```

### Common HTTP Status Codes
- 🟢 200: Success
- 🟢 201: Created
- 🟡 400: Bad Request
- 🔴 404: Not Found
- 🔴 500: Internal Server Error

## 🔧 Setup Instructions

1. Ensure PostgreSQL is running on port 5434
2. Create database:
```sql
CREATE DATABASE binary_supermarket;
```

3. Update application.properties if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5434/binary_supermarket
spring.datasource.username=postgres
spring.datasource.password=leandre
```

4. Run the application:
```bash
mvn spring-boot:run
```

## 🧪 Testing Flow

1. Create a product
2. Add quantity to the product
3. Register a customer
4. Add items to cart
5. Test purchase operations
6. View purchase history

## 📝 Notes

- 🌐 All endpoints are CORS-enabled
- 📄 All responses are in JSON format
- ⏰ All timestamps are in ISO-8601 format
- 💵 All monetary values are in BigDecimal format
- 🔑 All IDs are auto-generated except product codes 
