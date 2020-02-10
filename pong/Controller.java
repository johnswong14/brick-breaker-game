package pong;

import pong.GameObject.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

    private GameWorld gw;
    private Katch katch;
    private Pop pop;
    private final int leftKey;
    private final int rightKey;
    private final int spaceKey;

    public Controller(GameWorld gw, Katch katch, Pop pop, int leftKey, int rightKey, int spaceKey) {
        this.gw = gw;
        this.katch = katch;
        this.pop = pop;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.spaceKey = spaceKey;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == leftKey) {
            this.katch.setLeftPressed(true);
        }
        if (keyPressed == rightKey) {
            this.katch.setRightPressed(true);
        }
        if (keyPressed == spaceKey) {
            this.gw.setGameStart(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == leftKey) {
            this.katch.setLeftPressed(false);
        }
        if (keyReleased  == rightKey) {
            this.katch.setRightPressed(false);
        }
    }
}
