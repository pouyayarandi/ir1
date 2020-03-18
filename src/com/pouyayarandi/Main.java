package com.pouyayarandi;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Indexer indexer = new Indexer();
            System.out.println("Waiting for index...");
            indexer.indexData();
            System.out.println("Index finished");

        } catch (IOException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
