package org.webcrawler;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<WebCrawler> bots = new ArrayList<>();

        bots.add(new WebCrawler("https://www.wikipedia.org/", 1));
        bots.add(new WebCrawler("https://www.bing.com/", 2));

        for (WebCrawler bot : bots) {
            try {
                bot.getThread().join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}