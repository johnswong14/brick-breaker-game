package pong.GameObject;

import pong.GameWorld;
import pong.Audio;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class UnbreakableBlock extends GameObject {
    private GameWorld gw;
    private Audio audio;

    public UnbreakableBlock(double x, double y, BufferedImage img, GameWorld gw) {
        super(x, y, img);
        this.gw = gw;
        this.audio = gw.getAudio();
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
            audio.playBlock();
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        g2d.drawImage(this.img,rotation, null);
    }
}
