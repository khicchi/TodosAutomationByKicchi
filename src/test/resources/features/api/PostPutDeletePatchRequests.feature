@API @API-3PD
Feature: Testing API Post/Put/Patch/Delete request endpoints

  @TC-API006
  Scenario: One can save a todo
    Given a new todo with user id 1, title "demo meeting presentation"
    When todo is sent to api with post request
    Then the new todo should be created and returned with todo id

  @TC-API007
  Scenario: One can update a todo
    Given a todo with id 20 is fetched from api
    And todo's title is updated with "check jira sprint board"
    When updated todo is sent to api with put request
    Then the updated todo should be retrieved

  @TC-API008
  Scenario Outline: One can update a part of todo
    When a todo with "<id>" updated "<field>" with "<newValue>" is sent to api with patch request
    Then the updated todo should be retrieved with "<field>" as "<newValue>"
  Examples:
    |id|field|newValue|
    | 21 |userId     |4        |
    | 31 |title     | check the bug one more time       |
    | 41 |completed     |  true      |
    | 51 |completed     |  false      |

  @TC-API009 @wip
  Scenario: One can delete a todo
    When one send a del request to api for the todo with id 75
    Then the response should be empty
    And the response status must be successful