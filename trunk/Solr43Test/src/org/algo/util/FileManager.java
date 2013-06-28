package org.algo.util;

import java.io.File;

public class FileManager {

    String dir = "";

    String temp = "";

    public String[] serachFiles(String dir) {

        File root = new File(dir);

        File[] filesOrDirs = root.listFiles();

        for (int i = 0; i < filesOrDirs.length; i++) {
            if (filesOrDirs[i].isDirectory()) {
                serachFiles(filesOrDirs[i].getAbsolutePath());
            } else {
                temp += filesOrDirs[i].getAbsolutePath() + ",";

            }
        }

        return temp.split(",");

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        FileManager fm = new FileManager();
        String[] files = fm.serachFiles("E:/data");
        for (int i = 0; i < files.length; i++) {
            System.out.println("files[" + i + "]" + files[i]);
        }

    }
}