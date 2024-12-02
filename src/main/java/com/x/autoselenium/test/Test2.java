package com.x.autoselenium.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test2 {
    public static void main(String[] args) {

        String directoryPath = "C:/Users/admin/Desktop/test";

        File directory = new File(directoryPath);

        // 确保目录存在
        if (directory.exists() && directory.isDirectory()) {
            // 获取所有子文件夹
            File[] subDirectories = directory.listFiles(File::isDirectory);

            // 遍历并打印子文件夹名称
            if (subDirectories != null) {
                for (File subDirectory : subDirectories) {
                    System.out.println(subDirectory.getName());

                    String oldName = subDirectory.getName();
                    String newName = oldName.replace("huskj9","aaaaaaa");
                    subDirectory.renameTo(new File(subDirectory.getParent() +"\\" + newName));
                }
            }
        } else {
            System.out.println("The directory does not exist or is not a directory.");
        }

    }
}
