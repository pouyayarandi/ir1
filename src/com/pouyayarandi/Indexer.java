package com.pouyayarandi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Pouya on 3/17/20.
 */
public class Indexer {

    String fileAddress = "QueryResults.csv";

    private IndexWriter index;
    private CSVParser parser;

    Indexer(Directory directory) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        FileInputStream file = new FileInputStream(fileAddress);
        InputStreamReader input = new InputStreamReader(file);

        index = new IndexWriter(directory, new IndexWriterConfig(analyzer));
        parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(input);
    }

    void indexData() throws IOException {
        index.commit();
        for (CSVRecord record : parser) {
            index.addDocument(provideDocument(record));
            index.commit();
        }
        index.close();
    }

    private Document provideDocument(CSVRecord record) {
        String body = HtmlUtilities.convertToPlainText(record.get("Body")).toLowerCase();
        List<String> tags = TagsUtilities.parseTags(record.get("Tags"));
        boolean NoTagIncluded = TagsUtilities.isBodyContainsAnyTag(body, tags);
        Document document = new Document();
        document.add(new StringField("Id", record.get("Id"), Field.Store.YES));
        document.add(new TextField("Title", record.get("Title"), Field.Store.YES));
        document.add(new TextField("Body", body, Field.Store.YES));
        document.add(new TextField("DisplayName", record.get("DisplayName"), Field.Store.YES));
        document.add(new IntPoint("NoTagIncluded", NoTagIncluded ? 1 : 0));
        return document;
    }
}
