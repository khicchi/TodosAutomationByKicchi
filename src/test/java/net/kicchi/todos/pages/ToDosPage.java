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

    private static final String todoListContainerXPathLocator = "//ul[@class='todo-list']";

    public ToDosPage(){
        PageFactory.initElements(DriverUtil.getDriver(), this);
    }

    @FindBy(className = "new-todo")
    private WebElement newTodoTitleTextBox;

    @FindBy(css = ".todo-list li")
    private List<WebElement> allToDosAsList;

    public WebElement getTodoFromList(String todoTitle) {
        return DriverUtil.getDriver().findElement(By.xpath(todoListContainerXPathLocator +
                "//li[.='" + todoTitle + "']"));
    }

    public WebElement getDestroyButtonOfToDo(String todoTitle) {
        return DriverUtil.getDriver().findElement(By.xpath(todoListContainerXPathLocator +
                "//li[.='" + todoTitle + "']//*[@class='destroy']"));
    }

    public WebElement getEditBoxOfToDo(String todoToEdit){
        return DriverUtil.getDriver().findElement(By.xpath(todoListContainerXPathLocator+
                "//li[.='" + todoToEdit + "']//input[@class='edit']"));
    }
}
