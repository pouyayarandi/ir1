package com.pouyayarandi;

import com.sun.istack.internal.Nullable;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pouya on 3/19/20.
 */
public class SearchManager {

    private static Analyzer analyzer = new StandardAnalyzer();

    int limit = 10;
    float threshold = 0.0f;
    Post[] posts;

    SearchManager(@Nullable Post[] posts) {
        this.posts = posts;
    }

    IndexRecord[] searchPosts(Query query) throws IOException {
        List<IndexRecord> indexRecords = new ArrayList<>();

        for (Post post: posts) {
            float score = post.getMemoryIndex().search(query);
            if (score > threshold)
                indexRecords.add(new IndexRecord(post, score));
        }

        IndexRecord[] result = indexRecords.toArray(new IndexRecord[indexRecords.size()]);
        Arrays.sort(result);

        return result;
    }

    static float tagsIncludedInBody(String body, String[] tags) {
        MemoryIndex memoryIndex = new MemoryIndex();
        memoryIndex.addField("BodyTag", body, analyzer);
        Query query = QueryManager.makeMustNotQuery("BodyTag", tags);
        return memoryIndex.search(query);
    }
}
