package com.threeklines.cryptoplug.backside;

public class TweetCard {
    private final String tweetId;
    private final String userUrl;
    private String tweetUrl;
    private final int likes;
    private final int retweets;
    private final String datePosted;

    public TweetCard(String tweetId, String userUrl,  int likes, int retweets, String datePosted) {
        this.tweetId = tweetId;
        this.userUrl = userUrl;
        this.likes = likes;
        this.retweets = retweets;
        this.datePosted = datePosted;
    }

    public String getTweetId() {
        return tweetId;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public String getTweetUrl() {
        return tweetUrl;
    }

    public int getLikes() {
        return likes;
    }

    public int getRetweets() {
        return retweets;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setTweetUrl(String tweetUrl) {
        this.tweetUrl = tweetUrl;
    }
}
