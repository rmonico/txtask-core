package br.zero.txtask.core.parser.element.abstracttag;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.reader.ParserReader;

public abstract class AbstractTagMatcher implements ElementMatcher {

    private String tagPrefix;

    public AbstractTagMatcher(String tagPrefix) {
        this.tagPrefix = tagPrefix;
    }

    @Override
    public boolean matchs(ParserReader reader) throws ParserException, IOException {
        return reader.followed().by(this.tagPrefix).go();
    }

}
