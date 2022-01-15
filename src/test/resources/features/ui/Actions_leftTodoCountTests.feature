@UI @UI-Actions
Feature: Marking todos as completed or uncompleted, and updating the count of left todos

  Background: User should be on the main page
    Given user is on the main todos page

  @TC-UI007
  Scenario: User should not see the left item text and All/Active/Completed filters for 0 todos
    Then user should not see left item count and filter panel

  @TC-UI008
  Scenario: User should see the number of left items on each todo addition
    When user clicks on the new todo text box
    And user creates "buy a present to your son" as a new todo
    Then user should see 1 as left item count
    And user creates "do not forget car insurance" as a new todo
    Then user should see 2 as left item count

  @TC-UI09
  Scenario: User should see the number of left items on each todo deletion
    When user clicks on the new todo text box
    And user creates "buy a present to your son" as a new todo
    And user creates "do not forget car insurance" as a new todo
    Then user should see 2 as left item count
    And user deletes following todos
      |buy a present to your son|
    Then user should see 1 as left item count
    And user deletes following todos
      |do not forget car insurance|
    Then user should not see left item count and filter panel

  @TC-UI010
  Scenario Outline: User should see the number of left items on each todo completion
    When user clicks on the new todo text box
    And user creates "<firstTodo>" as a new todo
    Then user should see 1 as left item count
    And user checks completed checkbox of the "<firstTodo>"
    Then user should see 0 as left item count
    And user creates "<secondTodo>" as a new todo
    And user creates "<thirdTodo>" as a new todo
    And user creates "<fourthTodo>" as a new todo
    Then user should see 3 as left item count
    And user checks completed checkbox of the "<thirdTodo>"
    Then user should see 2 as left item count
    And user checks completed checkbox of the "<secondTodo>"
    Then user should see 1 as left item count
    Examples:
    |firstTodo|secondTodo|thirdTodo|fourthTodo|
    |buy a present to your son|connect to recruiter|attend IT webinar|finish the task|

