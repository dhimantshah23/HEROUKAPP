@RunAllScenarios
Feature: HEROKUAPP

  @getUsersbyCity
  Scenario Outline: Get Users by City
    When the request is performed using city "<city>"
    Then response should be 200
    And verify that the response includes the data for "<city>"

    Examples:
      | city    |
      | London   |

  @getAllUsers
  Scenario: Get Number of users
    When the request is performed to get all users
    Then response should be 200
    And the response does not include null id field

  @getAllUsers-within-50-miles-radius-of-london
  Scenario: Get users
    When the request is performed to get all users
    Then response should be 200
    And get all users within 50 miles radius of london

