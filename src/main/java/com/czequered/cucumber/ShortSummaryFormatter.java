/*
 * Copyright (c) 2017 Martin Varga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.czequered.cucumber;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Martin Varga
 */
public class ShortSummaryFormatter implements Reporter, Formatter {
    private File reportFile;
    private final PrintWriter writer;
    private final StringWriter stringWriter;
    private boolean passed = true;
    private String lastScenario = null;

    public ShortSummaryFormatter(File reportFile) {
        this.reportFile = reportFile;
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter, true);
    }

    @Override public void feature(Feature feature) {
        String name = feature.getName();
        String tags = extractTags(feature);
        writer.println(prettyPrint(0, tags, name));
    }

    @Override public void scenarioOutline(ScenarioOutline scenarioOutline) {
        // ignore - the scenarios contain the outline exactly once.
    }

    @Override public void scenario(Scenario scenario) {
        String name = scenario.getName();
        // ignore scenarios with the same name, those are examples from outline, just keep one
        if (!Objects.equals(lastScenario, name)) {
            String tags = extractTags(scenario);
            writer.println(prettyPrint(2, tags, name));
            lastScenario = name;
        }
    }

    @Override public void done() {
        writer.println();
        String status = passed ? Result.PASSED : Result.FAILED;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        String now = dateTimeFormatter.format(LocalDateTime.now());
        writer.println("Final result: " + status + ", tested on: " + now);
        writer.flush();
    }

    @Override public void close() {
        try (PrintWriter out = new PrintWriter(reportFile)) {
            out.println(stringWriter);
        } catch (Exception e) {
            throw new RuntimeException("Cannot write the short report.");
        } finally {
            writer.close();
        }
    }

    @Override public void result(Result result) {
        passed = passed && Result.PASSED.equals(result.getStatus());
    }

    private String extractTags(TagStatement tagStatement) {
        return tagStatement.getTags().stream().map(t -> t.getName().replace("@", "")).collect(Collectors.joining(", "));
    }

    private String prettyPrint(int indent, String tags, String name) {
        String tagsAndName = tags == null || tags.isEmpty() ? name : nameAndTags(name, tags);
        return leftPad(tagsAndName, indent);
    }

    private String nameAndTags(String name, String tags) {
        return name + " (" + tags + ")";
    }

    private String leftPad(String s, int length) {
        final char[] padding = new char[length];
        for (int i = 0; i < length; i++) {
            padding[i] = ' ';
        }
        return new String(padding).concat(s);
    }

    // unused methods

    @Override public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
        // ignore
    }

    @Override public void uri(String uri) {
        // ignore
    }

    @Override public void examples(Examples examples) {
        // ignore
    }

    @Override public void startOfScenarioLifeCycle(Scenario scenario) {
        // ignore
    }

    @Override public void background(Background background) {
        // ignore
    }

    @Override public void step(Step step) {
        // ignore
    }

    @Override public void endOfScenarioLifeCycle(Scenario scenario) {
        // ignore
    }

    @Override public void eof() {
        // ignore
    }

    @Override public void before(Match match, Result result) {
        // ignore
    }

    @Override public void after(Match match, Result result) {
        // ignore
    }

    @Override public void match(Match match) {
        // ignore
    }

    @Override public void embedding(String mimeType, byte[] data) {
        // ignore
    }

    @Override public void write(String text) {
        // ignore
    }
}
