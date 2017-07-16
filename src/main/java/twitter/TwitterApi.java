package twitter;

import twitter4j.TwitterException;

import java.util.List;

/**
 * Created by nicks189 on 7/11/17.
 */
public interface TwitterApi {
    // Add abstract class at some point for shared implementation

    void tweet(String tweet) throws TwitterException;

    void tweet(Tweet tweet) throws TwitterException;

    String username();

    List getMyTweets();

    List getUserTweets(String username);

    List getTimeline();

    List getFavorites();
}
