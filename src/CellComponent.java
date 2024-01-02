import javax.swing.*;
public class CellComponent extends JLabel{
    private final int row;
    private final int col;
    private boolean refreshPaused;

    public CellComponent(int row, int col) {
        this.row = row;
        this.col = col;
        this.setLocation(row * IconsHolder.CELL_SIZE,col * IconsHolder.CELL_SIZE);
        this.setSize(IconsHolder.CELL_SIZE, IconsHolder.CELL_SIZE);

    }

    public void setState(Cell cell, boolean refresh) {
        if(!refresh) {
            refreshPaused = true;
        }
        if (cell.isCovered()) {
            if (cell.isFlag()){
                this.setIcon(IconsHolder.flag);
            } else {
                this.setIcon(IconsHolder.covered);
            }
        } else {
            if (cell.getNumber() == 0){
                this.setIcon(IconsHolder.empty);
            } else if (cell.getNumber() == -1) {
                this.setIcon(IconsHolder.bomb);
            } else if (cell.getNumber() == -2) {
                this.setIcon(IconsHolder.exBomb);
            } else {
                this.setIcon(IconsHolder.icons[cell.getNumber() - 1]);
            }
        }
        if(!refresh) {
            refreshPaused = false;
        }
    }
    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }

    @Override
    public void revalidate() {
        if (!refreshPaused) {
            super.revalidate();
        }
    }

    @Override
    public void repaint() {
        if (!refreshPaused) {
            super.repaint();
        }
    }
}