package net.kicchi.todos.step_definitions.ui;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.kicchi.todos.utils.BrowserUtil;
import net.kicchi.todos.utils.DriverUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.Duration;

public class Hooks {

    @Before
    public void setUp(){
        BrowserUtil.turnOnImplicitWaits();
        DriverUtil.getDriver().manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario){
        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) DriverUtil.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","screenshot");
        }

        DriverUtil.closeDriver();
    }
}
