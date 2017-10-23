# TwitterBot
Twitter bot utilizing the Twitter4J API. Tweets, favorites, and follows users 
based on selected topics. Finds articles from Google News RSS feed using the Rome framework.

Check it out https://twitter.com/munchi333

To create your own bot, first register an application account at https://apps.twitter.com/
Then, create a file src/main/resources/twitter4j.properties containing the following:

- oauth.consumerKey=[Consumer Key]
- oauth.consumerSecret=[Consumer Secret]
- oauth.accessToken=[Access Token]
- oauth.accessTokenSecret=[Access Token Secret]

Update src/main/resources/bot-properties.json as you see fit. You can easily change the RSS feed or the keywords for the application.
