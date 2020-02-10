package pong.GameObject;

import pong.Audio;
import pong.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MultiColorBlock extends GameObject {
    private GameWorld gw;
    private Audio audio;
    private int points = 4;

    public MultiColorBlock(double x, double y, BufferedImage img, GameWorld gw) {
        super(x, y, img);
        this.gw = gw;
        this.audio = gw.getAudio();
    }

    public MultiColorBlock(double x, double y, BufferedImage img, GameWorld gw, boolean dblePts) {
        super(x, y, img);
        this.gw = gw;
        if (dblePts) {
            points *= 2;
        }
    }

    private void genPts() {
        Random rand = new Random();
        int min = 25;
        int max = 50;
        points = rand.nextInt(max + min);
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public Rectangle2D.Double getBounds2D() {
        return new Rectangle2D.Double(x, y, imgWidth, imgHeight);
    }

    @Override
    public void update() {

    }

    @Override
    public void handleCollision(Collidable obj) {
        if (obj instanceof Pop) {
            this.isVisible = false;
            gw.addGameScore(points);
            audio.playBlock();
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        g2d.drawImage(this.img,rotation, null);
    }
}
