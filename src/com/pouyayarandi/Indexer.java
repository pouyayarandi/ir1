package com.pouyayarandi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.memory.MemoryIndex;

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
    private List<MemoryIndex> memoryIndices = new ArrayList<>();
    private Analyzer analyzer = new StandardAnalyzer();

    Indexer() throws IOException {
        FileInputStream file = new FileInputStream(fileAddress);
        InputStreamReader input = new InputStreamReader(file);
        parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(input);
    }

    List<MemoryIndex> getMemoryIndices() {
        return memoryIndices;
    }

    void indexData() throws IOException {
        for (CSVRecord record : parser) {
            memoryIndices.add(provideMemoryIndex(record));
        }
    }

    private MemoryIndex provideMemoryIndex(CSVRecord record) {
        String body = HtmlUtilities.convertToPlainText(record.get("Body"));
        List<String> tags = TagsUtilities.parseTags(record.get("Tags"));
        boolean NoTagIncluded = TagsUtilities.isBodyContainsAnyTag(body, tags);

        MemoryIndex memoryIndex = new MemoryIndex();
        memoryIndex.addField(new StringField("Id", record.get("Id"), Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("Title", record.get("Title"), Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("Body", body, Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("DisplayName", record.get("DisplayName"), Field.Store.YES), analyzer);
        memoryIndex.addField(new IntPoint("NoTagIncluded", NoTagIncluded ? 1 : 0), analyzer);
        return memoryIndex;
    }
}
