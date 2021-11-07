package com.luxoft.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileAnalyzer {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] pathWord = bufferedReader.readLine().split(" ");
        showResult(wordCounter(pathWord[0], pathWord[1]));
        bufferedReader.close();
    }

    public static List<String> wordCounter(String path, String word) throws IOException {
        ArrayList<String> resultList = new ArrayList<>();
        int counter = 0;
        FileInputStream fileInputStream = new FileInputStream(path);

        List<String> list = new ArrayList();
        int data = fileInputStream.read();
        StringBuilder stringBuilder = new StringBuilder();

        while (data != -1){
            char c = (char)data;
            stringBuilder.append(c);
            data = fileInputStream.read();
        }

        String[] expressions = stringBuilder.toString().replaceAll("\\.", ".#").replaceAll("!", "!#").replaceAll("\\?", "?#").split("#");

        Pattern pattern = Pattern.compile("(^|\\s+)"+ word +"(\\s+|[.!?,]|$)");

        for (String s : expressions){
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()){
                counter++;
                list.add(s.trim());
            }
        }
        resultList.add(counter + "");
        for (String expression : list){
            resultList.add(expression);
        }
        fileInputStream.close();

        return resultList;
    }

    public static void showResult(List<String> list){
        for (String string : list){
            if((list.indexOf(string)) == 0){
                System.out.println("Word meets " + string + " times");
            }else{
                System.out.println(string);
            }
        }
    }
}
