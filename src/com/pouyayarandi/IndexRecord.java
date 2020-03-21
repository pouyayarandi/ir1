package com.pouyayarandi;

/**
 * Created by Pouya on 3/19/20.
 */
public class IndexRecord implements Comparable<IndexRecord> {
    public Post post;
    public float score;

    public IndexRecord(Post post, float score) {
        this.post = post;
        this.score = score;
    }

    @Override
    public int compareTo(IndexRecord o) {
        return (int)((o.score - score) * 10_000_000);
    }
}
