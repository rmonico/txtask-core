package br.zero.txtask.core.parser.element.taggroup;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

import java.util.List;

import static br.zero.txtask.core.parser.element.taggroup.Constants.IMPLICIT_TAG_INSERTION_INITIAL_PREFIX;

public class TagGroupScope extends AbstractScope<List<Tag>> {

    public TagGroupScope(String... prefixes) {
        assert prefixes.length > 0 : "At least one prefix must be passed";

        setMatcher(new TagGroupMatcher(prefixes[0]));
        setParser(new TagGroupParser(prefixes));
    }

    @Override
    public Scope<?> findScope(ParserReader reader) {
        return this;
    }

    @Override
    public boolean done() {
        return true;
    }
}
