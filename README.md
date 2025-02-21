[Link Deployment](https://overseas-cardinal-xuandu-dad35b97.koyeb.app/)

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
