package com.luxoft.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileAnalyserTest {

    @Test
    public void testVerifyWordMeetsJustOnce() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "test text duck";

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(1, Integer.parseInt(FileAnalyzer.wordCounter("h:\\tmp.txt", "duck").get(0)));
    }

    @Test
    public void testVerifyWordMeetsCoupleTimes() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "test duck. text duck";

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(2, Integer.parseInt(FileAnalyzer.wordCounter("h:\\tmp.txt", "duck").get(0)));
    }

    @Test
    public void testVerifyWordMeetsOnceInCoupleOfExpressionsTimes() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "First expression which doesn't contain word. text duck";

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(1, Integer.parseInt(FileAnalyzer.wordCounter("h:\\tmp.txt", "duck").get(0)));
    }

    @Test
    public void testVerifyWordMeetsInDifferentExpressions() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "First duck. text duck? This expression ends with duck exclamation sign!";

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(3, Integer.parseInt(FileAnalyzer.wordCounter("h:\\tmp.txt", "duck").get(0)));
    }

    @Test
    public void testVerifyAllExpressionsShow() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "First duck. text duck? This expression ends with duck exclamation sign!";

        StringBuilder stringBuilder = new StringBuilder(testText);
        String[] expressions = stringBuilder.toString().replaceAll("\\.", ".#").replaceAll("!", "!#").replaceAll("\\?", "?#").split("#");
        ArrayList<String> list = new ArrayList<>();
        list.add("3");
        for (String s : expressions){
            list.add(s.trim());
        }

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(list, FileAnalyzer.wordCounter("h:\\tmp.txt", "duck"));
    }

    @Test
    public void testVerifyAllExpressionsShowExeptOne() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "First duck. text duck? This expression ends with exclamation sign!";

        StringBuilder stringBuilder = new StringBuilder(testText);
        String[] expressions = stringBuilder.toString().replaceAll("\\.", ".#").replaceAll("!", "!#").replaceAll("\\?", "?#").split("#");
        ArrayList<String> list = new ArrayList<>();
        list.add("2");
        for (String s : expressions){
            list.add(s.trim());
        }

        list.remove(3);

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(list, FileAnalyzer.wordCounter("h:\\tmp.txt", "duck"));
    }

    @Test
    public void testVerifyEmptyFile() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "";

        ArrayList<String> list = new ArrayList<>();
        list.add("0");

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(list, FileAnalyzer.wordCounter("h:\\tmp.txt", "duck"));
    }

    @Test
    public void testVerifySingleWord() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "duck";

        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("duck");

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(list, FileAnalyzer.wordCounter("h:\\tmp.txt", "duck"));
    }

    @Test
    public void testVerifySingleNonMatchableWord() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "duck1";

        ArrayList<String> list = new ArrayList<>();
        list.add("0");

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(list, FileAnalyzer.wordCounter("h:\\tmp.txt", "duck"));
    }

    @Test
    public void testVerifySingleMatchableWordWithUpperCase() throws IOException {
        File.createTempFile("tmp", "txt", new File("h:\\"));

        String testText = "ducK";

        ArrayList<String> list = new ArrayList<>();
        list.add("0");

        FileOutputStream fileOutputStream = new FileOutputStream("h:\\tmp.txt");
        fileOutputStream.write(testText.getBytes());
        fileOutputStream.close();

        assertEquals(list, FileAnalyzer.wordCounter("h:\\tmp.txt", "duck"));
    }
}
