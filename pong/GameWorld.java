package pong;

import pong.GameObject.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import static javax.imageio.ImageIO.read;

public class GameWorld extends JPanel {
    public static final String GAME_TITLE = "Super Rainbow Reef Pong";
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 500;
    private JFrame jf;
    private Audio audio;
    private BufferedImage world;
    private Graphics2D worldBuffer;
    private BufferedImage bg1img;
    private BufferedImage congratsImg;
    private BufferedImage purpBlockImg;
    private BufferedImage yellowBlockImg;
    private BufferedImage redBlockImg;
    private BufferedImage greenBlockImg;
    private BufferedImage tealBlockImg;
    private BufferedImage blueBlockImg;
    private BufferedImage whiteBlockImg;
    private BufferedImage dblePtsBlockImg;
    private BufferedImage wallBlockImg;
    private BufferedImage unbBlockImg;
    private BufferedImage lifeBlockImg;
    private BufferedImage splitBlockImg;
    private BufferedImage smBigLegImg;
    private BufferedImage lgBigLegImg;
    private BufferedImage[] katchImg;
    private BufferedImage popImg;
    private Katch katch;
    private Pop pop;
    private Controller ctrl;
    private boolean gameStart;
    private boolean gameOver;
    private boolean gameFinished;
    private int gameScore;
    private int lives = 3;
    private int level = 1;
    private int bigLegCount;

    private FileInputStream mapFileName;
    private BufferedReader mapFile;

    private ArrayList<GameObject> gameObjs;

    public static void main(String[] args) {
        GameWorld gw = new GameWorld();
        gw.init();
        try {
            while (true) {
                if (gw.isAlive()) {
                    if (gw.isMapFinished()) {
                        if (gw.level != 3) {
                            gw.loadNextMap();
                        }
                        else {
                            gw.gameStart = false;
                            gw.gameOver = true;
                            gw.gameFinished = true;
                        }
                    }
                    gw.katch.update();
                    if (gw.gameStart) {
                        gw.updateCollidables();
                        gw.checkCollision();
                    }
                }
                else {
                    gw.gameOver = true;
                }
                gw.repaint();
                Thread.sleep(1000 / 144);
            }
        }
        catch (InterruptedException ignored) {
        }
    }

    private void init() {
        System.out.println(System.getProperty("user.dir"));

        this.jf = new JFrame(GAME_TITLE);

        this.world = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

        loadAudio();
        loadList();
        loadImg();
        loadMap();
        loadKatch();
        loadPop();
        loadCtrl();

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);
        this.jf.setSize(SCREEN_WIDTH, SCREEN_HEIGHT + 22);
        this.jf.setResizable(false);
        this.jf.setLocationRelativeTo(null);
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }

    private void loadAudio() {
        audio = new Audio();
    }

    private void loadList() {
        gameObjs = new ArrayList<>();
    }

    private void loadImg() {
        try {
            bg1img = read(new File("Resources/sprite/Background1.bmp"));
            congratsImg = read(new File("Resources/sprite/Congratulation.gif"));

            purpBlockImg = read(new File("Resources/sprite/Block1.gif"));
            yellowBlockImg = read(new File("Resources/sprite/Block2.gif"));
            redBlockImg = read(new File("Resources/sprite/Block3.gif"));
            greenBlockImg = read(new File("Resources/sprite/Block4.gif"));
            tealBlockImg = read(new File("Resources/sprite/Block5.gif"));
            blueBlockImg = read(new File("Resources/sprite/Block6.gif"));
            whiteBlockImg = read(new File("Resources/sprite/Block7.gif"));
            dblePtsBlockImg = read(new File("Resources/sprite/Block_double.gif"));
            wallBlockImg = read(new File("Resources/sprite/Wall.gif"));
            unbBlockImg = read(new File("Resources/sprite/Block_solid.gif"));
            lifeBlockImg = read(new File("Resources/sprite/Block_life.gif"));
            splitBlockImg = read(new File("Resources/sprite/Block_split.gif"));
            smBigLegImg = read(new File("Resources/sprite/Bigleg_small.png"));
            lgBigLegImg = read(new File("Resources/sprite/Bigleg.png"));

            katchImg = new BufferedImage[2];
            for (int i = 0; i < 2; i++) {
                katchImg[i] = read(new File("Resources/sprite/Katch_" + (i+1) + ".gif"));
            }

            popImg = read(new File("Resources/sprite/Pop.png"));
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    // 16x24 Blocks
    private void loadMap() {
        //map = new MapLoader(this);

        try {
            mapFileName = new FileInputStream("Resources/map/map_" + level + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mapFile = new BufferedReader(new InputStreamReader(mapFileName));

        try {
            String mapTextLine;
            int x = 0;
            int y = 0;
            mapTextLine = mapFile.readLine();
            while(mapTextLine != null){
                for (int i = 0; i < mapTextLine.length(); i++){
                    // purple block
                    if (mapTextLine.charAt(i) == 'P'){
                        MultiColorBlock mcb = new MultiColorBlock(x, y, purpBlockImg, this);
                        gameObjs.add(mcb);
                    }
                    // yellow block
                    else if (mapTextLine.charAt(i) == 'Y'){
                        MultiColorBlock mcb = new MultiColorBlock(x, y, yellowBlockImg, this);
                        gameObjs.add(mcb);
                    }
                    // red block
                    else if (mapTextLine.charAt(i) == 'R'){
                        MultiColorBlock mcb = new MultiColorBlock(x, y, redBlockImg, this);
                        gameObjs.add(mcb);
                    }
                    // green block
                    else if (mapTextLine.charAt(i) == 'G'){
                        MultiColorBlock mcb = new MultiColorBlock(x, y, greenBlockImg, this);
                        gameObjs.add(mcb);
                    }
                    // teal block
                    else if (mapTextLine.charAt(i) == 'T'){
                        MultiColorBlock mcb = new MultiColorBlock(x, y, tealBlockImg, this);
                        gameObjs.add(mcb);
                    }
                    // blue block
                    else if (mapTextLine.charAt(i) == 'B'){
                        MultiColorBlock mcb = new MultiColorBlock(x, y, blueBlockImg, this);
                        gameObjs.add(mcb);
                    }
                    // white block
                    else if (mapTextLine.charAt(i) == 'W'){
                        MultiColorBlock mcb = new MultiColorBlock(x, y, whiteBlockImg, this);
                        gameObjs.add(mcb);
                    }
                    // double pts block
                    else if (mapTextLine.charAt(i) == 'D'){
                        MultiColorBlock mcb = new MultiColorBlock(x, y, dblePtsBlockImg, this, true);
                        gameObjs.add(mcb);
                    }
                    // wall block
                    else if (mapTextLine.charAt(i) == '1'){
                        WallBlock wall = new WallBlock(x, y, wallBlockImg, this);
                        gameObjs.add(wall);
                    }
                    // unbreakable block
                    else if (mapTextLine.charAt(i) == '2'){
                        UnbreakableBlock unb = new UnbreakableBlock(x, y, unbBlockImg, this);
                        gameObjs.add(unb);
                    }
                    // life block
                    else if (mapTextLine.charAt(i) == '3'){
                        LifeBlock life = new LifeBlock(x, y, lifeBlockImg, this);
                        gameObjs.add(life);
                    }
                    // split pop block
                    else if (mapTextLine.charAt(i) == '4'){
                        SplitPopBlock split = new SplitPopBlock(x, y, splitBlockImg, this);
                        gameObjs.add(split);
                    }
                    // small big leg
                    else if (mapTextLine.charAt(i) == '5'){
                        SmallBigLeg smBL = new SmallBigLeg(x, y, smBigLegImg, this);
                        gameObjs.add(smBL);
                        bigLegCount += 1;
                    }
                    // big leg
                    else if (mapTextLine.charAt(i) == '6'){
                        LargeBigLeg lgBL = new LargeBigLeg(x, y, lgBigLegImg, this);
                        gameObjs.add(lgBL);
                        bigLegCount += 1;
                    }
                    x += wallBlockImg.getWidth();
                }
                x = 0;
                y += wallBlockImg.getHeight();
                mapTextLine = mapFile.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadKatch() {
        katch = new Katch((SCREEN_WIDTH/2)-(katchImg[0].getWidth()/2), 425, katchImg, this);
        gameObjs.add(katch);
    }

    private void loadPop() {
        pop = new Pop((SCREEN_WIDTH/2)-(popImg.getWidth()/2), 300, popImg, this);
        pop.setCount(1);
        gameObjs.add(pop);
    }

    private void loadCtrl() {
        ctrl = new Controller(this, katch, pop, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
        jf.addKeyListener(ctrl);
    }

    private void updateCollidables() {
        for (int i = 0; i < gameObjs.size(); i++) {
            Collidable collidable = gameObjs.get(i);
            if (collidable instanceof Katch) {
                continue;
            }
            if (collidable.isVisible()) {
                collidable.update();
            }
            else {
                if (collidable instanceof SmallBigLeg || collidable instanceof LargeBigLeg) {
                    bigLegCount -= 1;
                }
                gameObjs.remove(i);
                i--;
            }
        }
    }

    private void checkCollision() {
        for (int i = 0; i < gameObjs.size(); i++) {
            Collidable co1 = gameObjs.get(i);
            if (!(co1 instanceof Pop)) {
                continue;
            }
            Rectangle2D.Double co1_rect = co1.getBounds2D();
            for (int j = 0; j < gameObjs.size(); j++) {
                if (j == i) {
                    continue;
                }
                if (gameObjs.get(j) instanceof Pop) {
                    continue;
                }
                else {
                    Collidable co2 = gameObjs.get(j);
                    Rectangle2D.Double co2_rect = co2.getBounds2D();
                    if (co1_rect.intersects(co2_rect)) {
                        co1.handleCollision(co2);
                        co2.handleCollision(co1);
                    }
                }
            }
        }
    }

    private boolean isAlive() {
        if (lives > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isMapFinished() {
        if (bigLegCount == 0) {
            gameObjs.clear();
            pop.resetSpeed();
            return true;
        }
        else {
            return false;
        }
    }

    private void loadNextMap() {
        gameStart = false;
        level++;
        loadMap();
        loadKatch();
        loadPop();
        loadCtrl();
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }

    public void addGameScore(int newScore) {
        this.gameScore += newScore;
    }

    public void addLives(int lives) {
        if (this.lives < 3) {
            this.lives += lives;
        }
    }

    public void subLives(int lives) {
        if (this.lives > 0) {
            this.lives -= lives;
        }
    }

    public void addSplitPop(double x, double y) {
        gameObjs.add(new Pop(x, y, popImg, this));
    }

    public Audio getAudio() {
        return audio;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(world,0,0,null);
        worldBuffer = world.createGraphics();

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial Black", Font.BOLD, 15));

        if (!gameOver) {
            worldBuffer.drawImage(bg1img, 0, 0,null);

            for (int i = 0; i < gameObjs.size(); i++) {
                gameObjs.get(i).drawImage(worldBuffer);
            }

            g2.drawString("Score: " + this.gameScore, 20, 495);
            g2.drawString("Level " + this.level + "", 300, 495);

            // pop lives
            for (int i = 0; i < this.lives; i++) {
                g2.drawImage(popImg, 600 - (i * popImg.getWidth()/2), 480, popImg.getWidth()/2, popImg.getHeight()/2, null);
            }
        }
        else if (gameFinished) {
            g2.drawImage(congratsImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
            g2.drawString("High Score: " + this.gameScore, 20, 495);
        }
        else {
            g2.setFont(new Font("Arial Black", Font.BOLD, 20));
            g2.setColor(Color.BLACK);
            g2.drawString("Game Over", 260, 200);
            g2.drawString("High Score: " + this.gameScore, 250, 250);
            g2.setColor(Color.WHITE);
            g2.drawString("Game Over", 261, 201);
            g2.drawString("High Score: " + this.gameScore, 251, 251);
        }
    }
}
