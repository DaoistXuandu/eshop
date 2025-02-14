**Reflection 1**  
During this module, I implemented two new features using Spring Boot while adhering to clean code principles and secure coding practices. I ensured that variables, methods, and class names were meaningful and descriptive, making the codebase more readable and maintainable. Controller methods were correctly utilized to handle incoming HTTP requests and route them to service methods, keeping the business logic separate. The repository layer was used efficiently to manage database interactions, promoting a clean architecture.

However, while reviewing my code, I noticed an issue where a newly created product did not have an automatically generated ID, resulting in a null value. To resolve this, I manually generated the ID. Additionally, I realized that my commit messages were inconsistent, confusing and not focusing on standard commit name. Moving forward, I plan to adhere to a structured commit message format, such as the conventional commits standard (feat: add new product creation logic or fix: resolve product ID generation issue). Consistently following this practice will improve version control clarity and maintainability.

---

**Reflection 2**  
After writing unit tests, I feel more confident in my code's reliability, as I have tested edge cases to ensure robustness. The number of unit tests in a class depends on the complexity of the methods being tested. The greater the number of edge cases, the more tests should be written. To verify the sufficiency of our unit tests, we can use code coverage metrics. However, achieving 100% code coverage does not necessarily mean that the code is free from bugs, as poor test design can lead to false confidence.

Based on how i code `CreateProductFunctionalTest.java`, If i was asked to create another functional test suite that verifies the number of items in the product list. I may be considered creating a new Java class with the same setup procedures and instance variables. However, this approach would introduce code duplication, violating the **Don't Repeat Yourself (DRY)** principle and reducing overall code maintainability.

To improve cleanliness and maintain high code quality, I think refactor shared logic into a reusable helper class or a parent test class. This would allow different test suites to inherit common setup procedures and instance variables, improving maintainability and readability. Extracting duplicated code into utility methods or using test parameterization can also help streamline testing while ensuring consistency.

Moving forward, I will continue to apply clean code principles by reducing redundancy, maintaining modular code, and adhering to structured coding standards. These improvements will make the codebase more scalable and easier to collaborate on with team members.
