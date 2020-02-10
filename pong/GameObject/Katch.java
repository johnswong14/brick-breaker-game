package pong.GameObject;

import pong.GameWorld;
import pong.Audio;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Katch extends GameObject {

    private GameWorld gw;
    private Audio audio;
    private final int SPEED = 4;
    private final double GLIDE = 0.90;
    private boolean RightPressed;
    private boolean LeftPressed;
    private double vx;
    private int imgCounter;

    public Katch(double x, double y, BufferedImage[] imgAnim, GameWorld gw) {
        super(x, y, imgAnim);
        this.gw = gw;
        this.audio = gw.getAudio();
    }

    private void checkBorder() {
        if (x <= 20) {
            x = 20;
        }
        if (x >= GameWorld.SCREEN_WIDTH - 100) {
            x = GameWorld.SCREEN_WIDTH - 100;
        }
    }

    private BufferedImage getImage() {
        if (imgCounter < 100) {
            imgCounter++;
            return imgAnim[0];
        }
        else if (imgCounter >= 100 && imgCounter < 200) {
            imgCounter++;
            return imgAnim[1];
        }
        else {
            imgCounter = 1;
            return imgAnim[0];
        }
    }

    public void setRightPressed(boolean rightPressed) {
        this.RightPressed = rightPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.LeftPressed = leftPressed;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public Rectangle2D.Double getBounds2D() {
        return new Rectangle2D.Double(x, y, imgWidth, imgHeight);
    }

    @Override
    public void update() {
        if (this.LeftPressed) {
            vx -= SPEED;
        }
        else if (this.RightPressed) {
            vx += SPEED;
        }
        else if (!this.LeftPressed && !this.RightPressed) {
            vx *= GLIDE;
        }
        // limit velocity
        if (vx >= SPEED) {
            vx = SPEED;
        }
        else if (vx <= -SPEED) {
            vx = -SPEED;
        }
        x += vx;
        checkBorder();
    }

    @Override
    public void handleCollision(Collidable obj) {
        if (obj instanceof Pop) {
            audio.playKatch();
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        g2d.drawImage(getImage(), rotation, null);
    }
}
