package twitter;

import twitter4j.TwitterException;

import java.util.List;

/**
 * Created by nicks189 on 7/11/17.
 */
public interface TwitterApi {
    // Add abstract class at some point for shared implementation

    Tweet tweet(String tweet);

    Tweet tweet(Tweet tweet);

    String username();

    List<Tweet> getMyTweets();

    List<Tweet> getUserTweets(String username);

    List<Tweet> getTimeline();

    List<Tweet> getFavorites();

    List<String> getFollowedUsers();

    boolean followUser(String user);

    boolean unfollowUser(String user);

    boolean checkFollowingUser(String user);

    Tweet favoriteTweet(Tweet tweet);

    Tweet unfavoriteTweet(Tweet tweet);

    List<Tweet> search(String keyword);
}
