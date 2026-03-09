package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Item
{
    private String title;
    private String link;
    private String description;
    private String pubDate;

    public Item(String title, String link, String description, String pubDate)
    {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

}

class Feed
{
    private String title;
    private String link;
    private String description;
    private List<Item> items;

    public Feed(String title, String link, String description)
    {
        this.title = title;
        this.link = link;
        this.description = description;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

    public List<Item> getItems()
    {
        return items;
    }

}

public class Main
{
    public static void main(String[] args) throws IOException
    {
        String url = "http://rss.cnn.com/rss/edition.rss";

        Document doc = Jsoup.connect(url).get();

        Element channel = doc.selectFirst("channel");
        if (channel == null)
        {
            System.out.println("RSS feed-ul nu conține elementul <channel>!");
            return;
        }

        String titleFeed = channel.selectFirst("title") != null ? channel.selectFirst("title").text() : "Feed Title Does not exist!";
        String linkFeed = channel.selectFirst("link") != null ? channel.selectFirst("link").text() : "Feed Link Does not exist!";
        String descriptionFeed = channel.selectFirst("description") != null ? channel.selectFirst("description").text() : "Feed Description Does not exist!";

        Feed rssFeed = new Feed(titleFeed, linkFeed, descriptionFeed);

        Elements items = channel.select("item");
        for (Element item : items)
        {
            String titleItem = item.selectFirst("title") != null ? item.selectFirst("title").text() : "Item Title does not exist!";
            String linkItem = item.selectFirst("link") != null ? item.selectFirst("link").text() : "Item Link does not exist!";
            String descriptionItem = item.selectFirst("description") != null ? item.selectFirst("description").text() : "Item Description does not exist!";
            String pubDateItem = item.selectFirst("pubDate") != null ? item.selectFirst("pubDate").text() : "Item Publication Date does not exist!";

            Item rssItem = new Item(titleItem, linkItem, descriptionItem, pubDateItem);
            rssFeed.addItem(rssItem);
        }

        for (Item rssItem : rssFeed.getItems())
        {
            System.out.println("Title: " + rssItem.getTitle());
            System.out.println("Link: " + rssItem.getLink());
            System.out.println("\n");
        }
    }
}