📦 Warehouse Management System (WMS)

A web-based Warehouse Management System built using Java (Servlet + JSP MVC) with JPA + MySQL for database operations. The system manages inventory, suppliers, stock levels, and warehouse transactions while providing both dynamic HTML views and Excel export options for reporting.

🚀 Features

Manage products, suppliers, categories, and stock

Stock In / Stock Out tracking

Real-time inventory updates

Low-stock alerts

Transaction and inventory reports

Export data to Excel

Dynamic HTML-based views

Secure login for Admin & Staff

Clean MVC architecture for maintainability

🛠️ Tech Stack
Component	Technology
Backend	Java, Servlet, JSP, MVC
ORM	JPA (Hibernate)
Frontend	HTML, CSS, JavaScript
Database	MySQL
Server	Apache Tomcat
IDE	Eclipse
📂 Project Structure
/src
 ├── controller        # Servlet controllers
 ├── dao               # JPA repositories / database layer
 ├── model             # Entity classes
 ├── service           # Business logic
/webapp
 ├── views/            # JSP pages
 ├── assets/           # CSS, JS, images
 └── WEB-INF/web.xml   # Deployment descriptor

⚙️ Installation & Setup
1️⃣ Clone the repository
git clone https://github.com/your-username/warehouse-management-system.git

2️⃣ Import Project

Open Eclipse

File → Import → Dynamic Web Project

Select the cloned folder

3️⃣ Configure MySQL

Create a database (e.g., wms_db)

Update credentials in persistence.xml

4️⃣ Run the Application

Add Tomcat Server in Eclipse

Run project on server



🌟 Future Enhancements

REST API support

Spring Boot migration

Barcode scanner integration

Analytics dashboard with charts

Mobile-friendly UI

🤝 Contributing

Contributions, issues, and feature requests are welcome!
Feel free to submit a pull request.

📜 License

This project is licensed under the MIT License
