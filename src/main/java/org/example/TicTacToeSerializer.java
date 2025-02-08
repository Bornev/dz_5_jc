package org.example;

import java.io.*;

public class TicTacToeSerializer {
    public static void saveGameState(int[] board, String filename) throws IOException {
        if (board.length != 9) {
            throw new IllegalArgumentException("Поле должно содержать ровно 9 элементов");
        }

        // Каждая ячейка занимает 2 бита (0-3), поэтому в один байт помещается 4 ячейки
        byte[] bytes = new byte[3];

        // Упаковываем первый байт (ячейки 0-3)
        bytes[0] = (byte) ((board[0] << 6) | (board[1] << 4) | (board[2] << 2) | board[3]);

        // Упаковываем второй байт (ячейки 4-7)
        bytes[1] = (byte) ((board[4] << 6) | (board[5] << 4) | (board[6] << 2) | board[7]);

        // Упаковываем третий байт (ячейка 8 + дополнение)
        bytes[2] = (byte) (board[8] << 6);

        // Записываем в файл
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(bytes);
        }
    }

    public static int[] loadGameState(String filename) throws IOException {
        byte[] bytes = new byte[3];
        int[] board = new int[9];

        try (FileInputStream fis = new FileInputStream(filename)) {
            fis.read(bytes);
        }

        // Распаковываем первый байт
        board[0] = (bytes[0] >> 6) & 0x03;
        board[1] = (bytes[0] >> 4) & 0x03;
        board[2] = (bytes[0] >> 2) & 0x03;
        board[3] = bytes[0] & 0x03;

        // Распаковываем второй байт
        board[4] = (bytes[1] >> 6) & 0x03;
        board[5] = (bytes[1] >> 4) & 0x03;
        board[6] = (bytes[1] >> 2) & 0x03;
        board[7] = bytes[1] & 0x03;

        // Распаковываем третий байт
        board[8] = (bytes[2] >> 6) & 0x03;

        return board;
    }

    // Вспомогательный метод для вывода состояния поля
    public static void printBoard(int[] board) {
        System.out.println("Текущее состояние поля:");
        for (int i = 0; i < 9; i += 3) {
            System.out.printf("%d %d %d%n", board[i], board[i + 1], board[i + 2]);
        }
    }

    public static void main(String[] args) {
        try {
            // Пример состояния игры: 0 - пусто, 1 - X, 2 - O, 3 - резерв
            int[] board = {0, 1, 2, 0, 1, 0, 2, 1, 0};

            System.out.println("Исходное состояние:");
            printBoard(board);

            saveGameState(board, "game.dat");
            System.out.println("Состояние игры успешно сохранено!");

            int[] loadedBoard = loadGameState("game.dat");
            System.out.println("\nЗагруженное состояние:");
            printBoard(loadedBoard);

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
