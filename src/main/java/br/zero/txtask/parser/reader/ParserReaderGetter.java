package br.zero.txtask.parser.reader;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserReaderGetter {

    private ParserReader reader;
    private Integer charCount;
    private List<String> untilValues;

    public ParserReaderGetter(ParserReader reader) {
        this.reader = reader;
    }

    public ParserReaderGetter next(int charCount) {
        this.charCount = charCount;

        return this;
    }

    private List<String> untilValues() {
        if (this.untilValues == null)
            this.untilValues = new ArrayList<>();

        return this.untilValues;
    }

    public ParserReaderGetter until(String... values) {
        untilValues().addAll(asList(values));

        return this;
    }

    public ParserReaderGetter or() {
        return this;
    }

    public ParserReaderGetter eol() {
        untilValues().add(null);

        return this;
    }

    public String go() throws IOException {
        if (this.untilValues != null && this.charCount != null)
            throw new RuntimeException("Either 'until' or 'next' can be used, not both.");

        if (this.charCount != null) {
            int charCount = this.charCount;

            return reader.get(charCount).toString();
        } else {
            // TODO Refactor
            StringBuilder buffer = new StringBuilder("");

            boolean somethingFound = false;
            int indexOfFirstValue = -1;
            int readCharCount = 4;
            boolean readerFinished = false;

            while (!somethingFound && !readerFinished) {
                buffer = reader.get(readCharCount);

                readerFinished = buffer.length() < readCharCount;

                readCharCount *= 2;

                for (String value : untilValues) {
                    String v = value == null ? "\n" : value;

                    int valueIndex = buffer.indexOf(v);

                    boolean eolFoundOnEof = value == null && readerFinished;

                    if (valueIndex > -1) {
                        if (!somethingFound)
                            indexOfFirstValue = valueIndex;
                        else if (valueIndex < indexOfFirstValue)
                            indexOfFirstValue = valueIndex;
                        somethingFound = true;
                    } else if (eolFoundOnEof) {
                        indexOfFirstValue = buffer.length();
                        somethingFound = true;
                    }
                }
            }

            if (somethingFound)
                return buffer.substring(0, indexOfFirstValue).toString();
            else
                return "";
        }
    }

}
