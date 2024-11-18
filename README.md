# **Payment Service**

This is a Spring Boot-based Payment Service that supports secure transactions, multiple payment options, and seamless
integration with Kafka for order notifications. The service uses MySQL for transaction logs and allows integration with
various payment gateways.

---

## **Features**

1. Multiple Payment Options: Support for credit/debit cards, online banking, and other popular payment methods.
2. Secure Transactions: Ensure user trust by facilitating secure payment transactions.
3. Payment Receipt: Provide users with a receipt after a successful payment.
4. Once the payment is confirmed, it produces a message on Kafka to notify the Order Management Service.

---

## **Technologies Used**

- **Framework:** Spring Boot
- **Database:** MySQL
- **Messaging:** Apache Kafka
- **Build Tool:** Maven
- **Language:** Java
- **Payment Gateways:** Razorpay And Stripe

---

## **Setup Instructions**

### **1. Prerequisites**

- Java 11 or higher.
- MySQL installed and running.
- Kafka installed and running on `localhost:9092`.
- Maven installed.

---

### **2. Clone the Repository**

```bash
git clone https://github.com/udaysisodiya16/payment-service.git
```

---

### **3. Configure the Application**

Create a new database in MySQL:

```sql
CREATE DATABASE payment_service;
```

Update the database and Kafka configurations in `src/main/resources/application.properties`:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/payment_service
spring.datasource.username=root
spring.datasource.password=yourpassword
# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
# Kafka Topic
kafka.topic.payment.notification=payment_notification
# Server Configuration
server.port=8085
```

---

### **4. Run Kafka Locally**

1. Start Zookeeper:
   ```bash
   bin/zookeeper-server-start.sh config/zookeeper.properties
   ```
2. Start Kafka Server:
   ```bash
   bin/kafka-server-start.sh config/server.properties
   ```

---

### **5. Build and Run**

```bash
mvn clean install
```

#### **4.2. Run the Application**

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8085`.

---

### **6. Access Swagger API Documentation**

- After the application starts, access Swagger UI for API testing and documentation at:  
  [http://localhost:8085/swagger-ui.html](http://localhost:8085/swagger-ui.html)

---

## **API Endpoints**

### **1. Initiate Payment**

- **URL:** `POST /payment`
- **Request Body:**

```json
{
  "name": "Uday Sisodiya",
  "phoneNumber": "uday.sisodiya123@gmail.com",
  "email": "99889977522",
  "orderId": 12345,
  "amount": 150.75
}
```

- **Response:**

```json
"https://rzp.io/l/abc123xyz"
```

---

### **2. Get Payment Details**

- **URL:** `GET /payment/{transactionId}`
- **Response:**

```json
{
  "transactionId": 67890,
  "orderId": 12345,
  "amount": 150.75,
  "status": "SUCCESS",
  "createdAt": "2024-11-18T10:30:00Z",
  "receiptUrl": "/url"
}
```

### **3. Get All Order Payment Details**

- **URL:** `GET /payment//order/{orderId}`
- **Response:**

```json
[
  {
    "transactionId": 67890,
    "orderId": 12345,
    "amount": 150.75,
    "status": "FAILED",
    "createdAt": "2024-11-18T10:20:00Z",
    "receiptUrl": "/url1"
  },
  {
    "transactionId": 67890,
    "orderId": 12345,
    "amount": 150.75,
    "status": "SUCCESS",
    "createdAt": "2024-11-18T10:30:00Z",
    "receiptUrl": "/url2"
  }
]
```

---