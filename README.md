Introduction
-
This is a basic java implementation of supermarket checkout system.

UML Diagram
-
![UML Diagram](images/uml_diagram.png)

Technical details
-
- This project is written in JDK 17.

Instruction for execution
-
| command | description                                |
| ------- |--------------------------------------------|
|mvn clean| clean the project(remove target directory) |
| mvn build | build the project                          |
| mvn package | packaging into jar                         |
| java -cp supermarket-checkout-system.jar org.cdl.Main | execute the jar in the target directory    |


Further improvements
-
- Implement REST API with spring boot to create self-executable web application.
- Integrate SQL database to store setup rules and persist shopping basket.
