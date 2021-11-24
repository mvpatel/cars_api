### Car Rest API Application


#### Run the API by the Postman
- Go to the JAR File Folder
    - cd /cars_api/Document/JarFiles
- Run the JAR File
    - JAVA -jar web-0.0.1-SNAPSHOT.jar
- Get the Postman Collections
    - Open PostMan
    - Go to File -> Import
    - Upload the following File
        - /cars_api/Document/PostManResults/CarAPI.postman_test_run.json
    - Click on Import
- Run Postman Collections
    - After Import CARAPI Collections will be available in the Postman
    - Run request One by One to get proper output
    - If Required then add a new Request by duplicating any of the current requests.

#### Run all the Tests with Coverage
- Run all Tests by coverage on the following folder
    - /cars_api/src/test/java/com/car/controller/CarRestControllerIntegrationTest.java
    - /cars_api/src/test/java/com/car/service/impl/CarServiceImplTest.java


#### Following is done for this project
- Sequence Diagrams are added in the folder: /cars_api/Document/SequenceDiagrams
- Test Coverage is added in the folder: /cars_api/Document/TestCoverage
    - 100% Test Coverage
- Git Repository: https://github.com/mvpatel/cars_api
- SonarLint Check is done no Error found
- Verify is added for each Rest API method in the Controller
- As requirements two separate hierarchically linked resources are added as below.
    - Make
    - Model
- As Requirements Persistent storage is used by H2 Database and JPA Repository.
- Integration Tests are added.
    - Coverage is 100%
    - MockMVC is used for the Test
    - Each field is checked in all tests
    - multiple values are added by BeforeAll so code duplication is less
    - Code Duplication is verified by SonarLine No Code Duplication
- Unit Tests are added for the Service
    - Mockito is used for the Unit Tests
    - All Required methods are Verified for the Repository
    - Verification is specially done by times so any code changes should get error easily if any mistake
    - required Negative Tests cases are also added.
    - Coverage is 100%
    - Code Duplication is verified by Sonar Lint and no code Duplication is found.
- Git Ignore file is added to avoid not essential file push to Git hub.


#### Following Todo Work required to improve the Project as most of is not given in the requirements.
- @Verify should be improved by adding more validation in the Entity file
- Comments should be added in the details.
- SonarQube should be added in the pom file
- Jacoco test Coverage plugin required to be added in the pom file
    - Minimum 85% Test Coverage required should be added under Jacoco
- Git Commits should be improved.
- Postman tests should be added to the collection
- More TDD should be followed
- Naming convention should be improved and follow the company policy
- Log should be added where required.
- Swagger should be used for the API Document
- Car Model Information is not added as Requirements more discussion is required for this.
- Pom file check required and remove the unwanted dependency if required and update version where required
    - Version mostly should be added on the top of the pom file that checks required.
        
#### Comments from Reviewer