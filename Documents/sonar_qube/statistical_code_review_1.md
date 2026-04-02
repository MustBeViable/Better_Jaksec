# Statistical Code Review Report 1

First SonarQube/SonarScanner report with no fixes made

## Project Information

- **Project name:** BetterJakSec
- **Team members:** Sakari Honkavaara, Leevi Laune, Elias Rinne
- **Analysis date:**

---

## 1. Purpose

The purpose of this report is to document the static code analysis results of the BetterJakSec project using SonarQube and SonarScanner. The goal is to identify code quality issues, complexity problems, duplicated code, and other maintainability or reliability concerns, and to provide recommendations for improvement.

---

## 2. Tools and Environment

- **Static analysis tool:** SonarQube Community Build
- **Scanner used:** SonarScanner / SonarScanner for Maven
- **SonarQube server URL:** `http://localhost:9000`
- **Java version:** 21
- **Build tool:** Maven
- **Operating system:** MacOS and Windows 11
- **Analysis command used:**
  ```bash
  mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=YOUR_TOKEN
  ```
  - Replace token with your personal token
  - This covers wider range of statistics such as code coverage and tests

---

## Report

**Overview**

![Overview](./initial_report_images/overview.png)

**Issues**

*Security*

![Security](./initial_report_images/security.png)

No security issues were found

*Reliability*

![Reliability](./initial_report_images/reliability.png)

*Maintainablity*

![Maintainability](./initial_report_images/maintainability.png)

**Measures**

*Complexity*

![Complexity](./initial_report_images/complexity.png)

*Duplications*

![Duplications](./initial_report_images/duplications.png)

*Lines of code*

![Lines of Code](./initial_report_images/lines_of_code.png)