package org.example;


public class Main {
    public static void main(String[] args) {
        try {
            // Запускаем backup
            System.out.println("Запуск резервного копирования...");
            BackupService.createBackup("/app/data");

            // Запускаем TicTacToe
            System.out.println("\nЗапуск теста крестиков-ноликов...");
            int[] board = {0, 1, 2, 0, 1, 0, 2, 1, 0};
            TicTacToeSerializer.saveGameState(board, "/app/data/game.dat");

            System.out.println("Все тесты выполнены успешно!");
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
}