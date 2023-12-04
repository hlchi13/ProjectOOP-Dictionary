package Game.CatWord;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class CatWord {
    private Map map;
    private boolean isRunning;
    private MediaPlayer bg_music;
    public CatWord() {
        isRunning = true;
        map = new Map();
        bg_music = new MediaPlayer(new Media
                (new File("./src/main/resources/Game/sound/gameBg.mp3").toURI().toString()));
    }

    public void update(KeyEvent event) {
        map.update(event);
    }

    public void render(GraphicsContext gc) {
        if (isRunning) {
            map.render(gc);
            bg_music.play();
        } else {
            bg_music.stop();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
