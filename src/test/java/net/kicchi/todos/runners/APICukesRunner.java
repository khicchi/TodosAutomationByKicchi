package net.kicchi.todos.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber-api.json",
                "html:target/default-html-reports-api",
                "rerun:target/rerun.txt"},
        features = "src/test/resources/features/api",
        glue = "net/kicchi/todos/step_definitions/api",
        dryRun = false,
        tags = "@wip"
)
public class APICukesRunner {
}
