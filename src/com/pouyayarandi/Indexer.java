package com.pouyayarandi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pouya on 3/17/20.
 */
public class Indexer {

    String fileAddress = "QueryResults.csv";

    private CSVParser parser;
    private List<Post> posts = new ArrayList<>();

    Indexer() throws IOException {
        FileInputStream file = new FileInputStream(fileAddress);
        InputStreamReader input = new InputStreamReader(file);
        parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(input);
    }

    Post[] getPosts() {
        return posts.toArray(new Post[posts.size()]);
    }

    void indexData() throws IOException {
        for (CSVRecord record : parser) {
            posts.add(new Post(record));
        }
    }
}
