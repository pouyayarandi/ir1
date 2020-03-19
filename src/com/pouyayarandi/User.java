package com.pouyayarandi;

import org.apache.commons.csv.CSVRecord;

/**
 * Created by Pouya on 3/19/20.
 */
public class User {
    private String id;
    private String displayName;

    public User(CSVRecord record) {
        id = record.get("OwnerUserId");
        displayName = record.get("DisplayName");
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
