package com.pouyayarandi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pouya on 3/18/20.
 */
public class TagsUtilities {
    static List<String> parseTags(String input) {
        List<String> tags = new ArrayList<>();
        String currentTag = "";
        for (Character c: input.toCharArray()) {
            if (c.equals('<')) currentTag = "";
            else if (c.equals('>')) tags.add(currentTag);
            else currentTag += c;
        }
        return tags;
    }

    static boolean isBodyContainsAnyTag(String body, List<String> tags) {
        for (String tag: tags) {
            if (body.contains(tag)) return true;
        }
        return false;
    }
}
