package com.threeklines.cryptoplug.backside;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

public class ApiCenter {
    private final String TAG = "API CENTER";
    private final String CMC_API_KEY = "390095f8-8e99-43f6-85c1-af1e3662ddb6";
    private final AsyncHttpClient coinClient = new AsyncHttpClient();
    private final AsyncHttpClient articleClient = new AsyncHttpClient();

    public List<Coin> getCoinsList() {
        List<Coin> coins = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.put("CMC_PRO_API_KEY", CMC_API_KEY);
        String CMC_LATEST_COINS_LIST_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        coinClient.get(CMC_LATEST_COINS_LIST_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG, "onSuccess: getCoinList: status code " + statusCode);
                    coins.addAll(decodeCoinList(response));
                    Log.d(TAG, "onSuccess: ");
                } catch (JSONException e) {
                    Log.d(TAG, "onSuccess: json error: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.d(TAG, "onFailure: " + errorResponse.toString());
                } catch (Exception e) {
                    Log.d(TAG, "onFailure: error " + e.getMessage());
                }
            }
        });

        return coins;
    }

    public Coin getCoinMetadata(Coin coin) {
        final Coin[] updatedCoin = new Coin[1];
        RequestParams params = new RequestParams();
        params.put("CMC_PRO_API_KEY", CMC_API_KEY);
        params.put("slug", coin.getSlug());
        String CMC_COIN_METADATA_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/info";
        coinClient.get(CMC_COIN_METADATA_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "onSuccess: getCoinMetadata status code " + statusCode);
                try {
                    updatedCoin[0] = decodeMetadata(response, coin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure: " + errorResponse.toString());
            }
        });
        return updatedCoin[0];
    }

    public List<Article> getLatestArticles() {
        List<Article> articles = new ArrayList<>();
        RequestParams params = new RequestParams();
        String CRYPTO_COMPARE_API_KEY = "6b17e57183e07193070c91afe2ebc9d8dbce9f71505dc5dc616f68528129b1bc";
        params.put("api_key", CRYPTO_COMPARE_API_KEY);
        String CRYPTO_COMPARE_LATESTET_ARTICLES_URL = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";
        articleClient.get(CRYPTO_COMPARE_LATESTET_ARTICLES_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "onSuccess: getLatestArticles: status code " + statusCode);
                try {
                    articles.addAll(decodeArticles(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(TAG, "onFailure: " + errorResponse.toString());
            }
        });
        return articles;
    }

    private List<Coin> decodeCoinList(JSONObject coinData) throws JSONException {
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < coinData.getJSONArray("data").length(); i++) {
            Coin coin = new Coin();
            coin.setId(coinData.getJSONArray("data").getJSONObject(i).getInt("id"));
            coin.setName(coinData.getJSONArray("data").getJSONObject(i).getString("name"));
            coin.setSymbol(coinData.getJSONArray("data").getJSONObject(i).getString("symbol"));
            coin.setSlug(coinData.getJSONArray("data").getJSONObject(i).getString("slug"));
            coin.setCirculatingSupply(coinData.getJSONArray("data").getJSONObject(i).getInt("circulating_supply"));
            coin.setTotalSuppy(coinData.getJSONArray("data").getJSONObject(i).getInt("total_supply"));
//            coin.setMaxSupply(coinData.getJSONArray("data").getJSONObject(i).getLong("max_supply"));
            coin.setDateAdded(coinData.getJSONArray("data").getJSONObject(i).getString("date_added"));
            coin.setPrice(coinData.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("price"));
            coin.setVolume24h(coinData.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getLong("volume_24h"));
            coin.setPercentageChange1h(coinData.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_1h"));
            coin.setPercentageChange24h(coinData.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_24h"));
            coin.setPercentageChange7d(coinData.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_7d"));
            coin.setMarketCap(coinData.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getLong("market_cap"));
            coin.setLastUpdated(coinData.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getString("last_updated"));
            coins.add(coin);
            Log.d(TAG, "decodeCoinList: " + coin.getName() + " received");
        }
        return coins;
    }

    private Coin decodeMetadata(JSONObject metadata, Coin coin) throws JSONException {

        coin.setId(metadata.getJSONObject("data").getJSONObject("1").getInt("id"));
//        coin.setSymbol(metadata.getJSONObject("data").getJSONObject("1").getString("symbol"));
        coin.setCategory(metadata.getJSONObject("data").getJSONObject("1").getString("category"));
        coin.setLogo(metadata.getJSONObject("data").getJSONObject("1").getString("logo"));
        JSONObject urls = metadata.getJSONObject("data").getJSONObject("1").getJSONObject("urls");
        coin.getUrls().put("Website", urls.getJSONArray("website").getString(0));
        coin.getUrls().put("technical_doc", urls.getJSONArray("technical_doc").getString(0));
        coin.getUrls().put("source_code", urls.getJSONArray("source_code").getString(0));

        Log.d(TAG, "decodeMetadata: coin recieved " + coin.getId());

        return coin;
    }

    private List<Article> decodeArticles(JSONObject articles) throws JSONException {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        JSONArray stories = articles.getJSONArray("Data");

        for (int i = 0; i < stories.length(); i++) {
            String id = stories.getJSONObject(i).getString("id");
            String guid = stories.getJSONObject(i).getString("guid");
            int publishedOn = stories.getJSONObject(i).getInt("published_on");
            String imageUrl = stories.getJSONObject(i).getString("imageurl");
            String title = stories.getJSONObject(i).getString("title");
            String url = stories.getJSONObject(i).getString("url");
            String source = stories.getJSONObject(i).getString("source");
            String body = stories.getJSONObject(i).getString("body");
            String tags = stories.getJSONObject(i).getString("tags");
            String categories = stories.getJSONObject(i).getString("categories");
            Log.d(TAG, "decodeArticles: article received " + title);
            articleArrayList.add(new Article(id, guid, publishedOn, imageUrl, title, url, source, body, tags, categories));
        }
        return articleArrayList;
    }

    public void getTwitterStatuses(String searchKeyword, DatabaseHandler db) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey("W0sl9AfuyR7oyEhzKAPLj0fao")
                        .setOAuthConsumerSecret("Ss6z6VpHcEWTu1W8cgcgsD4jerEUuHR6RGE35RAeOdkFrIqs8i")
                        .setOAuthAccessToken("756143090163933184-gzzG1b5pGQsY6bx9CbJRYgFb7lPe5hq")
                        .setOAuthAccessTokenSecret("bfMRZITQTH4I6Z1CE6f9RqKDTJkEocV4sshAPDnlerL2d");
                TwitterFactory tf = new TwitterFactory(cb.build());
                Twitter twitter = tf.getInstance();

                Query searchQuery = new Query(searchKeyword);
                searchQuery.setCount(50);
                List<TweetCard> cardList = new ArrayList<>();
                try {
                    QueryResult queryResult = twitter.search(searchQuery);
                    List<Status> statuses = queryResult.getTweets();
                    for (Status status : statuses) {
                        String TAG = "Twitter Thread";
                        TweetCard card = createTweetCard(status);
                        card.setTweetUrl(getEmbeddedTweet(card.getUserUrl()));
                        cardList.add(card);
                    }

                    db.insertTweets(cardList);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private String getEmbeddedTweet(String link) {
        final String[] url = new String[1];
        RequestParams params = new RequestParams();
        params.put("url", link);
        coinClient.get("https://publish.twitter.com/oembed", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    url[0] = response.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure: failed to get tweet url");
            }
        });
        return url[0];
    }

    private TweetCard createTweetCard(Status status) {
        String id = status.getId() + "";
        String userUrl = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
        int likes = status.getFavoriteCount();
        int retweets = status.getRetweetCount();
        String datePosted = status.getCreatedAt().toString();

        return new TweetCard(id, userUrl, likes, retweets, datePosted);
    }

}
