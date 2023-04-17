import java.awt.image.BufferedImage;

public class BoardImage {
    public int type;
    public BufferedImage img;

    public BoardImage(int t, BufferedImage i) {
        type = t;
        img = i;
    }
}
