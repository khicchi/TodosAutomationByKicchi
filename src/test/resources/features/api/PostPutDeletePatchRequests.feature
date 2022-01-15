@API @API-3PD
Feature: Testing API Post/Put/Patch/Delete request endpoints

  @TC-API006
  Scenario: One can save a todo
    Given a new todo with user id 1, title "demo meeting presentation"
    When todo is sent to api with post request
    Then the new todo should be created and returned with todo id

  @TC-API007 @wip
  Scenario: One can update a todo
    Given I send a get request as todo id 20
    And I updated it's title to "check sprint board"
    When I send updated todo to api with put request
    Then I should get the updated todo as response