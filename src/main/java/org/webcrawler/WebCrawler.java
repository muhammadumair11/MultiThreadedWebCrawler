package org.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WebCrawler implements Runnable {

    private static final int MAX_DEPTH = 1;
    private Thread thread;
    private String firstLink;
    private ArrayList<String> visitedLinks = new ArrayList<>();
    private int ID;

    static Map<String, String> data = new HashMap<>();

    public WebCrawler(String firstLink, int num) {
        System.out.println("WebCrawler created");
        this.firstLink = firstLink;
        this.ID = num;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        crawl(1, firstLink);
    }

    public Thread getThread() {
        return thread;
    }


    private void crawl(int level, String url) {
        if (level <= 1) {

            Document doc = request(url);
            data.put(doc.nodeName(), url);

            if (doc != null) {


                for (Element link : doc.select("a[href]")) {
                    String nextLink = link.absUrl("href");
                    if (!visitedLinks.contains(nextLink)) {
                        crawl(level++, nextLink);
                    }
                }
            }
        }
    }

    private Document request(String url) {
        try {
            Connection conn = Jsoup.connect(url);

            Document doc = conn.get();

            if (conn.response().statusCode() == 200) {
                System.out.println("Link    " + url);
                System.out.println(doc.nodeName());

                visitedLinks.add(url);

                return doc;
            }
            return null;

        } catch (IOException e) {
            return null;
        }
    }
}
