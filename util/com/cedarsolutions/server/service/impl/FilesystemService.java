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

import java.io.File;
import java.util.List;

import com.cedarsolutions.server.service.IFilesystemService;
import com.cedarsolutions.util.FilesystemUtils;

/**
 * Service wrapper around FilesystemUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class FilesystemService extends AbstractService implements IFilesystemService {

    /** Normalize a path, so it uses all '/' characters. */
    @Override
    public String normalize(String path) {
        return FilesystemUtils.normalize(path);
    }

    /**
     * Join a list of filesystem elements together, kind of like Python's os.path.join().
     * @param elements  List of elements to be joined.
     * @return Complete path, with elements separated by '/' characters.
     */
    @Override
    public String join(String ... elements) {
        return FilesystemUtils.join(elements);
    }

    /**
     * Get the base name for a path, like Python's os.path.basename().
     * @param path    Path to operate on
     * @return Base name for the path, possibly null.
     */
    @Override
    public String getBasename(String path) {
        return FilesystemUtils.getBasename(path);
    }

    /**
     * Get the directory name for a path, like Python's os.path.dirname().
     * @param path    Path to operate on
     * @return Directory name for the path, possibly null.
     */
    @Override
    public String getDirname(String path) {
        return FilesystemUtils.getDirname(path);
    }

    /**
     * Copy a file into a directory, overwriting the file if it exists.
     * @param sourceFilePath  Source file path
     * @param targetDirPath   Target directory path
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void copyFileToDir(String sourceFilePath, String targetDirPath) {
        FilesystemUtils.copyFileToDir(sourceFilePath, targetDirPath);
    }

    /**
     * Copy a file, overwriting the target if it exists.
     * @param sourceFilePath  Source file path
     * @param targetFilePath  Target file path
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void copyFile(String sourceFilePath, String targetFilePath) {
        FilesystemUtils.copyFile(sourceFilePath, targetFilePath);
    }

    /**
     * Copy a file, overwriting the target if it exists.
     * @param sourceFile  Source file
     * @param targetFile  Target file
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void copyFile(File sourceFile, File targetFile) {
        FilesystemUtils.copyFile(sourceFile, targetFile);
    }

    /**
     * Create a file in an existing directory.
     * @param filePath  Path of the file to create
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void createFile(String filePath) {
        FilesystemUtils.createFile(filePath);
    }

    /**
     * Remove a file, if it exists.
     * @param filePath  Path of the file to remove
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void removeFile(String filePath) {
        FilesystemUtils.removeFile(filePath);
    }

    /**
     * Indicate whether a file exists.
     * @param filePath  Path of the file to check
     * @return True if the file exists, false otherwise.
     */
    @Override
    public boolean fileExists(String filePath) {
        return FilesystemUtils.fileExists(filePath);
    }

    /**
     * Create a directory including all parent directories, like 'mkdir -p'.
     * @param dirPath  Path of the directory to create
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void createDir(String dirPath) {
        FilesystemUtils.createDir(dirPath);
    }

    /**
     * Remove an empty directory, if it exists.
     * @param dirPath   Path of the directory to remove.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void removeEmptyDir(String dirPath) {
        FilesystemUtils.removeEmptyDir(dirPath);
    }

    /**
     * Remove a directory, if it exists.
     * If recursive=false, the directory must be empty.
     * @param dirPath    Path of the directory to remove.
     * @param recursive  Whether the directory should be removed recursively.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void removeDir(String dirPath, boolean recursive) {
        FilesystemUtils.removeDir(dirPath, recursive);
    }

    /**
     * Indicates whether a directory exists.
     * @param dirPath   Path of directory to check.
     * @return True if the directory exists, false otherwise.
     */
    @Override
    public boolean dirExists(String dirPath) {
        return FilesystemUtils.dirExists(dirPath);
    }

    /**
     * Writes string contents to a file, replacing the file if it already exists.
     * @param filePath  Path of the file to write
     * @param contents  String contents to be written to the file
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void writeFileContents(String filePath, String contents) {
        FilesystemUtils.writeFileContents(filePath, contents);
    }

    /**
     * Writes string contents to a file, replacing the file if it already exists.
     * @param filePath  Path of the file to write
     * @param contents  Set of lines to be written to the file
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public void writeFileContents(String filePath, List<String> contents) {
        FilesystemUtils.writeFileContents(filePath, contents);
    }

    /**
     * Get the contents of a file.
     * @param filePath  Path of the file to read
     * @return Contents of the file as a list of strings.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public List<String> getFileContents(String filePath) {
        return FilesystemUtils.getFileContents(filePath);
    }

    /**
     * Get the contents of a file.
     * @param filePath  Path of the file to read
     * @return Contents of the file as a single multi-line string.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public String getFileContentsAsString(String filePath) {
        return FilesystemUtils.getFileContentsAsString(filePath);
    }

    /**
     * Get a list of files and directories that match a glob pattern.
     * @param dirPath  Directory to operate on
     * @param glob     Ant-style glob to use
     * @return List of files and directories that match the glob.
     * @see <a href="http://stackoverflow.com/questions/794381">Stack Overflow</a>
     */
    @Override
    public List<String> getGlobContents(String dirPath, String glob) {
        return FilesystemUtils.getGlobContents(dirPath, glob);
    }

    /**
     * Get a list of the files and directories immediately within a directory.
     * @param dirPath  Directory to operate on
     * @return List of files and directories within the directory.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public List<String> getDirContents(String dirPath) {
        return FilesystemUtils.getDirContents(dirPath);
    }

    /**
     * Get a list of the files and directories within a directory.
     * @param dirPath    Directory to operate on
     * @param recursive  Recursively dig through all subdirectories
     * @return List of files and directories within the directory.
     * @throws CedarRuntimeException If there is a problem with the filesystem operation.
     */
    @Override
    public List<String> getDirContents(String dirPath, boolean recursive) {
        return FilesystemUtils.getDirContents(dirPath, recursive);
    }

    /**
     * Unzips the passed-in zip file.
     * @param zipFilePath   Path to the zip file on disk
     * @param targetDirPath Target directory that the zip contents should be written into
     * @see <a href="http://stackoverflow.com/questions/3321842/poor-performance-of-javas-unzip-utilities">Stack Overflow</a>
     */
    @Override
    public void unzip(String zipFilePath, String targetDirPath) {
        FilesystemUtils.unzip(zipFilePath, targetDirPath);
    }

}
