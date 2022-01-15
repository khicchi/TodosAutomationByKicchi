@UI @UI-Actions
Feature: 'Clear Completed' button functionality

  Background: User should be on the main page
    Given user is on the main todos page

  @TC-UI21
  Scenario: User should not see 'Clear Completed' button unless there is a completed todo
    When user creates "review testers code" as a new todo
    Then user should not see Clear Completed button
    And user creates "arrange a kt session for new mentors" as a new todo
    Then user should not see Clear Completed button

  @TC-UI22
  Scenario: User should see 'Clear Completed' button if there is at least one completed todo
    When user creates "review testers code" as a new todo
    Then user should not see Clear Completed button
    And user checks completed checkbox of the "review testers code"
    Then user should see Clear Completed button
    And user creates "arrange a kt session for new mentors" as a new todo
    Then user should see Clear Completed button

  @TC-UI23 @Regression
  Scenario: User should not see 'Clear Completed' button after undoing all the completed todos
    When user creates "review testers code" as a new todo
    And user checks completed checkbox of the "review testers code"
    And user creates "arrange a kt session for new mentors" as a new todo
    And user checks completed checkbox of the "arrange a kt session for new mentors"
    And user navigates to the "Completed" tab
    And user unchecks completed checkbox of the "review testers code"
    Then user should see Clear Completed button
    And user unchecks completed checkbox of the "arrange a kt session for new mentors"
    Then user should not see Clear Completed button

  @TC-UI24
  Scenario: User should not see 'Clear Completed' button after clicking it
    When user creates "review testers code" as a new todo
    And user checks completed checkbox of the "review testers code"
    And user clicks on the Clear Completed button
    Then user should not see Clear Completed button