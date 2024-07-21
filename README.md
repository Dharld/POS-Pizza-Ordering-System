
# Pizza Store Application

Welcome to the Pizza Store application repository. This project is a Java-based platform for managing pizza orders. It provides users with an intuitive interface to browse the menu, customize their orders, and process payments.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features

- **Menu Browsing:** Users can browse through a variety of pizzas with descriptions and prices.
- **Customization:** Users can customize their pizzas by selecting toppings, crust types, and sizes.
- **Order Summary:** Provides a summary of the order with an itemized list of selected pizzas and total cost.
- **User Authentication:** Users can sign up, log in, and manage their profiles.
- **Payment Integration:** Secure payment processing.
- **Order History:** Users can view their past orders.
- **Responsive Design:** Optimized for both desktop and mobile devices.

## Project Structure

```plaintext
Pizza Store Source Code/
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           └── demo/
│   │   │               ├── controllers/
│   │   │               │   ├── CardController.java
│   │   │               │   ├── CartController.java
│   │   │               │   ├── CartItemController.java
│   │   │               │   ├── CheckoutController.java
│   │   │               │   ├── CheckoutItemController.java
│   │   │               │   ├── HomeController.java
│   │   │               │   ├── HomeLayoutController.java
│   │   │               │   ├── LoginController.java
│   │   │               │   ├── MainController.java
│   │   │               │   ├── MainItemController.java
│   │   │               │   ├── MenuController.java
│   │   │               │   ├── PizzaBuilderController.java
│   │   │               │   ├── SignupController.java
│   │   │               ├── database/
│   │   │               │   ├── CartManager.java
│   │   │               │   ├── JDBCManager.java
│   │   │               │   ├── UserManager.java
│   │   │               ├── models/
│   │   │               │   ├── Address.java
│   │   │               │   ├── AppCard.java
│   │   │               │   ├── AppModel.java
│   │   │               │   ├── CartItem.java
│   │   │               │   ├── Category.java
│   │   │               │   ├── Option.java
│   │   │               │   ├── Order.java
│   │   │               │   ├── Product.java
│   │   │               │   ├── User.java
│   │   │               ├── utils/
│   │   │               │   ├── EmailUtil.java
│   │   │               │   ├── Mail.java
│   │   │               │   ├── OrderReceipt.java
│   │   │               │   ├── PaymentService.java
│   │   │               └── HelloApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       ├── java/
│       │   └── org/
│       │       └── example/
│       │           └── demo/
│       │               └── PizzaStoreApplicationTests.java
│       └── resources/
├── target/
└── README.md
```

## Getting Started

To get started with the Pizza Store application, follow these steps:

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

### Installation

1. **Clone the repository:**

```sh
git clone <repository-url>
cd Pizza Store Source Code
```

2. **Build the project:**

```sh
./mvnw clean install
```

3. **Run the application:**

```sh
./mvnw exec:java -Dexec.mainClass="org.example.demo.HelloApplication"
```

This will start the application. Open [http://localhost:8080](http://localhost:8080) to view it in the browser.

## Technologies Used

- **Java:** The main programming language used.
- **Maven:** Dependency management and build automation tool.
- **JavaFX:** Used for creating the user interface.
- **JDBC:** Java Database Connectivity for managing database interactions.

## Contributing

If you wish to contribute to the project, please follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b my-feature-branch`
3. Make your changes and commit them: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin my-feature-branch`
5. Submit a pull request.

## License

This project is licensed under the MIT License.

## Contact

For any inquiries, please contact the project maintainer at [maintainer@example.com](mailto:maintainer@example.com).
