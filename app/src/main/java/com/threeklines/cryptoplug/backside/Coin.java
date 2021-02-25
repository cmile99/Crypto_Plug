package com.threeklines.cryptoplug.backside;

import java.util.HashMap;

public class Coin {

    //final values-> coin metadata
    private  int id;
    private String name;
    private String symbol;
    private String category;
    private String slug;
    private String logo;
    private String platform;
    private final HashMap<String, String> urls = new HashMap<>();

    // the changing market values
    private int circulatingSupply;
    private int totalSuppy;
    private long maxSupply;
    private String lastUpdated;
    private String dateAdded;
    private boolean minable;
    private double price;
    private long volume24h;
    private double percentageChange1h;
    private double percentageChange24h;
    private double percentageChange7d;
    private long marketCap;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCategory() {
        return category;
    }

    public String getSlug() {
        return slug;
    }

    public String getLogo() {
        return logo;
    }

    public String getPlatform() {
        return platform;
    }

    public HashMap<String, String> getUrls() {
        return urls;
    }

    public int getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(int circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public int getTotalSuppy() {
        return totalSuppy;
    }

    public void setTotalSuppy(int totalSuppy) {
        this.totalSuppy = totalSuppy;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(long maxSupply) {
        this.maxSupply = maxSupply;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isMinable() {
        return minable;
    }

    public void setMinable(boolean minable) {
        this.minable = minable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(long volume24h) {
        this.volume24h = volume24h;
    }

    public double getPercentageChange1h() {
        return percentageChange1h;
    }

    public void setPercentageChange1h(double percentageChange1h) {
        this.percentageChange1h = percentageChange1h;
    }

    public double getPercentageChange24h() {
        return percentageChange24h;
    }

    public void setPercentageChange24h(double percentageChange24h) {
        this.percentageChange24h = percentageChange24h;
    }

    public double getPercentageChange7d() {
        return percentageChange7d;
    }

    public void setPercentageChange7d(double percentageChange7d) {
        this.percentageChange7d = percentageChange7d;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(long marketCap) {
        this.marketCap = marketCap;
    }
}
