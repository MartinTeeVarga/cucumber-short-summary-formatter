@Coffee
Feature: As a user, I want to drink coffee

  Background: Coffee Shop
    Given the coffee shop is open

  @LazyDev
  @Cheapskate
  Scenario: My boss goes on coffee run
    Given the boss is around
    When the boss buys me a coffee
    Then I will drink coffee


  @ActiveDev
  Scenario: I go buy my own coffee
    Given I have a break
    When I buy my own coffee
    Then I will drink coffee

  @Cheapskate
  Scenario Outline: Someone else goes for a coffee
    Given "<colleague>" is around
    When "<colleague>" buys coffee
    Then I will drink coffee
    Examples:
      | colleague |
      | Spike     |
      | Faye      |