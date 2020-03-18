package com.pouyayarandi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pouya on 3/18/20.
 */
public class HtmlUtilities {
    static String convertToPlainText(String input) {
        String result;
        result = removeHTMLTags(input);
        result = removeCharacterEntities(result);
        return result;
    }

    private static String removeHTMLTags(String input) {
        boolean isInTag = false;
        String result = "";
        for (Character c: input.toCharArray()) {
            if (c.equals('<')) isInTag = true;
            else if (c.equals('>')) isInTag = false;
            else if (!isInTag) result += c;
        }
        return result;
    }

    private static String removeCharacterEntities(String input) {
        HashMap<String, String> charEntityLookup = new HashMap<>();
        charEntityLookup.put("&nbsp;", "");
        charEntityLookup.put("&lt;", "<");
        charEntityLookup.put("&gt;", ">");
        charEntityLookup.put("&amp;", "&");
        charEntityLookup.put("&quot;", "\"");
        charEntityLookup.put("&apos;", "'");

        String result = input;
        for (Map.Entry<String, String> changeCase: charEntityLookup.entrySet()) {
            result = result.replaceAll(changeCase.getKey(), changeCase.getValue());
        }

        return result;
    }
}
