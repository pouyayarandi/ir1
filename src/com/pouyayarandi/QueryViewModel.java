package com.pouyayarandi;

import org.apache.lucene.search.Query;

import java.util.Date;

/**
 * Created by Pouya on 3/22/20.
 */
public class QueryViewModel {
    Query query;
    String description;

    QueryViewModel(Query query, String description) {
        this.query = query;
        this.description = description;
    }

    static QueryViewModel searchBodyWithQuery(String query) {
        String[] queryTokens = query.split(" ");
        return new QueryViewModel(QueryManager.makeMustQuery("Body", queryTokens), "'"+query+"'");
    }

    static QueryViewModel searchId(String id) {
        String[] terms = {id};
        return new QueryViewModel(QueryManager.makeShouldQuery("Id", terms), String.format("post with id %s", id));
    }

    static QueryViewModel searchPostsWithNotRelatedTags() {
        return new QueryViewModel(QueryManager.makeExactQuery("TagsScore", 0.0f), "posts with no related tags");
    }

    static QueryViewModel searchParentIdAndScore(String parentId, int score) {
        Query[] queries = {
                QueryManager.termQuery("ParentId", parentId),
                QueryManager.makeRangeQuery("Score", score, null)
        };
        Query query = QueryManager.makeAndQuery(queries);
        String desc = String.format("posts for question id '%s' with score more than %d", parentId, score);
        return new QueryViewModel(query, desc);
    }

    static QueryViewModel searchDateAndBody(String body, Date lowerDate, Date upperDate) {
        String[] queryTokens = body.split(" ");
        Query[] queries = {
                QueryManager.makeMustQuery("Body", queryTokens),
                QueryManager.makeRangeQuery("CreationDate", lowerDate.getTime(), upperDate.getTime())
        };
        Query query = QueryManager.makeAndQuery(queries);
        String desc = String.format("posts between %s and %s with '%s'",
                lowerDate.toString(), upperDate.toString(), body);
        return new QueryViewModel(query, desc);
    }
}
