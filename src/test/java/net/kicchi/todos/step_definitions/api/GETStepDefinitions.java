package net.kicchi.todos.step_definitions.api;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.kicchi.todos.modals.ToDo;
import net.kicchi.todos.utils.ConfigurationReaderUtil;
import org.junit.Assert;

import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

public class GETStepDefinitions {

    protected final String baseAPIUrl = Objects.requireNonNull(ConfigurationReaderUtil.getConfiguration())
            .getApiBaseURL();

    protected List<ToDo> toDoList;
    protected Response response;
    protected ToDo singleToDoFromAPI, newToDo;

    protected void assertStatus200(){
        assertThat(response.statusCode()).isEqualTo(200);
    }

    protected void assertContentTypeJson(){
        Assert.assertEquals("application/json; charset=utf-8", response.contentType());
    }

    protected void getTodosByField(String fieldName, Object parameter){
        response = given().queryParam(fieldName, parameter).when().get(baseAPIUrl);
        assertStatus200();
        assertContentTypeJson();
        toDoList = response.then().extract().body().jsonPath().getList(".", ToDo.class);
        assertThat(toDoList.size()).isNotZero();
        singleToDoFromAPI = toDoList.get(0);
    }

    @When("I send a get request with todo id {int}")
    public void iSendAGetRequestWithTodoId(int todoId) {
        getTodosByField("id", todoId);
    }

    @When("I send a get request")
    public void i_send_a_get_request() {
        response = when().get(baseAPIUrl);
        toDoList = response.then().extract().body().jsonPath().getList(".", ToDo.class);
        assertStatus200();
    }
    @Then("I should be able to retrieve all todos")
    public void i_should_be_able_to_retrieve_all_todos() {
        assertThat(toDoList.size()).isEqualTo(200);
    }
    @Then("both completed and not completed todos must be retrieved")
    public void both_completed_and_not_completed_todos_must_be_retrieved() {
        boolean doesCompletedTodoExist = false, doesNotCompletedTodoExist = false;

        doesCompletedTodoExist = toDoList.stream().anyMatch(ToDo::isCompleted);
        doesNotCompletedTodoExist = toDoList.stream().anyMatch(toDo -> !toDo.isCompleted());

        assertThat(doesCompletedTodoExist && doesNotCompletedTodoExist).isTrue();
    }

    @Then("I should be able to retrieve todo with id {int}")
    public void iShouldBeAbleToRetrieveTodoWithId(int todoId) {
        assertThat(singleToDoFromAPI).isNotNull();

        ToDo expectedToDo = new ToDo(100,"excepturi a et neque qui expedita vel voluptate",
                false, 5);
        assertThat(singleToDoFromAPI)
                .usingRecursiveComparison()
                .isEqualTo(expectedToDo);
    }

    @When("I send a get request with user id {int}")
    public void iSendAGetRequestWithUserId(int userId) {
        getTodosByField("userId", userId);
    }

    @Then("I should be able to retrieve todo with user id {int}")
    public void iShouldBeAbleToRetrieveTodoWithUserId(int userId) {
        assertThat(toDoList.stream().anyMatch(toDo -> toDo.getUserId() != userId)).isFalse();
    }

    @When("I send a get request with user completed status {string}")
    public void iSendAGetRequestWithUserCompletedStatus(String status) {
        getTodosByField("completed", status);
    }

    @Then("I should be able to retrieve todos with status {string}")
    public void iShouldBeAbleToRetrieveTodosWithStatus(String status) {
        assertThat(toDoList.stream().anyMatch(toDo -> toDo.isCompleted() != Boolean.parseBoolean(status))).isFalse();
    }


    @When("I send a get request with user completed title {string}")
    public void iSendAGetRequestWithUserCompletedTitle(String title) {
        getTodosByField("title", title);
    }

    @Then("I should be able to retrieve todos with title {string}")
    public void iShouldBeAbleToRetrieveTodosWithTitle(String title) {
        assertThat(toDoList.stream().anyMatch(toDo -> !toDo.getTitle().equals(title))).isFalse();
    }

}
