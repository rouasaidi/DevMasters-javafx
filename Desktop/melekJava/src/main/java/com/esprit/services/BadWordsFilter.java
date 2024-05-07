package com.esprit.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BadWordsFilter {
    private static final Set<String> badWords = new HashSet<>(Arrays.asList("badword1", "badword2", "badword3")); // Add more words as needed

    public static boolean containsBadWords(String content) {
        String[] words = content.toLowerCase().split("\\s+");
        for (String word : words) {
            if (badWords.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
