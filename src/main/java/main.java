import bot.TwitterBot;
import com.rometools.rome.io.FeedException;
import content.RomeFeed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicks189 on 7/15/17.
 */
public class main {
    public static void main(String[] args) {
        try {
            // String url = "https://news.google.com/news/rss/search/section/q/java%20programming%20language/java%20programming%20language";
            String url = "https://news.google.com/news/rss/headlines/section/topic/TECHNOLOGY";
            List<String> keywords = new ArrayList<>();
            keywords.add("java");
            keywords.add("cloud");
            keywords.add("dev");
            keywords.add("iphone");
            keywords.add("android");
            RomeFeed feed = new RomeFeed(url, keywords);

            TwitterBot bot = new TwitterBot(feed);

            System.out.println(bot.performAction());
        } catch (FeedException | IOException e) {
            System.out.println("Something went wrong.");
        }
    }
}
