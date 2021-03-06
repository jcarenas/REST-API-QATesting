Feature: PetShop CRUD
  Exercise to perform CRUD operations on the petStore

  Background:
    Given baseUri is https://petstore.swagger.io/v2/

  # Get "available" pets. Assert expected result
  Scenario Outline: Get "available" pets - Assert expected result
    When I set Content-Type header to application/json
    And I set the path to <path>
    And I set method to GET
    And I execute the request
    Then the response code is 200
    And the response body should be valid json
    And the response header Content-Type should be application/json
    And response json path element available must be positive

    Examples:
      | path             |
      | /store/inventory |

  # Post a new available pet to the store. Assert new pet added.
  Scenario Outline: Add new Pet - Assert new pet added
    When I set Content-Type header to application/json
    And I set {"category": {"id": 0,"name": "string"},"name": "Ada","photoUrls": ["string"],"tags": [{"id": 0,"name": "string"}],"status": "available"} to body
    And I set the path to <path>
    And I set method to POST
    And I execute the request
    Then the response code is 200
    And response body should be valid json
    And I save <bodyJsonPath> in SerenityBDD <sessionKey> session
    And the response header Content-Type should be application/json
    And The value for the "<key>" after put operation should be "<value>"
    And response json path element status should be available

    Examples:
      | path | key  | value | bodyJsonPath | sessionKey |
      | /pet | name | Ada   | id           | petId      |

  # Update this pet status to "sold". Assert status updated.
  Scenario Outline: Update pet status to "sold" - Assert status updated
    When I set Content-Type header to application/json
    And I set {"category": {"id": 0,"name": "string"},"name": "Ada","photoUrls": ["string"],"tags": [{"id": 0,"name": "string"}],"status": "sold"} to body
    And I set the path to <path>
    And I set method to PUT
    And I execute the request
    Then the response code is 200
    And response body should be valid json
    And I save <bodyJsonPath> in SerenityBDD <sessionKey> session
    And the response header Content-Type should be application/json
    And The value for the "<key>" after put operation should be "<value>"
    And response json path element status should be sold

    Examples:
      | path | key  | value | bodyJsonPath | sessionKey   |
      | /pet | name | Ada   | id           | petUpdatedId |

  # Delete this pet. Assert deletion.
  Scenario Outline: Delete this pet - Assert deletion
    When I set Content-Type header to application/json
    And I set the path to <path>
    And I set method to DELETE element with pathParameter <sessionKey> and session id key <sessionKey>
    And I execute the request
    Then the response code is 200
    And response body should be valid json
    And the response header Content-Type should be application/json
    And the response with json path element <elementKey> should be present in session variable <sessionKey>

    Examples:
      | path         | elementKey | sessionKey |
      | /pet/{petId} | message    | petId      |


  # Delete pet record updated. Assert deletion.
  Scenario Outline: Delete pet updated - Assert deletion
    When I set Content-Type header to application/json
    And I set the path to <path>
    And I set method to DELETE element with pathParameter <pathParameter> and session id key <sessionKey>
    And I execute the request
    Then the response code is 200
    And response body should be valid json
    And the response header Content-Type should be application/json
    And the response with json path element <elementKey> should be present in session variable <sessionKey>

    Examples:
      | path         | elementKey | sessionKey   | pathParameter |
      | /pet/{petId} | message    | petUpdatedId | petId         |