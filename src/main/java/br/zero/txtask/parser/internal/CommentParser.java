package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

import static br.zero.txtask.parser.internal.Constants.SINGLE_LINE_COMMENT_MARK;

class CommentParser {

    private static CommentParser instance = new CommentParser();

    static CommentParser commentParser() {
        return instance;
    }

    void parse(ParserReader reader, Consumer<String> consumer) throws IOException {
        if (!this.matches(reader))
            return;

        reader.consume().next(SINGLE_LINE_COMMENT_MARK.length()).go();

        String commentContents = reader.consume().eol().go();

        consumer.accept(commentContents);
    }

    private boolean matches(ParserReader reader) throws IOException {
        return reader.followed().by(SINGLE_LINE_COMMENT_MARK).go();
    }
}
