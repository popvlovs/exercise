package com.syt;

/**
 * Created by Think on 2017/7/4.
 */

public class SudokuBoard {

    private int[][] board;
    static final int size = 9;
    static final int cellSize = 3;

    public SudokuBoard(int[][] board) {

        this.board = board;
    }

    public void set(SudokuBoardPtr pos, int num) {
        board[pos.row()][pos.col()] = num;
    }

    public int get(SudokuBoardPtr pos) {
        return board[pos.row()][pos.col()];
    }

    public SudokuBoardPtr next() {
        SudokuBoardPtr curPos = new SudokuBoardPtr();
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j) {
                curPos.moveTo(i, j);
                if (get(curPos) == 0) {
                    return curPos;
                }
            }
        return curPos;
    }

    public boolean hasNext() {
        SudokuBoardPtr endPos = new SudokuBoardPtr(8, 8);
        return get(endPos) == 0;
    }

    public void erase(SudokuBoardPtr pos) {
        set(pos, 0);
    }

    public void print() {
        StringBuilder sudokuText = new StringBuilder();
        SudokuBoardPtr curPos = new SudokuBoardPtr();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                curPos.moveTo(i, j);
                sudokuText.append(get(curPos) + "  ");
            }
            sudokuText.append("\n");
        }
        System.out.println(sudokuText);
    }

    public boolean solve() {
        if (!this.hasNext())
            return true;

        //this.print();

        SudokuBoardPtr curPos = this.next();

        for (int val = 1; val != 10; ++val) {
            this.set(curPos, val);
            if (this.check(curPos) && solve()) {
                return true;
            }
            this.erase(curPos);
        }
        return false;
    }

    public boolean check(SudokuBoardPtr pos) {
        // 核心判断逻辑是行、列和3x3的格内都不重复
        SudokuBoardPtr curPos = new SudokuBoardPtr();
        int[] temp = new int[10]; // 0-9

        // 判断行
        for (int i = 0; i < size; ++i) {
            curPos.moveTo(pos.row(), i);
            int val = get(curPos);
            temp[val]++;
        }
        for (int i = 1; i <= size; ++i) {
            if (temp[i] >= 2)
                return false;
        }

        // 判断列
        temp = new int[10];
        for (int i = 0; i < size; ++i) {
            curPos.moveTo(i, pos.col());
            int val = get(curPos);
            temp[val]++;
        }
        for (int i = 1; i <= size; ++i) {
            if (temp[i] >= 2)
                return false;
        }

        // 判断3x3格子
        temp = new int[10];
        int row = pos.row() - pos.row() % cellSize;
        int col = pos.col() - pos.col() % cellSize;
        for (int i = row; i < row + cellSize; ++i)
            for (int j = col; j < col + cellSize; ++j) {
                curPos.moveTo(i, j);
                int val = get(curPos);
                temp[val]++;
            }
        for (int i = 1; i <= size; ++i) {
            if (temp[i] >= 2)
                return false;
        }

        return true;
    }
}
