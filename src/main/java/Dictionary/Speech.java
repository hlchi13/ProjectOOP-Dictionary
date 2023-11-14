package Dictionary;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Speech {
    public static void speakInEn(String text) {
         System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
         Voice voice = VoiceManager.getInstance().getVoice("kevin16");
         if (voice != null) {
             voice.setRate(175);
             voice.allocate();
             voice.speak(text);
         } else throw new IllegalStateException("Cannot find voice: kevin16");
    }
}
