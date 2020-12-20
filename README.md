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
    * Validations: available in feature file and checking SerenityBDD report
    
- Scenario Outline: Add new Pet - Assert new pet added
    * Endpoint: (POST) /pet (new pet passed as a json in the body)
    * Validations: available in feature file and checking SerenityBDD report
    * At this point, petId generated is saved in session as petId. This will be needed at the deletion moment.
    
- Scenario Outline: Update pet status to "sold" - Assert status updated
    * Endpoint: (PUT) /pet (pet updated passed as a json in the body)
    * Validations: available in feature file and checking SerenityBDD report
    * At this point, petId for updated register is saved in session as well as petUpdatedId. This will be needed at the deletion moment.
    
- Scenario Outline: Delete this pet - Assert deletion
    * Endpoint: (DELETE) /pet/{petId}
    * Validations: available in feature file and checking SerenityBDD report
        
- Scenario Outline: Delete pet updated - Assert deletion
    * Endpoint: (DELETE) /pet/{petId}
    * Validations: available in feature file and checking SerenityBDD report
    
## Aspects taken into account
##### Structure
SerenityBDD structure
##### Maintainability
Refactored and simple code
##### Re-usability
Parameterized methods, objects and properties.
##### Code quality
Static code analysis
##### Reliability
Based on validations
##### Logging and evidences management
Assertion messages, SerenityBDD report and console detailed messages