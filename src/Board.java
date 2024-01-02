public class Board {

    private Cell cells[][] = new Cell[Game.numRows][Game.numCols];

    public Board() {
        inItCells();
        putMines();
        putNumbers();
    }

    private void inItCells() {
        for (int i = 0; i < Game.numRows; i++) {
            for (int j = 0; j < Game.numCols; j++) {
                cells[i][j] = new Cell(true, 0, false, false);
            }
        }
    }
    // spreading the mines randomly across the board
    private void putMines() {
        for (int i = 0; i < Game.minesNum;) {
            int rh = (int) (Math.random() * Game.numRows);
            int rw = (int) (Math.random() * Game.numCols);
            if (!cells[rh][rw].isMine()) {
                cells[rh][rw].setMine();
                i++;
            }
        }
    }
    // spreading the numbers around the mines, if a cell for example
    // has 2 mines around it the value will be 2, if it doesn't have
    // mines around it, it will be zero, and if it's a mine it will be -1
    private void putNumbers() {
        for (int i = 0; i < Game.numRows; i++) {
            for (int j = 0; j < Game.numCols; j++) {
                int counter = 0;
                if (cells[i][j].isMine()) {
                    cells[i][j].setNumber(-1);
                } else {
                    if (i != 0 && cells[i - 1][j].isMine()) {
                        counter++;
                    }
                    if (i != 0 && j != Game.numCols -1 && cells[i - 1][j + 1].isMine()) {
                        counter++;
                    }
                    if (j != Game.numCols -1 && cells[i][j + 1].isMine()) {
                        counter++;
                    }
                    if (i != Game.numRows -1 && j != Game.numCols -1 && cells[i + 1][j + 1].isMine()) {
                        counter++;
                    }
                    if (i != Game.numRows -1 && cells[i + 1][j].isMine()) {
                        counter++;
                    }
                    if (i != Game.numRows -1 && j != 0 && cells[i + 1][j - 1].isMine()) {
                        counter++;
                    }
                    if (j != 0 && cells[i][j - 1].isMine()) {
                        counter++;
                    }
                    if (i != 0 && j != 0 && cells[i - 1][j - 1].isMine()) {
                        counter++;
                    }
                    cells[i][j].setNumber(counter);
                }
            }
        }
    }
    public int getNumber(int row, int col) {
        return cells[row][col].getNumber();
    }
    public boolean isCovered(int row, int col) {
        return cells[row][col].isCovered();
    }
    public void unCover(int row, int col) {
        cells[row][col].setCovered(false);
    }
    public void cover(int row, int col) {
        cells[row][col].setCovered(true);
    }
    public void explode(int row, int col) {
        cells[row][col].setNumber(-2);
    }
    public boolean isFlag(int row, int col) {
        return cells[row][col].isFlag();
    }
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
}