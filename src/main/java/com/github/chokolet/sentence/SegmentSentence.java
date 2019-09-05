package com.github.chokolet.sentence;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SegmentSentence {

    private SegmentSentence() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> getSentence(String text) {
        BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.KOREAN);
        boundary.setText(text);
        return printEachForward(boundary, text);
    }

    private static List<String> printEachForward(BreakIterator boundary, String source) {
        int start = boundary.first();
        List<String> getSentence = new ArrayList<>();

        for (int end = boundary.next(), i = 0; end != BreakIterator.DONE; start = end, end = boundary.next(), i++) {
            getSentence.add(source.substring(start, end));
        }
        return getSentence;
    }

}
