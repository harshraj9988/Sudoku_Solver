package com.example.sudoku_solver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private Solver gameBoardSolver;
    private Button solveBTN;
    private Button resetBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();
        solveBTN = findViewById(R.id.solve);
        resetBTN = findViewById(R.id.reset);
    }

    public void reset(View view) {
        gameBoardSolver.resetSudoku();
        gameBoard.invalidate();
    }

    public void solveBoard(View view) {
        gameBoardSolver.solveSudoku();
        gameBoard.invalidate();
    }


    public void BTNOnePress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),1);
        gameBoard.invalidate();
    }

    public void BTNTwoPress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),2);
        gameBoard.invalidate();
    }

    public void BTNThreePress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),3);
        gameBoard.invalidate();
    }

    public void BTNFourPress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),4);
        gameBoard.invalidate();
    }

    public void BTNFivePress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),5);
        gameBoard.invalidate();
    }

    public void BTNSixPress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),6);
        gameBoard.invalidate();
    }

    public void BTNSevenPress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),7);
        gameBoard.invalidate();
    }

    public void BTNEightPress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),8);
        gameBoard.invalidate();
    }

    public void BTNNinePress(View view) {
        gameBoardSolver.setNumberPos(this.getBaseContext(),9);
        gameBoard.invalidate();
    }


}