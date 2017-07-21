package twitter;

import twitter4j.*;
import utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicks189 on 7/11/17.
 */
public class Twitter4JApi implements TwitterApi {
    private Twitter twitter;
    private Log log;

    private static TwitterApi instance = new Twitter4JApi();

    public static TwitterApi getSingleton() { return instance; }

    private Twitter4JApi() {
        twitter = TwitterFactory.getSingleton();
        log = Log.getSingleton();
    }

    public Tweet tweet(String tweet) throws TwitterException {
        return statusToTweet(twitter.updateStatus(tweet));
    }

    public Tweet tweet(Tweet tweet) throws TwitterException {
        return statusToTweet(twitter.updateStatus(tweet.getValue()));
    }

    public List<Tweet> getMyTweets() {
        return getUserTweets(username());
    }

    public String username() {
        String res = "";
        try {
            res = twitter.getScreenName();
        } catch(TwitterException e) {
            log.add("Couldn't resolve bot username.");
        }
        return res;
    }

    public List<Tweet> getUserTweets(String username) {
        // Use pagination to get as many tweets as the API allows (usually around 3,200)
        List<Tweet> tweets = new ArrayList();
        Paging page = new Paging(1, 200);

        while(true) {
            try {
                int size = tweets.size();
                tweets.addAll(statusesToTweets(twitter.getUserTimeline(username, page)));
                page.setPage(page.getPage() + 1);

                // Limit to 10000
                if (tweets.size() == size || tweets.size() >= 10000) {
                    break;
                }
            } catch(TwitterException e) {
                log.add("Couldn't get tweets.");
            }
        }

        return tweets;
    }

    public List<Tweet> getTimeline() {
        List<Tweet> tweets = new ArrayList();
        Paging page = new Paging(1, 200);

        while(true) {
            try {
                int size = tweets.size();
                tweets.addAll(statusesToTweets(twitter.getHomeTimeline(page)));
                page.setPage(page.getPage() + 1);

                // Limit to 10000
                if (tweets.size() == size || tweets.size() >= 10000) {
                    break;
                }
            } catch(TwitterException e) {
                log.add("Couldn't get timeline.");
            }
        }

        return tweets;
    }

    public List<Tweet> getFavorites() {
        List<Tweet> tweets = new ArrayList();
        Paging page = new Paging(1, 200);

        while(true) {
            try {
                int size = tweets.size();
                tweets.addAll(statusesToTweets(twitter.getFavorites(page)));
                page.setPage(page.getPage() + 1);

                // Limit to 10000
                if (tweets.size() == size || tweets.size() >= 10000) {
                    break;
                }
            } catch(TwitterException e) {
                log.add("Couldn't get favorites.");
            }
        }

        return tweets;
    }

    protected Tweet statusToTweet(Status status) {
        return new Tweet(status.getText(), status.getUser().getScreenName(), status.getId());
    }

    protected List<Tweet> statusesToTweets(List<Status> statuses) {
        List<Tweet> tweets = new ArrayList<>();
        for(Status status : statuses) {
            tweets.add(statusToTweet(status));
        }
        return tweets;
    }
}
