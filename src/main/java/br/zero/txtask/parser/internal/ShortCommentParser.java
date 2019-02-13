package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

import static br.zero.txtask.parser.internal.Constants.SHORT_COMMENT_MARK;

class ShortCommentParser {

    private static final ShortCommentParser instance = new ShortCommentParser();

    static ShortCommentParser shortCommentParser() {
        return instance;
    }

    boolean matches(ParserReader reader, String identString) throws IOException {
        return reader.followed().by(identString + SHORT_COMMENT_MARK).go();
    }

    void parse(ParserReader reader, Consumer<String> consumer, String identString) throws IOException {
        String markString = identString + SHORT_COMMENT_MARK;

        String consumed = reader.consume().next(markString.length()).go();

        assert markString.equals(consumed) : "Consuming a short comment where there is no short comment";

        String commentContents = reader.consume().eol().go();

        consumer.accept(commentContents);
    }
}
