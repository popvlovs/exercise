package com.syt;

/**
 * Created by Think on 2017/7/9.
 */
public class EightQueens {
    static final int queensNum = 9;
    static int count = 0;
    boolean[][] board;

    public EightQueens() {
        this.board = new boolean[queensNum][queensNum];
    }

    private void print() {
        StringBuilder text = new StringBuilder();
        text.append("Solve " + count + ":\n");
        for (int i = 0; i < queensNum; ++i) {
            for (int j = 0; j < queensNum; ++j) {
                text.append(this.board[i][j] ? "● " : "○ ");
            }
            text.append("\n");
        }
        System.out.println(text);
        count++;
    }

    private void set(int i, int j) {
        this.board[i][j] = true;
    }

    private void clear(int i, int j) {
        this.board[i][j] = false;
    }

    private int nextRow() {
        for (int i = 0; i < queensNum; ++i) {
            boolean pastRow = false;
            for (int j = 0; j < queensNum; ++j) {
                if (board[i][j]) {
                    pastRow = true;
                    break;
                }
            }
            if (!pastRow) {
                return i;
            }
        }
        return queensNum + 1;
    }

    private boolean check(int row, int col) {
        for (int i = 0; i < queensNum; ++i) {
            if (i != row && board[i][col])
                return false;
        }

        for (int i = 0; i < queensNum; ++i) {
            if (i != col && board[row][i])
                return false;
        }

        for (int i = row + 1, j = col + 1; inRange(i, j); ++i, ++j) {
            if (board[i][j])
                return false;
        }

        for (int i = row + 1, j = col - 1; inRange(i, j); ++i, --j) {
            if (board[i][j])
                return false;
        }

        for (int i = row - 1, j = col + 1; inRange(i, j); --i, ++j) {
            if (board[i][j])
                return false;
        }

        for (int i = row - 1, j = col - 1; inRange(i, j); --i, --j) {
            if (board[i][j])
                return false;
        }
        return true;
    }

    private boolean inRange(int row, int col)

    {
        return row >= 0 && row < queensNum && col >= 0 && col < queensNum;
    }

    public void solve() {
        int row = nextRow();
        if (row >= queensNum) {
            print();
            return;
        }
        for (int i = 0; i < queensNum; ++i) {
            set(row, i);
            if (check(row, i)) {
                solve();
            }
            clear(row, i);
        }
    }
}
