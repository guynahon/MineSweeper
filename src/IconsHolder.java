import javax.swing.*;
import java.awt.*;

public class IconsHolder {
    public static final int CELL_SIZE = 40;
    public static ImageIcon covered = loadIcon("covered.png");
    public static ImageIcon empty = loadIcon("uncovered.png");
    public static ImageIcon[] icons = {
            loadIcon("1.png"),
            loadIcon("2.png"),
            loadIcon("3.png"),
            loadIcon("4.png"),
            loadIcon("5.png"),
            loadIcon("6.png"),
            loadIcon("7.png"),
            loadIcon("8.png")
    };
    public static ImageIcon bomb = loadIcon("Bomb.png");
    public static ImageIcon exBomb = loadIcon("BombExploded.png");
    public static ImageIcon flag = loadIcon("FLAG.png");
    public static ImageIcon smiley = loadIcon("smiley.png");
    public static ImageIcon victory = loadIcon("sunglass.png");
    public static ImageIcon dead = loadIcon("dead.png");


    private static ImageIcon loadIcon (String filename) {
        ImageIcon originalIcon = new ImageIcon(filename);
        Image image = originalIcon.getImage();
        Image newImage = image.getScaledInstance(CELL_SIZE, CELL_SIZE,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
}