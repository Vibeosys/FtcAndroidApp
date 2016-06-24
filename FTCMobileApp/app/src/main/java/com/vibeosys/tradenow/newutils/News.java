package com.vibeosys.tradenow.newutils;

/**
 * Created by akshay on 21-06-2016.
 */
public class News {

    private String title;
    private String link;
    private String guid;
    private String pubdate;
    private String description;
    private String contentEncoded;

    public News(String title, String link, String guid, String pubdate, String description,
                String contentEncoded) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.pubdate = pubdate;
        this.description = description;
        this.contentEncoded = contentEncoded;
    }

    public News(String title, String link, String guid, String pubdate, String description) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.pubdate = pubdate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentEncoded() {
        return contentEncoded;
    }

    public void setContentEncoded(String contentEncoded) {
        this.contentEncoded = contentEncoded;
    }
}
