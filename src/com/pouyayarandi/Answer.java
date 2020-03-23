package com.pouyayarandi;

import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.memory.MemoryIndex;

/**
 * Created by Pouya on 3/22/20.
 */
public class Answer extends Post {

    private int score;
    private String parentPostId;

    Answer(CSVRecord record) {
        super(record);
        score = Integer.parseInt(record.get("Score"));
        parentPostId = record.get("ParentId");
    }

    public String getParentPostId() {
        return parentPostId;
    }

    public int getScore() {
        return score;
    }

    @Override
    public MemoryIndex getMemoryIndex() {
        MemoryIndex memoryIndex = super.getMemoryIndex();
        memoryIndex.addField(new IntPoint("Score", score), analyzer);
        memoryIndex.addField(new IntPoint("ParentId", Integer.parseInt(parentPostId)), analyzer);
        return memoryIndex;
    }

    @Override
    public void printObject() {
        super.printObject();
        System.out.println(String.format("SCORE  %d", score));
    }
}
