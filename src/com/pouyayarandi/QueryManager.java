package com.pouyayarandi;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.*;

/**
 * Created by Pouya on 3/18/20.
 */
public class QueryManager {

    List<MemoryIndex> memoryIndices;
    private Analyzer analyzer;
    int limit = 10;
    float threshold = 0.0f;

    QueryManager(List<MemoryIndex> memoryIndices) throws IOException {
        analyzer = new StandardAnalyzer();
        this.memoryIndices = memoryIndices;
    }

    private String makeQueryString(String queryString) {
        String[] tokens = queryString.split(" ");
        List<String> processedTokens = new ArrayList<>();
        for (String token: tokens) processedTokens.add("+"+token);
        String query = String.join(" ", processedTokens);
        System.out.println(query);
        return query;
    }

    HashMap<MemoryIndex, Float> searchBody(String queryTerm) throws ParseException, IOException {
        HashMap<MemoryIndex, Float> result = new HashMap<>();
        QueryParser queryParser = new QueryParser("Body", analyzer);
        Query query = queryParser.parse(makeQueryString(queryTerm));

        for (MemoryIndex memoryIndex: memoryIndices) {
            float score = memoryIndex.search(query);
            if (score > threshold)
                result.put(memoryIndex, score);
        }

        return result;
    }
}
