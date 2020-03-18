package com.pouyayarandi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.RAMDirectory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Pouya on 3/17/20.
 */
public class Indexer {

    String fileAddress = "QueryResults.csv";

    private IndexWriter index;
    private CSVParser parser;

    Indexer() throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        FileInputStream file = new FileInputStream(fileAddress);
        InputStreamReader input = new InputStreamReader(file);

        index = new IndexWriter(new RAMDirectory(), new IndexWriterConfig(analyzer));
        parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(input);
    }

    void indexData() throws IOException {
        for (CSVRecord record : parser) {
            System.out.println(HtmlUtilities.convertToPlainText(record.get("Body")));
            index.addDocument(provideDocument(record));
            index.commit();
        }
        index.close();
        System.out.println("index finished");
    }

    private Document provideDocument(CSVRecord record) {
        Document document = new Document();
        document.add(new TextField("Body", record.get("Body"), Field.Store.YES));
        return document;
    }
}
