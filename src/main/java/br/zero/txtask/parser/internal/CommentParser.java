package br.zero.txtask.parser.internal;

import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

import static br.zero.txtask.parser.internal.LongCommentParser.longCommentParser;
import static br.zero.txtask.parser.internal.ParserUtilities.identString;
import static br.zero.txtask.parser.internal.ShortCommentParser.shortCommentParser;

class CommentParser {

    private static CommentParser instance = new CommentParser();

    static CommentParser commentParser() {
        return instance;
    }

    void parse(ParserReader reader, Consumer<String> consumer, int identLevel) throws IOException {
        String identString = identString(identLevel);

        if (shortCommentParser().matches(reader, identString))
            shortCommentParser().parse(reader, consumer, identString);
        else if (longCommentParser().matches(reader, identString))
            longCommentParser().parse(reader, consumer, identString);
    }

}
