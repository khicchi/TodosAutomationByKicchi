@UI @UI-CRUD
Feature: CRUD operations on one or multiple todos

  Background: User should be on the main page
    Given user is on the main todos page

  @TC-UI001 @Smoke
  Scenario: User should be able to create a new todo
    When user clicks on the new todo text box
    And user writes "homework must be done" as a new todo
    And user presses enter key
    Then user should see "homework must be done" in todo list

  @TC-UI002 @Smoke
  Scenario: User should be able to delete a todo
    When user clicks on the new todo text box
    * user writes "homework must be done" as a new todo
    * user presses enter key
    And user clicks on the cross icon at the right-end of the "homework must be done"
    Then user should not see the "homework must be done"

  @TC-UI003
  Scenario: User should be able to create multiple todos
    When user creates following todos
      | maths exam reminder |
      | go to dentist       |
      | take the pills      |
    Then user should see all created todos on the todo list

  @TC-UI004 @Regression
  Scenario: User should be able to delete multiple todos sequentially
    When user creates following todos
      | maths exam reminder |
      | go to dentist       |
      | take the pills      |
    And user deletes following todos
      | maths exam reminder |
      | go to dentist       |
    Then user should not see the deleted todos

  @TC-UI005 @Smoke
  Scenario Outline: User should be able to edit a todo
    When user clicks on the new todo text box
    And user writes "<new_title>" as a new todo
    * user presses enter key
    * user double clicks over the "<new_title>" todo
    And user changes "<new_title>" title to "<updated_title>"
    And user presses enter key on edit box
    Then user should see "<updated_title>" in todo list
    Examples:
      | new_title             | updated_title         |
      | homework must be done | arrange video meeting |

  @TC-UI006 @Regression
  Scenario: User should be able to edit multiple todos sequentially
    When user creates following todos
      | maths exam reminder |
      | go to dentist       |
      | take the pills      |
    And user edits following todos
      | maths exam reminder | basketball match at 20 PM |
      | go to dentist | go to hospital |
    Then user should see following todos in todo list
      | basketball match at 20 PM |
      | go to hospital            |
      | take the pills            |
