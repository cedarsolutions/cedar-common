/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2014-2016 Kenneth J. Pronovici.
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
package com.cedarsolutions.server.service;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Service wrapper around FilesystemUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public interface IFilesystemService {

    /** Get the current working directory. */
    String getCurrentWorkingDir();

    /** Normalize a path, so it uses all '/' characters. */
     String normalize(String path);

    /**
     * Join a list of filesystem elements together, kind of like Python's os.path.join().
     * @param elements  List of elements to be joined.
     * @return Complete path, with elements separated by '/' characters.
     */
     String join(String... elements);

    /**
     * Get the base name for a path, like Python's os.path.basename().
     * @param path    Path to operate on
     * @return Base name for the path, possibly null.
     */
     String getBasename(String path);

    /**
     * Get the directory name for a path, like Python's os.path.dirname().
     * @param path    Path to operate on
     * @return Directory name for the path, possibly null.
     */
     String getDirname(String path);

     /**
      * Get the length of a file on disk.
      * @param filePath  Path of the file to check
      * @return Length of the file in bytes, or zero if the file does not exist.
      */
     long getFileSize(String filePath);

     /**
      * Check whether a path is absolute.
      * @param  path   Path to operate on
      * @return True if the path is absolute, false otherwise.
      */
     boolean isAbsolutePath(String path);

    /**
     * Copy a file into a directory, overwriting the file if it exists.
     * @param sourceFilePath  Source file path
     * @param targetDirPath   Target directory path
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void copyFileToDir(String sourceFilePath, String targetDirPath);

    /**
     * Copy a file, overwriting the target if it exists.
     * @param sourceFilePath  Source file path
     * @param targetFilePath  Target file path
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void copyFile(String sourceFilePath, String targetFilePath);

    /**
     * Copy a file, overwriting the target if it exists.
     * @param sourceFile  Source file
     * @param targetFile  Target file
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void copyFile(File sourceFile, File targetFile);

     /**
      * Move a file, overwriting the target if it exists.
      * @param sourceFilePath  Source file path
      * @param targetFilePath  Target file path
      * @throws CedarRuntimeException If there is a problem with the filesystem operation.
      */
     void moveFile(String sourceFilePath, String targetFilePath);

     /**
      * Move a file, overwriting the target if it exists.
      * @param sourceFile  Source file
      * @param targetFile  Target file
      * @throws CedarRuntimeException If there is a problem with the filesystem operation.
      */
     void moveFile(File sourceFile, File targetFile);

    /**
     * Create a file in an existing directory.
     * @param filePath  Path of the file to create
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void createFile(String filePath);

    /**
     * Remove a file, if it exists.
     * @param filePath  Path of the file to remove
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void removeFile(String filePath);

    /**
     * Indicate whether a file exists.
     * @param filePath  Path of the file to check
     * @return True if the file exists, false otherwise.
     */
     boolean fileExists(String filePath);

    /**
     * Create a directory including all parent directories, like 'mkdir -p'.
     * @param dirPath  Path of the directory to create
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void createDir(String dirPath);

    /**
     * Remove an empty directory, if it exists.
     * @param dirPath   Path of the directory to remove.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void removeEmptyDir(String dirPath);

    /**
     * Remove a directory, if it exists.
     * If recursive=false, the directory must be empty.
     * @param dirPath    Path of the directory to remove.
     * @param recursive  Whether the directory should be removed recursively.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void removeDir(String dirPath, boolean recursive);

    /**
     * Indicates whether a directory exists.
     * @param dirPath   Path of directory to check.
     * @return True if the directory exists, false otherwise.
     */
     boolean dirExists(String dirPath);

    /**
     * Writes string contents to a file, replacing the file if it already exists.
     * @param filePath  Path of the file to write
     * @param contents  String contents to be written to the file
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void writeFileContents(String filePath, String contents);

    /**
     * Writes string contents to a file, replacing the file if it already exists.
     * @param filePath  Path of the file to write
     * @param contents  Set of lines to be written to the file
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     void writeFileContents(String filePath, List<String> contents);

    /**
     * Get the contents of a file.
     * @param filePath  Path of the file to read
     * @return Contents of the file as a list of strings.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     List<String> getFileContents(String filePath);

    /**
     * Get the contents of a file.
     * @param filePath  Path of the file to read
     * @return Contents of the file as a single multi-line string.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     String getFileContentsAsString(String filePath);

     /**
      * Get a list of files and directories that match a glob pattern.
      * @param dirPath  Directory to operate on
      * @param glob     Ant-style glob to use
      * @return List of files and directories that match the glob.
      * @see <a href="http://stackoverflow.com/questions/794381">Stack Overflow</a>
      */
     List<String> getGlobContents(String dirPath, String glob);

    /**
     * Get a list of the files and directories immediately within a directory.
     * @param dirPath  Directory to operate on
     * @return List of files and directories within the directory.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     List<String> getDirContents(String dirPath);

    /**
     * Get a list of the files and directories within a directory.
     * @param dirPath    Directory to operate on
     * @param recursive  Recursively dig through all subdirectories
     * @return List of files and directories within the directory.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
     List<String> getDirContents(String dirPath, boolean recursive);

    /**
     * Unzips the passed-in zip file.
     * @param zipFilePath   Path to the zip file on disk
     * @param targetDirPath Target directory that the zip contents should be written into
     * @see <a href="http://stackoverflow.com/questions/3321842/poor-performance-of-javas-unzip-utilities">Stack Overflow</a>
     */
     void unzip(String zipFilePath, String targetDirPath);

     /**
      * Get the last modified date for a file, in UTC.
      * @param filePath  Path of the file to check
      * @return UTC date representing the last modified time for the file.
      */
     Date getLastModifiedDate(String filePath);

}
