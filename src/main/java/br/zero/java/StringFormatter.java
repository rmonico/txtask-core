package br.zero.java;

public class StringFormatter {

    private String s;

    public StringFormatter(String s) {
        this.s = s;
    }

    public static StringFormatter s(String s) {
        return new StringFormatter(s);
    }

    public String format(Object... args) {
        return String.format(this.s, args);
    }

    public static String ordinal(int index) {
        return s("%d%s").format(index + 1, suffix(index));
    }

    public static String suffix(int index) {
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
