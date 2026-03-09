package org.example;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// ADT pentru un item RSS
class RSSItem {
    private String title;
    private String link;
    private String description;
    private String pubDate;

    public RSSItem(String title, String link, String description, String pubDate) {
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

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }
}

// ADT pentru întregul feed RSS
class RSSFeed {
    private String title;
    private String link;
    private String description;
    private List<RSSItem> items;

    public RSSFeed(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.items = new ArrayList<>();
    }

    public void addItem(RSSItem item) {
        items.add(item);
    }

    public List<RSSItem> getItems() {
        return items;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }
}

public class Main {
    public static void main(String[] args) {
        String url = "http://rss.cnn.com/rss/edition.rss";

        try {
            // Se încarcă documentul XML de la URL
            Document doc = Jsoup.connect(url).get();

            // Se selectează canalul principal și verificăm dacă există
            Element channel = doc.selectFirst("channel");
            if (channel == null) {
                System.out.println("RSS feed-ul nu conține elementul <channel>!");
                return;
            }

            // Se citesc atributele generale, verificând null
            Element titleEl = channel.selectFirst("title");
            String feedTitle = titleEl != null ? titleEl.text() : "";

            Element linkEl = channel.selectFirst("link");
            String feedLink = linkEl != null ? linkEl.text() : "";

            Element descEl = channel.selectFirst("description");
            String feedDescription = descEl != null ? descEl.text() : "";

            // Se creează ADT-ul feed
            RSSFeed rssFeed = new RSSFeed(feedTitle, feedLink, feedDescription);

            // Se parcurg item-urile
            Elements items = channel.select("item");
            for (Element item : items) {
                Element itemTitleEl = item.selectFirst("title");
                String title = itemTitleEl != null ? itemTitleEl.text() : "";

                Element itemLinkEl = item.selectFirst("link");
                String link = itemLinkEl != null ? itemLinkEl.text() : "";

                Element itemDescEl = item.selectFirst("description");
                String description = itemDescEl != null ? itemDescEl.text() : "";

                Element itemPubDateEl = item.selectFirst("pubDate");
                String pubDate = itemPubDateEl != null ? itemPubDateEl.text() : "";

                RSSItem rssItem = new RSSItem(title, link, description, pubDate);
                rssFeed.addItem(rssItem);
            }

            // Afișarea titlului și link-ului fiecărui item
            for (RSSItem rssItem : rssFeed.getItems()) {
                System.out.println("Titlu: " + rssItem.getTitle());
                System.out.println("Link: " + rssItem.getLink());
                System.out.println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}