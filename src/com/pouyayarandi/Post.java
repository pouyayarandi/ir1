package com.pouyayarandi;

import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
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
    private boolean isTagIncluded;

    private Analyzer analyzer = new StandardAnalyzer();

    public Post(CSVRecord record) {
        id = record.get("Id");
        title = record.get("Title");
        body = HtmlUtilities.convertToPlainText(record.get("Body"));
        tags = TagsUtilities.parseTags(record.get("Tags"));
        user = new User(record);

        SearchManager searchManager = new SearchManager();
        if (tags.length == 0) isTagIncluded = false;
        else isTagIncluded = searchManager.isTagsIncludedInBody(body, tags);
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

    private boolean isTagIncluded() {
        return isTagIncluded;
    }

    public MemoryIndex getMemoryIndex() {
        MemoryIndex memoryIndex = new MemoryIndex();
        memoryIndex.addField(new StringField("Id", id, Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("Title", title, Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("Body", body, Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("DisplayName", user.getDisplayName(), Field.Store.YES), analyzer);
        memoryIndex.addField(new IntPoint("TagIncluded", isTagIncluded() ? 1 : 0), analyzer);
        return memoryIndex;
    }
}
