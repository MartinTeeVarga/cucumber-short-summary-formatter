# cucumber-short-summary-formatter [![Build Status](https://travis-ci.org/sm4/cucumber-short-summary-formatter.svg?branch=master)](https://travis-ci.org/sm4/cucumber-short-summary-formatter)  [![Download](https://api.bintray.com/packages/sm4/maven/cucumber-short-summary-formatter/images/download.svg)](https://bintray.com/sm4/maven/cucumber-short-summary-formatter/_latestVersion)
A short summary formatter for Cucumber JVM, prints only features, scenarios and the final result.

## Sample output
The formatter will print any feature name with all tags, then all scenarios with tags. Scenario Outlines are printed as one scenario, further examples are ignored. Finally the formatter prints a result (passed if all steps in all scenarios passed) and today's date.
```
As a user, I want to drink coffee (Coffee)
  My boss goes on coffee run (LazyDev, Cheapskate)
  I go buy my own coffee (ActiveDev)
  Someone else goes for a coffee (Cheapskate, Coffee)
As a user, I want to eat lunch
  Pizza day

Final result: passed, tested on: 21/01/2017
```

## Usage
The package is currently deployed in user bintray repository, waiting to be included in jCenter(). For now, you have to add both repository and dependency:
```
repositories {
    maven {
        url 'http://dl.bintray.com/sm4/maven'
    }
}

dependencies {
    testCompile 'com.czequered.cucumber:cucumber-short-summary-formatter:0.0.1'
}
```

Then use it as any other cucumber plugin, just specify the full path to the Java class in your JUnit test class:
```
@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/"}, plugin={"com.czequered.cucumber.ShortSummaryFormatter:build/test-report-short.txt"})
public class RunCukes {
}
```
Or as a parameter for commandline:
```
--plugin com.czequered.cucumber.ShortSummaryFormatter:build/test-results/short.txt
```