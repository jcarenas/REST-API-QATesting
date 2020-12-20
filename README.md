# REST-API-QATesting
QA Testing exercise using RestAssured and SerenityBDD

DEMO PET STORE API automation.

Based on seed-tas-serenitybdd-be-restapi

## Getting Started

### Prerequisites

Any software to clone this repository (e.g. SourceTree, Git installed locally...)

**NOTE:** Gradle local installation is not needed because is integrated in the project with the Wrapper.

### Installing

Clone this repository

## Running the tests
**From terminal using gradle**
```
gradlew clean test aggregate
```

**Running Cucumber with Serenity feature files directly from IntelliJ**

Details Here: https://johnfergusonsmart.com/running-cucumber-serenity-feature-files-directly-intellij/

![debug configuration](resources/images/00_Cucumber_Java_Run_Debug_Configuration.PNG)<br/>



## Content and features
- serenity.properties
```
# To share data between scenarios. In other case, session is reset each time. Default value: false
serenity.maintain.session = true
```

### Scenarios description
- Scenario Outline: Get "available" pets - Assert expected result
    * Endpoint: (GET) /pet/findByStatus where status is "available"
    * Validations:
        * the response code is 200
        * the response body should be valid json
        * the response header "Content-Type" should be "application/json"
        * response json path element "available" must be positive
    
- Scenario Outline: Add new Pet - Assert new pet added
    * Enpoint: (POST) /pet (new pet passed as a json in the body)
    * Validations:
        * the response code is 200
        * response body should be valid json
        * the response header "Content-Type" should be "application/json"
        * The value for the "name" after put operation should be "Ada"
        * response json path element status should be available
    * At this point, petId generated is saved in session. This will be needed at the deletion moment.
    
- Scenario Outline: Update pet status to "sold" - Assert status updated
    * Endpoint: (PUT) /pet (pet updated passed as a json in the body)
    * Validations:
        * the response code is 200
        * response body should be valid json
        * the response header "Content-Type" should be "application/json"
        * The value for the "name" after put operation should be "Ada"
        * response json path element status should be sold
        
- Scenario Outline: Delete this pet - Assert deletion
    * Endpoint: (DELETE) /pet/{petId}
    * Validations:
        * the response code is 200
        * response body should be valid json
        * the response header "Content-Type" should be "application/json"
        * the response with json path element "message" should be present in session variable "petId"
        

  