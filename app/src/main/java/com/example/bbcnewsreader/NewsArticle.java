package com.example.bbcnewsreader;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Date;

/**
 * The NewsArticle class represents a news article.
 */
public class NewsArticle {
    private String title;
    private String description;
    private String HTTPLink;
    private String date;

    protected long id;

    /**
     * Constructs a NewsArticle object based on the provided XML element.
     * @param element The XML element containing the news article data.
     */
    public NewsArticle(Element element){
        this.title = element.getElementsByTagName("title").item(0).getTextContent();
        this.description = element.getElementsByTagName("description").item(0).getTextContent();
        this.HTTPLink = element.getElementsByTagName("link").item(0).getTextContent();
        this.date = element.getElementsByTagName("pubDate").item(0).getTextContent();
    }
    /**
     * Constructs a NewsArticle object with the specified properties.
     * @param title The title of the news article.
     * @param description The description of the news article.
     * @param date The date of the news article.
     * @param HTTPLink The HTTP link of the news article.
     * @param id The ID of the news article.
     */
    public NewsArticle(String title, String description, String date, String HTTPLink, long id){
        this.title = title;
        this.description = description;
        this.HTTPLink = HTTPLink;
        this.date = date;
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getHTTPLink() {
        return HTTPLink;
    }
    public String getDate() {
        return date;
    }

    public long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    /**
     * Returns a string representation of the NewsArticle object.
     * @return A string representation of the NewsArticle object.
     */

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", HTTPlink='" + HTTPLink + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}


