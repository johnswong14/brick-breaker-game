package pong.GameObject;

import pong.Audio;
import pong.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SmallBigLeg extends GameObject {
    private GameWorld gw;
    private Audio audio;
    private int health;

    public SmallBigLeg(double x, double y, BufferedImage img, GameWorld gw) {
        super(x, y, img);
        this.gw = gw;
        this.audio = gw.getAudio();
        this.health = 100;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public Rectangle2D.Double getBounds2D() {
        return new Rectangle2D.Double(x, y, imgWidth, imgHeight);
    }

    @Ove    rride
    public void update() {
    }

    @Override
    public void handleCollision(Collidable obj) {
        if (obj instanceof Pop) {
            this.health -= 50;
            gw.addGameScore(10);
            audio.playBigLeg();
            if (health <= 0) {
                this.isVisible = false;
            }
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        g2d.drawImage(this.img,rotation, null);
    }
}
