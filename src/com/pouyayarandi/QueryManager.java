package com.pouyayarandi;

import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
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

    public static Query makeMustNotQuery(String field, String[] queryStrings) {
        return makeQueryWithOccur(field, queryStrings, BooleanClause.Occur.MUST_NOT);
    }

    public static Query makeMustQuery(String field, String queryString) {
        String[] queryTokens = queryString.split(" ");
        return QueryManager.makeMustQuery(field, queryTokens);
    }

    public static Query makeExactQuery(String field, int value) {
        return IntPoint.newExactQuery(field, value);
    }

    public static Query makeExactQuery(String field, float value) {
        return FloatPoint.newExactQuery(field, value);
    }
}
