package com.pouyayarandi;

import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.memory.MemoryIndex;

/**
 * Created by Pouya on 3/22/20.
 */
public class Question extends Post {

    protected String title;
    private String[] tags;
    private float tagsScore;

    Question(CSVRecord record) {
        super(record);
        title = record.get("Title");
        tags = TagsUtilities.parseTags(record.get("Tags"));
        tagsScore = tags.length == 0 ? 1.0f : SearchManager.tagsScoreInBody(body, tags);
    }

    public String getTitle() {
        return title;
    }

    public String[] getTags() {
        return tags;
    }

    @Override
    public MemoryIndex getMemoryIndex() {
        MemoryIndex memoryIndex = super.getMemoryIndex();
        memoryIndex.addField(new TextField("Title", title, Field.Store.YES), analyzer);
        memoryIndex.addField(new FloatPoint("TagsScore", tagsScore), analyzer);
        return memoryIndex;
    }

    @Override
    public void printObject() {
        System.out.println(String.format("TITLE  %s", title));
        super.printObject();
        System.out.println(String.format("TAG    %s", String.join(", ", tags)));
    }
}
