package com.threeklines.cryptoplug.backside;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataCenter {
    private static final String TAG = "Coin Info";
    private static final String CMC_API_KEY = "390095f8-8e99-43f6-85c1-af1e3662ddb6";
    private static final String CRYPTO_COMPARE_API_KEY = "6b17e57183e07193070c91afe2ebc9d8dbce9f71505dc5dc616f68528129b1bc";
    private static final AsyncHttpClient coinClient = new AsyncHttpClient();
    private static final AsyncHttpClient articleClient = new AsyncHttpClient();



    public static void getCoinsList(){
        RequestParams params = new RequestParams();
        params.put("CMC_PRO_API_KEY", CMC_API_KEY);
        params.put("limit", 10);
        String CMC_LATEST_COINS_LIST_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        coinClient.get(CMC_LATEST_COINS_LIST_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    decodeCoinList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure: " + errorResponse.toString());
            }
        });
    }

    public static void getCoinMetadata(final Coin coin){
        RequestParams params = new RequestParams();
        params.put("CMC_PRO_API_KEY", CMC_API_KEY);
        params.put("slug", coin.getSlug());
        String CMC_COIN_METADATA_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/info";
        coinClient.get(CMC_COIN_METADATA_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    decodeMetadata(response, coin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure: " + errorResponse.toString());
            }
        });
    }

    private static void decodeCoinList(JSONObject coinData) throws JSONException {
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < coinData.getJSONArray("data").length()-1; i ++){
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
        }
        for (Coin coin1 : coins) {
            Log.d(TAG, "decodeCoinList: " + coin1.getName() + ", " + coin1.getPrice() + ", " + coin1.getPercentageChange1h());
        }
    }

    private static void decodeMetadata(JSONObject metadata, Coin coin) throws JSONException {

        coin.setSymbol(metadata.getJSONObject("data").getJSONObject("1").getString("symbol"));
        coin.setCategory(metadata.getJSONObject("data").getJSONObject("1").getString("category"));
        coin.setLogo(metadata.getJSONObject("data").getJSONObject("1").getString("logo"));
        JSONObject urls = metadata.getJSONObject("data").getJSONObject("1").getJSONObject("urls");
        coin.getUrls().put("Website", urls.getJSONArray("website").getString(0));
        coin.getUrls().put("technical_doc", urls.getJSONArray("technical_doc").getString(0));
        coin.getUrls().put("source_code", urls.getJSONArray("source_code").getString(0));

        Log.d(TAG, "decodeMetadata: " + coin.getName() + ", " + coin.getSymbol());
        for (String s: coin.getUrls().keySet()){
            Log.d(TAG, "decodeMetadata: " + s + " -> " + coin.getUrls().get(s));
        }
    }

    public static void getLatestArticles(){
        RequestParams params = new RequestParams();
        params.put("api_key", CRYPTO_COMPARE_API_KEY);
        String CRYPTO_COMPARE_LATESTET_ARTICLES_URL = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";
        articleClient.get(CRYPTO_COMPARE_LATESTET_ARTICLES_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG, "onSuccess: " + decodeArticles(response).get(0).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(TAG, "onFailure: " + errorResponse.toString());
            }
        });
    }

    public static void getCategories(){
        RequestParams params = new RequestParams();
        params.put("api_key", CRYPTO_COMPARE_API_KEY);
        String CRYPTO_COMPARE_CARTEGORIES = "https://min-api.cryptocompare.com/data/news/categories";
        articleClient.get(CRYPTO_COMPARE_CARTEGORIES, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(TAG, "onFailure: " + errorResponse.toString());
            }
        });
    }

    private static ArrayList<Article> decodeArticles(JSONObject articles) throws JSONException {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        JSONArray stories = articles.getJSONArray("Data");

        for (int i = 0; i < stories.length(); i++){
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

            articleArrayList.add(new Article(id, guid, publishedOn, imageUrl, title, url, source, body, tags, categories));
        }
        return articleArrayList;
    }

}
