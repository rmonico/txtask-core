package br.zero.txtask.core.parser.element.taggroup;

import java.io.IOException;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.parser.AbstractScope;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.Scope;
import br.zero.txtask.core.parser.reader.ParserReader;

public class TagGroupScope extends AbstractScope<Tag> {

    public TagGroupScope() {
        setMatcher(new TagGroupMatcher());
        setParser(new TagGroupParser());
    }

    @Override
    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean done() {
        return true;
    }
}
