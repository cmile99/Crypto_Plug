package com.threeklines.cryptoplug.backside;

public class Article {
    private final String id;
    private final String guid;
    private final int datePublished;
    private final String imageUrl;
    private final String title;
    private final String url;
    private final String source;
    private final String body;
    private final String tags;
    private final String categories;

    public Article(String id, String guid, int datePublished, String imageUrl, String title, String url, String source, String body, String tags, String categories) {
        this.id = id;
        this.guid = guid;
        this.datePublished = datePublished;
        this.imageUrl = imageUrl;
        this.title = title;
        this.url = url;
        this.source = source;
        this.body = body;
        this.tags = tags;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }

    public int getDatePublished() {
        return datePublished;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }

    public String getBody() {
        return body;
    }

    public String getTags() {
        return tags;
    }

    public String getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", guid='" + guid + '\'' +
                ", datePublished=" + datePublished +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }
}
