package com.pouyayarandi;

import org.apache.lucene.search.Query;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Indexer indexer = new Indexer();
            System.out.println("Waiting for index (It may take a while) ...");
            indexer.indexData();
            System.out.println("Index finished");
            SearchManager searchManager = new SearchManager(indexer.getPosts());

            Query query = QueryManager.makeExactQuery("TagsScore", 0.0f);
            //Query query = QueryManager.makeMustQuery("Body", "python");
            IndexRecord[] indexRecords = searchManager.searchPosts(query);
            System.out.println(String.format("%d result(s) were found for: %s", indexRecords.length, "--"));
            /*
            for (IndexRecord record: indexRecords) {
                System.out.println(record.post.getTitle());
                System.out.println(record.post.getUser().getDisplayName());
                System.out.println(record.score);
                System.out.println("====================");
            }*/
        } catch (IOException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
