package net.kicchi.todos.step_definitions.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.kicchi.todos.modals.ToDo;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PPPDStepDefinitions extends APIBase {

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

    @Given("a todo with id {int} is fetched from api")
    public void aTodoWithIdIsFetchedFromApi(int todoID) {
        getTodosByField("id", todoID);
    }

    @And("todo's title is updated with {string}")
    public void todoSTitleIsUpdatedWith(String todoTitleToUpdate) {
        singleToDoFromAPI.setTitle(todoTitleToUpdate);
        updatedToDo = singleToDoFromAPI;
    }

    @When("updated todo is sent to api with put request")
    public void updatedTodoIsSentToApiWithPutRequest() {
        response = given().log().all()
                .body(updatedToDo)
                .when().log().all().put(baseAPIUrl + "/" + updatedToDo.getId());
    }

    @Then("the updated todo should be retrieved")
    public void theUpdatedTodoShouldBeRetrieved() {
        assertStatus200();
        assertContentTypeJson();
        assertThat(singleToDoFromAPI.getTitle()).isEqualTo(updatedToDo.getTitle());
    }

    @When("a todo with {string} updated {string} with {string} is sent to api with patch request")
    public void aTodoWithIdUpdatedWithIsSentToApiWithPatchRequest(String todoId, String fieldName, String newValue) {
        String patchValue = "";
        if (fieldName.equals("userId"))
            patchValue = "{ \"userId\": " + newValue + "}";
        else if (fieldName.equals("title"))
            patchValue = "{ \"title\": \"" + newValue + "\"}";
        else if (fieldName.equals("completed"))
            patchValue = "{ \"completed\": " + newValue + "}";

        response =  given().contentType(ContentType.JSON)
                .and().body(patchValue)
                .when().patch(baseAPIUrl + "/" + todoId);
    }

    @Then("the updated todo should be retrieved with {string} as {string}")
    public void theUpdatedTodoShouldBeRetrievedWithAs(String fieldName, String updatedValue) {
        singleToDoFromAPI = response.body().as(ToDo.class);
        switch (fieldName){
            case "userId":
                assertThat(singleToDoFromAPI.getUserId()).isEqualTo(Integer.parseInt(updatedValue));
                break;
            case "title":
                assertThat(singleToDoFromAPI.getTitle()).isEqualTo(updatedValue);
                break;
            case "completed":
                assertThat(singleToDoFromAPI.isCompleted()).isEqualTo(Boolean.parseBoolean(updatedValue));
                break;
        }
    }

    @When("one send a del request to api for the todo with id {int}")
    public void oneSendADelRequestToApiForTheTodoWithId(int todoIdToDelete) {
        response = given().accept(ContentType.JSON)
                .when().delete(baseAPIUrl + "/" + todoIdToDelete);
    }


    @Then("the response should be empty")
    public void theResponseShouldBeEmpty() {
        assertThat(response.body().toString()).doesNotContain("id");
    }

    @And("the response status must be successful")
    public void theResponseStatusMustBeSuccessful() {
        assertStatus200();
    }
}
