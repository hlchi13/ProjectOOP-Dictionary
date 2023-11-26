package Dictionary;

import javafx.scene.control.Alert;
import javazoom.jl.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslatorAPI {
    /** Reference: https://stackoverflow.com/questions/8147284/how-to-use-google-translate-api-in-my-java-application*/
    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbyiLQ9zsiCig51jIswW7bvrfOeN4EnEtX0a7fYVjvE7BGBfH_9AUmzf5dRvk6k7yt7ynQ/exec"
                + "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void playGoogleSound(String target, String language) {
        try {
            /**
             * Reference: https://stackoverflow.com/questions/30599797/google-translate-api-text-to-speech-http-requests-forbidden
             * https://translate.google.com.vn/translate_tts?ie=UTF-8&q=ANYTHING_TEXT&tl=en&client=tw-ob
             */
            target = URLEncoder.encode(target);
            URL url = new URL("https://translate.google.com/translate_tts?ie=UTF-8" +
                    "&q=" + target +
                    "&tl=" + language +
                    "&client=tw-ob");
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            InputStream audio = urlConn.getInputStream();
            new Player(audio).play();
            urlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Vượt quá số kí tự có thể dịch được");

            alert.showAndWait();
        }
    }
}
