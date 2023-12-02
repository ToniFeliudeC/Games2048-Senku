package com.example.games;

import java.util.ArrayList;

public class Game2048Utils {

    public static ArrayList<ArrayList<Integer>> rotateRight(ArrayList<ArrayList<Integer>> board) {
        int rows = board.size();
        int cols = board.get(0).size();
        ArrayList<ArrayList<Integer>> rotatedBoard = new ArrayList<>(cols);

        for (int col = 0; col < cols; col++) {
            ArrayList<Integer> newColumn = new ArrayList<>(rows);
            for (int row = rows - 1; row >= 0; row--) {
                newColumn.add(board.get(row).get(col));
            }
            rotatedBoard.add(newColumn);
        }

        return rotatedBoard;
    }

    public static ArrayList<ArrayList<Integer>> rotateLeft(ArrayList<ArrayList<Integer>> board) {
        int rows = board.size();
        int cols = board.get(0).size();
        ArrayList<ArrayList<Integer>> rotatedBoard = new ArrayList<>(cols);

        for (int col = cols - 1; col >= 0; col--) {
            ArrayList<Integer> newColumn = new ArrayList<>(rows);
            for (int row = 0; row < rows; row++) {
                newColumn.add(board.get(row).get(col));
            }
            rotatedBoard.add(newColumn);
        }

        return rotatedBoard;
    }

    public static ArrayList<ArrayList<Integer>> rotate180(ArrayList<ArrayList<Integer>> board) {
        int rows = board.size();
        int cols = board.get(0).size();
        ArrayList<ArrayList<Integer>> rotatedBoard = new ArrayList<>(rows);

        for (int row = rows - 1; row >= 0; row--) {
            ArrayList<Integer> newRow = new ArrayList<>(cols);
            for (int col = cols - 1; col >= 0; col--) {
                newRow.add(board.get(row).get(col));
            }
            rotatedBoard.add(newRow);
        }

        return rotatedBoard;
    }
}
