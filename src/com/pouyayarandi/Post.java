package com.pouyayarandi;

import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.memory.MemoryIndex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pouya on 3/19/20.
 */
public class Post {
    protected String id;
    protected String body;
    protected User user;
    protected Date creationDate;

    protected Analyzer analyzer = new StandardAnalyzer();

    public Post(CSVRecord record) {
        id = record.get(0);
        body = HtmlUtilities.convertToPlainText(record.get("Body"));
        user = new User(record);
        try {
            creationDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(record.get(4));
        } catch (ParseException e) {
            creationDate = null;
        }
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public Long getCreationTimestamp() {
        return creationDate != null ? creationDate.getTime() : -1;
    }

    public MemoryIndex getMemoryIndex() {
        MemoryIndex memoryIndex = new MemoryIndex();
        memoryIndex.addField(new IntPoint("Id", Integer.parseInt(id)), analyzer);
        memoryIndex.addField(new TextField("Body", body, Field.Store.YES), analyzer);
        memoryIndex.addField(new TextField("DisplayName", user.getDisplayName(), Field.Store.YES), analyzer);
        memoryIndex.addField(new LongPoint("CreationDate", getCreationTimestamp()), analyzer);
        return memoryIndex;
    }

    public void printObject() {
        System.out.println(String.format("ID     %s", id));
        System.out.println(String.format("DATE   %s", creationDate.toString()));
        System.out.println(String.format("BODY   %s%s",
                body.substring(0, Math.min(body.length(), 100)).replace("\n",""),
                body.length() > 100 ? "..." : ""));
        System.out.println(String.format("USER   %s", user.getDisplayName()));
    }
}
