@API @API-Get
Feature: Testing API Get request endpoints

  @TC-API001
  Scenario: All todos should be retrieved as a list
    When I send a get request
    Then I should be able to retrieve all todos
    And both completed and not completed todos must be retrieved

  @TC-API002
  Scenario: A specific todo should be retrieved by it's todo id
    When I send a get request with todo id 100
    Then I should be able to retrieve todo with id 100

  @TC-API003
  Scenario: A specific todo should be retrieved by it's user id
    When I send a get request with user id 2
    Then I should be able to retrieve todo with user id 2

  @TC-API004
  Scenario Outline: A specific todo should be retrieved by it's completed status
    When I send a get request with user completed status "<status>"
    Then I should be able to retrieve todos with status "<status>"
    Examples:
    |status|
    |      true|
    |      false|

  @TC-API005
  Scenario Outline: A specific todo should be retrieved by it's title
    When I send a get request with user completed title "<title>"
    Then I should be able to retrieve todos with title "<title>"
    Examples:
      |title|
      |accusamus eos facilis sint et aut voluptatem|
      |delectus aut autem|