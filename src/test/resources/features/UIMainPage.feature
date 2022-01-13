@UI
Feature: User should create, update, delete, complete, undo complete one or multiple todos

  Background: User should be on the main page
    Given user is on the main todos page

  Scenario: User should be able to create a new todo
    When user clicks on the new todo text box
    And user writes "homework must be done" as a new todo
    And user presses enter key
    Then user should see "homework must be done" as newly created todo

  Scenario: User should be able to delete a todo
    When user clicks on the new todo text box
    * user writes "homework must be done" as a new todo
    * user presses enter key
    And user clicks on the cross icon at the right-end of the "homework must be done"
    Then user should not see the "homework must be done"