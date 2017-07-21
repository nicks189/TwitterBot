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

    public Tweet tweet(String tweet) {
        try {
            Tweet res = statusToTweet(twitter.updateStatus(tweet));
            log.add("Tweet sent with id " + res.getId());
            return res;
        } catch(TwitterException e) {
            log.add("Failed to send tweet.");
            return new Tweet(tweet);
        }
    }

    public Tweet tweet(Tweet tweet) {
        return tweet(tweet.getValue());
    }

    public List<Tweet> getMyTweets() {
        return getUserTweets(username());
    }

    public String username() {
        String res = "";
        try {
            res = twitter.getScreenName();
        } catch(TwitterException e) {
            log.add("Couldn't resolve username.");
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
                log.add("Couldn't get user tweets.");
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

    public boolean followUser(String user) {
        try {
            twitter.createFriendship(user);
            log.add("Followed user " + user);
            return true;
        } catch(TwitterException e) {
            log.add("Couldn't follow user " + user);
            return false;
        }
    }

    public boolean unfollowUser(String user) {
        try {
            twitter.destroyFriendship(user);
            log.add("Unfollowed user " + user);
            return true;
        } catch(TwitterException e) {
            log.add("Couldn't unfollow user " + user);
            return false;
        }
    }

    public boolean checkFollowingUser(String user) {
        try {
            Relationship relationship = twitter.showFriendship(username(), user);
            return relationship.isSourceFollowingTarget();
        } catch(TwitterException e) {
            log.add("Couldn't get relationship with " + user);
        }
        return false;
    }

    public Tweet favoriteTweet(Tweet tweet) {
        try {
            twitter.createFavorite(tweet.getId());
            tweet.setFavorite(true);
            log.add("Favorited tweet with id " + tweet.getId());
        } catch(TwitterException e) {
            log.add("Couldn't favorite tweet.");
        }
        return tweet;
    }

    public Tweet unfavoriteTweet(Tweet tweet) {
        try {
            twitter.destroyFavorite(tweet.getId());
            tweet.setFavorite(false);
            log.add("Unfavorited tweet with id " + tweet.getId());
        } catch(TwitterException e) {
            log.add("Couldn't unfavorite tweet.");
        }
        return tweet;
    }

    public List<Tweet> search(String keyword) {
        try {
            Query query = new Query(keyword);
            return statusesToTweets(twitter.search(query).getTweets());
        } catch(TwitterException e) {
            log.add("Couldn't search for tweets.");
            return null;
        }
    }

    protected Tweet statusToTweet(Status status) {
        return new Tweet(status.getText(), status.getUser().getScreenName(), status.getId());
    }

    protected List<Tweet> statusesToTweets(List<Status> statuses) {
        System.out.println(statuses.size());
        List<Tweet> tweets = new ArrayList<>();
        for(Status status : statuses) {
            tweets.add(statusToTweet(status));
        }
        return tweets;
    }

    protected Status lookupStatus(long id) throws TwitterException {
        return twitter.lookup(id).get(0);
    }
}
