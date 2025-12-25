# Hotel Reservation System

## 📌 Project Description
- This project is a console-based Hotel Reservation System developed using Java.
- The system allows customers to view available rooms, make reservations, cancel reservations, and complete payments.
- Object-Oriented Programming (OOP) principles are strictly applied throughout the project.

---

## 🧱 Technologies Used
- Java
- Eclipse IDE
- JUnit 5
- CSV file handling

---

## 🏗️ Project Structure
- model  
  - Hotel  
  - Room (abstract)  
  - StandardRoom  
  - DeluxeRoom  
  - Customer  
- reservation  
  - Reservation  
- service  
  - ReservationManager  
- payment  
  - Payment (interface)  
  - CashPayment  
  - CreditCardPayment  
- utils  
  - CSVUtil  
- main  
  - Main  

---

## ⚙️ Features
- List available hotel rooms
- Make a room reservation
- Cancel an existing reservation
- View all reservations
- Process payments (cash or credit card)
- Save and load data using CSV files

---

## 🧠 Object-Oriented Design
- Inheritance is used for room types (StandardRoom, DeluxeRoom).
- Interface-based design is applied for payment methods.
- Encapsulation is ensured using private fields and public getters/setters.
- Separation of concerns is maintained between model, service, and utility layers.

---

## 🧪 Unit Testing
- JUnit 5 is used for unit testing.
- The following components are tested:
  - Room and its subclasses
  - Reservation
  - ReservationManager
  - Hotel
  - Payment implementations
- Tests validate core functionalities such as booking, cancellation, and payment processing.

---

## 📊 UML & Diagrams
- UML Class Diagram illustrates the system architecture and class relationships.
- Use Case Diagram represents user-system interactions.

---

## ▶️ How to Run
- Open the project in Eclipse.
- Run the `Main` class.
- Follow the console menu to interact with the system.

---

## 👤 Author
- Fatih Çatalçam
