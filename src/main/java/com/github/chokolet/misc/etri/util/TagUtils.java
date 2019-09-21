package com.github.chokolet.misc.etri.util;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class TagUtils {

    private TagUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * right spacce check
     *
     * @param word
     * @return
     */
    public static boolean lrSpaceChecker(String word) {
        if (word.charAt(0) == ' ' || word.charAt(word.length() - 1) == ' ') {
            return true;
        }
        return false;
    }


    private static final Set<String> SS_POS = new HashSet<String>() {{
        add("\"");
        add("'");
    }};

    private static final Set<String> SW_POS = new HashSet<String>() {{
        add("@");
        add("#");
        add("$");
        add("%");
        add("^");
        add("&");
        add("*");
        add("_");
        add("+");
        add("=");
        add("`");
    }};
    private static final Set<String> SO_POS = new HashSet<String>() {{
        add("~");
        add("-");
    }};

    /**
     * Convert etri pos tag string.
     *
     * @param surface the surface
     * @param pos     the pos
     * @return the string
     */
    public static String convertEtriPosTag(String surface, String pos) {

        if (SS_POS.contains(surface)) return "SS";
        if (SW_POS.contains(surface)) return "SW";
        if (SO_POS.contains(surface)) return "SO";

        switch (pos) {
            case "SC":
                return "SP";
            case "NNBC":
                return "NNB";
            case "SSO":
            case "SSC":
                return "SS";
            case "SY":
                return "SW";
            case "UN":
                return "UNK";
        }

        return pos;
    }
}
