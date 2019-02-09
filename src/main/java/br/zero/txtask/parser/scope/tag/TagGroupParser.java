package br.zero.txtask.parser.scope.tag;

import br.zero.txtask.model.Tag;
import br.zero.txtask.parser.scope.ElementParser;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TagGroupParser implements ElementParser<List<Tag>> {

    private final String[] prefixes;

    public TagGroupParser(String... prefixes) {
        this.prefixes = prefixes;
    }

    @Override
    public List<Tag> parse(ParserReader reader) throws IOException {
        List<Tag> tagList = new ArrayList<>();

        int prefixIndex;
        while ((prefixIndex = reader.followed().byAnyOf(this.prefixes).which()) > -1) {
            reader.consume().next(this.prefixes[prefixIndex].length()).go();

            Tag tag = new Tag();

            String tagName = reader.consume().until(this.prefixes).or().eol().go();

            tag.setName(tagName);

            tagList.add(tag);
        }

        return tagList;
    }

}
