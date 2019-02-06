package br.zero.txtask.core.parser.element.abstracttag;

import java.io.IOException;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public abstract class AbstractTagMatcher implements ElementMatcher<Tag> {

    private ElementParser<Tag> parser;
    private String tagPrefix;

    public AbstractTagMatcher(String tagPrefix) {
        this.tagPrefix = tagPrefix;
        this.parser = makeParser();
    }

    protected abstract ElementParser<Tag> makeParser();

    @Override
    public boolean matchs(ParserReader reader,
            ParserContext context) throws ParserException, IOException {
        return reader.followed().by(this.tagPrefix).go();
    }

    @Override
    public ElementParser<Tag> getParser() {
        return this.parser;
    }

}
