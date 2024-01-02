public class Cell {
    private boolean isCovered;
    private int number;
    private boolean isMine;
    private boolean isFlag;

    Cell(boolean isCovered, int number, boolean isMine, boolean isFlag) {
        this.isCovered = isCovered;
        this.number = number;
        this.isMine = isMine;
        this.isFlag = isFlag;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine() {
        isMine = true;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean f) {
        isFlag = f;
    }
}