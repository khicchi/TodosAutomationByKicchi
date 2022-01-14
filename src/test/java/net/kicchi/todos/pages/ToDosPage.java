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

    @FindBy(css = ".todo-count strong")
    private WebElement leftItemCountElement;

    @FindBy(xpath = "//ul[@class='filters']/li/a[.='All']")
    private WebElement filterElementAll;

    @FindBy(xpath = "//ul[@class='filters']/li/a[.='Active']")
    private WebElement filterElementActive;

    @FindBy(xpath = "//ul[@class='filters']/li/a[.='Completed']")
    private WebElement filterElementCompleted;

    @FindBy(css = "ul.todo-list li.completed")
    private List<WebElement> completedTodoElements;

    @FindBy(xpath = "//ul[@class='todo-list']//li[@class='']//label")
    private List<WebElement> activeTodoElements;

    @FindBy(xpath = "//ul[@class='todo-list']//li/div/label")
    private List<WebElement> allTodoElements;

    @FindBy(className = "clear-completed")
    private WebElement clearCompletedButton;

    @FindBy(className = "clear-completed")
    private List<WebElement> clearCompletedButtons;

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

    public WebElement getCompleteCheckBoxOfToDo(String todoToEdit){
        return DriverUtil.getDriver().findElement(By.xpath(todoListContainerXPathLocator+
                "//li[.='" + todoToEdit + "']/div/input"));
    }

    public int getLeftItemCount(){
        return Integer.parseInt(leftItemCountElement.getText());
    }

    public boolean doesCompletedTodoExist(String completedTodoTitle){
        for (WebElement webElement : completedTodoElements) {
            List<WebElement> foundElement = webElement.
                    findElements(By.xpath("/div//label[.='" + completedTodoTitle + "']"));
            if(foundElement != null)
                return true;
        }
        return false;
    }

    public void navigateToTab(String tabNameToNavigate){
        switch (tabNameToNavigate){
            case "All":
                filterElementAll.click();
                break;
            case "Active":
                filterElementActive.click();
                break;
            case "Completed":
                filterElementCompleted.click();
                break;
        }
    }



}
