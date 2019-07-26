package pong.GameObject;

import pong.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SplitPopBlock extends GameObject {
    private GameWorld gw;

    public SplitPopBlock(double x, double y, BufferedImage img, GameWorld gw) {
        super(x, y, img);
        this.gw = gw;
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
            Pop pop = (Pop) obj;
            gw.addSplitPop(pop.getX(), pop.getY());
            this.isVisible = false;
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        g2d.drawImage(this.img,rotation, null);
    }
}
