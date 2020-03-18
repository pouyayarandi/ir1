package com.pouyayarandi;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Indexer indexer = new Indexer();
            indexer.indexData();
        } catch (IOException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
