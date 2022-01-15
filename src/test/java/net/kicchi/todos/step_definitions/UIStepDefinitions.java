package net.kicchi.todos.step_definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import net.kicchi.todos.pages.ToDosPage;
import net.kicchi.todos.utils.BrowserUtil;
import net.kicchi.todos.utils.ConfigurationReaderUtil;
import net.kicchi.todos.utils.DriverUtil;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@Log4j2
public class UIStepDefinitions {

    private ToDosPage toDosPage;
    private DataTable multipleToDoTitlesForCreation, multipleToDoTitlesForDeletion;
    WebElement todoToEdit;

    @Given("user is on the main todos page")
    public void user_is_on_the_main_todos_page() {
        //open the main page of todos mvc project
        log.info("I am on the page");
        DriverUtil.getDriver().get(Objects.requireNonNull(ConfigurationReaderUtil.getConfiguration())
                                                                                .getMainPageUrl());
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
        BrowserUtil.turnOffImplicitWaits();
        multipleToDoTitlesForDeletion.asList().forEach(this::userShouldNotSeeThe);
        BrowserUtil.turnOnImplicitWaits();
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
    }

    @Then("user should see {int} as left item count")
    public void userShouldSeeAsLeftItemCount(int leftItemCountExpected) {
        Assert.assertEquals("Left item count did not match",
                leftItemCountExpected, toDosPage.getLeftItemCount());
    }

    @Then("user should not see left item count and filter panel")
    public void userShouldNotSeeLeftItemCountAndFilterPanel() {
        BrowserUtil.turnOffImplicitWaits();
        Assert.assertThrows(NoSuchElementException.class,
                () -> System.out.println(toDosPage.getLeftItemCount()));
        Assert.assertThrows(NoSuchElementException.class,
                () -> System.out.println(toDosPage.getFilterElementActive().getText()));
        BrowserUtil.turnOnImplicitWaits();
    }


    @And("user creates {string} as a new todo")
    public void userCreatesAsANewTodo(String newTodoTitle) {
        userWritesAsANewTodo(newTodoTitle);
        toDosPage.getNewTodoTitleTextBox().sendKeys(Keys.ENTER);
    }

    @And("user checks completed checkbox of the {string}")
    public void userChecksCompletedCheckboxOfThe(String todoTitleToComplete) {
        toDosPage.getCompleteCheckBoxOfToDo(todoTitleToComplete).click();
    }

    @And("user navigates to the {string} tab")
    public void userNavigatesToTheTab(String tabNameToNavigate) {
        toDosPage.navigateToTab(tabNameToNavigate);
    }

    @Then("user should see the {string} in Completed tab")
    public void userShouldSeeTheInCompletedTab(String todoTitleToCheck) {
        assertThat(toDosPage.doesCompletedTodoExist(todoTitleToCheck)).isTrue();
    }

    @Then("user should not see the {string} in Active tab")
    public void userShouldNotSeeTheInActiveTab(String todoTitleToCheck) {
        List<String> activeTodoTitles = BrowserUtil.getElementsText(toDosPage.getActiveTodoElements());
        assertThat(activeTodoTitles.contains(todoTitleToCheck)).isFalse();
    }

    @Then("user should see the following todos in the All tab")
    public void userShouldSeeTheFollowingTodosInTheAllTab(List<String> todoTitlesToCheck) {
        List<String> activeTodoTitles = BrowserUtil.getElementsText(toDosPage.getAllTodoElements());
        for (String expectedTitle:todoTitlesToCheck) {
            if (!activeTodoTitles.contains(expectedTitle))
                Assert.fail(expectedTitle + " can not be found in the All tab");
        }
    }

    @Then("user should not see Clear Completed button")
    public void userShouldNotSeeClearCompletedButton() {
        BrowserUtil.turnOffImplicitWaits();
        assertThat(toDosPage.getClearCompletedButtons().size()).isEqualTo(0);
        BrowserUtil.turnOnImplicitWaits();
    }

    @Then("user should see Clear Completed button")
    public void userShouldSeeClearCompletedButton() {
        assertThat(toDosPage.getClearCompletedButtons().size()).isEqualTo(1);
    }

    @And("user unchecks completed checkbox of the {string}")
    public void userUnchecksCompletedCheckboxOfThe(String todoTitleToUncheck) {
        toDosPage.getCompleteCheckBoxOfToDo(todoTitleToUncheck).click();
    }

    @Then("user clicks on the Clear Completed button")
    public void userClicksOnTheClearCompletedButton() {
        toDosPage.getClearCompletedButton().click();
    }
}
