package com.pouyayarandi;

import com.sun.istack.internal.Nullable;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pouya on 3/19/20.
 */
public class SearchManager {

    private static Analyzer analyzer = new StandardAnalyzer();

    int limit = -1;
    float threshold = 0.0f;

    Question[] questions;
    Answer[] answers;

    SearchManager(@Nullable Question[] questions, @Nullable Answer[] answers) {
        this.questions = questions;
        this.answers = answers;
    }

    private IndexRecord[] searchPosts(Post[] posts, Query query) {
        List<IndexRecord> indexRecords = new ArrayList<>();
        for (Post post: posts) {
            float score = post.getMemoryIndex().search(query);
            if (score > threshold) indexRecords.add(new IndexRecord(post, score));
        }
        IndexRecord[] result = indexRecords.toArray(new IndexRecord[indexRecords.size()]);
        Arrays.sort(result);
        return limit > 0 ? Arrays.copyOfRange(result, 0, Math.min(limit, result.length)) : result;
    }

    IndexRecord[] searchQuestions(Query query) {
        return searchPosts(questions, query);
    }

    IndexRecord[] searchAnswers(Query query) {
        return searchPosts(answers, query);
    }

    static float tagsScoreInBody(String body, String[] tags) {

        for (String tag: tags)
            if (body.toLowerCase().contains(tag.toLowerCase())) return 1.0f;

        MemoryIndex memoryIndex = new MemoryIndex();
        memoryIndex.addField(new TextField("body", body, Field.Store.NO), analyzer);
        float finalScore = 0.0f;
        for (String tag: tags) {
            float score = memoryIndex.search(new TermQuery(new Term("body", tag.toLowerCase())));
            if (score > finalScore) finalScore = score;
        }
        return finalScore;
    }
}
