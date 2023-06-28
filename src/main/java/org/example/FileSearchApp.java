package org.example;


import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileSearchApp {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java FileSearchApp <file mask> <directory>");

        }

        String fileMask = args[0];
        String directory = args[1];

        ExecutorService executor = Executors.newFixedThreadPool(10); // создаем пул потоков

        searchFiles(new File(directory), fileMask, executor); // запускаем поиск файлов

        executor.shutdown(); // останавливаем пул потоков

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // ждем завершения всех потоков
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void searchFiles(File directory, String fileMask, ExecutorService executor) {
        if (!directory.isDirectory()) {
            System.out.println(directory.getAbsolutePath() + " is not a directory");

        }

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                searchFiles(file, fileMask, executor); // рекурсивно ищем в поддиректориях
            } else if (file.getName().matches(fileMask)) {
                executor.execute(() -> System.out.println(file.getAbsolutePath())); // выводим путь к найденному файлу
            }
        }
    }
}








   


