package bot;

import content.Entry;
import content.Feed;
import twitter.Tweet;
import twitter.Twitter4JApi;
import twitter.TwitterApi;
import twitter4j.TwitterException;
import utils.Log;

import java.util.List;
import java.util.Random;

/**
 * Created by nicks189 on 7/11/17.
 */
public class TwitterBot {
    private Feed feed;
    private TwitterApi twitter;
    private Log log;

    public TwitterBot(Feed feed) {
        this.feed = feed;
        twitter = Twitter4JApi.getSingleton();
        log = Log.getSingleton();
    }

    public boolean performAction() {
        return buildAndSendTweet();
    }

    public boolean buildAndSendTweet() {
        List<Entry> feedEntries = feed.getEntries();
        Entry entry = randomEntry(feedEntries);

        String title = entry.getTitle();
        String hashtags = generateHashtags(entry.getTitle());
        String url = entry.getUrl();

        // Tweet in format of <Title> <Hashtags> <Link>
        String tweetmsg = title + " " + hashtags + " " + url;

        if(tweetmsg.length() > 140) {
            if(url.length() + hashtags.length() > 140) {
                log.add("Article url is too long; tweet can't be sent.");
                return false;
            }
            // Truncate title and add enough space for "... "
            tweetmsg = title.substring(0, title.length() - (tweetmsg.length() - 135));
            tweetmsg += "... ";
            tweetmsg += hashtags + " ";
            tweetmsg += url;
        }

        Tweet tweet = new Tweet(tweetmsg, url);
        if(tweet.checkTweetExists()) {
            log.add("Tweet with similar content already exists.");
            return false;
        }

        try {

            twitter.tweet(tweet);
            log.add("Tweet sent.");
            return true;

        } catch(TwitterException e) {

            log.add("Failed to send tweet.");
            return false;

        }
    }

    public boolean findFavorite() {
        return false;
    }

    public boolean findUnfavorite() {
        return false;
    }

    public boolean findFollow() {
        return false;
    }

    public boolean findUnfollow() {
        return false;
    }

    private Entry randomEntry(List<Entry> entries) {
        Random rand = new Random();
        return entries.get(rand.nextInt(entries.size()));
    }

    private String generateHashtags(String entryTitle) {
        Random rand = new Random();
        List<String> keywords = feed.getKeywords();
        String hashtags = "";

        // Max 3 hashtags
        int number = rand.nextInt(4);

        if(keywords.size() == 1 && number % 2 == 0) {
            hashtags = "#" + keywords.get(0).toLowerCase();
        } else if(keywords.size() == 0) {
            // Do nothing
        } else {
            for(int i = 0; i < number; i++) {
                if(entryTitle.toLowerCase().contains(keywords.get(i).toLowerCase())) {
                    hashtags += "#" + keywords.get(i).toLowerCase() + " ";
                }
            }
        }

        return hashtags;
    }
}
