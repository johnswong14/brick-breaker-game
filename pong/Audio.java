package pong;

import javax.sound.sampled.*;
import java.io.File;

public class Audio {
    private String bg, bigleg, block, katch, lost, wall;
    private Clip bgSound, biglegSound, blockSound, katchSound, lostSound, wallSound;

    public Audio() {
        bg = "Resources/audio/Music.mid";
        bigleg = "Resources/audio/Sound_bigleg.wav";
        block = "Resources/audio/Sound_block.wav";
        katch = "Resources/audio/Sound_katch.wav";
        lost = "Resources/audio/Sound_lost.wav";
        wall = "Resources/audio/Sound_wall.wav";

        bgSound = createSound(bg);
        bgSound.loop(5);
    }

    private Clip createSound(String fileName) {
        try{
            File soundFile = new File(fileName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
            return clip;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private synchronized void playSound(Clip clip){
        clip.start();
    }

    public synchronized void playBigLeg(){
        biglegSound = createSound(bigleg);
        playSound(biglegSound);
    }

    public synchronized void playBlock(){
        blockSound = createSound(block);
        playSound(blockSound);
    }

    public synchronized void playKatch(){
        katchSound = createSound(katch);
        playSound(katchSound);
    }

    public synchronized void playLost(){
        lostSound = createSound(lost);
        playSound(lostSound);
    }

    public synchronized void playWall(){
        wallSound = createSound(wall);
        playSound(wallSound);
    }
}
