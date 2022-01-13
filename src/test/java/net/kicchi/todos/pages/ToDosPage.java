package net.kicchi.todos.pages;


import lombok.Getter;
import net.kicchi.todos.exception.AutomationException;
import net.kicchi.todos.utils.DriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class ToDosPage {

    public ToDosPage(){
        PageFactory.initElements(DriverUtil.getDriver(), this);
    }

    @FindBy(className = "new-todo")
    private WebElement newTodoTitleTextBox;

    @FindBy(css = ".todo-list li")
    private List<WebElement> allToDosAsList;

    public WebElement getTodoFromList(String todoTitle) throws AutomationException {
        try{
            return DriverUtil.getDriver().findElement(By.xpath("//ul[@class='todo-list']//li[.='" +
                    todoTitle + "']"));
        }catch (NoSuchElementException e){
            throw new AutomationException("There is no todo with '" + todoTitle + "' title.");
        }
    }
}
