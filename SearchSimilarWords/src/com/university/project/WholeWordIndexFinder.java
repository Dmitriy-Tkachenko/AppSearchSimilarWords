package com.university.project;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WholeWordIndexFinder {
    private String searchString;

    public WholeWordIndexFinder(String searchString) {
        this.searchString = searchString;
    }

    public List<IndexWrapper> findIndexesForKeyword(String keyword) {
        String regex = "\\b"+keyword+"\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(searchString);

        List<IndexWrapper> wrappers = new ArrayList<>();

        while(matcher.find()){
            int end = matcher.end();
            int start = matcher.start();
            IndexWrapper wrapper = new IndexWrapper(start, end);
            wrappers.add(wrapper);
        }
        return wrappers;
    }
}
