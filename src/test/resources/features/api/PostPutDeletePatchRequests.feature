@API @API-3PD
Feature: Testing API Post/Put/Patch/Delete request endpoints

  @TC-API006 @wip
  Scenario: One can save a todo
    Given a new todo with user id 1, title "demo meeting presentation"
    When todo is sent to api with post request
    Then the new todo should be created and returned with todo id