package com.pouyayarandi;

import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.memory.MemoryIndex;

/**
 * Created by Pouya on 3/22/20.
 */
public class Answer extends Post {

    private int score;

    Answer(CSVRecord record) {
        super(record);
        score = Integer.parseInt(record.get("Score"));
    }

    public int getScore() {
        return score;
    }

    @Override
    public MemoryIndex getMemoryIndex() {
        MemoryIndex memoryIndex = super.getMemoryIndex();
        memoryIndex.addField(new IntPoint("Score", score), analyzer);
        return memoryIndex;
    }
}
