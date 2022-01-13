package net.kicchi.todos.step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.kicchi.todos.exception.AutomationException;
import net.kicchi.todos.pages.ToDosPage;
import net.kicchi.todos.utils.ConfigurationReaderUtil;
import net.kicchi.todos.utils.DriverUtil;
import org.openqa.selenium.Keys;

import static org.assertj.core.api.Assertions.*;

public class UIStepDefinitions {

    ToDosPage toDosPage;

    @Given("user is on the main todos page")
    public void user_is_on_the_main_todos_page() {
        //open the main page of todos mvc project
        DriverUtil.getDriver().get(ConfigurationReaderUtil.getConfiguration().getMainPageUrl());
        toDosPage = new ToDosPage();
    }
    @When("user clicks on the new todo text box")
    public void user_clicks_on_the_new_todo_text_box() {
        toDosPage.getNewTodoTitleTextBox().click();
    }

    @And("user writes {string} as a new todo")
    public void userWritesAsANewTodo(String newTodoTitle) {
        toDosPage.getNewTodoTitleTextBox().sendKeys(newTodoTitle);
    }

    @And("user presses enter key")
    public void userPressesEnterKey() {
        toDosPage.getNewTodoTitleTextBox().sendKeys(Keys.ENTER);
    }

    @Then("user should see {string} as newly created todo")
    public void userShouldSeeAsNewlyCreatedTodo(String todoTitleToCheckIfExists) throws AutomationException {
        assertThat(toDosPage.getTodoFromList(todoTitleToCheckIfExists)).isNotNull();
    }

    @And("user clicks on the cross icon at the right-end of the {string}")
    public void userClicksOnTheCrossIconAtTheRightEndOfThe(String todoTitleToDelete) {

    }

    @Then("user should not see the {string}")
    public void userShouldNotSeeThe(String todoTitleToCheckIfDeleted) {

    }
}
