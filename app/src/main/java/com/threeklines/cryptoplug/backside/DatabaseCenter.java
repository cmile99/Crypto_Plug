package com.threeklines.cryptoplug.backside;

public class DatabaseCenter {
    private static final String DATABASE_NAME = "cryptoplug.db";

    //Coin table and its columns
    public static final String COIN_TABLE = "coin_details";
    public static final String COIN_ID_COLUMN = "id";
    public static final String COIN_NAME_COLUMN = "name";
    public static final String COIN_SYMBOL_COLUMN = "symbol";
    public static final String COIN_CATEGORY_COLUMN = "category";
    public static final String COIN_SLUG_COLUMN = "slug";
    public static final String COIN_LOGO_COLUMN = "logo";
    public static final String COIN_DATE_ADDED_COLUMN = "date_added";
    public static final String COIN_CIRCULATING_SUPPLY_COLUMN = "circulating_supply";
    public static final String COIN_TOTAL_SUPPLY_COLUMN = "total_supply";
    public static final String COIN_mAX_SUPPLY_COLUMN = "max_supply";
    public static final String COIN_LAST_UPDATED_COLUMN = "last_updated";
    public static final String COIN_MINABLE_COLUMN = "minable";

    //Coin urls table
    public static final String COIN_URLS_TABLE = "coin_urls";
    public static final String COIN_URLS_ID_COLUMN = "id";
    public static final String  COIN_URLS_URL_DESTINATION = "url_destination";
    public static final String COIN_URLS_URL = "url";


}
