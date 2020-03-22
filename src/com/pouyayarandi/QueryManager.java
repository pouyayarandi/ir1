package com.pouyayarandi;

import com.sun.istack.internal.Nullable;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * Created by Pouya on 3/18/20.
 */
public class QueryManager {
    private static Query makeQueryWithOccur(String field, String[] queryStrings, BooleanClause.Occur occur) {
        Query query = null;
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
        for (String string: queryStrings)
            query = queryBuilder.add(new TermQuery(new Term(field, string)), occur).build();
        return query;
    }

    public static Query makeMustQuery(String field, String[] queryStrings) {
        return makeQueryWithOccur(field, queryStrings, BooleanClause.Occur.MUST);
    }

    public static Query makeShouldQuery(String field, String[] queryStrings) {
        return makeQueryWithOccur(field, queryStrings, BooleanClause.Occur.SHOULD);
    }

    public static Query termQuery(String field, String term) {
        return new TermQuery(new Term(field, term));
    }

    public static Query makeAndQuery(Query[] queries) {
        Query query = null;
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
        for (Query q: queries)
            query = queryBuilder.add(q, BooleanClause.Occur.MUST).build();
        return query;
    }

    public static Query makeRangeQuery(String field, @Nullable Integer lowerValue, @Nullable Integer upperValue) {
        return IntPoint.newRangeQuery(field,
                lowerValue == null ? Integer.MIN_VALUE : lowerValue,
                upperValue == null ? Integer.MAX_VALUE : upperValue);
    }

    public static Query makeRangeQuery(String field, @Nullable Long lowerValue, @Nullable Long upperValue) {
        return LongPoint.newRangeQuery(field,
                lowerValue == null ? Long.MIN_VALUE : lowerValue,
                upperValue == null ? Long.MAX_VALUE : upperValue);
    }

    public static Query makeExactQuery(String field, float value) {
        return FloatPoint.newExactQuery(field, value);
    }
}
