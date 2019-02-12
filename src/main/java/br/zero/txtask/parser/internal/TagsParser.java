package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Tag;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class TagsParser {

    private static TagsParser instance = new TagsParser();

    static void parse(ParserReader reader, Consumer<Tag> consumer, String prefix) throws IOException {
        instance.internalParse(reader, consumer, prefix);
    }

    void internalParse(ParserReader reader, Consumer<Tag> consumer, String prefix) throws IOException {
        List<Tag> tagList = new ArrayList<>();

        // TODO Remove this variable if not used anymore
        int prefixIndex;
        while ((prefixIndex = reader.followed().by(prefix).which()) > -1) {
            reader.consume().next(prefix.length()).go();

            Tag tag = new Tag();

            String tagName = reader.consume().until(prefix).or().eol().go();

            tag.setName(tagName);

            consumer.accept(tag);
        }

    }

}
