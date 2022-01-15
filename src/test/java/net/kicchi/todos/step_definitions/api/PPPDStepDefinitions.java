package net.kicchi.todos.step_definitions.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.kicchi.todos.modals.ToDo;
import net.kicchi.todos.utils.ConfigurationReaderUtil;
import org.junit.Assert;

import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PPPDStepDefinitions {

    protected final String baseAPIUrl = Objects.requireNonNull(ConfigurationReaderUtil.getConfiguration())
            .getApiBaseURL();

    protected Response response;
    protected ToDo singleToDoFromAPI, newToDo, updatedToDo;

    @Given("a new todo with user id {int}, title {string}")
    public void aNewTodoWithUserIdTitle(int userId, String title) {
        newToDo = new ToDo(title, userId);
    }

    @When("todo is sent to api with post request")
    public void todoIsSentToApiWithPostRequest() {
        response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(newToDo)
                .when()
                .post(baseAPIUrl);

        assertThat(response.statusCode()).isEqualTo(201);
        assertContentTypeJson();
    }

    @Then("the new todo should be created and returned with todo id")
    public void theNewTodoShouldBeCreatedAndReturnedWithTodoId() {
        singleToDoFromAPI = response.as(ToDo.class);
        assertThat(singleToDoFromAPI.getId()).isNotZero();
        assertThat(singleToDoFromAPI.isCompleted()).isEqualTo(newToDo.isCompleted());
        assertThat(singleToDoFromAPI.getTitle()).isEqualTo(newToDo.getTitle());
        assertThat(singleToDoFromAPI.getUserId()).isEqualTo(newToDo.getUserId());
    }

    @And("I updated it's title to {string}")
    public void iUpdatedItSTitleTo(String newTitle) {
        singleToDoFromAPI.setTitle(newTitle);
        updatedToDo = singleToDoFromAPI;
    }

    @When("I send updated todo to api with put request")
    public void iSendUpdatedTodoToApiWithPutRequest() {
        response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(updatedToDo)
                .when()
                .put(baseAPIUrl);

        assertThat(response.statusCode()).isEqualTo(200);
        assertContentTypeJson();
    }

    @Then("I should get the updated todo as response")
    public void iShouldGetTheUpdatedTodoAsResponse() {
        singleToDoFromAPI = response.body().as(ToDo.class);
        assertThat(singleToDoFromAPI.getTitle()).isEqualTo(updatedToDo.getTitle());
    }

    @Given("I send a get request as todo id {int}")
    public void iSendAGetRequestAsTodoId(int todoId) {

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
}
