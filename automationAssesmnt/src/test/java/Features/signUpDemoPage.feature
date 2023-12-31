Feature: Signup Page

  @validCred
  Scenario: Successful signup
    Given I am on the signup page
    When I enter valid credentials
    And I submit the signup form
    Then I should see a success message

  @ExistingCred
  Scenario: Signup with existing email
    Given I am on the signup page
    When I enter an existing email
    And I submit the signup form
    Then I should see an error message

  @passMismatch
  Scenario: Password mismatch
    Given I am on the signup page
    When I enter valid credentials
    And the password and confirm password fields do not match
    And I submit the signup form
    Then I should see an error message

  @invalEmailForm
  Scenario: Invalid email format
    Given I am on the signup page
    When I enter an invalid email format
    And I submit the signup form
    Then I should see an error message
