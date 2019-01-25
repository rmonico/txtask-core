package br.zero.txtask.core.matchers;

public class Utilities {

    public static String toStrWithOrdinal(int index) {
        return String.format("%d%s", index + 1, ordinalOf(index));
    }

    public static String ordinalOf(int index) {
        switch (index) {
            case 0:
                return "st";
            case 1:
                return "nd";
            case 2:
                return "rd";
            default:
                return "th";
        }
    }

}
