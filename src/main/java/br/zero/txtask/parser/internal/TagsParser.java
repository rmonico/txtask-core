package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Tag;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

class TagsParser {

    private static TagsParser instance = new TagsParser();

    static TagsParser tagsParser() {
        return instance;
    }

    void parse(ParserReader reader, Consumer<Tag> consumer, String prefix) throws IOException {
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
