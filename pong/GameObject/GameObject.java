package pong.GameObject;

import java.awt.image.BufferedImage;

public abstract class GameObject implements Collidable, Drawable {
    protected double x, y;
    protected BufferedImage img;
    protected BufferedImage[] imgAnim;
    protected int imgWidth, imgHeight;
    protected boolean isVisible;

    public GameObject(double x, double y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.imgWidth = img.getWidth();
        this.imgHeight = img.getHeight();
        isVisible = true;
    }

    public GameObject(double x, double y, BufferedImage[] imgAnim) {
        this.x = x;
        this.y = y;
        this.imgAnim = imgAnim;
        this.imgWidth = imgAnim[0].getWidth();
        this.imgHeight = imgAnim[0].getHeight();
        isVisible = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
