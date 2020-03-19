package com.pouyayarandi;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Indexer indexer = new Indexer();
            SearchManager searchManager = new SearchManager();

            System.out.println("Waiting for index...");
            indexer.indexData();
            System.out.println("Index finished");

            IndexRecord[] indexRecords = searchManager.searchPosts(indexer.getPosts(), "Body", "python");
            for (IndexRecord record: indexRecords) {
                System.out.println(record.post.getTitle());
                System.out.println(record.post.getUser().getDisplayName());
                System.out.println(record.score);
                System.out.println("====================");
            }
        } catch (IOException | ParseException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
