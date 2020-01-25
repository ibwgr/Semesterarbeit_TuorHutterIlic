package TankWarsGame.GUI;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Music {
    /*String path;

    public Music(String pathToMusic){
        this.path = pathToMusic;
    }*/

    public static void playMusic(String path)
    {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();

            /*FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)
            gainControl.setValue(0.5f);
            Thread.sleep(clip.getMicrosecondLength());
            */}
        catch (Exception e) {
        }
    }

    public static void playMusicContinous(String path)
    {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception e) {
        }
    }
}
