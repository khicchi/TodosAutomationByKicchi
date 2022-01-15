package net.kicchi.todos.step_definitions.api;

import io.restassured.response.Response;
import net.kicchi.todos.modals.ToDo;
import net.kicchi.todos.utils.ConfigurationReaderUtil;
import org.junit.Assert;

import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class APIBase {
    protected final String baseAPIUrl = Objects.requireNonNull(ConfigurationReaderUtil.getConfiguration())
            .getApiBaseURL();

    protected List<ToDo> toDoList;
    protected Response response;
    protected ToDo singleToDo;
    protected ToDo newToDo;

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
        singleToDo = toDoList.get(0);
    }
}
