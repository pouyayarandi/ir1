package com.pouyayarandi;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import java.io.IOException;

/**
 * Created by Pouya on 3/18/20.
 */
public class QueryManager {
    Directory directory;
    private Analyzer analyzer;
    private Query query;
    private IndexSearcher indexSearcher;
    int limit = 10;

    QueryManager(Directory directory) throws IOException {
        this.directory = directory;
        analyzer = new StandardAnalyzer();
        indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
    }

    TopDocs searchBody(String queryTerm) throws ParseException, IOException {
        queryTerm = queryTerm.toLowerCase();
        String[] tokens = queryTerm.split(" ");
        for (String token: tokens) token = "+" + token;
        QueryParser queryParser = new QueryParser("Body", analyzer);
        query = queryParser.parse(String.join(" ", tokens));
        return indexSearcher.search(query, limit);
    }
}
