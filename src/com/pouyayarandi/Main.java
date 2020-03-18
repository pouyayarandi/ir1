package com.pouyayarandi;

import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        try {
            Indexer indexer = new Indexer();
            QueryManager queryManager = new QueryManager(indexer.getMemoryIndices());

            System.out.println("Waiting for index...");
            indexer.indexData();
            System.out.println("Index finished");

            HashMap<MemoryIndex, Float> results = queryManager.searchBody("python");
            System.out.println(results.values());
        } catch (IOException | ParseException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
