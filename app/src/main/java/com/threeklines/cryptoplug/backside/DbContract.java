package com.threeklines.cryptoplug.backside;

import android.provider.BaseColumns;

public final class DbContract {
    private DbContract() {
    }

    //Coin Table contents
    public static class CoinsTable implements BaseColumns {
        public static final String TABLE_NAME = "cryptos";
        public static final String ID_COLUMN = "id";
        public static final String NAME_COLUMN = "name";
        public static final String SYMBOL_COLUMN = "symbol";
        public static final String CATEGORY_COLUMN = "category";
        public static final String SLUG_COLUMN = "slug";
        public static final String LOGO_COLUMN = "logo";
        public static final String CIRCULATING_SUPPLY_COLUMN = "circulating_supply";
        public static final String TOTAL_SUPPLY_COLUMN = "total_supply";
        public static final String MAX_SUPPLY_COLUMN = "max_supply";
        public static final String LAST_UPDATED_COLUMN = "last_updated";
        public static final String DATE_ADDED_COLUMN = "date_added";
        public static final String PRICE_COLUMN = "price";
        public static final String VOLUME24H_COLUMN = "volume24h";
        public static final String PERCENTAGE_CHANGE1H_COLUMN = "change1h";
        public static final String PERCENTAGE_CHANGE24_COLUMN = "change24h";
        public static final String PERCENTAGE_CHANGE7d_COLUMN = "change7d";
        public static final String MARKET_CAP_COLUMN = "market_cap";
    }

    //Coin links table
    public static class CoinUrlsTable implements BaseColumns {
        public static final String COINS_URLS_TABLE_NAME = "coin_urls";
        public static final String URL_DESTINATON_COLUMN = "url_destination";
        public static final String URL_COLUMN = "url";
        public static final String COIN_ID_COLUMN = "coin_id";
    }

    //Articles table
    public static class ArticlesTable implements BaseColumns {
        public static final String ARTICLES_TABLE_NAME = "articles";
        public static final String ID_COLUMN = "id";
        public static final String GUID_COULUMN = "guid";
        public static final String DATE_PUBLISHED_COLUMN = "date_published";
        public static final String IMAGE_URL_COLUMN = "image_url";
        public static final String TITLE_COLUMN = "title";
        public static final String URL_COLUMN = "url";
        public static final String SOURCE_COLUMN = "source";
        public static final String BODY_COLUMN = "body";
        public static final String TAGS_COLUMN = "tags";
        public static final String CATEGORIES_COLUMN = "catergories";
    }

    public static class TweetsCardTable {
        public static final String TABLE_NAME = "tweets";
        public static final String TWEET_ID = "tweet_id";
        public static final String TWEET_USER_URL = "user_url";
        public static final String TWEET_EMBEDDED_URL = "embedded_url";
        public static final String TWEET_LIKES = "likes";
        public static final String TWEET_RETWEET_COUNT = "retweet_count";
        public static final String TWEET_DATE_POSTED = "date_posted";
    }
}
