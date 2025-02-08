package org.example;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class BackupService {
    public static void createBackup(String sourcePath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path backup = Paths.get("./backup");

        // Создаём директорию для резервных копий, если она не существует
        if (!Files.exists(backup)) {
            Files.createDirectory(backup);
            System.out.println("Создана папка для резервных копий: " + backup.toAbsolutePath());
        }

        // Копируем только файлы (без директорий)
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            int copiedFiles = 0;
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    Path destination = backup.resolve(file.getFileName());
                    Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING);
                    copiedFiles++;
                    System.out.println("Скопирован файл: " + file.getFileName());
                }
            }
            System.out.println("Всего скопировано файлов: " + copiedFiles);
        }
    }

    public static void main(String[] args) {
        try {
            createBackup("./source");
            System.out.println("Резервное копирование успешно завершено!");
        } catch (IOException e) {
            System.err.println("Ошибка при создании резервной копии: " + e.getMessage());
        }
    }
}