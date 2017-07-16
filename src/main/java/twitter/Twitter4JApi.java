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

    public void tweet(String tweet) throws TwitterException {
        twitter.updateStatus(tweet);
    }

    public void tweet(Tweet tweet) throws TwitterException {
        return;
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

    public List getMyTweets() {
        return getUserTweets(username());
    }

    public List getUserTweets(String username) {
        // Use pagination to get as many tweets as the API allows (usually around 3,200)
        List statuses = new ArrayList();
        Paging page = new Paging(1, 200);

        while(true) {
            try {
                int size = statuses.size();
                statuses.addAll(twitter.getUserTimeline(username, page));
                page.setPage(page.getPage() + 1);

                // Limit to 10000
                if (statuses.size() == size || statuses.size() >= 10000) {
                    break;
                }
            } catch(TwitterException e) {
                log.add("Couldn't get tweets.");
            }
        }

        return statuses;
    }

    public List getTimeline() {
        List statuses = new ArrayList();
        Paging page = new Paging(1, 200);

        while(true) {
            try {
                int size = statuses.size();
                statuses.addAll(twitter.getHomeTimeline(page));
                page.setPage(page.getPage() + 1);

                // Limit to 10000
                if (statuses.size() == size || statuses.size() >= 10000) {
                    break;
                }
            } catch(TwitterException e) {
                log.add("Couldn't get timeline.");
            }
        }

        return statuses;
    }

    public List getFavorites() {
        List statuses = new ArrayList();
        Paging page = new Paging(1, 200);

        while(true) {
            try {
                int size = statuses.size();
                statuses.addAll(twitter.getFavorites(page));
                page.setPage(page.getPage() + 1);

                // Limit to 10000
                if (statuses.size() == size || statuses.size() >= 10000) {
                    break;
                }
            } catch(TwitterException e) {
                log.add("Couldn't get favorites.");
            }
        }

        return statuses;
    }
}
