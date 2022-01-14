@UI @UI-Actions
Feature: Accessing the todos by their completion or active status
  by clicking the All, Active and Completed tabs

  Background: User should be on the main page
    Given user is on the main todos page

  @TC-UI11 @Smoke
  Scenario: User should see the completed todo in the "Completed" tab
    And user creates "update regression suite" as a new todo
    When user checks completed checkbox of the "update regression suite"
    And user navigates to the "Completed" tab
    Then user should see the "update regression suite" in Completed tab

  @TC-UI12
  Scenario: User should not see the completed todo in the "Active" tab
    And user creates "update regression suite" as a new todo
    When user checks completed checkbox of the "update regression suite"
    And user navigates to the "Active" tab
    Then user should not see the "update regression suite" in Active tab

  @TC-UI13 @Regression
  Scenario: User should be able to see both completed and not completed todos in the "All" tab
    And user creates "update regression suite" as a new todo
    And user creates "reschedule scrum grooming meeting" as a new todo
    And user checks completed checkbox of the "update regression suite"
    Then user should see the following todos in the All tab
    |update regression suite          |
    |reschedule scrum grooming meeting|
    And user navigates to the "Active" tab
    And user navigates to the "All" tab
    Then user should see the following todos in the All tab
      |update regression suite          |
      |reschedule scrum grooming meeting|
    And user navigates to the "Completed" tab
    And user navigates to the "All" tab
    Then user should see the following todos in the All tab
      |update regression suite          |
      |reschedule scrum grooming meeting|
    And user checks completed checkbox of the "reschedule scrum grooming meeting"
    Then user should see the following todos in the All tab
      |update regression suite          |
      |reschedule scrum grooming meeting|