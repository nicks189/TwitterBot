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
    private Random rand;

    public TwitterBot(Feed feed) {
        this.feed = feed;
        twitter = Twitter4JApi.getSingleton();
        log = Log.getSingleton();
        rand = new Random();
    }

    public boolean performAction() {
        rand.setSeed(System.currentTimeMillis());
        int num = rand.nextInt(3);

        if(num == 0) {
            return buildAndSendTweet();
        } else if(num == 1) {
            return findFavorite();
        } else if(num == 2) {
            return findFollow();
        }
        return false;
    }

    public boolean buildAndSendTweet() {
        List<Entry> feedEntries = feed.getEntries();
        if(feedEntries.isEmpty()) {
            log.add("Couldn't find any articles to tweet about.");
            return false;
        }
        Entry entry = (Entry) randomElement(feedEntries);

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

        // Need to add some sort of tweet verification here


        // If the returned tweet doesn't have an id then it failed
        if(twitter.tweet(tweet).getId() == 0) {
            return false;
        }
        return true;
    }

    public boolean findFavorite() {
        String keyword = (String) randomElement(feed.getKeywords());
        System.out.println(keyword);
        List<Tweet> tweets = twitter.search(keyword);

        // Should add some verification here


        Tweet tweet = (Tweet) randomElement(tweets);
        if(tweet == null) {
            log.add("Couldn't find any tweets to favorite.");
            return false;
        }
        tweet = twitter.favoriteTweet(tweet);
        if(!tweet.isFavorited()) {
            return false;
        }
        return true;
    }

    public boolean findUnfavorite() {
        return false;
    }

    public boolean findFollow() {
        List<Tweet> favorites = twitter.getFavorites();
        Tweet tweet = (Tweet) randomElement(favorites);

        if(tweet == null) {
            log.add("There are no favorited tweets to follow from.");
            return false;
        }

        String user = tweet.getCreator();
        if(twitter.checkFollowingUser(user)) {
            log.add("Already following user " + user);
            return false;
        }
        return twitter.followUser(user);
    }

    public boolean findUnfollow() {
        return false;
    }

    private Object randomElement(List elements) {
        if(elements.size() != 0) {
            return elements.get(rand.nextInt(elements.size()));
        }
        return null;
    }

    private String generateHashtags(String entryTitle) {
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
