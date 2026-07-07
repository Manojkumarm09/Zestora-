# 🍽 Zestora — Food Delivery Web Application

A full-stack food delivery web application built with **Java, JSP, Servlets, JDBC, and MySQL**, following the **MVC architecture** with a **DAO design pattern**.

Zestora covers the complete food ordering journey — from guest browsing, through authentication, cart management, and a multi-step checkout, to order history and reordering.

---

## 🚀 Features

### Guest & Authentication
- Guests can browse restaurants and menus without logging in
- Cart, checkout, and order history require login — guests are redirected with a clear message
- Passwords are securely hashed with a salt before storage
- Login attempt tracking — accounts are automatically blocked after 3 failed attempts

### Restaurants & Menu
- Restaurant listing with live search by name or cuisine
- Menu items organized by category with clickable filter tabs
- Inline quantity stepper `[- 1 +]` to add/remove items directly from the menu

### Cart & Pricing
- Real-time subtotal, GST (5%), and delivery fee calculation
- **Free delivery** automatically applied above ₹500
- Quantity adjustment and item removal directly from the cart

### Checkout
- **3-step checkout wizard:**
  1. Contact details (name, phone) + delivery address (saved or custom)
  2. Order summary review
  3. Payment method selection — Cash, UPI, or Card
- Simulated UPI QR-code payment with an animated success confirmation *(demo simulation — not connected to a live payment gateway)*

### Orders
- Order success page with Order ID and estimated delivery time
- Order history with full item breakdowns
- Cancel a pending order
- Reorder a past order directly back into the cart

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java, Jakarta Servlets |
| Frontend | JSP, HTML, CSS, JavaScript |
| Database | MySQL |
| Data Access | JDBC with DAO pattern |
| Server | Apache Tomcat |
| Architecture | MVC (Model-View-Controller) |

---

## 📁 Project Structure
Zestora/
├── src/main/java/com/foodiehub/
│   ├── controller/     → Servlets (request handling)
│   ├── DAO/             → Data Access Object interfaces
│   ├── DAOImpl/         → DAO implementations (JDBC queries)
│   ├── model/           → POJOs (User, Cart, Menu, OrderTable, etc.)
│   └── utility/         → DBConnection, PasswordUtil
├── src/main/webapp/
│   ├── css/style.css    → All styling
│   ├── images/          → Restaurant, menu, and UI images
│   └── *.jsp            → Views (restaurant, menu, cart, checkout, orders, auth)
└── lib/                 → MySQL JDBC connector

---

## ⚙️ Setup Instructions

### Prerequisites
- Java 17+
- Apache Tomcat 10.x
- MySQL Server
- Eclipse IDE (or any Jakarta EE-compatible IDE)

### Steps

1. **Clone the repository**
git clone https://github.com/Manojkumarm09/Zestora-.git

2. **Import into Eclipse**
   File → Import → Existing Projects into Workspace → select the cloned folder

3. **Set up the database**
   Create a MySQL database and set up the following tables, matching the model classes in `src/main/java/com/foodiehub/model/`:
   - `user` — stores registered users (hashed passwords)
   - `restaurant` — restaurant listings
   - `menu` — menu items per restaurant
   - `cart` — active cart items per user
   - `ordertable` — placed orders
   - `orderitem` — items within each order

4. **Configure the database connection**
   Open `src/main/java/com/foodiehub/utility/DBConnection.java` and update:
```java
   String url = "jdbc:mysql://localhost:3306/your_database_name";
   String username = "your_mysql_username";
   String password = "your_mysql_password";
```

5. **Run on Tomcat**
   Right-click the project → Run As → Run on Server

6. **Access the app**
http://localhost:8080/Zestora/

---

## 🔒 Security Notes

- Passwords are hashed (SHA-256 with per-user salt) — never stored in plain text
- Login lockout after 3 failed attempts prevents brute-force attempts
- Guest access is scoped intentionally — browsing is open, ordering requires authentication

---

## 🔮 Future Improvements

- Real payment gateway integration (currently simulated for demo purposes)
- Admin panel for restaurant and order status management
- Server-side search and filtering for scalability
- Automated testing

---

## 👤 Author

**Manoj Kumar M**
Final-year B.E. Information Science & Engineering
