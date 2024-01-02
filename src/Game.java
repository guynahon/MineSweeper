import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Game implements MouseListener {
    private JFrame frame;
    private JButton restartButton;
    private JPanel mainPanel;
    public static final int numCols = 9;
    public static final int numRows = 9;
    public static final int minesNum = 10;
    private Board board;
    private int count;
    private CellComponent[][] cellsComp = new CellComponent[numRows][numCols];
    public Game() {
        frame = new JFrame();
        //frame.setSize(IconsHolder.CELL_SIZE * height, IconsHolder.CELL_SIZE * width);
        frame.setTitle("Guy's MineSweeper");
        ImageIcon logo = new ImageIcon("BombExploded.png");
        frame.setIconImage(logo.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        mainPanel = new JPanel();
        mainPanel.setLocation(0, 0);
        mainPanel.setLayout(new GridLayout(numRows, numCols));
        restartButton = new JButton();
        restartButton.setIcon(IconsHolder.smiley);
        restartButton.setBorder(null);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(restartButton);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel);
        inItCellComp();
        resetBoard();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void resetBoard() {
        count = 0;
        restartButton.setIcon(IconsHolder.smiley);
        board = new Board();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                coverCell(row, col, false);
            }
        }
        frame.revalidate();
        frame.repaint();
    }
    public void inItCellComp() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                cellsComp[row][col] = new CellComponent(row, col);
                cellsComp[row][col].addMouseListener(this);
                mainPanel.add(cellsComp[row][col]);
            }
        }
    }
    public void openEmptyCells(int row, int col) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols || board.getNumber(row, col) == -1 || !board.isCovered(row, col)) {
            return;
        }
        if (board.getNumber(row, col) == 0) {
            openCell(row, col, false);
            openEmptyCells(row - 1, col);
            openEmptyCells(row - 1, col + 1);
            openEmptyCells(row, col + 1);
            openEmptyCells(row + 1, col + 1);
            openEmptyCells(row + 1, col);
            openEmptyCells(row + 1, col - 1);
            openEmptyCells(row, col - 1);
            openEmptyCells(row - 1, col - 1);
        } else if (board.getNumber(row, col) >= 1 && board.getNumber(row, col) <= 8) {
            openCell(row, col, false);
        }
    }
    public void openAllCells(int row, int col) {
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                openCell(i, j, false);
            }
        }
        frame.revalidate();
        frame.repaint();
    }
    public void openCell(int row, int col, boolean refresh) {
        board.unCover(row, col);
        updateCellUI(row, col, refresh);
        count++;
        if (count == numRows * numCols - minesNum) {
            restartButton.setIcon(IconsHolder.victory);
        }
    }
    public void coverCell(int row, int col, boolean refresh) {
        board.cover(row, col);
        updateCellUI(row, col, refresh);
    }
    public void updateCellUI(int row, int col, boolean refresh) {
        cellsComp[row][col].setState(board.getCell(row, col), refresh);
    }
    public void flag(int row, int col, boolean f) {
        board.getCell(row, col).setFlag(f);
        updateCellUI(row, col, true);
    }
    public void openSemi(int row, int col) {
        int num = board.getCell(row, col).getNumber();
        int sum = 0;
        if (isFlagAndMine(row - 1, col)) { sum++; }
        if (isFlagAndMine(row - 1, col + 1)) {sum++;}
        if (isFlagAndMine(row, col + 1)) {sum++;}
        if (isFlagAndMine(row + 1, col + 1)) {sum++;}
        if (isFlagAndMine(row + 1, col)) {sum++;}
        if (isFlagAndMine(row + 1, col - 1)) {sum++;}
        if (isFlagAndMine(row, col - 1)) {sum++;}
        if (isFlagAndMine(row - 1, col - 1)) {sum++;}
        if (sum == num) {
            openCellAndEmptyCellsAroundIt(row - 1, col);
            openCellAndEmptyCellsAroundIt(row - 1, col + 1);
            openCellAndEmptyCellsAroundIt(row, col + 1);
            openCellAndEmptyCellsAroundIt(row + 1, col + 1);
            openCellAndEmptyCellsAroundIt(row + 1, col);
            openCellAndEmptyCellsAroundIt(row + 1, col - 1);
            openCellAndEmptyCellsAroundIt(row, col - 1);
            openCellAndEmptyCellsAroundIt(row - 1, col - 1);
            frame.revalidate();
            frame.repaint();
        }
    }
    public boolean isFlagAndMine(int row, int col) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols) {
            return false;
        }
        return board.isFlag(row, col) && board.getCell(row, col).isMine();
    }
    private void openCellAndEmptyCellsAroundIt(int row, int col) {
        if (row < 0 || row >= this.numRows || col < 0 || col >= this.numCols) {
            return;
        }
        if (board.isCovered(row, col) && !board.getCell(row, col).isMine()) {
            openCell(row, col, false);
            openEmptyCells(row, col);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        CellComponent comp = (CellComponent) e.getComponent();
        boolean isLeft = SwingUtilities.isLeftMouseButton(e);
        boolean isRight = SwingUtilities.isRightMouseButton(e);
        int r = comp.getRow();
        int c = comp.getCol();
        if (isRight && !isLeft && board.isCovered(r, c)) {
            flag(r, c, !board.isFlag(r, c));
        } else if (isLeft && !isRight && e.isAltDown()) {
            openSemi(r, c);
        } else if (isLeft && !isRight) {
            if (!board.isCovered(r, c)) {
                //return
            } else if (board.getNumber(r, c) == -1) {
                board.explode(r, c);
                openAllCells(r, c);
                restartButton.setIcon(IconsHolder.dead);
            } else if (board.getNumber(r, c) == 0) {
                openEmptyCells(r, c);
                frame.revalidate();
                frame.repaint();
            } else {
                openCell(r, c, true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}