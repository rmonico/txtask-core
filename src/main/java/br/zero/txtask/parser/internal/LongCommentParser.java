package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

import static br.zero.txtask.parser.internal.Constants.LONG_COMMENT_MARK;
import static java.lang.System.lineSeparator;

class LongCommentParser {

    private static LongCommentParser instance = new LongCommentParser();

    static LongCommentParser longCommentParser() {
        return instance;
    }


    private String markString(String identString) {
        return identString + LONG_COMMENT_MARK + lineSeparator();
    }

    boolean matches(ParserReader reader, String identString) throws IOException {
        return reader.followed().by(markString(identString)).go();
    }

    void parse(ParserReader reader, Consumer<String> consumer, String identString) throws IOException {
        consumeInitialMarkString(reader, identString);

        StringBuilder commentBuilder = consumeCommentContents(reader, identString);

        consumer.accept(commentBuilder.toString());

        consumeFinalMarkString(reader);
    }

    private StringBuilder consumeCommentContents(ParserReader reader, String identString) throws IOException {
        StringBuilder commentBuilder = new StringBuilder();

        String[] endMarkStrings = {identString + LONG_COMMENT_MARK, identString + LONG_COMMENT_MARK + lineSeparator()};

        while (!endMarkFound(reader, endMarkStrings)) {
            if (commentBuilder.length() > 0)
                commentBuilder.append(lineSeparator());

            reader.consume().next(identString.length()).go();

            String commentLine = reader.consume().eol().go();

            reader.consume().next(lineSeparator().length()).go();

            commentBuilder.append(commentLine);
        }

        return commentBuilder;
    }

    private void consumeInitialMarkString(ParserReader reader, String identString) throws IOException {
        String markString = markString(identString);

        String consumed = reader.consume().next(markString.length()).go();

        assert markString.equals(consumed) : "Consuming a long comment where there is no long comment";
    }

    private void consumeFinalMarkString(ParserReader reader) throws IOException {
        reader.consume().eol().go();
    }

    private boolean endMarkFound(ParserReader reader, String... endMarkStrings) throws IOException {
        return reader.followed().byAnyOf(endMarkStrings).go();
    }
}
