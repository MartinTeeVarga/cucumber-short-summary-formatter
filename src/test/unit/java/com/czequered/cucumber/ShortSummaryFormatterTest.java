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

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Martin Varga
 */
public class ShortSummaryFormatterTest {
    @Test
    public void formatterTest() throws Exception {
        URL expectedAsUrl = this.getClass().getResource("/expected.txt");
        String expected = new String(Files.readAllBytes(Paths.get(expectedAsUrl.toURI())));
        String actual = new String(Files.readAllBytes(Paths.get("build/test-report-short.txt")));
        assertEquals(expected, actual);
    }

    @Test
    public void should_create_missing_parent_directories() {
        File parentDir = new File("build/somedir");
        File reportFile = new File(parentDir, "somefile.txt");
        reportFile.delete();
        assertFalse(reportFile.exists());
        parentDir.delete();
        assertFalse(parentDir.exists());

        ShortSummaryFormatter shortSummaryFormatter = new ShortSummaryFormatter(reportFile);
        shortSummaryFormatter.close();

        assertTrue(reportFile.exists());
    }

    @Test
    public void should_create_default_file_if_dir_specified() {
        File reportFile = new File("build/dir");
        if (reportFile.exists() && reportFile.listFiles() != null) {
            Arrays.stream(reportFile.listFiles()).forEach(File::delete);
        }
        reportFile.delete();
        assertFalse(reportFile.exists());
        reportFile.mkdirs();
        assertTrue(reportFile.exists());

        ShortSummaryFormatter shortSummaryFormatter = new ShortSummaryFormatter(reportFile);
        shortSummaryFormatter.close();

        assertTrue(reportFile.exists());
        assertTrue(reportFile.listFiles()[0].getName().equals(ShortSummaryFormatter.DEFAULT_REPORT_FILE));
    }
}