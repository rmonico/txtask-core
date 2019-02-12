package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

import static br.zero.txtask.parser.internal.Constants.SINGLE_LINE_COMMENT_MARK;
import static br.zero.txtask.parser.internal.ParserUtilities.identString;

class CommentParser {

    private static CommentParser instance = new CommentParser();

    static CommentParser commentParser() {
        return instance;
    }

    void parse(ParserReader reader, Consumer<String> consumer, int identLevel) throws IOException {
        String markString = identString(identLevel) + SINGLE_LINE_COMMENT_MARK;

        if (!this.matches(reader, markString))
            return;

        reader.consume().next(markString.length()).go();

        String commentContents = reader.consume().eol().go();

        consumer.accept(commentContents);
    }

    private boolean matches(ParserReader reader, String markString) throws IOException {
        return reader.followed().by(markString).go();
    }
}
