package com.syt;

/**
 * Created by Think on 2017/7/4.
 */
public class SudokuBoardPtr {

    public SudokuBoardPtr() {
        this.row = 0;
        this.col = 0;
    }

    public SudokuBoardPtr(int x, int y) {
        this.row = x;
        this.col = y;
    }

    public int row;
    public int col;

    public int row() {
        return this.row;
    }

    public int col() {
        return this.col;
    }

    public void moveTo(int x, int y) {
        this.row = x;
        this.col = y;
    }
}
