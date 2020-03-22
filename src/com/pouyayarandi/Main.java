package com.pouyayarandi;

import org.apache.lucene.search.Query;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            IndexManager indexManager = new IndexManager();
            System.out.println("Waiting for index (It may take a while) ...");
            indexManager.indexData();
            System.out.println("Index finished");
            SearchManager searchManager = new SearchManager(indexManager.getQuestions());

            Query query = QueryManager.makeExactQuery("TagsScore", 0.0f);
            //Query query = QueryManager.makeMustQuery("Body", "ios");
            //Query query = QueryManager.makeMustQuery("Id", "57842756");

            IndexRecord[] indexRecords = searchManager.searchPosts(query);
            System.out.println(String.format("%d result(s) were found for: %s", indexRecords.length, "--"));

            for (IndexRecord record: indexRecords) {
                Question question = (Question)(record.post);
                System.out.println(question.getTitle());
                System.out.println(question.getUser().getDisplayName());
                System.out.println(record.score);
                System.out.println("============================================================");
            }
        } catch (IOException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
