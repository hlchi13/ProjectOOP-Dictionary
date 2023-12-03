package Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameTime {
    private Timer timer;
    private TimerTask timerTask;
    private Label timerLabel;
    private int totalTime;
    private boolean isEnd;


    public GameTime(Label timerLabel, int totalTime) {
        timer = new Timer();
        this.timerLabel = timerLabel;
        this.totalTime = totalTime;
        isEnd = false;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
        if (isEnd == true) {
            timer.cancel();
        }
    }

    public void run() {
        timerTask = new TimerTask() {
            int secondsLeft = totalTime;
            @Override
            public void run() {
                    if (secondsLeft >= 0) {
                        long minutes = TimeUnit.SECONDS.toMinutes(secondsLeft);
                        long seconds = secondsLeft - minutes * 60;
                        Platform.runLater(() -> {
                            timerLabel.setText("Time left: " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                        });
                        secondsLeft--;

                    } else {
                        isEnd = true;
                        timerTask.cancel();
                    }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
}
