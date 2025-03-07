[Link Deployment](https://overseas-cardinal-xuandu-dad35b97.koyeb.app/)
[Modul 4](https://docs.google.com/document/d/1-fsOyyajozE2QSV-QBEA_YR_ZZn9yhKr/edit?tab=t.0#heading=h.gjdgxs)

# WEEK 3
````
1) Explain what principles you apply to your project!
````
**Single Responsibility Principle (SRP):**  
By separating each class into its own file, I ensured that every class has only one responsibility. For example, CarController now strictly handles car-related requests without inheriting product-related behavior from ProductController. Similarly, the Repository, Service, Model, and Controller layers each focus on one aspect: the Repository manages database operations, the Service handles business logic, the Model defines the object entity, and the Controller manages HTTP requests. This approach guarantees that each class only changes for one reason.

**Open-Closed Principle (OCP):**  
I designed my code to be open for extension but closed for modification. By defining clear interfaces for repositories and services (such as CarRepositoryInterface and CarService), I can add new behaviors or switch implementations without altering the existing, well-tested code. This means that if I need to support a new persistence mechanism or business logic, I can simply extend the system by implementing new classes that adhere to these interfaces, rather than modifying the current ones.

**Liskov Substitution Principle (LSP):**  
I ensured that my system complies with the LSP by designing the application so that any subclass or alternative implementation of an interface can replace another without affecting the program’s correctness. For instance, by relying on service and repository interfaces rather than concrete classes, I can swap out a CarRepository implementation with another that meets the same contract without breaking the functionality of the controllers or services that depend on it.

**Interface Segregation Principle (ISP):**  
I implemented ISP by breaking down large, monolithic interfaces into smaller, more focused ones. Each repository or service interface now contains only the methods relevant to its specific domain, ensuring that classes are not forced to depend on methods they don't use. This makes the codebase more modular and easier to maintain, as clients interact only with the precise functionality they require, rather than being burdened by unnecessary methods.

**Dependency Inversion Principle (DIP):**  
I adhered to DIP by ensuring that high-level modules, such as controllers, depend on abstractions rather than concrete implementations. This was achieved through constructor injection and by defining interfaces for services and repositories. With controllers interacting solely with these abstractions, I can easily swap out or mock implementations for testing or future enhancements without affecting the overall system, thus promoting flexibility and maintainability.

````
2) Explain the advantages of applying SOLID principles to your project with examples.
````
- **Ease of Maintenance (SRP):**
    - Every class in my project has a single, well-defined responsibility.
    - For example, if I need to update business logic, I only modify the Service layer without touching the Controller or Repository. This separation minimizes unexpected side effects and simplifies future maintenance.

- **Improved Testability (DIP & Abstraction):**
    - By depending on abstractions, I can easily substitute real components with mocks during testing.
    - For instance, since CarController depends on a CarService interface, I can inject a mock CarService for unit tests. This isolation leads to more focused and reliable tests, ensuring that each component works correctly on its own.

- **Enhanced Flexibility (OCP):**
    - The design is open for extension but closed for modification, meaning new features can be added without altering stable, tested code.
    - For example, if I need to add new behaviors to the Car domain, I can extend the current implementation through additional classes or methods without risking the introduction of bugs in existing functionality.

- **Modular and Decoupled Architecture:**
    - Applying SOLID principles results in a system where each component (Repository, Service, Model, Controller) is decoupled and independently manageable.
    - This modularity makes the project more adaptable to changes, easier to understand, and simpler to debug.

````
3) Explain the disadvantages of not applying SOLID principles to your project with examples.
````
- **Reduced Maintainability (SRP Violation):**
    - Without SRP, classes take on multiple responsibilities, making them harder to understand and maintain.
    - *Example:* If CarController manages both HTTP requests and business logic, a change in the business rules may require modifications to the controller, potentially introducing unintended side effects in request handling.

- **Harder to Test (DIP Violation):**
    - Not depending on abstractions forces components to rely on concrete implementations, which complicates unit testing.
    - *Example:* If CarController directly creates or tightly couples with a specific CarService implementation, it becomes difficult to inject mocks or stubs for isolated testing, leading to brittle tests.

- **Limited Flexibility (OCP Violation):**
    - When code is not designed to be extended, adding new features means altering existing, stable code, increasing the risk of bugs.
    - *Example:* Adding new behavior to the Car domain would require direct changes in the CarService or CarRepository classes, rather than simply extending their functionality through new classes.

- **High Coupling and Overly Broad Interfaces (ISP Violation):**
    - Without ISP, interfaces might include methods that are not needed by all clients, leading to tight coupling and unnecessary complexity.
    - *Example:* A single large interface for all product operations might force the CarRepository to implement methods irrelevant to car operations, making the system harder to understand and modify.

- **Overall Impact on Development:**
    - The absence of SOLID principles results in a codebase that is more error-prone, less modular, and harder to maintain.
    - This leads to increased development time, more challenging debugging sessions, and a system that is less adaptable to future changes or scaling needs.git


# WEEK 2
**Reflection 1**
````
List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
````

Code Qualities that i solve and encouter
- multiple unused import, so i fixed the import and remove the unused part and remain the important one
- Empty setup function, in the previous file RepositoryTest i create a setup function that has no instruction and make it an empty file and so i remove it in this commit 

After running the analysis, there are no major issues regarding code quality, apart from the need for an additional review to ensure maintainability. Since maintainability often requires a second perspective, it's not something I can fully address on my own. However, the workflow and setup outlined in the tutorial have been helpful in identifying potential areas for improvement.

One limitation of the current setup is that I require to pushing changes directly to the main branch to obtain the full analysis results and i think this is not ideal as catching issues earlier in the development process would improve efficiency and reduce the risk of introducing problems into the main codebase. A more optimized approach would involve integrating the analysis into the pre-merge workflow to detect issues before they reach production.

Also during the exercise I already have 100% coverage on all of function

![Data Coverage](https://cdn.discordapp.com/attachments/1268572223157960845/1342481340070887425/Screenshot_2025-02-21_at_20.00.48.png?ex=67b9caad&is=67b8792d&hm=80e0100021f245f6ac132e7402472ea23513275560ab88b73291a52f9d5d02c7&)

**Reflection 2**
````
Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
````

The current implementation meets the definition of Continuous Integration (CI) by automatically running tests on every push and pull request. This ensures that code changes are verified before merging, helping to catch issues early. Additionally, the SonarCloud analysis step improves code quality by identifying potential bugs and maintainability concerns. However, while CI is well-implemented, the setup does not include an automated deployment process, meaning it does not fully meet the definition of Continuous Deployment (CD). The Scorecard workflow enhances security by analyzing supply chain risks and enforcing branch protection but does not contribute to CI/CD automation for delivering software to users.

To achieve full Continuous Deployment, the pipeline should be extended to include automated deployment steps. This can be done by adding a job that deploys successful builds to a staging or production environment after tests pass. Tools like GitHub Actions, GitLab CI/CD, or third-party services such as AWS CodeDeploy, Kubernetes, or Firebase Hosting can handle this process. Additionally, implementing feature flags and automated rollback mechanisms would ensure safe deployments by allowing quick rollbacks if an issue is detected in production.

# WEEK 1

**Reflection 1**  
During this module, I implemented two new features using Spring Boot while adhering to clean code principles and secure coding practices. I ensured that variables, methods, and class names were meaningful and descriptive, making the codebase more readable and maintainable. Controller methods were correctly utilized to handle incoming HTTP requests and route them to service methods, keeping the business logic separate. The repository layer was used efficiently to manage database interactions, promoting a clean architecture.

However, while reviewing my code, I noticed an issue where a newly created product did not have an automatically generated ID, resulting in a null value. To resolve this, I manually generated the ID. Additionally, I realized that my commit messages were inconsistent, confusing and not focusing on standard commit name. Moving forward, I plan to adhere to a structured commit message format, such as the conventional commits standard (feat: add new product creation logic or fix: resolve product ID generation issue). Consistently following this practice will improve version control clarity and maintainability.

---

**Reflection 2**  
After writing unit tests, I feel more confident in my code's reliability, as I have tested edge cases to ensure robustness. The number of unit tests in a class depends on the complexity of the methods being tested. The greater the number of edge cases, the more tests should be written. To verify the sufficiency of our unit tests, we can use code coverage metrics. However, achieving 100% code coverage does not necessarily mean that the code is free from bugs, as poor test design can lead to false confidence.

Based on how i code `CreateProductFunctionalTest.java`, If i was asked to create another functional test suite that verifies the number of items in the product list. I think creating a new Java class with the same setup procedures and instance variables may be a valid approach. However, this approach would introduce code duplication, violating the **Don't Repeat Yourself (DRY)** principle and reducing overall code maintainability.

To improve cleanliness and maintain high code quality, I think refactor shared logic into a reusable helper class or a parent test class. This would allow different test suites to inherit common setup procedures and instance variables, improving maintainability and readability. Extracting duplicated code into utility methods or using test parameterization can also help streamline testing while ensuring consistency.

Moving forward, I will continue to apply clean code principles by reducing redundancy, maintaining modular code, and adhering to structured coding standards. These improvements will make the codebase more scalable and easier to collaborate on with team members.
