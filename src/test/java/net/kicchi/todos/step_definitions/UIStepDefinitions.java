package net.kicchi.todos.step_definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.kicchi.todos.pages.ToDosPage;
import net.kicchi.todos.utils.ConfigurationReaderUtil;
import net.kicchi.todos.utils.DriverUtil;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class UIStepDefinitions {

    private ToDosPage toDosPage;
    private DataTable multipleToDoTitlesForCreation, multipleToDoTitlesForDeletion;
    WebElement todoToEdit;

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

    @Then("user should see {string} in todo list")
    public void userShouldSeeInTodoList(String todoTitleToCheckIfExists) {
        assertThat(toDosPage.getTodoFromList(todoTitleToCheckIfExists)).isNotNull();
    }

    @And("user clicks on the cross icon at the right-end of the {string}")
    public void userClicksOnTheCrossIconAtTheRightEndOfThe(String todoTitleToDelete) {
        new Actions(DriverUtil.getDriver())
                .moveToElement(toDosPage.getTodoFromList(todoTitleToDelete)).perform();
        toDosPage.getDestroyButtonOfToDo(todoTitleToDelete).click();
    }

    @Then("user should not see the {string}")
    public void userShouldNotSeeThe(String todoTitleToCheckIfDeleted) {
        Assert.assertThrows(NoSuchElementException.class,
                () -> toDosPage.getTodoFromList(todoTitleToCheckIfDeleted));
    }

    @When("user creates following todos")
    public void user_creates_following_todos(DataTable dataTable) {
        multipleToDoTitlesForCreation = dataTable;
        dataTable.asList().forEach(newToDoTitle -> {
            user_clicks_on_the_new_todo_text_box();
            userWritesAsANewTodo(newToDoTitle);
            userPressesEnterKey();
        });
    }
    @Then("user should see all created todos on the todo list")
    public void user_should_see_all_created_todos_on_the_todo_list() {
        multipleToDoTitlesForCreation.asList().forEach(this::userShouldSeeInTodoList);
    }

    @And("user deletes following todos")
    public void userDeletesFollowingTodos(DataTable dataTable) {
        multipleToDoTitlesForDeletion = dataTable;
        dataTable.asList().forEach(this::userClicksOnTheCrossIconAtTheRightEndOfThe);
    }

    @Then("user should not see the deleted todos")
    public void userShouldNotSeeTheDeletedTodos() {
        multipleToDoTitlesForDeletion.asList().forEach(this::userShouldNotSeeThe);
    }

    @And("user double clicks over the {string} todo")
    public void userDoubleClicksOverTheTodo(String todoTitleToEdit) {
        WebElement todoToEdit = toDosPage.getTodoFromList(todoTitleToEdit);
        Actions actions = new Actions(DriverUtil.getDriver());
        actions.moveToElement(todoToEdit).doubleClick().perform();
    }

    @And("user changes {string} title to {string}")
    public void userChangesTitleTo(String titleToFind, String titleToChange) {
        todoToEdit = toDosPage.getEditBoxOfToDo(titleToFind);

        for (int i = 0; i < titleToFind.length(); i++) {
            todoToEdit.sendKeys(Keys.BACK_SPACE);
        }

        todoToEdit.sendKeys(titleToChange);
    }

    @And("user presses enter key on edit box")
    public void userPressesEnterKeyOnEditBox() {
        todoToEdit.sendKeys(Keys.ENTER);
    }

    @And("user edits following todos")
    public void userEditsFollowingTodos(Map<String, String> oldUpdatedToDoPairs) {
        for (var pair:oldUpdatedToDoPairs.entrySet()) {
            userDoubleClicksOverTheTodo(pair.getKey());
            userChangesTitleTo(pair.getKey(), pair.getValue());
            userPressesEnterKeyOnEditBox();
        }
    }

    @Then("user should see following todos in todo list")
    public void userShouldSeeFollowingTodosInTodoList(List<String> titlesToCheck) {
        titlesToCheck.forEach(this::userShouldSeeInTodoList);
        try{
            Thread.sleep(3000);
        }
        catch (Exception e){

        }
    }
}
