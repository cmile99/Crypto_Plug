package com.threeklines.cryptoplug.backside;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TAG = "Database Handler";

    public static final String CREATE_COINS_TABLE = "CREATE TABLE " + DbContract.CoinsTable.TABLE_NAME + "(" +
            DbContract.CoinsTable.ID_COLUMN + " INTEGER," +
            DbContract.CoinsTable.NAME_COLUMN + " TEXT," +
            DbContract.CoinsTable.SYMBOL_COLUMN + " TEXT, " +
            DbContract.CoinsTable.CATEGORY_COLUMN + " TEXT, " +
            DbContract.CoinsTable.SLUG_COLUMN + " TEXT, " +
            DbContract.CoinsTable.LOGO_COLUMN + " TEXT, " +
            DbContract.CoinsTable.CIRCULATING_SUPPLY_COLUMN + " INTEGER, " +
            DbContract.CoinsTable.DATE_ADDED_COLUMN + " TEXT, " +
            DbContract.CoinsTable.TOTAL_SUPPLY_COLUMN + " REAL, " +
            DbContract.CoinsTable.MAX_SUPPLY_COLUMN + " INTEGER, " +
            DbContract.CoinsTable.LAST_UPDATED_COLUMN + " TEXT, " +
            DbContract.CoinsTable.PRICE_COLUMN + " TEXT, " +
            DbContract.CoinsTable.VOLUME24H_COLUMN + " INTEGER, " +
            DbContract.CoinsTable.PERCENTAGE_CHANGE1H_COLUMN + " REAL, " +
            DbContract.CoinsTable.PERCENTAGE_CHANGE24_COLUMN + " REAL, " +
            DbContract.CoinsTable.PERCENTAGE_CHANGE7d_COLUMN + " REAL, " +
            DbContract.CoinsTable.MARKET_CAP_COLUMN + " INTEGER)";

    public static final String CREATE_URLS_TABLE = "CREATE TABLE " + DbContract.CoinUrlsTable.COINS_URLS_TABLE_NAME + "(" +
            DbContract.CoinUrlsTable.COIN_ID_COLUMN + " INTEGER," +
            DbContract.CoinUrlsTable.URL_DESTINATON_COLUMN + " TEXT, " +
            DbContract.CoinUrlsTable.URL_COLUMN + " TEXT)";

    public static final String CREATE_ARTICLES_TABLE = "CREATE TABLE " + DbContract.ArticlesTable.ARTICLES_TABLE_NAME + "(" +
            DbContract.ArticlesTable.ID_COLUMN + " INTERGER PRIMARY KEY, " +
            DbContract.ArticlesTable.GUID_COULUMN + " TEXT, " +
            DbContract.ArticlesTable.DATE_PUBLISHED_COLUMN + " INTEGER, " +
            DbContract.ArticlesTable.IMAGE_URL_COLUMN + " TEXT, " +
            DbContract.ArticlesTable.TITLE_COLUMN + " TEXT, " +
            DbContract.ArticlesTable.URL_COLUMN + " TEXT, " +
            DbContract.ArticlesTable.SOURCE_COLUMN + " TEXT, " +
            DbContract.ArticlesTable.BODY_COLUMN + " TEXT, " +
            DbContract.ArticlesTable.TAGS_COLUMN + " TEXT, " +
            DbContract.ArticlesTable.CATEGORIES_COLUMN + " TEXT)";

    public static final String CREATE_TWEETS_TABLE = "CREATE TABLE " + DbContract.TweetsCardTable.TABLE_NAME + "(" +
            DbContract.TweetsCardTable.TWEET_ID + " INTEGER, " +
            DbContract.TweetsCardTable.TWEET_USER_URL + " TEXT, " +
            DbContract.TweetsCardTable.TWEET_EMBEDDED_URL + " TEXT, " +
            DbContract.TweetsCardTable.TWEET_LIKES + " INTEGER, " +
            DbContract.TweetsCardTable.TWEET_RETWEET_COUNT + " INTEGER, " +
            DbContract.TweetsCardTable.TWEET_DATE_POSTED + " TEXT)";

    public static final String DELETE_COINS_TABLE = "DROP TABLE IF EXISTS " + DbContract.CoinsTable.TABLE_NAME;
    public static final String DELETE_URLS_TABLE = "DROP TABLE IF EXISTS " + DbContract.CoinUrlsTable.COINS_URLS_TABLE_NAME;
    public static final String DELETE_ARTICLES_TABLE = "DROP TABLE IF EXISTS " + DbContract.ArticlesTable.ARTICLES_TABLE_NAME;
    public static final String DELETE_TWEETS_TABLE = "DROP TABLE IF EXISTS " + DbContract.TweetsCardTable.TABLE_NAME;

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COINS_TABLE);
        db.execSQL(CREATE_URLS_TABLE);
        db.execSQL(CREATE_ARTICLES_TABLE);
        db.execSQL(CREATE_TWEETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ARTICLES_TABLE);
        db.execSQL(DELETE_URLS_TABLE);
        db.execSQL(DELETE_COINS_TABLE);
        db.execSQL(DELETE_TWEETS_TABLE);
        onCreate(db);
    }


    public void insertCoinList(List<Coin> coins) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "insertCoinList: checking" + coins.size() );
        for (Coin coin : coins) {
            ContentValues values = new ContentValues();
            values.put(DbContract.CoinsTable.ID_COLUMN, coin.getId());
            values.put(DbContract.CoinsTable.NAME_COLUMN, coin.getName());
            values.put(DbContract.CoinsTable.SYMBOL_COLUMN, coin.getSymbol());
            values.put(DbContract.CoinsTable.SLUG_COLUMN, coin.getSlug());
            values.put(DbContract.CoinsTable.CIRCULATING_SUPPLY_COLUMN, coin.getCirculatingSupply());
            values.put(DbContract.CoinsTable.TOTAL_SUPPLY_COLUMN, coin.getTotalSuppy());
            values.put(DbContract.CoinsTable.MAX_SUPPLY_COLUMN, coin.getMaxSupply());
            values.put(DbContract.CoinsTable.LAST_UPDATED_COLUMN, coin.getLastUpdated());
            values.put(DbContract.CoinsTable.DATE_ADDED_COLUMN, coin.getDateAdded());
            values.put(DbContract.CoinsTable.PRICE_COLUMN, coin.getPrice());
            values.put(DbContract.CoinsTable.VOLUME24H_COLUMN, coin.getVolume24h());
            values.put(DbContract.CoinsTable.PERCENTAGE_CHANGE1H_COLUMN, coin.getPercentageChange1h());
            values.put(DbContract.CoinsTable.PERCENTAGE_CHANGE24_COLUMN, coin.getPercentageChange24h());
            values.put(DbContract.CoinsTable.PERCENTAGE_CHANGE7d_COLUMN, coin.getPercentageChange7d());
            values.put(DbContract.CoinsTable.MARKET_CAP_COLUMN, coin.getMarketCap());
            Log.d(TAG, "insertCoinList: coin added at row " + db.insert(DbContract.CoinsTable.TABLE_NAME, null, values));
        }
    }

    public void insertCoinMetadata(Coin coin) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DbContract.CoinsTable.ID_COLUMN + "= ?";
        String[] selectionArgs = {coin.getSlug()};
        ContentValues values = new ContentValues();
        values.put(DbContract.CoinsTable.CATEGORY_COLUMN, coin.getCategory());
        values.put(DbContract.CoinsTable.LOGO_COLUMN, coin.getLogo());
        Log.d(TAG, "insertCoinMetadata: update made at " + db.update(DbContract.CoinsTable.TABLE_NAME, values, selection, selectionArgs));
        for (String s : coin.getUrls().keySet()) {
            insertUrls(coin.getId(), s, coin.getUrls().get(s), db);
        }
    }

    private void insertUrls(int coinId, String destination, String link, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(DbContract.CoinUrlsTable.COIN_ID_COLUMN, coinId);
        values.put(DbContract.CoinUrlsTable.URL_DESTINATON_COLUMN, destination);
        values.put(DbContract.CoinUrlsTable.URL_COLUMN, link);

        Log.d(TAG, "insertUrls: links entered " + db.insert(DbContract.CoinUrlsTable.COINS_URLS_TABLE_NAME, null, values));
    }

    public void insertArticles(List<Article> articles) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Article article : articles) {
            ContentValues values = new ContentValues();
            values.put(DbContract.ArticlesTable.ID_COLUMN, article.getId());
            values.put(DbContract.ArticlesTable.GUID_COULUMN, article.getGuid());
            values.put(DbContract.ArticlesTable.DATE_PUBLISHED_COLUMN, article.getDatePublished());
            values.put(DbContract.ArticlesTable.IMAGE_URL_COLUMN, article.getImageUrl());
            values.put(DbContract.ArticlesTable.TITLE_COLUMN, article.getTitle());
            values.put(DbContract.ArticlesTable.URL_COLUMN, article.getUrl());
            values.put(DbContract.ArticlesTable.SOURCE_COLUMN, article.getSource());
            values.put(DbContract.ArticlesTable.BODY_COLUMN, article.getBody());
            values.put(DbContract.ArticlesTable.TAGS_COLUMN, article.getTags());
            values.put(DbContract.ArticlesTable.CATEGORIES_COLUMN, article.getCategories());
            Log.d(TAG, "insertArticles: Article inserted at row " + db.insert(DbContract.ArticlesTable.ARTICLES_TABLE_NAME, null, values));
        }
    }

    public ArrayList<Article> queryArticles() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Article> articles = new ArrayList<>();
        String[] projection = {
                DbContract.ArticlesTable.ID_COLUMN,
                DbContract.ArticlesTable.GUID_COULUMN,
                DbContract.ArticlesTable.DATE_PUBLISHED_COLUMN,
                DbContract.ArticlesTable.IMAGE_URL_COLUMN,
                DbContract.ArticlesTable.TITLE_COLUMN,
                DbContract.ArticlesTable.URL_COLUMN,
                DbContract.ArticlesTable.SOURCE_COLUMN,
                DbContract.ArticlesTable.BODY_COLUMN,
                DbContract.ArticlesTable.TAGS_COLUMN,
                DbContract.ArticlesTable.CATEGORIES_COLUMN
        };

        Cursor cursor = db.query(DbContract.ArticlesTable.ARTICLES_TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Article article = new Article(
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.ID_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.GUID_COULUMN)),
                        cursor.getInt(cursor.getColumnIndex(DbContract.ArticlesTable.DATE_PUBLISHED_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.IMAGE_URL_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.TITLE_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.URL_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.SOURCE_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.BODY_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.TAGS_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(DbContract.ArticlesTable.CATEGORIES_COLUMN))
                );
                Log.d(TAG, "queeryArticles: Article: " + article.getTitle());
                articles.add(article);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return articles;
    }

    public ArrayList<Coin> queryCoinList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Coin> coins = new ArrayList<>();
        String[] projetion = {
                DbContract.CoinsTable.ID_COLUMN,
                DbContract.CoinsTable.NAME_COLUMN,
                DbContract.CoinsTable.SYMBOL_COLUMN,
                DbContract.CoinsTable.CATEGORY_COLUMN,
                DbContract.CoinsTable.SLUG_COLUMN,
                DbContract.CoinsTable.LOGO_COLUMN,
                DbContract.CoinsTable.CIRCULATING_SUPPLY_COLUMN,
                DbContract.CoinsTable.TOTAL_SUPPLY_COLUMN,
                DbContract.CoinsTable.MAX_SUPPLY_COLUMN,
                DbContract.CoinsTable.LAST_UPDATED_COLUMN,
                DbContract.CoinsTable.PRICE_COLUMN,
                DbContract.CoinsTable.VOLUME24H_COLUMN,
                DbContract.CoinsTable.PERCENTAGE_CHANGE1H_COLUMN,
                DbContract.CoinsTable.PERCENTAGE_CHANGE24_COLUMN,
                DbContract.CoinsTable.PERCENTAGE_CHANGE7d_COLUMN,
                DbContract.CoinsTable.MARKET_CAP_COLUMN
        };

        Cursor coinResults = db.query(DbContract.CoinsTable.TABLE_NAME, projetion, null, null, null, null, null);

        if (coinResults.moveToFirst()) {
            do {
                Coin coin = new Coin();
                coin.setId(coinResults.getInt(coinResults.getColumnIndex(DbContract.CoinsTable.ID_COLUMN)));
                coin.setName(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.NAME_COLUMN)));
                coin.setSymbol(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.SYMBOL_COLUMN)));
                coin.setCategory(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.CATEGORY_COLUMN)));
                coin.setSlug(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.SLUG_COLUMN)));
                coin.setLogo(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.LOGO_COLUMN)));
                coin.setCirculatingSupply(coinResults.getInt(coinResults.getColumnIndex(DbContract.CoinsTable.CIRCULATING_SUPPLY_COLUMN)));
                coin.setTotalSuppy(coinResults.getInt(coinResults.getColumnIndex(DbContract.CoinsTable.TOTAL_SUPPLY_COLUMN)));
                coin.setMaxSupply(coinResults.getInt(coinResults.getColumnIndex((DbContract.CoinsTable.MAX_SUPPLY_COLUMN))));
                coin.setLastUpdated(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.LAST_UPDATED_COLUMN)));
                coin.setPrice(coinResults.getDouble(coinResults.getColumnIndex(DbContract.CoinsTable.PRICE_COLUMN)));
                coin.setVolume24h(coinResults.getLong(coinResults.getColumnIndex(DbContract.CoinsTable.VOLUME24H_COLUMN)));
                coin.setPercentageChange1h(coinResults.getDouble(coinResults.getColumnIndex(DbContract.CoinsTable.PERCENTAGE_CHANGE1H_COLUMN)));
                coin.setPercentageChange24h(coinResults.getDouble(coinResults.getColumnIndex(DbContract.CoinsTable.PERCENTAGE_CHANGE24_COLUMN)));
                coin.setPercentageChange7d(coinResults.getDouble(coinResults.getColumnIndex(DbContract.CoinsTable.PERCENTAGE_CHANGE7d_COLUMN)));
                coin.setMarketCap(coinResults.getInt(coinResults.getColumnIndex(DbContract.CoinsTable.MARKET_CAP_COLUMN)));

                Map<String, String> list = queeryUrls(coin.getId(), db);
                for (String s : list.keySet()) {
                    coin.getUrls().put(s, list.get(s));
                }
                Log.d(TAG, "queeryCoinList: Coin ->  " + coin.getName());
                coins.add(coin);
            } while (coinResults.moveToNext());
            coinResults.close();
        }
        return coins;
    }

    public Coin querryCoin(String slug){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projetion = {
                DbContract.CoinsTable.ID_COLUMN,
                DbContract.CoinsTable.NAME_COLUMN,
                DbContract.CoinsTable.SYMBOL_COLUMN,
                DbContract.CoinsTable.CATEGORY_COLUMN,
                DbContract.CoinsTable.SLUG_COLUMN,
                DbContract.CoinsTable.LOGO_COLUMN,
                DbContract.CoinsTable.CIRCULATING_SUPPLY_COLUMN,
                DbContract.CoinsTable.TOTAL_SUPPLY_COLUMN,
                DbContract.CoinsTable.MAX_SUPPLY_COLUMN,
                DbContract.CoinsTable.LAST_UPDATED_COLUMN,
                DbContract.CoinsTable.PRICE_COLUMN,
                DbContract.CoinsTable.VOLUME24H_COLUMN,
                DbContract.CoinsTable.PERCENTAGE_CHANGE1H_COLUMN,
                DbContract.CoinsTable.PERCENTAGE_CHANGE24_COLUMN,
                DbContract.CoinsTable.PERCENTAGE_CHANGE7d_COLUMN,
                DbContract.CoinsTable.MARKET_CAP_COLUMN
        };

        String selection = DbContract.CoinsTable.SLUG_COLUMN + " =?";
        String[] selectionArgs = {slug};

        Cursor coinResults = db.query(DbContract.CoinsTable.TABLE_NAME, projetion, selection, selectionArgs, null, null, null);
        Coin coin = new Coin();
        if (coinResults.moveToFirst()) {
            do {

                coin.setId(coinResults.getInt(coinResults.getColumnIndex(DbContract.CoinsTable.ID_COLUMN)));
                coin.setName(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.NAME_COLUMN)));
                coin.setSymbol(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.SYMBOL_COLUMN)));
                coin.setCategory(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.CATEGORY_COLUMN)));
                coin.setSlug(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.SLUG_COLUMN)));
                coin.setLogo(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.LOGO_COLUMN)));
                coin.setCirculatingSupply(coinResults.getInt(coinResults.getColumnIndex(DbContract.CoinsTable.CIRCULATING_SUPPLY_COLUMN)));
                coin.setTotalSuppy(coinResults.getInt(coinResults.getColumnIndex(DbContract.CoinsTable.TOTAL_SUPPLY_COLUMN)));
                coin.setMaxSupply(coinResults.getInt(coinResults.getColumnIndex((DbContract.CoinsTable.MAX_SUPPLY_COLUMN))));
                coin.setLastUpdated(coinResults.getString(coinResults.getColumnIndex(DbContract.CoinsTable.LAST_UPDATED_COLUMN)));
                coin.setPrice(coinResults.getDouble(coinResults.getColumnIndex(DbContract.CoinsTable.PRICE_COLUMN)));
                coin.setVolume24h(coinResults.getLong(coinResults.getColumnIndex(DbContract.CoinsTable.VOLUME24H_COLUMN)));
                coin.setPercentageChange1h(coinResults.getDouble(coinResults.getColumnIndex(DbContract.CoinsTable.PERCENTAGE_CHANGE1H_COLUMN)));
                coin.setPercentageChange24h(coinResults.getDouble(coinResults.getColumnIndex(DbContract.CoinsTable.PERCENTAGE_CHANGE24_COLUMN)));
                coin.setPercentageChange7d(coinResults.getDouble(coinResults.getColumnIndex(DbContract.CoinsTable.PERCENTAGE_CHANGE7d_COLUMN)));
                coin.setMarketCap(coinResults.getInt(coinResults.getColumnIndex(DbContract.CoinsTable.MARKET_CAP_COLUMN)));

                Map<String, String> list = queeryUrls(coin.getId(), db);
                for (String s : list.keySet()) {
                    coin.getUrls().put(s, list.get(s));
                }
            } while (coinResults.moveToNext());
        }
        return coin;
    }

    private HashMap<String, String> queeryUrls(int id, SQLiteDatabase db) {
        String[] projection = {DbContract.CoinUrlsTable.URL_COLUMN, DbContract.CoinUrlsTable.URL_DESTINATON_COLUMN};
        String selection = DbContract.CoinUrlsTable.COIN_ID_COLUMN + " = ?";
        String[] selectionArgs = {id + ""};
        Cursor urls = db.query(DbContract.CoinUrlsTable.COINS_URLS_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        HashMap<String, String> links = new HashMap<>();
        if (urls.moveToFirst()) {
            do {
                links.put(urls.getString(urls.getColumnIndex(DbContract.CoinUrlsTable.URL_DESTINATON_COLUMN)), urls.getString(urls.getColumnIndex(DbContract.CoinUrlsTable.URL_COLUMN)));
            } while (urls.moveToNext());
        }
        urls.close();
        Log.d(TAG, "queeryUrls: links for coin with id " + id);
        return links;
    }

    public void insertTweets(List<TweetCard> tweetCards) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_TWEETS_TABLE);
        db.execSQL(CREATE_TWEETS_TABLE);
        for (TweetCard tweetCard : tweetCards) {
            ContentValues values = new ContentValues();
            values.put(DbContract.TweetsCardTable.TWEET_ID, tweetCard.getTweetId());
            values.put(DbContract.TweetsCardTable.TWEET_USER_URL, tweetCard.getUserUrl());
            values.put(DbContract.TweetsCardTable.TWEET_EMBEDDED_URL, tweetCard.getTweetUrl());
            values.put(DbContract.TweetsCardTable.TWEET_LIKES, tweetCard.getLikes());
            values.put(DbContract.TweetsCardTable.TWEET_RETWEET_COUNT, tweetCard.getRetweets());
            values.put(DbContract.TweetsCardTable.TWEET_DATE_POSTED, tweetCard.getDatePosted().toString());
            Log.d(TAG, "insertTweets: tweet inserted at row " + db.insert(DbContract.TweetsCardTable.TABLE_NAME, null, values));
        }
    }

    public List<TweetCard> queryTweetCards() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<TweetCard> tweetCards = new ArrayList<>();
        String[] projection = {
                DbContract.TweetsCardTable.TWEET_ID,
                DbContract.TweetsCardTable.TWEET_USER_URL,
                DbContract.TweetsCardTable.TWEET_EMBEDDED_URL,
                DbContract.TweetsCardTable.TWEET_LIKES,
                DbContract.TweetsCardTable.TWEET_RETWEET_COUNT,
                DbContract.TweetsCardTable.TWEET_DATE_POSTED,
        };

        Cursor cursor = db.query(DbContract.TweetsCardTable.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {

                TweetCard card = new TweetCard(
                    cursor.getString(cursor.getColumnIndex(DbContract.TweetsCardTable.TWEET_ID)),
                    cursor.getString(cursor.getColumnIndex(DbContract.TweetsCardTable.TWEET_USER_URL)),
                    cursor.getInt(cursor.getColumnIndex(DbContract.TweetsCardTable.TWEET_LIKES)),
                    cursor.getInt(cursor.getColumnIndex(DbContract.TweetsCardTable.TWEET_RETWEET_COUNT)),
                    cursor.getString(cursor.getColumnIndex(DbContract.TweetsCardTable.TWEET_DATE_POSTED))
            );
                card.setTweetUrl(cursor.getString(cursor.getColumnIndex(DbContract.TweetsCardTable.TWEET_EMBEDDED_URL)));
                tweetCards.add(card);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tweetCards;
    }

}
