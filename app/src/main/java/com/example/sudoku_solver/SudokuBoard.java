package com.example.sudoku_solver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class SudokuBoard extends View {



    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;

    private final int letterColor;

    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    private int cellSize;
    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();

    private final Solver solver = new Solver();

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // A TypedArray for storing all the attributes declared in the attributes.xml file
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.SudokuBoard, 0, 0);

        try {
            // Extracting the board color from the TypedArray
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);
            letterColor = a.getInteger(R.styleable.SudokuBoard_letterColor, 0);

        } finally {
            // free up some memory after extracting the attributes.
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        // dimension to make the board square
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        // each cell size in the sudoku board
        cellSize = dimension / 9;
        //setting the dimensions of the sudoku board to be a square
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //drawing the border of the sudoku board
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true);

        //colors the filled cells
        cellFillColorPaint.setStyle(Paint.Style.FILL);
        cellFillColorPaint.setColor(cellFillColor);
        cellFillColorPaint.setAntiAlias(true);

        //highlights the row and column of the selected cell
        cellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        cellsHighlightColorPaint.setColor(cellsHighlightColor);
        cellsHighlightColorPaint.setAntiAlias(true);


        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColor);


        colorCell(canvas, solver.getSelected_row(), solver.getSelected_column());
        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        drawCells(canvas);
        drawNumbers(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isValid;
        // getting x and y co-ordinate of users' touch input
        float x = event.getX();
        float y = event.getY();

        int act = event.getAction();
        if (act == MotionEvent.ACTION_DOWN) {

            //selecting the row and column according to the users' touch co-ordinates
            solver.setSelected_row((int) Math.ceil(y / cellSize));
            solver.setSelected_column((int) Math.ceil(x / cellSize));
            isValid = true;

        } else isValid = false;

        return isValid;
    }


    private void drawNumbers(Canvas canvas) {

        letterPaint.setTextSize(cellSize - 2); //TODO : change the text size here
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (solver.getBoard()[r][c] != 0) {
                    String text = Integer.toString(solver.getBoard()[r][c]);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();


                    canvas.drawText(text, (c * cellSize) + ((cellSize - width) / 2),
                            (r * cellSize + cellSize) - ((cellSize - height) / 2), letterPaint);
                }
            }
        }
    }


    private void colorCell(Canvas canvas, int row, int column) {
        if (solver.getSelected_column() != -1 && solver.getSelected_row() != -1) {
            //highlighting the selected column
            canvas.drawRect((column - 1) * cellSize, 0, column * cellSize
                    , cellSize * 9, cellsHighlightColorPaint);
            //highlighting the selected row
            canvas.drawRect(0, (row - 1) * cellSize, cellSize * 9
                    , row * cellSize, cellsHighlightColorPaint);
            //highlighting the selected cell
            canvas.drawRect((column - 1) * cellSize, (row - 1) * cellSize, column * cellSize
                    , row * cellSize, cellsHighlightColorPaint);
        }
        invalidate();   //refreshes the board;
    }

    private void drawThinLines() {
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(4);
        boardColorPaint.setColor(boardColor);
    }

    private void drawThickLines() {
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);
    }

    private void drawCells(Canvas canvas) {
        for (int c = 0; c < 10; c++) {
            if (c % 3 == 0) drawThickLines();
            else drawThinLines();

            canvas.drawRect(cellSize * c, 0, cellSize * c, getHeight(), boardColorPaint);
        }
        for (int r = 0; r < 10; r++) {
            if (r % 3 == 0) drawThickLines();
            else drawThinLines();
            canvas.drawRect(0, cellSize * r, getWidth(), cellSize * r, boardColorPaint);
        }
    }

    public Solver getSolver() {
        return this.solver;
    }
}
