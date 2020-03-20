package com.pouyayarandi;

import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.memory.MemoryIndex;

/**
 * Created by Pouya on 3/19/20.
 */
public class Post {
    private String id;
    private String title;
    private String body;
    private String[] tags;
    private User user;
    private float tagsScore;

    private Analyzer analyzer = new StandardAnalyzer();

    public Post(CSVRecord record) {
        id = record.get("Id");
        title = record.get("Title");
        body = HtmlUtilities.convertToPlainText(record.get("Body"));
        tags = TagsUtilities.parseTags(record.get("Tags"));
        user = new User(record);

        if (tags.length == 0) {
            tagsScore = 0.0f;
        }
        else {
            tagsScore = SearchManager.tagsIncludedInBody(body, tags);
            System.out.println(tagsScore);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public MemoryIndex getMemoryIndex() {
        MemoryIndex memoryIndex = new MemoryIndex();
        memoryIndex.addField(new StringField("Id", id, Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("Title", title, Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("Body", body, Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("DisplayName", user.getDisplayName(), Field.Store.YES), analyzer);
        memoryIndex.addField(new FloatPoint("TagsScore", tagsScore), analyzer);
        return memoryIndex;
    }
}
