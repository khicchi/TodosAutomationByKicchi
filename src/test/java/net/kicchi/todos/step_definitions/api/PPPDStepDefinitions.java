package net.kicchi.todos.step_definitions.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
        singleToDo = response.as(ToDo.class);
        assertThat(singleToDo.getId()).isNotZero();
        assertThat(singleToDo.isCompleted()).isEqualTo(newToDo.isCompleted());
        assertThat(singleToDo.getTitle()).isEqualTo(newToDo.getTitle());
        assertThat(singleToDo.getUserId()).isEqualTo(newToDo.getUserId());
    }
}
