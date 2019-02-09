package br.zero.txtask.parser.reader;

import java.io.IOException;

public class ParserReaderMatcher {

    private ParserReader reader;
    private String[] values;

    public ParserReaderMatcher(ParserReader reader) {
        this.reader = reader;
    }

    public ParserReaderMatcher by(String value) {
        this.values = new String[] { value };

        return this;
    }

    public ParserReaderMatcher byEol() {
        // TODO Windows will break this line
        return by("\n");
    }

    public ParserReaderMatcher byAnyOf(String... values) {
        this.values = values;

        return this;
    }

    public boolean go() throws IOException {
        return which() > -1;
    }

    public int which() throws IOException {
        int lengthOfGreatest = getLengthOfGreatest();

        String buffer = reader.get().next(lengthOfGreatest).go();

        return this.matchValues(buffer);
    }

    private int getLengthOfGreatest() {
        int lengthOfGreatest = 0;

        for (CharSequence value : values)
            if (value.length() > lengthOfGreatest)
                lengthOfGreatest = value.length();

        return lengthOfGreatest;
    }

    private int matchValues(String buffer) {
        for (int i = 0; i < values.length; i++) {
            String value = values[i];

            if (buffer.startsWith(value))
                return i;
        }

        return -1;
    }

}