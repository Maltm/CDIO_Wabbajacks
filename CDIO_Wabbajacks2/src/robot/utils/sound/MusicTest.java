package robot.utils.sound;

import lejos.nxt.Sound;

public class MusicTest {
   private static String[] melody = { "C4", "D4", "E4", "C4", 
      "E4", "C4",   "E4"};

   public static void main(String[] args) {
      Music music = new Music();
      for (int i = 0; i < melody.length; i++) {
         music.musicPiano(melody[i], 300);
         System.out.println(melody[i]);
      }
      Sound.pause(300);
      for (int i = 0; i < melody.length; i++) {
         music.musicTone(melody[i], 300);
         System.out.println(melody[i]);
      }
   }
}