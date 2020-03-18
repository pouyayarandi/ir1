package com.pouyayarandi;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Directory directory = new RAMDirectory();
            Indexer indexer = new Indexer(directory);
            QueryManager queryManager = new QueryManager(directory);

            System.out.println("Waiting for index...");
            indexer.indexData();
            System.out.println("Index finished");

            //queryManager.searchBody("Python");
        } catch (IOException  exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
