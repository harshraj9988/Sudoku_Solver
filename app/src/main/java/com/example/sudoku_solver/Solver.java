package com.example.sudoku_solver;


import android.content.Context;
import android.widget.Toast;

public class Solver {
    int[][] board;

    int selected_row;
    int selected_column;


    Solver() {
        selected_row = -1;
        selected_column = -1;


        board = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }

    }

    //adds an user input number
    public void setNumberPos(Context mContext,int num) {
        if (this.selected_row != -1 && this.selected_column != -1) {
            if (this.board[this.selected_row - 1][this.selected_column - 1] == num) {
                this.board[this.selected_row - 1][this.selected_column - 1] = 0;
            }else if(isSafe(this.selected_row - 1, this.selected_column - 1, num)){
                this.board[this.selected_row - 1][this.selected_column - 1] = num;
            }
            else {
                this.board[this.selected_row - 1][this.selected_column - 1] = 0;
                Toast.makeText(mContext, "Invalid Input", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int[][] getBoard() {
        return this.board;
    }


    public int getSelected_row() {
        return selected_row;
    }

    public void setSelected_row(int selected_row) {
        this.selected_row = selected_row;
    }

    public int getSelected_column() {
        return selected_column;
    }

    public void setSelected_column(int selected_column) {
        this.selected_column = selected_column;
    }


    //logic to solve the board
    private boolean solve() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int c = 1; c <= 9; c++) {
                        if (isSafe(row, col, c)) {
                            board[row][col] = c;
                            if (solve())
                                return true;
                            else
                                board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }

        //TODO return the answered 2d array from this point
        return true;
    }

    private boolean isSafe(int row, int col, int c) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == c)
                return false;
            if (board[row][i] == c)
                return false;
            if (board[3 * (row / 3) + (i / 3)][3 * (col / 3) + i % 3] == c)
                return false;
        }
        return true;
    }

    public void solveSudoku() {
        solve();
    }


    //reset the board
    public void resetSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }
    }
}

