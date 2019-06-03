import java.applet.AudioClip;
import java.applet.Applet;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Sound
{
      //public static final AudioClip LASER_TURRET_PRIMARY = Applet.newAudioClip(Sound.class.getResource("/Sounds/Laser_Turret/laser_turret_primary.wav"));
      
      public static final File LASER_TURRET_PRIMARY = new File("/Sound/Laser_Turret/laser_turret_primary.wav");
      
      
      public static void playSound(File soundFile) throws Exception {
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            
            DataLine.Info info = new DataLine.Info( Clip.class, sound.getFormat() );
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(sound);
            
            clip.addLineListener(new LineListener() {
                  
                  public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                              event.getLine().close();
                        }
                  }
            });
            
            clip.start();
      }

}