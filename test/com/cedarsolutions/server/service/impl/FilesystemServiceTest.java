/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2014 Kenneth J. Pronovici.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Apache License, Version 2.0.
 * See LICENSE for more information about the licensing terms.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Author   : Kenneth J. Pronovici <pronovic@ieee.org>
 * Language : Java 6
 * Project  : Common Java Functionality
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.cedarsolutions.server.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cedarsolutions.exception.CedarRuntimeException;
import com.cedarsolutions.server.service.IFilesystemService;
import com.cedarsolutions.util.StringUtils;

/**
 * Unit test for FilesystemService, identical to the tests for FilesystemUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class FilesystemServiceTest {

    /** Setup before all tests. */
    @BeforeClass public static void prepare() {
        if (getService().dirExists("target/working")) {
            getService().removeDir("target/working", true);
        }
        getService().createDir("target/working");
    }

    /** Cleanup after all tests. */
    @AfterClass public static void cleanup() {
        if (getService().dirExists("target")) {
            getService().removeDir("target", true);
        }
    }

    /** Test the normalize() method. */
    @Test public void testNormalize() {
        assertEquals(null, getService().normalize(null));
        assertEquals("", getService().normalize(""));
        assertEquals("path", getService().normalize("path"));
        assertEquals("one/two", getService().normalize("one/two"));
        assertEquals("/one/two", getService().normalize("/one/two"));
        assertEquals("/one/two", getService().normalize("\\one\\two"));
        assertEquals("/one/two", getService().normalize("\\one/two"));
    }

    /** Test the join() method. */
    @Test public void testJoin() {
        assertEquals("", getService().join());
        assertEquals("element", getService().join("element"));
        assertEquals("element1/element2", getService().join("element1", "element2"));
        assertEquals("1/2/3/4/5/6/7", getService().join("1", "2", "3", "4", "5", "6", "7"));
        assertEquals("element1/element2", getService().join(null, "element1", "element2"));
    }

    /** Test the getBasename() method. */
    @Test public void testGetBasename() {
        assertEquals(null, getService().getBasename(null));
        assertEquals(null, getService().getBasename(""));
        assertEquals("a.txt", getService().getBasename("a.txt"));
        assertEquals("a.txt", getService().getBasename("dir/a.txt"));
        assertEquals("a.txt", getService().getBasename("c:/dir/a.txt"));
        assertEquals("a.txt", getService().getBasename("c:/path/to/dir/a.txt"));
        assertEquals("a.txt", getService().getBasename("/path/to/dir/a.txt"));
    }

    /** Test the getDirname() method. */
    @Test public void testGetDirname() {
        assertEquals(null, getService().getDirname(null));
        assertEquals(null, getService().getDirname(""));
        assertEquals(null, getService().getDirname("a.txt"));
        assertEquals("dir", getService().getDirname("dir/a.txt"));
        assertEquals("c:/dir", getService().getDirname("c:/dir/a.txt"));
        assertEquals("c:/path/to/dir", getService().getDirname("c:/path/to/dir/a.txt"));
        assertEquals("/path/to/dir", getService().getDirname("/path/to/dir/a.txt"));
    }

    /** Test the getFileSize() method. */
    @Test public void testGetFileSize() throws Exception {
        try {
            File targetDir = new File("target");
            File targetFile = new File("target/working/size.txt");

            assertTrue(targetDir.exists());

            FileOutputStream stream = new FileOutputStream("target/working/size.txt");
            stream.write(StringUtils.getBytes("LINE1"));
            stream.close();

            assertTrue(targetDir.exists());
            assertTrue(targetFile.exists());

            assertEquals("LINE1".length(), getService().getFileSize("target/working/size.txt"));
        } finally {
            getService().removeFile("target/working/size.txt");
        }
    }

    /** Test the isAbsolutePath() method. */
    @Test public void testIsAbsolute() {
        assertTrue(getService().isAbsolutePath("/path/to/whatever"));
        assertTrue(getService().isAbsolutePath("C:\\path\\to\\whatever"));
        assertFalse(getService().isAbsolutePath("path/to/whatever"));
        assertFalse(getService().isAbsolutePath("path\\to\\whatever"));
    }

    /** Test the directory methods. */
    @Test public void testCreateRemoveDir() throws Exception {
        File targetDir = new File("target");
        File firstSubdir = new File("target/working/subdir");
        File secondSubdir = new File("target/working/subdir/subdir2");
        File thirdSubdir = new File("target/working/subdir/subdir3");

        assertTrue(targetDir.exists());
        assertFalse(firstSubdir.exists());
        assertFalse(secondSubdir.exists());
        assertFalse(thirdSubdir.exists());

        assertTrue(getService().dirExists("target"));
        assertFalse(getService().dirExists("target/working/subdir"));
        assertFalse(getService().dirExists("target/working/subdir/subdir2"));

        getService().createDir("target/working/subdir/subdir2");

        assertTrue(targetDir.exists());
        assertTrue(firstSubdir.exists());
        assertTrue(secondSubdir.exists());

        assertTrue(getService().dirExists("target"));
        assertTrue(getService().dirExists("target/working/subdir"));
        assertTrue(getService().dirExists("target/working/subdir/subdir2"));

        getService().createDir("target/working/subdir/subdir2");

        assertTrue(targetDir.exists());
        assertTrue(firstSubdir.exists());
        assertTrue(secondSubdir.exists());

        assertTrue(getService().dirExists("target"));
        assertTrue(getService().dirExists("target/working/subdir"));
        assertTrue(getService().dirExists("target/working/subdir/subdir2"));

        getService().removeEmptyDir("target/working/subdir/subdir2");

        assertTrue(targetDir.exists());
        assertTrue(firstSubdir.exists());
        assertFalse(secondSubdir.exists());

        assertTrue(getService().dirExists("target"));
        assertTrue(getService().dirExists("target/working/subdir"));
        assertFalse(getService().dirExists("target/working/subdir/subdir2"));

        getService().removeEmptyDir("target/working/subdir/subdir2");  // not there, but it shouldn't matter

        assertTrue(targetDir.exists());
        assertTrue(firstSubdir.exists());
        assertFalse(secondSubdir.exists());

        assertTrue(getService().dirExists("target"));
        assertTrue(getService().dirExists("target/working/subdir"));
        assertFalse(getService().dirExists("target/working/subdir/subdir2"));

        getService().removeEmptyDir("target/working/subdir");

        assertTrue(targetDir.exists());
        assertFalse(firstSubdir.exists());
        assertFalse(secondSubdir.exists());

        assertTrue(getService().dirExists("target"));
        assertFalse(getService().dirExists("target/working/subdir"));
        assertFalse(getService().dirExists("target/working/subdir/subdir2"));

        getService().createDir("target/working/subdir/subdir2");
        getService().createDir("target/working/subdir/subdir3");

        assertTrue(targetDir.exists());
        assertTrue(firstSubdir.exists());
        assertTrue(secondSubdir.exists());
        assertTrue(thirdSubdir.exists());

        assertTrue(getService().dirExists("target"));
        assertTrue(getService().dirExists("target/working/subdir"));
        assertTrue(getService().dirExists("target/working/subdir/subdir2"));
        assertTrue(getService().dirExists("target/working/subdir/subdir3"));

        getService().removeDir("target/working/subdir", true);

        assertTrue(targetDir.exists());
        assertFalse(firstSubdir.exists());
        assertFalse(secondSubdir.exists());
        assertFalse(thirdSubdir.exists());

        assertTrue(getService().dirExists("target"));
        assertFalse(getService().dirExists("target/working/subdir"));
        assertFalse(getService().dirExists("target/working/subdir/subdir2"));
        assertFalse(getService().dirExists("target/working/subdir/subdir3"));
    }

    /** Test the createFile() and removeFile() methods. */
    @Test public void testCreateRemoveFile() throws Exception {
        File targetDir = new File("target");
        File targetFile = new File("target/working/file.txt");

        assertTrue(targetDir.exists());
        assertFalse(targetFile.exists());
        assertFalse(getService().fileExists("target/working/file.txt"));

        getService().removeFile("target/working/file.txt");

        assertTrue(targetDir.exists());
        assertFalse(targetFile.exists());
        assertFalse(getService().fileExists("target/working/file.txt"));

        getService().createFile("target/working/file.txt");

        assertTrue(targetDir.exists());
        assertTrue(targetFile.exists());
        assertTrue(getService().fileExists("target/working/file.txt"));

        getService().removeFile("target/working/file.txt");

        assertTrue(targetDir.exists());
        assertFalse(targetFile.exists());
        assertFalse(getService().fileExists("target/working/file.txt"));
    }

    /** Test the getFileContents() method. */
    @Test public void testGetFileContents() throws Exception {
        try {
            File targetDir = new File("target");
            File targetFile = new File("target/working/file.txt");

            assertTrue(targetDir.exists());

            FileOutputStream stream = new FileOutputStream("target/working/file.txt");
            stream.write(StringUtils.getBytes("LINE1\n"));
            stream.write(StringUtils.getBytes("LINE2\n"));
            stream.write(StringUtils.getBytes("LINE3\n"));
            stream.write(StringUtils.getBytes("LINE4\n"));
            stream.close();

            assertTrue(targetDir.exists());
            assertTrue(targetFile.exists());

            List<String> lines = getService().getFileContents("target/working/file.txt");
            assertNotNull(lines);
            assertEquals(4, lines.size());
            assertEquals("LINE1", lines.get(0));
            assertEquals("LINE2", lines.get(1));
            assertEquals("LINE3", lines.get(2));
            assertEquals("LINE4", lines.get(3));
        } finally {
            getService().removeFile("target/working/file.txt");
        }
    }

    /** Test the getFileContentsAsString() method, with a trailing newline. */
    @Test public void testGetFileContentsAsString1() throws Exception {
        try {
            File targetDir = new File("target");
            File targetFile = new File("target/working/file.txt");

            assertTrue(targetDir.exists());

            FileOutputStream stream = new FileOutputStream("target/working/file.txt");
            stream.write(StringUtils.getBytes("LINE1\n"));
            stream.write(StringUtils.getBytes("LINE2\n"));
            stream.write(StringUtils.getBytes("LINE3\n"));
            stream.write(StringUtils.getBytes("LINE4\n"));
            stream.close();

            assertTrue(targetDir.exists());
            assertTrue(targetFile.exists());

            String result = getService().getFileContentsAsString("target/working/file.txt");
            assertNotNull(result);
            assertEquals("LINE1\nLINE2\nLINE3\nLINE4\n", result);
        } finally {
            getService().removeFile("target/working/file.txt");
        }
    }

    /** Test the getFileContentsAsString() method, without a trailing newline. */
    @Test public void testGetFileContentsAsString2() throws Exception {
        try {
            File targetDir = new File("target");
            File targetFile = new File("target/working/file.txt");

            assertTrue(targetDir.exists());

            FileOutputStream stream = new FileOutputStream("target/working/file.txt");
            stream.write(StringUtils.getBytes("LINE1\n"));
            stream.write(StringUtils.getBytes("LINE2\n"));
            stream.write(StringUtils.getBytes("LINE3\n"));
            stream.write(StringUtils.getBytes("LINE4"));
            stream.close();

            assertTrue(targetDir.exists());
            assertTrue(targetFile.exists());

            String result = getService().getFileContentsAsString("target/working/file.txt");
            assertNotNull(result);
            assertEquals("LINE1\nLINE2\nLINE3\nLINE4", result);
        } finally {
            getService().removeFile("target/working/file.txt");
        }
    }

    /** Test writeFileContents(). */
    @Test public void testWriteFileContentsString() throws Exception {
        assertFalse(getService().fileExists("target/working/file.txt"));

        String source = "LINE1\nLINE2\nLINE3\nLINE4\n";

        getService().writeFileContents("target/working/file.txt", source);

        List<String> resultLines = getService().getFileContents("target/working/file.txt");
        assertNotNull(resultLines);
        assertEquals(4, resultLines.size());
        assertEquals("LINE1", resultLines.get(0));
        assertEquals("LINE2", resultLines.get(1));
        assertEquals("LINE3", resultLines.get(2));
        assertEquals("LINE4", resultLines.get(3));

        getService().removeFile("target/working/file.txt");
    }

    /** Test writeFileContents(). */
    @Test public void testWriteFileContentsList() throws Exception {
        assertFalse(getService().fileExists("target/working/file.txt"));

        List<String> sourceLines = new ArrayList<String>();
        sourceLines.add("LINE1");
        sourceLines.add("LINE2");
        sourceLines.add("LINE3");
        sourceLines.add("LINE4");

        getService().writeFileContents("target/working/file.txt", sourceLines);

        List<String> resultLines = getService().getFileContents("target/working/file.txt");
        assertNotNull(resultLines);
        assertEquals(4, resultLines.size());
        assertEquals("LINE1", resultLines.get(0));
        assertEquals("LINE2", resultLines.get(1));
        assertEquals("LINE3", resultLines.get(2));
        assertEquals("LINE4", resultLines.get(3));

        getService().removeFile("target/working/file.txt");
    }

    /** Test the copyFile() method. */
    @Test public void testCopyFile() throws Exception {
        assertFalse(getService().fileExists("target/working/file.txt"));
        assertFalse(getService().fileExists("target/working/copy.txt"));

        List<String> sourceLines = new ArrayList<String>();
        sourceLines.add("LINE1");
        sourceLines.add("LINE2");
        sourceLines.add("LINE3");
        sourceLines.add("LINE4");

        getService().writeFileContents("target/working/file.txt", sourceLines);
        getService().copyFile("target/working/file.txt", "target/working/copy.txt");

        assertTrue(getService().fileExists("target/working/copy.txt"));

        List<String> resultLines = getService().getFileContents("target/working/copy.txt");
        assertNotNull(resultLines);
        assertEquals(4, resultLines.size());
        assertEquals("LINE1", resultLines.get(0));
        assertEquals("LINE2", resultLines.get(1));
        assertEquals("LINE3", resultLines.get(2));
        assertEquals("LINE4", resultLines.get(3));

        getService().removeFile("target/working/file.txt");
        getService().removeFile("target/working/copy.txt");
    }

    /** Test the copyFileToDir() method. */
    @Test public void testCopyFileToDir() throws Exception {
        assertFalse(getService().fileExists("target/working/file.txt"));
        assertFalse(getService().dirExists("target/working/newdir"));
        assertFalse(getService().fileExists("target/working/newdir/file.txt"));

        getService().createDir("target/working/newdir");

        List<String> sourceLines = new ArrayList<String>();
        sourceLines.add("LINE1");
        sourceLines.add("LINE2");
        sourceLines.add("LINE3");
        sourceLines.add("LINE4");

        getService().writeFileContents("target/working/file.txt", sourceLines);
        getService().copyFileToDir("target/working/file.txt", "target/working/newdir");

        assertTrue(getService().fileExists("target/working/newdir/file.txt"));

        List<String> resultLines = getService().getFileContents("target/working/newdir/file.txt");
        assertNotNull(resultLines);
        assertEquals(4, resultLines.size());
        assertEquals("LINE1", resultLines.get(0));
        assertEquals("LINE2", resultLines.get(1));
        assertEquals("LINE3", resultLines.get(2));
        assertEquals("LINE4", resultLines.get(3));

        getService().removeFile("target/working/newdir/file.txt");
        getService().removeFile("target/working/file.txt");
        getService().removeEmptyDir("target/working/newdir");
    }

    /** Test getGlobContents(). */
    @Test public void testGetGlobContents() throws Exception {
        try {
            assertFalse(getService().dirExists("target/working/contents"));

            try {
                getService().getGlobContents("target/working/contents", "*");
                fail("Expected CedarRuntimeException");
            } catch (CedarRuntimeException e) { }

            getService().createDir("target/working/contents");
            getService().createDir("target/working/contents/subdir1");
            getService().createDir("target/working/contents/subdir2");
            getService().createDir("target/working/contents/subdir3");
            getService().createFile("target/working/contents/file1");
            getService().createFile("target/working/contents/file2");
            getService().createFile("target/working/contents/subdir1/file3");

            List<String> contents = getService().getGlobContents("target/working/contents", "*");
            assertEquals(5, contents.size());
            assertTrue(contents.contains("subdir1"));
            assertTrue(contents.contains("subdir2"));
            assertTrue(contents.contains("subdir3"));
            assertTrue(contents.contains("file1"));
            assertTrue(contents.contains("file2"));

            contents = getService().getGlobContents("target/working/contents", "subdir*");
            assertEquals(3, contents.size());
            assertTrue(contents.contains("subdir1"));
            assertTrue(contents.contains("subdir2"));
            assertTrue(contents.contains("subdir3"));

            contents = getService().getGlobContents("target/working/contents", "**/file*");
            assertEquals(3, contents.size());
            assertTrue(contents.contains("file1"));
            assertTrue(contents.contains("file2"));
            assertTrue(contents.contains("subdir1/file3"));

            contents = getService().getGlobContents("target/working/contents/subdir1", "*");
            assertEquals(1, contents.size());
            assertTrue(contents.contains("file3"));

            contents = getService().getGlobContents("target/working/contents/subdir2", "*");
            assertTrue(contents.isEmpty());

            contents = getService().getGlobContents("target/working/contents/subdir3", "*");
            assertTrue(contents.isEmpty());

            try {
                getService().getGlobContents("target/working/contents/file1", "*");
                fail("Expected CedarRuntimeException");
            } catch (CedarRuntimeException e) { }
        } finally {
            getService().removeDir("target/working/contents", true);
        }
    }

    /** Test getDirContents(). */
    @Test public void testGetDirContents() throws Exception {
        try {
            assertFalse(getService().dirExists("target/working/contents"));

            try {
                getService().getDirContents("target/working/contents");
                fail("Expected CedarRuntimeException");
            } catch (CedarRuntimeException e) { }

            getService().createDir("target/working/contents");
            getService().createDir("target/working/contents/subdir1");
            getService().createDir("target/working/contents/subdir2");
            getService().createDir("target/working/contents/subdir3");
            getService().createFile("target/working/contents/file1");
            getService().createFile("target/working/contents/file2");
            getService().createFile("target/working/contents/subdir1/file3");

            List<String> contents = getService().getDirContents("target/working/contents");
            assertEquals(5, contents.size());
            assertTrue(contents.contains("subdir1"));
            assertTrue(contents.contains("subdir2"));
            assertTrue(contents.contains("subdir3"));
            assertTrue(contents.contains("file1"));
            assertTrue(contents.contains("file2"));

            contents = getService().getDirContents("target/working/contents/subdir1");
            assertEquals(1, contents.size());
            assertTrue(contents.contains("file3"));

            contents = getService().getDirContents("target/working/contents/subdir2");
            assertTrue(contents.isEmpty());

            contents = getService().getDirContents("target/working/contents/subdir3");
            assertTrue(contents.isEmpty());

            try {
                getService().getDirContents("target/working/contents/file1");
                fail("Expected CedarRuntimeException");
            } catch (CedarRuntimeException e) { }
        } finally {
            getService().removeDir("target/working/contents", true);
        }
    }

    /** Test unzip(). */
    @Test public void testUnzip() {
        getService().copyFile("test/com/cedarsolutions/util/test.zip", "target/working/test.zip");
        getService().createDir("target/working/unzip");
        getService().unzip("target/working/test.zip", "target/working/unzip");
        List<String> contents = getService().getDirContents("target/working/unzip", true);
        assertEquals(6, contents.size());
        assertTrue(contents.contains("test"));
        assertTrue(contents.contains("test/file1.txt"));
        assertTrue(contents.contains("test/folder1"));
        assertTrue(contents.contains("test/folder1/file2.txt"));
        assertTrue(contents.contains("test/folder2"));
        assertTrue(contents.contains("test/folder2/file3.txt"));
    }

    /** Test getLastModifiedDate(). */
    @Test public void testGetLastModifiedDate() {
        Date lastModified = getService().getLastModifiedDate("test/com/cedarsolutions/util/test.zip");
        assertEquals(lastModified.getTime(), new File("test/com/cedarsolutions/util/test.zip").lastModified());
    }

    /** Get a service instance for testing. */
    private static IFilesystemService getService() {
        return new FilesystemService();
    }

}
