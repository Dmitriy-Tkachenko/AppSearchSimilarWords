package com.university.project;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Logic {
    private Algorithms algorithms = new Algorithms();

    public void sendRequest(String textRequest, JTextArea mainTextArea, JTextArea topListArea) {
        String text = deleteAllCharactersExceptLetters(mainTextArea.getText());
        String[] textSplit = text.split(" ");

        Map<String, Integer> keywordsAndDistLevenshtein = fillMapKeywordsAndDistLevenshtein(textSplit, textRequest);
        keywordsAndDistLevenshtein = sortMapByValue(keywordsAndDistLevenshtein);

        final int NUMB_TOP_POS = writeNumbTopPos(keywordsAndDistLevenshtein.size());

        String[] topList = topKeywordsByDistLevenshtein(NUMB_TOP_POS, keywordsAndDistLevenshtein);

        Map<String, Integer> keywordsAndCountKeywords = countKeywordsInText(NUMB_TOP_POS, mainTextArea, topList);

        writeTopList(topListArea, keywordsAndCountKeywords);

        highlightKeywords(NUMB_TOP_POS, mainTextArea, keywordsAndCountKeywords);
        highlightKeywords(NUMB_TOP_POS, topListArea, keywordsAndCountKeywords);
    }

    private String deleteAllCharactersExceptLetters(String text) {
        return text.replaceAll("[^a-zA-Zа-яА-Я]", " ").replaceAll("\\s+", " ").trim();
    }

    private Map<String, Integer> fillMapKeywordsAndDistLevenshtein(String[] textSplit, String textRequest) {
        Map<String, Integer> keywordsAndDistLevenshtein = new HashMap<>();
        for (String word : textSplit) {
            int dist = algorithms.distLevenshtein(textRequest, word);
            keywordsAndDistLevenshtein.putIfAbsent(word, dist);
        }
        return keywordsAndDistLevenshtein;
    }

    private int writeNumbTopPos(int countWords) {
        return Math.min(countWords, 10);
    }

    private LinkedHashMap<String, Integer> sortMapByValue(Map<String, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
    }

    private String[] topKeywordsByDistLevenshtein(int NUMB_TOP_POS, Map<String, Integer> keywordsAndDistLevenshtein) {
        String[] topList = new String[NUMB_TOP_POS];
        for (int i = 0; i < NUMB_TOP_POS; i++) {
            topList[i] = String.valueOf(keywordsAndDistLevenshtein.keySet().toArray()[i]);
        }
        return topList;
    }

    private LinkedHashMap<String, Integer> countKeywordsInText(final int NUMB_TOP_POS, JTextArea textArea, String[] topList) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        WholeWordIndexFinder finder = new WholeWordIndexFinder(textArea.getText());
        for (int i = 0; i < NUMB_TOP_POS; i++) {
            List<IndexWrapper> indexes = finder.findIndexesForKeyword(topList[i]);
            map.put(topList[i], indexes.size());
        }
        return map;
    }

    private void writeTopList(JTextArea topListArea, Map<String, Integer> keywordsAndCountKeywords) {
        topListArea.setText("");
        keywordsAndCountKeywords.forEach((k, v) -> topListArea.append(k + " - " + v + "\n"));
    }

    private void highlightKeywords(final int NUMB_TOP_POS, JTextArea textArea, Map<String, Integer> map) {
        WholeWordIndexFinder finder = new WholeWordIndexFinder(textArea.getText());

        textArea.getHighlighter().removeAllHighlights();

        for (int i = 0; i < NUMB_TOP_POS; i++) {
            List<IndexWrapper> indexes = finder.findIndexesForKeyword(String.valueOf(map.keySet().toArray()[i]));
            DefaultHighlighter.DefaultHighlightPainter highlightPainter = colorsToHighlight(i);
            for (IndexWrapper index : indexes) {
                try {
                    textArea.getHighlighter().addHighlight(index.getStart(), index.getEnd(), highlightPainter);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private DefaultHighlighter.DefaultHighlightPainter colorsToHighlight(int wordOrder) {
        DefaultHighlighter.DefaultHighlightPainter highlighter;
        switch (wordOrder) {
            case 0 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                break;
            }
            case 1 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
                break;
            }
            case 2 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
                break;
            }
            case 3 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
                break;
            }
            case 4 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
                break;
            }
            case 5 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
                break;
            }
            case 6 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
                break;
            }
            case 7 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
                break;
            }
            case 8 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.MAGENTA);
                break;
            }
            case 9 : {
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.blue);
                break;
            }
            default:
                highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
        }
        return highlighter;
    }
}
