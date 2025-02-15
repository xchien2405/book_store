package com.nhom1.bookstore.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nhom1.bookstore.entity.Book;

public class Optimization {
    final static String DEFAULT_PATH = "/img/product/";
    final static String DEFAULT_FOLDER = "src/main/resources/static/img/product";

    public static void cleanImage(List<Book> bookList) {
        for (int i = 0; i < getAllImageFileName().size(); i++) {
            List<String> fileNames = getAllImageFileName();
            boolean found = false;
            for (Book book : bookList) {
                if (fileNames.get(i).equals(book.getBookImage())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                File fileToDelete = getAllFile()[i];
                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                    System.out.println("Deleted file: " + fileToDelete.getName());
                }
                i--;
            }
        }
    }

    private static List<String> getAllImageFileName() {
        File[] files = getAllFile();
        List<String> imageFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file.getName())) {
                    imageFiles.add(DEFAULT_PATH + file.getName());
                }
            }
        }
        return imageFiles;
    }

    private static File[] getAllFile() {
        File folder = new File(DEFAULT_FOLDER);
        return folder.listFiles();
    }

    private static boolean isImageFile(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
                || fileName.endsWith(".gif")) {
            return true;
        } else {
            return false;
        }
    }
}
