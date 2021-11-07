package com.luxoft.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {

    @Test
    public void testVerifyNumberOfFilesInDirectoryTree() throws IOException {
        Path path = Files.createTempDirectory("temp");

        assertEquals(0, FileManager.countFiles(path.toString()));

        Path path1 = Files.createTempDirectory(path, "temp1");
        Path path2 = Files.createTempDirectory(path1, "temp1");
        File.createTempFile("tmp1", ".txt", new File(path.toString()));

        assertEquals(1, FileManager.countFiles(path.toString()));

        File.createTempFile("tmp2", ".txt", new File(path1.toString()));
        File.createTempFile("tmp3", ".txt", new File(path2.toString()));

        assertEquals(3, FileManager.countFiles(path.toString()));
    }

    @Test
    public void testVerifyNumberOfDirectoriesInDirectoryTree() throws IOException {
        Path path = Files.createTempDirectory("temp");
        assertEquals(0, FileManager.countDirs(path.toString()));

        Path path1 = Files.createTempDirectory(path, "temp1");
        Path path2 = Files.createTempDirectory(path1, "temp1");

        assertEquals(2, FileManager.countDirs(path.toString()));
    }

    @Test
    public void testVerifyCopyFilesAndFolders() throws IOException {
        // The path where copy function will take info
        Path pathFrom = Files.createTempDirectory("tempFrom");
        File fileFrom = File.createTempFile("tmp1", ".txt", new File(pathFrom.toString()));

        // Fulfill temporary txt file with some lines
        FileOutputStream fileOutputStream = new FileOutputStream(fileFrom);
        fileOutputStream.write("Test string".getBytes());
        fileOutputStream.close();

        // Create a temp folder where all data will be copied
        Path pathWhere = Files.createTempDirectory("tempWhere");
        assertEquals(0, FileManager.countFiles(pathWhere.toString()));

        FileManager.copy(pathFrom.toString(), pathWhere.toString());

        assertEquals(1, FileManager.countFiles(pathWhere.toString()));

        FileInputStream fileInputStream = new FileInputStream(pathWhere.toString() + File.separator + fileFrom.getName());
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = fileInputStream.readAllBytes();
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append((char)bytes[i]);
        }

        assertEquals("Test string", stringBuilder.toString());
    }

    @Test
    public void testVerifyCopyFilesAndFoldersFromDirectoryTree() throws IOException {
        // The path where copy function will take info
        Path pathFrom = Files.createTempDirectory("tempFrom");
        File fileFrom = File.createTempFile("tmp1", ".txt", new File(pathFrom.toString()));
        Path pathFromSubDirectory = Files.createTempDirectory(pathFrom, "tempFrom");
        File subFileFrom = File.createTempFile("tmp2", ".txt", new File(pathFromSubDirectory.toString()));

        // Fulfill all temporary txt files with some test lines
        FileOutputStream fileOutputStream = new FileOutputStream(fileFrom);
        fileOutputStream.write("Test string".getBytes());
        fileOutputStream.close();

        FileOutputStream fileOutputStream1 = new FileOutputStream(subFileFrom);
        fileOutputStream1.write("Test string in sub file".getBytes());
        fileOutputStream1.close();

        // Create a temp folder where all data will be copied
        Path pathWhere = Files.createTempDirectory("tempWhere");
        assertEquals(0, FileManager.countDirs(pathWhere.toString()));
        assertEquals(0, FileManager.countFiles(pathWhere.toString()));

        FileManager.copy(pathFrom.toString(), pathWhere.toString());

        assertEquals(1, FileManager.countDirs(pathWhere.toString()));
        assertEquals(2, FileManager.countFiles(pathWhere.toString()));

        // reading all data from first txt file
        FileInputStream fileInputStream = new FileInputStream(pathWhere.toString() + File.separator + fileFrom.getName());
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = fileInputStream.readAllBytes();
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append((char)bytes[i]);
        }
        fileInputStream.close();

        // reading from sub txt file
        FileInputStream fileInputStream1 = new FileInputStream(pathFromSubDirectory.toString() + File.separator + subFileFrom.getName());
        StringBuilder stringBuilder1 = new StringBuilder();
        byte[] bytes1 = fileInputStream1.readAllBytes();
        for (int i = 0; i < bytes1.length; i++) {
            stringBuilder1.append((char)bytes1[i]);
        }
        fileInputStream1.close();

        assertEquals("Test string", stringBuilder.toString());
        assertEquals("Test string in sub file", stringBuilder1.toString());
    }

    @Test
    public void testVerifyMoveFiles() throws IOException {
        // The path where copy function will take info
        Path pathFrom = Files.createTempDirectory("tempFrom");
        File fileFrom = File.createTempFile("tmp1", ".txt", new File(pathFrom.toString()));

        // Fulfill temporary txt file with some lines
        FileOutputStream fileOutputStream = new FileOutputStream(fileFrom);
        fileOutputStream.write("Test string".getBytes());
        fileOutputStream.close();

        // Create a temp folder where all data will be copied
        Path pathWhere = Files.createTempDirectory("tempWhere");
        assertEquals(0, FileManager.countFiles(pathWhere.toString()));

        FileManager.move(pathFrom.toString(), pathWhere.toString());

        assertEquals(1, FileManager.countFiles(pathWhere.toString()));
        Assertions.assertThrows(NullPointerException.class, () -> {
            FileManager.countFiles(pathFrom.toString());
        });

        FileInputStream fileInputStream = new FileInputStream(pathWhere.toString() + File.separator + fileFrom.getName());
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = fileInputStream.readAllBytes();
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append((char)bytes[i]);
        }

        assertEquals("Test string", stringBuilder.toString());
    }

    @Test
    public void testVerifyMoveFilesAndFoldersFromDirectoryTree() throws IOException {
        // The path where copy function will take info
        Path pathFrom = Files.createTempDirectory("tempFrom");
        File fileFrom = File.createTempFile("tmp1", ".txt", new File(pathFrom.toString()));
        Path pathFromSubDirectory = Files.createTempDirectory(pathFrom, "tempFrom");
        File subFileFrom = File.createTempFile("tmp2", ".txt", new File(pathFromSubDirectory.toString()));

        // Fulfill all temporary txt files with some test lines
        FileOutputStream fileOutputStream = new FileOutputStream(fileFrom);
        fileOutputStream.write("Test string".getBytes());
        fileOutputStream.close();

        FileOutputStream fileOutputStream1 = new FileOutputStream(subFileFrom);
        fileOutputStream1.write("Test string in sub file".getBytes());
        fileOutputStream1.close();

        // Create a temp folder where all data will be copied
        Path pathWhere = Files.createTempDirectory("tempWhere");
        assertEquals(0, FileManager.countDirs(pathWhere.toString()));
        assertEquals(0, FileManager.countFiles(pathWhere.toString()));

        FileManager.move(pathFrom.toString(), pathWhere.toString());

        assertEquals(1, FileManager.countDirs(pathWhere.toString()));
        assertEquals(2, FileManager.countFiles(pathWhere.toString()));
        Assertions.assertThrows(NullPointerException.class, () -> {
            FileManager.countFiles(pathFrom.toString());
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            FileManager.countDirs(pathFrom.toString());
        });

        // reading all data from first txt file
        FileInputStream fileInputStream = new FileInputStream(pathWhere.toString() + File.separator + fileFrom.getName());
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = fileInputStream.readAllBytes();
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append((char)bytes[i]);
        }
        fileInputStream.close();

        // reading from sub txt file
        FileInputStream fileInputStream1 = new FileInputStream(pathWhere.toString() + File.separator + pathFromSubDirectory.getFileName() + File.separator + subFileFrom.getName());
        StringBuilder stringBuilder1 = new StringBuilder();
        byte[] bytes1 = fileInputStream1.readAllBytes();
        for (int i = 0; i < bytes1.length; i++) {
            stringBuilder1.append((char)bytes1[i]);
        }
        fileInputStream1.close();

        assertEquals("Test string", stringBuilder.toString());
        assertEquals("Test string in sub file", stringBuilder1.toString());
    }
}
