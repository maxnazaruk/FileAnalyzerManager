package com.luxoft.io;

import java.io.*;

public class FileManager {
    public static void main(String[] args) throws IOException {
        //System.out.println(countFiles("h:\\book"));
        //System.out.println(countDirs("h:\\book"));

        move("h:\\aaa", "h:\\bbb");
    }

    public static int countFiles(String path){
        int count = 0;
            File[] file = new File(path).listFiles();

            for (int i = 0; i < file.length; i++) {
                if(file[i].isDirectory()){
                    count += countFiles(file[i].getAbsolutePath());
                }else if(file[i].isFile()){
                    count++;
                }
            }

        return count;
    }

    public static int countDirs(String path){
        int count = 0;
        File[] file = new File(path).listFiles();
        for (int i = 0; i < file.length; i++) {
            if(file[i].isDirectory()){
                count++;
                count += countDirs(file[i].getAbsolutePath());
            }
        }
        return count;
    }

    public static void copy(String from, String to) throws IOException {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
            File[] file = new File(from).listFiles();
        for (int i = 0; i < file.length; i++) {
            if(file[i].isFile()){
                String newFile = to + File.separator + file[i].getName();
                new File(newFile).createNewFile();

                fileInputStream = new FileInputStream(file[i]);
                fileOutputStream = new FileOutputStream(newFile);
                fileOutputStream.write(fileInputStream.readAllBytes());
                fileInputStream.close();
                fileOutputStream.close();
            }else if(file[i].isDirectory()){
                String newFolder = to + File.separator +  file[i].getName();
                new File(newFolder).mkdir();
                copy(file[i].getAbsolutePath(), newFolder);
            }
        }
        return;
    }

    public static void move(String from, String to) throws IOException {
        copy(from, to);
        removeDirectory(new File(from));
    }

    public static void removeDirectory(File folder){
        File[] allContents = folder.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                removeDirectory(file);
            }
        }
        folder.delete();
        return;
    }
}
