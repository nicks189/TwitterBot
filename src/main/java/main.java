import bot.TwitterBot;
import com.rometools.rome.io.FeedException;
import content.RomeFeed;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utils.Log;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicks189 on 7/15/17.
 */
public class main {
    public static void main(String[] args) {
        try {
            Log log = Log.getSingleton();

            Path logPath = Paths.get(System.getProperty("user.dir"));
            logPath = Paths.get(logPath.toString(), "logs", "log.txt");
            log.addFile(logPath.toFile());

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("src/main/resources/bot-properties.json"));
            String url = (String) jsonObject.get("url");

            JSONArray jsonArray = (JSONArray) jsonObject.get("keywords");
            List<String> keywords = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                keywords.add((String)jsonArray.get(i));
            }

            System.out.println(keywords);

            RomeFeed feed = new RomeFeed(url, keywords);
            TwitterBot bot = new TwitterBot(feed);
            bot.start();

        } catch (Exception e) {

            System.err.println("Couldn't set up bot! Make sure you added src/main/resources/twitter4j.properties");

        }
    }
}
