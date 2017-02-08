import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/"}, plugin={"com.czequered.cucumber.ShortSummaryFormatter:build/test-report-short"})
public class RunCukes {
}
