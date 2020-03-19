package com.pouyayarandi;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pouya on 3/19/20.
 */
public class SearchManager {

    private Analyzer analyzer;

    int limit = 10;
    float threshold = 0.0f;

    SearchManager() {
        analyzer = new StandardAnalyzer();
    }

    IndexRecord[] searchPosts(Post[] posts, String field, String queryTerm) throws ParseException, IOException {
        List<IndexRecord> indexRecords = new ArrayList<>();

        for (Post post: posts) {
            String[] queryTokens = queryTerm.split(" ");
            Query query = QueryManager.makeMustQuery(field, queryTokens);
            float score = post.getMemoryIndex().search(query);
            if (score > threshold)
                indexRecords.add(new IndexRecord(post, score));
        }

        IndexRecord[] result = indexRecords.toArray(new IndexRecord[indexRecords.size()]);
        Arrays.sort(result);

        return result;
    }

    boolean isTagsIncludedInBody(String body, String[] tags) {
        MemoryIndex memoryIndex = new MemoryIndex();
        memoryIndex.addField("BodyTemp", body, analyzer);
        Query query = QueryManager.makeMustNotQuery("BodyTemp", tags);
        float score = memoryIndex.search(query);
        return score != 0.0f;
    }
}
