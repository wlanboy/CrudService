Feature: Create hello

  Scenario: create hello object

    Given I call POST "http://localhost:8002/hello" with the following attributes
      | identifier  | target | status |
      |             | test   | start  |

    Then The response status should be 200
    And The response should contain "target" with value "test"
    And The response should not contain "status" with value "start"