package twitter;

import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nicks189 on 7/15/17.
 */
public class Tweet {
    private long id = 0;
    private String creator;
    private String value;
    private TwitterApi twitter;

    public Tweet() {
        twitter = Twitter4JApi.getSingleton();
        creator = twitter.username();
    }

    public Tweet(String value) {
        twitter = Twitter4JApi.getSingleton();
        creator = twitter.username();
        this.value = value;
    }

    public Tweet(String value, String creator) {
        twitter = Twitter4JApi.getSingleton();
        this.value = value;
        this.creator = creator;
    }

    public Tweet(String value, long id) {
        twitter = Twitter4JApi.getSingleton();
        creator = twitter.username();
        this.id = id;
        this.value = value;
    }

    public Tweet(String value, String creator, long id) {
        twitter = Twitter4JApi.getSingleton();
        this.id = id;
        this.value = value;
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFavorited() {
        // TODO
        return false;
    }

    public boolean setFavorite() {
        // TODO
        return false;
    }

    public void sendTweet() throws TwitterException {
        twitter.tweet(this);
    }

    public boolean checkTweetExists() {
        List<Tweet> myTweets = twitter.getMyTweets();

        return false;
    }
}
