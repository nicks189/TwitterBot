package twitter;

import twitter4j.TwitterException;

import java.util.List;

/**
 * Created by nicks189 on 7/11/17.
 */
public interface TwitterApi {
    // Add abstract class at some point for shared implementation

    Tweet tweet(String tweet) throws TwitterException;

    Tweet tweet(Tweet tweet) throws TwitterException;

    String username();

    List<Tweet> getMyTweets();

    List<Tweet> getUserTweets(String username);

    List<Tweet> getTimeline();

    List<Tweet> getFavorites();
}
